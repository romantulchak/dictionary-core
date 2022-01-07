package com.dictionary.service.impl;

import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordTranslationNotFoundException;
import com.dictionary.exception.word.WordsAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.model.User;
import com.dictionary.model.Word;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.WordRepository;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.WordService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void create(CreateWordRequest createWordRequest, Authentication authentication) {
        Set<String> existingWords = new HashSet<>();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = new User().setId(userDetails.getId());
        Language language = languageRepository.findByCode(createWordRequest.getCode())
                .orElseThrow((() -> new LanguageNotFoundException(createWordRequest.getCode())));
        List<Word> words = new ArrayList<>();
        String key = UUID.randomUUID().toString();
        for (String word : createWordRequest.getWords()) {
            if (wordRepository.existsByNameAndLanguageCode(word, createWordRequest.getCode())) {
                existingWords.add(word);
            } else {
                words.add(initWord(user, key, language, word));
            }
        }
        if (!existingWords.isEmpty()) {
            throw new WordsAlreadyExistsException(existingWords);
        }
        createWordRequest.getLanguagesTo().forEach(translateTo -> {
            Language translatedLanguage = languageRepository.findByCode(translateTo.getCode())
                    .orElseThrow((() -> new LanguageNotFoundException(translateTo.getCode())));
             words.addAll(createTranslatedWord(user, key, translateTo.getWords(), translatedLanguage));
        });
        if (!words.isEmpty()){
            wordRepository.saveAll(words);
        }
    }

    /**
     * Init word via call constructor
     *
     * @param user     who create this word
     * @param key      for word
     * @param language for word
     * @param word     name of word
     * @return initialized word
     */
    private Word initWord(User user, String key, Language language, String word) {
        return new Word(word, user, language, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> translateWord(String word, String languageFrom, String languageTo) {
        Word keyProjection = getWordByNameAndCode(word, languageFrom).orElseThrow(WordTranslationNotFoundException::new);
        return wordRepository.findByKeysInAndLanguageCode(keyProjection.getKey(), languageTo)
                .stream()
                .map(translatedWord -> getNameByRegister(word, translatedWord))
                .toList();
    }

    /**
     * Finds word by its name and language code
     *
     * @param name of word
     * @param languageCode  code of language
     * @return found word
     */
    private Optional<Word> getWordByNameAndCode(String name, String languageCode) {
        String wordInCapitalRegister = StringUtils.capitalize(name.toLowerCase());
        return wordRepository.findByCapitalNameAndLanguageCode(wordInCapitalRegister, languageCode);
    }

    /**
     * Checks which case the word is in
     *
     * @param word           from client
     * @param translatedWord word after translation
     * @return word in the correct case since came from the client
     * Ex: Привіт (ua) -> Hello (us)
     * ПРИВІТ (ua) -> HELLO (us)
     * привіт (ua) -> hello (us)
     */
    private String getNameByRegister(String word, Word translatedWord) {
        if (StringUtils.isAllUpperCase(word)) {
            return translatedWord.getUppercaseName();
        } else if (StringUtils.isAllLowerCase(word)) {
            return translatedWord.getLowercaseName();
        } else {
            return translatedWord.getCapitalName();
        }
    }


    /**
     * Checks if Word exists for translated language if yes
     * gets keys for this word and add current key otherwise init word
     * and att it to list
     *
     * @param user to set word creator
     * @param key to set word key
     * @param translatedWords from client
     * @param translatedLanguage for getting language code
     * @return list with created words otherwise empty list
     */
    private List<Word> createTranslatedWord(User user, String key, Set<String> translatedWords, Language translatedLanguage) {
        List<Word> words = new ArrayList<>();
        translatedWords.forEach(translatedWord -> {
            Optional<Word> foundedWord = getWordByNameAndCode(translatedWord, translatedLanguage.getCode());
            if (foundedWord.isPresent()) {
                Word word = foundedWord.get();
                word.getKeys().add(key);
                wordRepository.save(word);
            }else{
                words.add(initWord(user, key, translatedLanguage, translatedWord));
            }
        });
        return words;
    }
}
