package com.dictionary.security.service;

import com.dictionary.model.User;
import com.dictionary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Finds user by his username and return {@link UserDetailsImpl}
     *
     * @param username for finding user by his username
     * @return details about user from {@link UserDetailsImpl}
     * @throws UsernameNotFoundException if user with username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
        return UserDetailsImpl.build(user);
    }
}
