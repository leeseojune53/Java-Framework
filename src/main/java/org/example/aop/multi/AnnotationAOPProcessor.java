package org.example.aop.multi;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationAOPProcessor {

    public final Map<Class, MultiCallback> annotationCallBackMap;

    public AnnotationAOPProcessor(Map<Class, MultiCallback> annotationCallBackMap) {
        this.annotationCallBackMap = annotationCallBackMap;
    }


    public Map<Class, Map<Method, List<MultiCallback>>> getMethodAopFunction() {
        Map<Class, Map<Method, List<MultiCallback>>> result = new HashMap<>();

        // Type annotation handling
        var classes = new Reflections("org.example").getTypesAnnotatedWith(Component.class);

        for (Class typeClass : classes) {
            for (Method method : typeClass.getMethods()) {
                result.computeIfAbsent(
                        typeClass,
                        k -> new HashMap<>()
                ).computeIfAbsent(
                        method,
                        k -> new ArrayList<>()
                ).add(annotationCallBackMap.get(Component.class));
            }
        }

        // Method annotation handling
        var methods = new Reflections("org.example", Scanners.MethodsAnnotated).getMethodsAnnotatedWith(Transactional.class);
        for (Method method : methods) {
            result.computeIfAbsent(
                    method.getDeclaringClass(),
                    k -> new HashMap<>()
            ).computeIfAbsent(
                    method,
                    k -> new ArrayList<>()
            ).add(annotationCallBackMap.get(Transactional.class));
        }

        return result;
    }

}
