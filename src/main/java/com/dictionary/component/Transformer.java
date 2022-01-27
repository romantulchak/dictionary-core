package com.dictionary.component;

import com.dictionary.dto.LanguageDTO;
import com.dictionary.dto.PrivilegesDTO;
import com.dictionary.dto.WordDTO;
import com.dictionary.dto.user.RoleDTO;
import com.dictionary.model.Language;
import com.dictionary.model.Role;
import com.dictionary.model.Word;
import com.dictionary.model.type.RoleType;
import com.dictionary.projection.LanguageWithUserIdProjection;
import com.dictionary.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.dictionary.utility.PrivilegesHandler.hasPrivilegesByRole;
import static com.dictionary.utility.PrivilegesHandler.hasPrivilegesByRoles;

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

    /**
     * Converts language to DTO with Privileges
     *
     * @param language to convert
     * @param userDetails to get user roles
     * @return converted language to DTO
     */
    public LanguageDTO languageToDTO(LanguageWithUserIdProjection language, UserDetailsImpl userDetails){
        LanguageDTO languageDTO = modelMapper.map(language, LanguageDTO.class);
        boolean canModify = language.getUserId().equals(userDetails.getId()) || hasPrivilegesByRoles(Set.of(RoleType.ROLE_ADMIN.name(), RoleType.ROLE_MODERATOR.name()), userDetails);
        boolean canDelete = language.getUserId().equals(userDetails.getId()) || hasPrivilegesByRole(RoleType.ROLE_ADMIN.name(), userDetails);
        PrivilegesDTO privilegesDTO = new PrivilegesDTO(canModify, canDelete);
        languageDTO.setPrivileges(privilegesDTO);
        return languageDTO;
    }

    public WordDTO wordToDTO(Word word){
        return modelMapper.map(word, WordDTO.class);
    }
}
