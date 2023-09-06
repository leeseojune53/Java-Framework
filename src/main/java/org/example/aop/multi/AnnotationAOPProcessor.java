package org.example.aop.multi;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationAOPProcessor {

    public static Map<Method, List<MultiCallback>> getMethodAopFunction() {
        Map<Method, List<MultiCallback>> result = new HashMap<>();

        // aopSpecs order is aop's order
        List<AopSpec> aopSpecs = List.of(new TransactionalInterceptor(), new ComponentInterceptor());

        aopSpecs.forEach(spec -> {
            spec.getCallbackMap().forEach((method, callbacks) -> {
                result.computeIfAbsent(method, k -> new ArrayList<>()).addAll(callbacks);
            });
        });

        return result;
    }
}
