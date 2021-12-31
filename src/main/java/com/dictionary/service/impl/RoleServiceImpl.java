package com.dictionary.service.impl;

import com.dictionary.exception.role.RoleNotFoundException;
import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;
import com.dictionary.repository.RoleRepository;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findRoleByType(RoleType roleType) throws RoleNotFoundException {
        return roleRepository.findByName(roleType)
                .orElseThrow(() -> new RoleNotFoundException(roleType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> findRolesForUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return roleRepository.findAllByUsersId(userDetails.getId())
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

}
