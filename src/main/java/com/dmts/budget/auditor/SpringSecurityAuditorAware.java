package com.dmts.budget.auditor;

import com.dmts.budget.entity.User;
import com.dmts.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<User> {

    @Autowired
    UserService userService;

    @Override
    public Optional<User> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return Optional.of(userService.findById(Long.parseLong(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername())));
    }
}