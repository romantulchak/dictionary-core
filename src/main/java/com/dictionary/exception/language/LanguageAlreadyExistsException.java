package com.dictionary.exception.language;

public class LanguageAlreadyExistsException extends RuntimeException {
    public LanguageAlreadyExistsException(String language) {
        super(String.format("Language: %s already exists", language));
    }
}
