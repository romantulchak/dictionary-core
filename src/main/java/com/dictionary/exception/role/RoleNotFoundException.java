package com.dictionary.exception.role;

import com.dictionary.model.type.RoleType;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(RoleType roleType) {
        super(String.format("Role %s not found", roleType.name()));
    }
}
