package com.dictionary.service.impl;

import com.dictionary.component.Transformer;
import com.dictionary.dto.user.UserDTO;
import com.dictionary.exception.language.LanguageNotFoundException;
import com.dictionary.model.Language;
import com.dictionary.model.User;
import com.dictionary.repository.LanguageRepository;
import com.dictionary.repository.UserRepository;
import com.dictionary.repository.WordRepository;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final LanguageRepository languageRepository;
    private final Transformer transformer;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserInformation(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.getById(userDetails.getId());
        long totalNumberOfWords = wordRepository.countWordByUserId(user.getId());
        long totalNumberOfLanguages = languageRepository.countLanguageByUserId(user.getId());
        return transformer.userToDTO(user, totalNumberOfLanguages, totalNumberOfWords);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguageToPreferred(String code, Authentication authentication) {
        Language language = languageRepository.findByCode(code)
                .orElseThrow(() -> new LanguageNotFoundException(code));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPreferredLanguage(language);
        userRepository.save(user);
    }
}
