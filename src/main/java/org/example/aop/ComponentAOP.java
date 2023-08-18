package org.example.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

import org.example.annotataion.Component;

public class ComponentAOP {

    public static Map<Class, Object> makeComponent() {
        var classes = new Reflections("org.example").getTypesAnnotatedWith(Component.class);

        Map<Class, Object> components = new HashMap<>();

        for (Class clazz : classes) {
            components.put(clazz, getComponent(clazz));
        }

        return components;
    }

    private static Object getComponent(Class clazz) {
        return Enhancer.create(clazz, (MethodInterceptor) (obj, method, args, proxy) -> {
            System.out.println(
                    "Class: " + clazz.getName() + "  Method : " + method.getName() + " Component Annotated Method");
            return proxy.invokeSuper(obj, args);
        });
    }
}
