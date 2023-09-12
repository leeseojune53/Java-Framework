package org.example.aop;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.example.app.domain.user.repository.UserRepository;
import org.example.app.domain.user.service.UserService;

public class ApplicationContext {

    public static Map<Class<?>, Object> beans = new HashMap<>();

    public static void generateBeans() {
        try {
            beans.put(UserRepository.class, BeanInstanceFactory.generate(UserRepository.class, beans));
            beans.put(UserService.class, BeanInstanceFactory.generate(UserService.class, beans));
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
