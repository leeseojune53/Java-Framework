package org.example.framework.ioc;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import org.example.framework.aop.InterceptorChain;

public class BeanInstanceFactory {

    // ByteBuddy's proxy method is overridden by latest interceptor.
    public static Object generate(Class<?> clazz, Map<Class<?>, Object> beans)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {

        var buddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(InterceptorChain.class))
                .make()
                .load(clazz.getClassLoader())
                .getLoaded();

        if (buddy.getDeclaredConstructors().length == 0 || buddy.getDeclaredConstructors().length > 1)
            throw new RuntimeException("Constructor must be only one.");

        var constructor = buddy.getDeclaredConstructors()[0];

        var constructorParameterSize = constructor.getParameters().length;

        var arguments = Arrays.stream(constructor.getParameters())
                .map(it -> beans.getOrDefault(it.getType(), null))
                .filter(Objects::nonNull)
                .toArray();

        if (constructorParameterSize != arguments.length)
            throw new RuntimeException("Constructor parameter size and arguments size must be same.");

        return constructor.newInstance(arguments);
    }
}
