package org.example;

import org.example.framework.ioc.ApplicationContext;
import org.example.app.domain.user.controller.UserController;

public class RootApplication {

    public static void main(String[] args) {
        ApplicationContext.generateBeans();

        var userController = (UserController) ApplicationContext.beans.get(UserController.class);

        userController.useService();
    }
}
