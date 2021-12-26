package com.dictionary.model.type;

public enum RoleType {
    ROLE_USER("User"),
    ROLE_MODERATOR("Moderator"),
    ROLE_ADMIN("Admin");

    private String value;

    RoleType(String value){
        this.value = value;
    }
}
