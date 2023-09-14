package org.example.app.domain.user.controller;

import org.example.annotataion.Controller;
import org.example.app.domain.user.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void useService() {
        userService.doSomethingWithTransaction();
    }
}
