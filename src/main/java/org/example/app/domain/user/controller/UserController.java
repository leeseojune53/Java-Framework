package org.example.app.domain.user.controller;

import org.example.annotataion.Controller;
import org.example.annotataion.Endpoint;
import org.example.app.domain.user.service.UserService;
import org.example.core.HttpMethod;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void useService() {
        userService.doSomethingWithTransaction();
    }

    // TODO make Tomcat server
    @Endpoint(url = "/users", method = HttpMethod.POST)
    public void createUser() {}
}
