package com.dictionary.exception.language;

public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException(String code) {
        super(String.format("Language with code %s not found", code));
    }
}
