package org.example.framework.ioc;

import static org.example.framework.aop.AopSpec.BASE_PACKAGE;

import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

import org.example.framework.annotataion.Component;

public class GetBeanService {

    public static Set<Class<?>> getBeanClasses() {
        return new Reflections(BASE_PACKAGE)
                .getTypesAnnotatedWith(Component.class).stream()
                        .filter(it -> !it.isAnnotation())
                        .collect(Collectors.toSet());
    }
}
