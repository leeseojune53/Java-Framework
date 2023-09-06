package org.example.aop.multi;

import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;

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
