package com.dmts.budget.eventhandlers;

import com.dmts.budget.entity.User;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(User.class)
public class EventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(User user) {
        System.out.println(String.format("Creating user with username=%s, email=%s and role=%s",
                user.getUsername(), user.getEmail(), user.getRole().getRoleName()));
    }

    @HandleAfterCreate
    public void handleAfterCreate(User user) {
        System.out.println(String.format("User with username=%s, email=%s and role=%s created successfully",
                user.getUsername(), user.getEmail(), user.getRole().getRoleName()));
    }

}
