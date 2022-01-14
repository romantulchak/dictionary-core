package com.dictionary.component.access;

import com.dictionary.repository.LanguageRepository;
import com.dictionary.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccessToLanguage {

    private LanguageRepository languageRepository;

    public boolean hasAccess(Authentication authentication, long id){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return languageRepository.existsByIdAndUserId(id, userDetails.getId());
    }
}
