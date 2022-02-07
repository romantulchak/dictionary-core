package com.dictionary.controller;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.model.View;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import com.dictionary.service.LanguageService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public void create(@Valid @RequestBody CreateLanguageRequest createLanguageRequest, Authentication authentication) {
        languageService.create(createLanguageRequest, authentication);
    }

    @GetMapping("/all")
    @JsonView(View.LanguageView.class)
    public List<LanguageDTO> findAllLanguages() {
        return languageService.findAllLanguages();
    }

    @GetMapping("/languages-for-panel")
    @PreAuthorize("isAuthenticated()")
    public List<LanguageDTO> findLanguagesWithPrivileges(@RequestParam(value = "page", defaultValue = "0") String page,
                                                         @RequestParam(value = "size", defaultValue = "10") String size,
                                                         Authentication authentication) {
        return languageService.findLanguagesForProfile(page, size, authentication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && (hasRole('MODERATOR') || hasRole('ADMIN') || @userAccessToLanguage.hasAccess(authentication, #id))")
    public void delete(@PathVariable("id") long id) {
        languageService.delete(id);
    }

    @GetMapping("/total-pages")
    @PreAuthorize("isAuthenticated()")
    public long totalPagesCount(@RequestParam(value = "size", defaultValue = "10") String size){
        return languageService.getTotalPagesCount(size);
    }
}
