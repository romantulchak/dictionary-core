package com.dictionary.service.impl;

import com.dictionary.component.Transformer;
import com.dictionary.dto.WordDTO;
import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordTranslationNotFoundException;
import com.dictionary.exception.word.WordsAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.model.User;
import com.dictionary.model.word.Word;
import com.dictionary.projection.WordKeyProjection;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.WordRepository;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.security.payload.request.word.WordDescription;
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
    private final Transformer transformer;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void create(CreateWordRequest createWordRequest, Authentication authentication) throws LanguageNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = new User().setId(userDetails.getId());
        String key = UUID.randomUUID().toString();
        List<Word> words = new ArrayList<>(checkAndInitWordSToTranslate(createWordRequest, user, key));
        createWordRequest.getLanguagesTo().forEach(translateTo -> {
            Language translatedLanguage = languageRepository.findByCode(translateTo.getCode())
                    .orElseThrow((() -> new LanguageNotFoundException(translateTo.getCode())));
            words.addAll(createTranslatedWord(user, key, translateTo.getWords(), translatedLanguage));
        });
        wordRepository.saveAll(words);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WordDTO> translateWord(String word, String languageFrom, String languageTo) throws WordTranslationNotFoundException {
        WordKeyProjection keyProjection = getWordByNameAndCode(word, languageFrom).orElseThrow(WordTranslationNotFoundException::new);
        return wordRepository.findByKeysInAndLanguageCode(keyProjection.getKey(), languageTo)
                .stream()
                .map(transformer::wordToDTO)
                .toList();
    }

    /**
     * @param createWordRequest to get words and language code
     * @param user              to get user in system
     * @param key               unique key for word to translate and translated words
     * @return words to be stored
     */
    private List<Word> checkAndInitWordSToTranslate(CreateWordRequest createWordRequest, User user, String key) throws WordsAlreadyExistsException {
        List<Word> words = new ArrayList<>();
        Language language = languageRepository.findByCode(createWordRequest.getCode())
                .orElseThrow((() -> new LanguageNotFoundException(createWordRequest.getCode())));
        Set<String> existingWords = new HashSet<>();
        for (WordDescription word : createWordRequest.getWords()) {
            if (wordRepository.existsByNameAndLanguageCode(word.getWord(), createWordRequest.getCode())) {
                existingWords.add(word.getWord());
            } else {
                words.add(initWord(user, key, language, word));
            }
        }
        if (!existingWords.isEmpty()) {
            throw new WordsAlreadyExistsException(existingWords);
        }
        return words;
    }

    /**
     * Init word via call constructor
     *
     * @param user            who create this word
     * @param key             for word
     * @param language        for word
     * @param wordDescription contains name and description of word
     * @return initialized word
     */
    private Word initWord(User user, String key, Language language, WordDescription wordDescription) {
        return new Word(wordDescription.getWord(), user, language, key, wordDescription.getDescription());
    }

    /**
     * Finds word by its name and language code
     *
     * @param name         of word
     * @param languageCode code of language
     * @return {@link WordKeyProjection}
     */
    private Optional<WordKeyProjection> getWordByNameAndCode(String name, String languageCode) {
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
     * @param user               to set word creator
     * @param key                to set word key
     * @param translatedWords    from client
     * @param translatedLanguage for getting language code
     * @return list with created words otherwise empty list
     */
    private List<Word> createTranslatedWord(User user, String key, Set<WordDescription> translatedWords, Language translatedLanguage) {
        List<Word> words = new ArrayList<>();
        translatedWords.forEach(translatedWord -> {
            Optional<Word> foundedWord = wordRepository.findWordKeys(translatedWord.getWord(), translatedLanguage.getCode());
            if (foundedWord.isPresent()) {
                Word word = foundedWord.get();
                word.getKeys().add(key);
                wordRepository.save(word);
            } else {
                words.add(initWord(user, key, translatedLanguage, translatedWord));
            }
        });
        return words;
    }
}
