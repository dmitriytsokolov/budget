package com.dmts.budget.service;

import com.dmts.budget.dto.UserDto;
import com.dmts.budget.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<User> findAll(Pageable page);

    List<User> findAll();

    User findByUsername(String username);

    User findById(long id);

    User findByEmail(String email);

    boolean save(User user);

    UserDto findByEmailQuery(String email);

    List<User> findAllByExample(Example<User> example);
}