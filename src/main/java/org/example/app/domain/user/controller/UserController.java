package org.example.app.domain.user.controller;

import org.example.annotataion.Component;
import org.example.app.domain.user.service.UserService;

@Component
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
