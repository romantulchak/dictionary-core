package com.dictionary.exception.file;

public class FolderCreationException extends RuntimeException{
    public FolderCreationException() {
        super("Something went wrong during default folder creation");
    }
}
