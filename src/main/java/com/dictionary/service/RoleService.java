package com.dictionary.service;

import com.dictionary.exception.role.RoleNotFoundException;
import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;

import java.util.Set;
import java.util.UUID;

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
     * @param id user id
     * @return available roles for user
     */
    Set<String> findRolesForUser(UUID id);
}
