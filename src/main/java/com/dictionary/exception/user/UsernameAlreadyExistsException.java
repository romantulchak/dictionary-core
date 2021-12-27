package com.dictionary.exception.user;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super(String.format("User with name %s already exists", username));
    }

}
