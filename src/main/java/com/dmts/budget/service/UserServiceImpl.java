package com.dmts.budget.service;

import com.dmts.budget.entity.User;
import com.dmts.budget.repository.RoleRepository;
import com.dmts.budget.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final int PAGE_SIZE = 2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @PreAuthorize("!#user.password.contains('?')")
    public boolean save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByRoleName("User"));
        userRepository.save(user);
        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll(int page) {
        return userRepository.findAll(PageRequest.of(--page, PAGE_SIZE)).getContent();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
