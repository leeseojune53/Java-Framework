package org.example;

import org.example.aop.ApplicationContext;
import org.example.app.domain.user.service.UserService;

public class RootApplication {

    public static void main(String[] argss) {
        ApplicationContext.generateBeans();

        var userService = (UserService) ApplicationContext.beans.get(UserService.class);

        userService.doSomethingWithTransaction();
    }
}
