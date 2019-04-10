package com.dmts.budget.service;

import com.dmts.budget.entity.Role;

public interface RoleService {

    Role findByRoleName(String roleName);
}