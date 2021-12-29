package com.dictionary.component;

import com.dictionary.dto.user.RoleDTO;
import com.dictionary.model.Role;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public RoleDTO convertRoleToDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }
}
