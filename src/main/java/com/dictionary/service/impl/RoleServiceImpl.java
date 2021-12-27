package com.dictionary.service.impl;

import com.dictionary.exception.role.RoleNotFoundException;
import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;
import com.dictionary.repository.RoleRepository;
import com.dictionary.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByType(RoleType roleType) throws RoleNotFoundException {
        return roleRepository.findByName(roleType)
                .orElseThrow(() -> new RoleNotFoundException(roleType));
    }

}
