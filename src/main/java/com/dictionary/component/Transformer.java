package com.dictionary.component;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.dto.PrivilegesDTO;
import com.dictionary.dto.WordDTO;
import com.dictionary.dto.user.RoleDTO;
import com.dictionary.model.Language;
import com.dictionary.model.Role;
import com.dictionary.model.Word;
import com.dictionary.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public RoleDTO roleToDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }

    public LanguageDTO languageToDTO(Language language){
        return modelMapper.map(language, LanguageDTO.class);
    }

    public LanguageDTO languageToDTO(Language language, UserDetailsImpl userDetails){
        LanguageDTO languageDTO = modelMapper.map(language, LanguageDTO.class);
        boolean canModify = language.getUser().getId().equals(userDetails.getId());
        boolean canDelete = language.getUser().getId().equals(userDetails.getId()) || !userDetails.getAuthorities().isEmpty();
        PrivilegesDTO privilegesDTO = new PrivilegesDTO(canModify, canDelete);
        languageDTO.setPrivileges(privilegesDTO);
        return languageDTO;
    }

    public WordDTO wordToDTO(Word word){
        return modelMapper.map(word, WordDTO.class);
    }
}
