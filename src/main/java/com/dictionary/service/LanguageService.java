package com.dictionary.service;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.exception.language.LanguageAlreadyExistsException;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LanguageService {

    /**
     * Checks if the selected language does not exist and creates it
     * otherwise throw exception
     *
     * @param createLanguageRequest data to persist it to Language entity
     * @throws LanguageAlreadyExistsException if language already exists
     */
    void create(CreateLanguageRequest createLanguageRequest, Authentication authentication) throws LanguageAlreadyExistsException;

    /**
     * Finds all languages
     *
     * @return all languages
     */
    List<LanguageDTO> findAllLanguages();

    /**
     * Delete language from DB
     *
     * @param id of language to delete
     */
    void delete(long id);

    /**
     * Finds languages with user access to modify/delete
     * and with preferred option for user in system
     *
     * @param page number of current page
     * @param size number of elements per page
     * @param authentication to check user access to language
     * @return languages with user access
     */
    List<LanguageDTO> findLanguagesForProfile(String page, String size, Authentication authentication);

    /**
     * Counts total pages by element size per page
     *
     * @param size number of elements per page
     * @param isForUser if true, only query for user-created languages, otherwise query all languages
     * @param authentication to get user in system
     * @return total number of pages
     */
    long getTotalPagesCount(String size, boolean isForUser, Authentication authentication);

    /**
     * Finds user languages with full access (modify/delete)
     *
     * @param page number of current page
     * @param size number of elements per page
     * @param authentication to get user id
     * @return user languages with full access
     */
    List<LanguageDTO> findUserLanguages(String page, String size, Authentication authentication);

    /**
     * Finds languages with preferred one
     *
     * @param authentication to get user id
     * @return languages with user preferred language
     */
    List<LanguageDTO> findLanguagesWithPreferred(Authentication authentication);
}
