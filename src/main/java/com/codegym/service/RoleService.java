package com.codegym.service;

import com.codegym.model.Role;

public interface RoleService {
    Iterable<Role> findAll();

    Role findById(Long id);

    void save(Role role);

    void remove(Long id);
}
