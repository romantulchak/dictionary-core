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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final Transformer transformer;
    private final WebClient webClient;

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
            webClient.post()
                    .uri("/language/create")
                    .body(Mono.just(createLanguageRequest), CreateLanguageRequest.class)
                    .retrieve()
                    .bodyToMono(CreateLanguageRequest.class)
                    .block();
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
        Long preferredLanguageId = userRepository.findUserPreferredLanguageId(userDetails.getUsername());
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
        Long userPreferredLanguageId = userRepository.findUserPreferredLanguageId(userDetails.getUsername());
        return languageRepository.findAllByUserId(userDetails.getId(), pageable).getContent()
                .stream()
                .map(language -> transformer.languageToDTOWithDefaultPrivileges(language, userPreferredLanguageId))
                .sorted()
                .toList();
    }

    @Override
    public List<LanguageDTO> findLanguagesWithPreferred(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userPreferredLanguageId = userRepository.findUserPreferredLanguageId(userDetails.getUsername());
        List<Language> languages = languageRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        if (userPreferredLanguageId != null){
            OptionalInt preferredLanguageIndex = IntStream.range(0, languages.size())
                    .filter(value -> userPreferredLanguageId == languages.get(value).getId())
                    .findFirst();
            if (preferredLanguageIndex.isPresent()) {
                Language preferredLanguage = languages.remove(preferredLanguageIndex.getAsInt());
                languages.add(0, preferredLanguage);
            }
        }
        return languages.stream()
                .map(transformer::languageToDTO)
                .toList();
    }
}
