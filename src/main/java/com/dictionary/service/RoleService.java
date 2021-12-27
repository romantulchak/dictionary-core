package com.dictionary.service;

import com.dictionary.exception.role.RoleNotFoundException;
import com.dictionary.model.Role;
import com.dictionary.model.type.RoleType;

public interface RoleService {

    Role findRoleByType(RoleType roleType) throws RoleNotFoundException;

}
