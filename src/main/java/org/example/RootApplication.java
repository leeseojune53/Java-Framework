package org.example;

import org.example.aop.ApplicationContext;
import org.example.app.domain.user.controller.UserController;
import org.example.app.domain.user.service.UserService;

public class RootApplication {

    public static void main(String[] argss) {
        ApplicationContext.generateBeans();

        var userService = (UserController) ApplicationContext.beans.get(UserController.class);

        userService.useService();
    }
}
