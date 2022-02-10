package com.dictionary.exception.file;

public class AudioCreationException extends RuntimeException {
    public AudioCreationException(){
        super("Something went wrong due creating audio for word");
    }
}
