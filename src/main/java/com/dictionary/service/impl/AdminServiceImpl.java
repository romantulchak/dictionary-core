package com.dictionary.service.impl;

import com.dictionary.model.type.RoleType;
import com.dictionary.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public List<String> findAvailableRoles() {
        return RoleType.roles();
    }
}
