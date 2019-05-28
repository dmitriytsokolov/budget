package com.dmts.budget.controller;

import com.dmts.budget.entity.User;
import com.dmts.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class RestfulController {

    @Autowired
    private UserService service;

    @GetMapping("/user/{id}")
    @PreAuthorize("authentication.principal.username.equals(#id)")
    User getMyInfo(@PathVariable String id) {
        return service.findById(Integer.parseInt(id));
    }

    @GetMapping("/users/{page}")
    List<User> getAllUsers(@PathVariable @Min(1) int page) {
        return service.findAll(page);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return service.findAll();
    }

}
