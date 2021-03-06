package com.dictionary.controller;

import com.dictionary.dto.WordDTO;
import com.dictionary.model.View;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.service.WordService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping(value = "/api/v1/word")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public void create(@RequestBody CreateWordRequest createWordRequest, Authentication authentication) {
        wordService.create(createWordRequest, authentication);
    }

    @GetMapping("/translate")
    public List<WordDTO> translateWord(@RequestParam(value = "word") String word,
                                       @RequestParam(value = "languageFrom") String languageFrom,
                                       @RequestParam(value = "languageTo") String languageTo) {
        return wordService.translateWord(word, languageFrom, languageTo);
    }

    @GetMapping("/for-user")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.WordView.class)
    public List<WordDTO> findWordsForUser(@RequestParam(value = "page", defaultValue = "0") String page,
                                          @RequestParam(value = "size", defaultValue = "10") String size,
                                          Authentication authentication){
        return wordService.findWordsForUser(page, size, authentication);
    }

    @GetMapping("/{id}/examples")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.WordView.class)
    public List<String> findWordExamples(@PathVariable("id") long id){
        return wordService.findExamplesByWordId(id);
    }

    @GetMapping("/by-letter/{letter}")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.WordView.class)
    public List<WordDTO> findWordByFirstLetterForUser(@PathVariable("letter") String letter,
                                                      @RequestParam(value = "page", defaultValue = "0") String page,
                                                      @RequestParam(value = "size", defaultValue = "10") String size,
                                                      Authentication authentication){
        return wordService.findWordByFirstLetterForUser(letter, page, size, authentication);
    }

    @GetMapping("/{languageCode}")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.WordView.class)
    public List<WordDTO> findWordsByLanguageCode(@PathVariable("languageCode") String languageCode,
                                                 @RequestParam(value = "page", defaultValue = "0") String page,
                                                 @RequestParam(value = "size", defaultValue = "10") String size){
        return wordService.findWordsByLanguage(languageCode, page, size);
    }

    @GetMapping("/top-words-by-language/{languageCode}")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.WordView.class)
    public List<WordDTO> findTopWordsByLanguage(@PathVariable("languageCode") String languageCode,
                                                @RequestParam(value = "page", defaultValue = "0") String page,
                                                @RequestParam(value = "size", defaultValue = "10") String size){
        return wordService.findTopWordsByLanguage(languageCode, page, size);
    }
}
