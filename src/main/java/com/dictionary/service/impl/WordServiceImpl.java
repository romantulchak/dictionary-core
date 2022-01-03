package com.dictionary.service.impl;

import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordAlreadyExistsException;
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

import java.util.ArrayList;
import java.util.List;

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
        if (wordRepository.existsByNameAndLanguageCode(createWordRequest.getWord(), createWordRequest.getCode())) {
            throw new WordAlreadyExistsException(createWordRequest.getWord());
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = new User().setId(userDetails.getId());
        Language language = languageRepository.findByCode(createWordRequest.getCode())
                .orElseThrow((() -> new LanguageNotFoundException(createWordRequest.getCode())));
        List<Word> words = new ArrayList<>();
        String key = KeyEncoder.encodeKey(createWordRequest.getWord());
        Word mainWord = new Word(createWordRequest.getWord(), user, language, key);
        createWordRequest.getLanguagesTo().forEach(translateTo -> {
            Language translatedLanguage = languageRepository.findByCode(translateTo.getCode())
                    .orElseThrow((() -> new LanguageNotFoundException(translateTo.getCode())));
            Word translatedWord = new Word(translateTo.getWord(), user, translatedLanguage, key);
            words.add(translatedWord);
        });
        words.add(mainWord);
        wordRepository.saveAll(words);
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
     * @param word from client
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
