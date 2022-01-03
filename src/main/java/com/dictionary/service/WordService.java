package com.dictionary.service;

import com.dictionary.security.payload.request.word.CreateWordRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WordService {

    /**
     * Creates the word and his translations for selected languages
     *
     * @param createWordRequest to create word for language
     * @param authentication to get user in system
     */
    void create(CreateWordRequest createWordRequest, Authentication authentication);

    /**
     * Translates the word in two directions
     * Ex: Привіт (ua) -> Hello (us)
     *     Hello (us) -> Привіт (ua)
     *
     * @param word to translate
     * @param languageFrom language code from which it is translated Ex: us, ua, pl
     * @param languageTo language code to which it is translated Ex: us, ua, pl
     * @return List of translated words found
     */
    List<String> translateWord(String word, String languageFrom, String languageTo);
}
