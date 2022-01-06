package com.dictionary.service.impl;

import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordsAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.model.User;
import com.dictionary.model.Word;
import com.dictionary.projection.WordKeyProjection;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.WordRepository;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.WordService;
import com.dictionary.utility.KeyEncoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    /**
     * {@inheritDoc}
     */
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
        createWordRequest.getLanguagesTo().forEach(translateTo -> {
            Language translatedLanguage = languageRepository.findByCode(translateTo.getCode())
                    .orElseThrow((() -> new LanguageNotFoundException(translateTo.getCode())));
            translateTo.getWords().forEach(word -> {
                words.add(initWord(user, key, translatedLanguage, word));
            });
        });
        if (!existingWords.isEmpty()) {
            throw new WordsAlreadyExistsException(existingWords);
        }
        wordRepository.saveAll(words);
    }

    /**
     * Init word via call constructor
     *
     * @param user who create this word
     * @param key for word
     * @param language for word
     * @param word name of word
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
        String wordInCapitalRegister = StringUtils.capitalize(word.toLowerCase());
        WordKeyProjection keyProjection = wordRepository.findByCapitalNameAndLanguageCode(wordInCapitalRegister, languageFrom)
                .orElse(() -> KeyEncoder.encodeKey(word));
        return wordRepository.findByKeyAndLanguageCode(keyProjection.getKey(), languageTo)
                .stream()
                .map(translatedWord -> getNameByRegister(word, translatedWord))
                .toList();
    }

    /**
     * Checks which case the word is in
     *
     * @param word           from client
     * @param translatedWord word after translation
     * @return word in the correct case since came from the client
     *         Ex: Привіт (ua) -> Hello (us)
     *             ПРИВІТ (ua) -> HELLO (us)
     *             привіт (ua) -> hello (us)
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
}
