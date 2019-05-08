package com.dmts.budget.service;

import com.dmts.budget.entity.User;
import com.dmts.budget.repository.RoleRepository;
import com.dmts.budget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String execute(Connection<?> connection) {
        Facebook facebook = ((Connection<Facebook>) connection).getApi();
        String [] fields = { "id", "email",  "first_name", "last_name" };
        org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
        String email = userProfile.getEmail();
        User user = userService.findByEmail(email);
        if(user == null) {
            user = new User();
            user.setUsername(connection.getDisplayName());
            user.setPassword(String.valueOf(email));
            user.setEmail(email);
            user.setRole(roleRepository.findByRoleName("User"));
            userService.save(user);
        }
        return user.getUsername();
    }
}