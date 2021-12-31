package com.dictionary.controller;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.model.View;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import com.dictionary.service.LanguageService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR') OR hasRole('ADMIN')")
    public void create(@Valid @RequestBody CreateLanguageRequest createLanguageRequest){
        languageService.create(createLanguageRequest);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.LanguageView.class)
    public List<LanguageDTO> findAllLanguages(){
        return languageService.findAllLanguages();
    }
}
