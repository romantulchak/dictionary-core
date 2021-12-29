package com.dictionary.controller;

import com.dictionary.model.View;
import com.dictionary.service.RoleService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping(value = "/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/user-roles/{id}")
    @PreAuthorize("isAuthenticated()")
    @JsonView(View.RoleView.class)
    public Set<String> findRolesForUser(@PathVariable("id") UUID id){
        return roleService.findRolesForUser(id);
    }

}
