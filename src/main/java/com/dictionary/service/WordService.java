package com.dictionary.service;

import com.dictionary.security.payload.request.word.CreateWordRequest;
import org.springframework.security.core.Authentication;

public interface WordService {

    /**
     * Creates the word and his translations for selected languages
     *
     * @param createWordRequest to create word for language
     * @param authentication to get user in system
     */
    void create(CreateWordRequest createWordRequest, Authentication authentication);

}
