package com.dictionary.model.type;

import java.util.Arrays;
import java.util.List;

public enum RoleType {
    ROLE_USER("User"),
    ROLE_MODERATOR("Moderator"),
    ROLE_ADMIN("Admin");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    /**
     * Gets all available roles
     *
     * @return all available roles
     */
    public static List<String> roles() {
        return Arrays.stream(RoleType.values())
                .map(String::valueOf)
                .toList();
    }
}
