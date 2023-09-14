package org.example;

import org.example.aop.ApplicationContext;
import org.example.app.domain.user.controller.UserController;

public class RootApplication {

    public static void main(String[] argss) {
        ApplicationContext.generateBeans();

        var userController = (UserController) ApplicationContext.beans.get(UserController.class);

        userController.useService();
    }
}
