package com.dictionary.controller;

import com.dictionary.model.View;
import com.dictionary.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping(value = "/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/user-roles")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.RoleView.class)
    public Set<String> findRolesForUser(Authentication authentication){
        return roleService.findRolesForUser(authentication);
    }

}
