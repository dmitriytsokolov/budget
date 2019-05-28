package com.dmts.budget.service;

import com.dmts.budget.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll(int page);

    List<User> findAll();

    User findByUsername(String username);

    User findById(int id);

    User findByEmail(String email);

    boolean save(User user);
}