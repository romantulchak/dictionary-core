package com.dictionary.service;

import java.util.List;

public interface AdminService {

    /**
     * Finds all roles in system
     *
     * @return all roles in system
     */
    List<String> findAllRoles();

}
