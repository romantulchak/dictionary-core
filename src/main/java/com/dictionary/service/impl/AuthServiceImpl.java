package com.dictionary.service.impl;

import com.dictionary.dto.JwtDTO;
import com.dictionary.exception.user.EmailAlreadyExistsException;
import com.dictionary.exception.user.UsernameAlreadyExistsException;
import com.dictionary.model.Role;
import com.dictionary.model.User;
import com.dictionary.model.type.RoleType;
import com.dictionary.repository.UserRepository;
import com.dictionary.security.jwt.JwtUtils;
import com.dictionary.security.payload.request.LoginRequest;
import com.dictionary.security.payload.request.SignupRequest;
import com.dictionary.security.service.UserDetailsImpl;
import com.dictionary.service.AuthService;
import com.dictionary.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    /**
     * @inheritDoc
     */
    @Override
    public JwtDTO authenticateUser(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = jwtUtils.generateJwtToken(authenticate);
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        return new JwtDTO(userDetails.getId(), jwt, userDetails.getUsername(), userDetails.getEmail());
    }

    @Override
    public JwtDTO registerUser(SignupRequest signupRequest) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(signupRequest.getUsername());
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException(signupRequest.getEmail());
        }
        String encodePassword = encoder.encode(signupRequest.getPassword());
        Set<Role> roles = Set.of(roleService.findRoleByType(RoleType.ROLE_USER));
        User user = new User()
                .setFirstName(signupRequest.getFirstName())
                .setLastName(signupRequest.getLastName())
                .setUsername(signupRequest.getUsername())
                .setEmail(signupRequest.getEmail())
                .setPassword(encodePassword)
                .setRoles(roles);
        userRepository.save(user);
        LoginRequest loginRequest = new LoginRequest(signupRequest.getUsername(), signupRequest.getPassword());
        return authenticateUser(loginRequest);
    }
}
