package com.dictionary.service;

import com.dictionary.exception.language.LanguageAlreadyExistsException;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;

public interface LanguageService {

    /**
     * Checks if the selected language does not exist and creates it
     * otherwise throw exception
     *
     * @param createLanguageRequest data to persist it to Language entity
     * @throws LanguageAlreadyExistsException if language already exists
     */
    void create(CreateLanguageRequest createLanguageRequest) throws LanguageAlreadyExistsException;

}
