package com.dmts.budget.service;

import com.dmts.budget.entity.User;

public interface UserService {

    User findByUsername(String username);

    User findByFbUserId(long fbUserId);

    boolean save(User user);
}