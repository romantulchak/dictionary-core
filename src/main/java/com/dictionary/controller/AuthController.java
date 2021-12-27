package com.dictionary.controller;

import com.dictionary.dto.JwtDTO;
import com.dictionary.security.payload.request.LoginRequest;
import com.dictionary.security.payload.request.SignupRequest;
import com.dictionary.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * @inheritDoc
     */
    @PostMapping("/sign-in")
    public JwtDTO authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    /**
     * @inheritDoc
     */
    @PostMapping("/sign-up")
    public JwtDTO registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
