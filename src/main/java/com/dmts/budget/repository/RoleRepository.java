package com.dmts.budget.repository;

import com.dmts.budget.entity.Role;
import com.dmts.budget.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

}
