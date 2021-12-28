package com.dictionary.controller;

import com.dictionary.model.View;
import com.dictionary.service.AdminService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('MODERATOR')")
    @JsonView(View.RoleView.class)
    public List<String> findAvailableRoles(){
        return adminService.findAvailableRoles();
    }
}
