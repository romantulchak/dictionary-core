package com.dictionary.service;

import com.dictionary.dto.WordDTO;
import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordTranslationNotFoundException;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WordService {

    /**
     * Creates the words and them translations for selected languages
     *
     * @param createWordRequest to create words for language
     * @param authentication to get user in system
     */
    void create(CreateWordRequest createWordRequest, Authentication authentication) throws LanguageNotFoundException;

    /**
     * Translates the word in two directions
     * Ex: Привіт (ua) -> Hello (us)
     *     Hello (us) -> Привіт (ua)
     *
     * @param word to translate
     * @param languageFrom language code from which it is translated Ex: us, ua, pl
     * @param languageTo language code to which it is translated Ex: us, ua, pl
     * @return List of translated words found with description
     */
    List<WordDTO> translateWord(String word, String languageFrom, String languageTo) throws WordTranslationNotFoundException;

    /**
     * Finds words created by user in system
     *
     * @param page number of current page
     * @param size number of elements per page
     * @param authentication to get user id
     * @return words created by user
     */
    List<WordDTO> findWordsForUser(String page, String size, Authentication authentication);

    /**
     * Find examples for word
     *
     * @param id word id to find his examples
     * @return examples for word
     */
    List<String> findExamplesByWordId(long id);

    /**
     * Finds words by first letter
     *
     * @param letter find words that start with this letter
     * @param page number of current page
     * @param size number of elements per page
     * @param authentication to get user id
     * @return words which starts with letter selected by user
     */
    List<WordDTO> findWordByFirstLetterForUser(String letter, String page, String size, Authentication authentication);
}
