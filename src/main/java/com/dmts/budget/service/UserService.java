package com.dmts.budget.service;

import com.dmts.budget.entity.User;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean save(User user);
}