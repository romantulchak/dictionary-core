package com.dictionary.controller;

import com.dictionary.dto.user.UserDTO;
import com.dictionary.model.View;
import com.dictionary.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.UserView.class)
    public UserDTO getUserInformation(Authentication authentication){
        return userService.getUserInformation(authentication);
    }

}
