package org.example.aop;

import java.util.List;

import org.example.app.domain.user.repository.UserRepository;
import org.example.app.domain.user.service.UserService;

public class GetBeanService {

    public static List<Class<?>> getBeanClasses() {
        return List.of(UserRepository.class, UserService.class);
    }
}
