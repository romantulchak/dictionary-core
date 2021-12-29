package com.dictionary.service;

import com.dictionary.dto.JwtDTO;
import com.dictionary.exception.user.EmailAlreadyExistsException;
import com.dictionary.exception.user.UsernameAlreadyExistsException;
import com.dictionary.security.payload.request.auth.LoginRequest;
import com.dictionary.security.payload.request.auth.SignupRequest;

public interface AuthService {

    /**
     * Authenticate User by username and password
     *
     * @param loginRequest to authenticate user
     * @return jwt response with token and username
     */
    JwtDTO authenticateUser(LoginRequest loginRequest);

    /**
     * Checks if username and emails are available if no
     * register new user and authenticate otherwise throw exception
     *
     * @param signupRequest to register and authenticate user
     * @return jwt response with token and username
     */
    JwtDTO registerUser(SignupRequest signupRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;
}
