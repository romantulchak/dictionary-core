package com.dictionary.exception.word;

public class WordTranslationNotFoundException extends RuntimeException {
    public WordTranslationNotFoundException(){
        super("Translated word not found");
    }
}
