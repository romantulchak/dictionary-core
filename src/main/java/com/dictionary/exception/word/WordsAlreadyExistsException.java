package com.dictionary.exception.word;

import java.util.Set;

public class WordsAlreadyExistsException extends RuntimeException {

    private final Set<String> words;

    public WordsAlreadyExistsException(Set<String> words){
        super("Words already exists");
        this.words = words;
    }

    public Set<String> getWords() {
        return words;
    }
}
