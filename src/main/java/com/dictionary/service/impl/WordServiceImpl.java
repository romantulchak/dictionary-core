package com.dictionary.service.impl;

import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.exception.word.WordAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.model.User;
import com.dictionary.model.Word;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.WordRepository;
import com.dictionary.security.payload.request.word.CreateWordRequest;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CreateWordRequest createWordRequest, Authentication authentication) {
        if (wordRepository.existsByNameAndLanguageCode(createWordRequest.getName(), createWordRequest.getCode())) {
            throw new WordAlreadyExistsException(createWordRequest.getName());
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = new User().setId(userDetails.getId());
        Language language = languageRepository.findByCode(createWordRequest.getCode())
                .orElseThrow((() -> new LanguageNotFoundException(createWordRequest.getCode())));
        Word word = new Word(createWordRequest.getName(), user, language);
        wordRepository.save(word);
    }
}
