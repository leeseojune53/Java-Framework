package org.example;

import org.example.aop.MultiProxyFactory;
import org.example.app.domain.user.service.UserService;

public class RootApplication {

    public static void main(String[] argss) throws InstantiationException, IllegalAccessException {
        var userService = (UserService) MultiProxyFactory.generate(UserService.class);

        userService.doSomethingWithTransaction();
    }
}
