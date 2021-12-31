package com.dictionary.controller;

import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping(value = "/api/v1/word")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public void create(@Valid @RequestBody CreateWordRequest createWordRequest, Authentication authentication){
        wordService.create(createWordRequest, authentication);
    }

}
