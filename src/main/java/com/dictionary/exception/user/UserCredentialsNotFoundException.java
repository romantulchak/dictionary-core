package com.dictionary.exception.user;

public class UserCredentialsNotFoundException extends RuntimeException {
    public UserCredentialsNotFoundException(){
        super("User credentials not found");
    }
}
