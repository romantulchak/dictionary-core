package com.dictionary.service;

import com.dictionary.dto.user.UserDTO;
import org.springframework.security.core.Authentication;

public interface UserService {

    /**
     * Gets information about user in system
     *
     * @param authentication to get current user in system
     * @return user data transfer object to be shown on client
     */
    UserDTO getUserInformation(Authentication authentication);
}
