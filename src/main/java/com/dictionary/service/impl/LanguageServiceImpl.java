package com.dictionary.service.impl;

import com.dictionary.component.Transformer;
import com.dictionary.dto.LanguageDTO;
import com.dictionary.exception.language.LanguageAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import com.dictionary.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final Transformer transformer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CreateLanguageRequest createLanguageRequest) throws LanguageAlreadyExistsException {
        if (languageRepository.existsByName(createLanguageRequest.getName())){
            throw new LanguageAlreadyExistsException(createLanguageRequest.getName());
        }
        Language language = new Language(createLanguageRequest.getName(), createLanguageRequest.getCode());
        languageRepository.save(language);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LanguageDTO> findAllLanguages() {
        return languageRepository.findAll()
                .stream()
                .sorted()
                .map(transformer::languageToDTO)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id) {
        this.languageRepository.deleteById(id);
    }
}
