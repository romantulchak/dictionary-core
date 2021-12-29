package com.dictionary.controller;

import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import com.dictionary.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/create")
    public void create(@Valid @RequestBody CreateLanguageRequest createLanguageRequest){
        languageService.create(createLanguageRequest);
    }
}
