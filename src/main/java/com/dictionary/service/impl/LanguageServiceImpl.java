package com.dictionary.service.impl;

import com.dictionary.component.Transformer;
import com.dictionary.dto.LanguageDTO;
import com.dictionary.exception.language.LanguageAlreadyExistsException;
import com.dictionary.model.Language;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.UserRepository;
import com.dictionary.security.payload.request.language.CreateLanguageRequest;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.LanguageService;
import com.dictionary.utility.PageableUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final Transformer transformer;

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(CreateLanguageRequest createLanguageRequest, Authentication authentication) throws LanguageAlreadyExistsException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails != null){
            if (languageRepository.existsByName(createLanguageRequest.getName())){
                throw new LanguageAlreadyExistsException(createLanguageRequest.getName());
            }
            Language language = new Language(createLanguageRequest.getName(), createLanguageRequest.getCode(), userDetails.getId());
            languageRepository.save(language);
        }else {
            throw new UsernameNotFoundException("User not found exception");
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LanguageDTO> findLanguagesForProfile(String page, String size, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(PageableUtil.getPage(page), PageableUtil.getPageSize(size));
        Long preferredLanguageId = userRepository.findUserPreferredLanguage(userDetails.getUsername());
        return languageRepository.findAllWithUserId(pageable).getContent()
                .stream()
                .map(language -> transformer.languageToDTO(language, userDetails, preferredLanguageId))
                .sorted()
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalPagesCount(String size, boolean isForUser, Authentication authentication) {
        int pageSize = PageableUtil.getPageSize(size);
        long totalElements;
        if (isForUser){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            totalElements = languageRepository.countLanguageByUserId(userDetails.getId());
        }else{
            totalElements = languageRepository.countAllBy();
        }
        if (totalElements <= pageSize){
            return 1;
        }
        return (long) Math.ceil(totalElements / (double) pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LanguageDTO> findUserLanguages(String page, String size, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(PageableUtil.getPage(page), PageableUtil.getPageSize(size));
        return languageRepository.findAllByUserId(userDetails.getId(), pageable).getContent()
                .stream()
                .map(transformer::languageToDTOWithDefaultPrivileges)
                .sorted()
                .toList();
    }
}
