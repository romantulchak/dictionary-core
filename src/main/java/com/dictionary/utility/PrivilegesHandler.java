package com.dictionary.utility;

import com.dictionary.exception.user.UserCredentialsNotFoundException;
import com.dictionary.security.service.UserDetailsImpl;

import java.util.Set;

public class PrivilegesHandler {

    private PrivilegesHandler() {

    }

    /**
     * Checks if user is in selected role
     *
     * @param role to check
     * @param userDetails to get user roles
     * @return true if user has role otherwise false
     */
    public static boolean hasPrivilegesByRole(String role, UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
        }
        throw new UserCredentialsNotFoundException();
    }

    /**
     *Checks if user is in selected roles
     *
     * @param roles to check
     * @param userDetails to get user roles
     * @return true if user has at least one role from the list otherwise false
     */
    public static boolean hasPrivilegesByRoles(Set<String> roles, UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> roles.contains(grantedAuthority.getAuthority()));
        }
        throw new UserCredentialsNotFoundException();
    }

}
