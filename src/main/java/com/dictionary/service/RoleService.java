package com.dictionary.service;

import com.dictionary.exception.role.RoleNotFoundException;
import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface RoleService {

    /**
     * Finds role by its type and return otherwise
     * throw exception
     *
     * @param roleType to find role by its type
     * @return role by RoleType
     * @throws RoleNotFoundException if role not found
     */
    Role findRoleByType(RoleType roleType) throws RoleNotFoundException;

    /**
     * Finds all available roles for user
     *
     * @param authentication to get current user id in system
     * @return available roles for user
     */
    Set<String> findRolesForUser(Authentication authentication);
}
