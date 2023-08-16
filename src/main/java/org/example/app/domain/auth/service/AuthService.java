package org.example.app.domain.auth.service;

import org.example.annotataion.Component;
import org.example.app.domain.user.service.UserService;

@Component
public class AuthService {

    public final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void doSomething() {
        System.out.println("Auth HIHI");
        userService.doSomething();
        //doSomething
    }

}
