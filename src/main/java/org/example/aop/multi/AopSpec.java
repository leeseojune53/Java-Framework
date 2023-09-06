package org.example.aop.multi;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AopSpec {

    private static final String BASE_PACKAGE = "org.example";

    abstract Class<? extends Annotation> getAnnotation();
    abstract MultiCallback getCallback();
    Map<Method, List<MultiCallback>> getCallbackMap() {
        var annotationTarget = getAnnotation().getAnnotation(Target.class);
        if(annotationTarget == null || annotationTarget.value().length > 1) {
            throw new RuntimeException(String.format("Annotation %s must have exactly one target", getAnnotation().getName()));
        }

        switch (annotationTarget.value()[0]) {
            case TYPE -> {
                Map<Method, List<MultiCallback>> result = new HashMap<>();
                var classes = new Reflections(BASE_PACKAGE).getTypesAnnotatedWith(getAnnotation());
                for (Class<?> typeClass : classes) {
                    for (Method method : typeClass.getMethods()) {
                        result
                                .computeIfAbsent(method, k -> new ArrayList<>())
                                .add(getCallback());
                    }
                }
                return result;
            }
            case METHOD -> {
                Map<Method, List<MultiCallback>> result = new HashMap<>();
                var methods =
                        new Reflections(BASE_PACKAGE, Scanners.MethodsAnnotated).getMethodsAnnotatedWith(getAnnotation());
                for (Method method : methods) {
                    result.computeIfAbsent(method, k -> new ArrayList<>())
                            .add(getCallback());
                }
                return result;
            }
            default -> throw new RuntimeException(String.format("Annotation %s must have exactly one target", getAnnotation().getName()));
        }
    }
}
