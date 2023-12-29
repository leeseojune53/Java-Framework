package org.example.framework.ioc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {

    public static Map<Class<?>, Object> beans = new HashMap<>();
    public static int beanCount;

    public static void generateBeans() {
        beanCount = GetBeanService.getBeanClasses().size();
        generateRecursion(GetBeanService.getBeanClasses(), 0);
    }

    private static void generateRecursion(Set<Class<?>> retrySet, int attempt) {
        var newRetrySet = new HashSet<Class<?>>();

        // TODO find circular dependency
        // compare beanCount and attempt is not good. if creating more beans this code will reduce performance

        if (attempt > beanCount) throw new RuntimeException("Circular dependency detected." + retrySet.toString());

        retrySet.forEach(it -> {
            try {
                beans.put(it, BeanInstanceFactory.generate(it, beans));
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | RuntimeException e) {
                newRetrySet.add(it);
            }
        });

        if (!newRetrySet.isEmpty()) generateRecursion(newRetrySet, attempt + 1);
    }
}
