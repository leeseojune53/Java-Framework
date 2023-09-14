package org.example.aop;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    public static Map<Class<?>, Object> beans = new HashMap<>();

    public static void generateBeans() {
        generateRecursion(GetBeanService.getBeanClasses());
    }

    private static void generateRecursion(Set<Class<?>> retrySet) {
        var newRetrySet = new HashSet<Class<?>>();

        System.out.println("HI");

        retrySet.forEach(it -> {
            try {
                beans.put(it, BeanInstanceFactory.generate(it, beans));
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | RuntimeException e) {
                newRetrySet.add(it);
            }
        });

        if (!newRetrySet.isEmpty()) generateRecursion(newRetrySet);
    }
}
