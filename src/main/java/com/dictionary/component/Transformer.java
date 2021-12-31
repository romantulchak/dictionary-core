package com.dictionary.component;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.dto.user.RoleDTO;
import com.dictionary.model.Language;
import com.dictionary.model.Role;
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
}
