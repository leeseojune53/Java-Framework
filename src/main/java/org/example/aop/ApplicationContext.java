package org.example.aop;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.example.app.domain.apply.service.ApplyService;
import org.example.app.domain.auth.service.AuthService;
import org.example.app.domain.user.controller.UserController;
import org.example.app.domain.user.repository.UserRepository;
import org.example.app.domain.user.service.UserService;

public class ApplicationContext {

    public static Map<Class<?>, Object> beans = new HashMap<>();

    public static void generateBeans() {
        try {
            beans.put(UserRepository.class, BeanInstanceFactory.generate(UserRepository.class, beans));
            beans.put(UserService.class, BeanInstanceFactory.generate(UserService.class, beans));
            beans.put(AuthService.class, BeanInstanceFactory.generate(AuthService.class, beans));
            beans.put(ApplyService.class, BeanInstanceFactory.generate(ApplyService.class, beans));
            beans.put(UserController.class, BeanInstanceFactory.generate(UserController.class, beans));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
