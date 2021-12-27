package com.dictionary.exception.user;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("User with email %s already registered", email));
    }
}
