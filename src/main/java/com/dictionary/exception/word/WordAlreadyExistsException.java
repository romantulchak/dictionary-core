package com.dictionary.exception.word;

public class WordAlreadyExistsException extends RuntimeException {
    public WordAlreadyExistsException(String name) {
        super(String.format("Word \"%s\" already exists", name));
    }
}
