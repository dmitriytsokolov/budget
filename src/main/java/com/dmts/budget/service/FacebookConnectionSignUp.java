package com.dmts.budget.service;

import com.dmts.budget.entity.User;
import com.dmts.budget.repository.RoleRepository;
import com.dmts.budget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String execute(Connection<?> connection) {
        long providerUserId = Long.valueOf(connection.getKey().getProviderUserId());
        User user = userRepository.findByFbUserId(providerUserId);
        if(user == null) {
            user = new User();
            user.setUsername(connection.getDisplayName());
            user.setPassword("");
            user.setRole(roleRepository.findByRoleName("User"));
            userRepository.save(user);
        }
        return user.getUsername();
    }
}