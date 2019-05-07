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
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String execute(Connection<?> connection) {
        long providerUserId = Long.valueOf(connection.getKey().getProviderUserId());
        User user = userService.findByFbUserId(providerUserId);
        if(user == null) {
            user = new User();
            user.setUsername(connection.getDisplayName());
            user.setPassword(String.valueOf(providerUserId));
            user.setFbUserId(providerUserId);
            user.setRole(roleRepository.findByRoleName("User"));
            userService.save(user);
        }
        return user.getUsername();
    }
}