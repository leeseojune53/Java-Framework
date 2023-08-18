package org.example.app.domain.auth.service;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.example.app.domain.user.service.UserService;

@Component
public class AuthService {

    public final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void getUser() {
        System.out.println("Auth HIHI");
        userService.doSomethingWithTransaction();
        // doSomething
    }
}
