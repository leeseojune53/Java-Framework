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

    public static final Map<Class, MultiCallback> annotationCallBackMap = Map.of(
                                    Component.class,
                                    (obj, method, args, proxy, chain) -> {
                                        System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                                                + " Component Annotated Method");
                                        chain.next(obj, method, args, proxy);
                                        return null;
                                    },
                                    Transactional.class,
                                    (obj, method, args, proxy, chain) -> {
                                        Transaction transaction = SessionManager.getSessionManager().getTransaction();
                                        try {
                                            transaction.begin();
                                            System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
                     method.getName()
                                                    + "CGLIB Transaction Begin");
                                            transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                                            chain.next(obj, method, args, proxy);
                                            transaction.commit();
                                            System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
                     method.getName()
                                                    + "CGLIB Transaction Commit");
                                            return null;
                                        } catch (Exception e) {
                                            transaction.rollback();
                                            throw new RuntimeException();
                                        }
                                    }
    );


    public static Map<Method, List<MultiCallback>> getMethodAopFunction() {
        Map<Method, List<MultiCallback>> result = new HashMap<>();

        // Type annotation handling
        var classes = new Reflections("org.example").getTypesAnnotatedWith(Component.class);

        for (Class typeClass : classes) {
            for (Method method : typeClass.getMethods()) {
                result
                        .computeIfAbsent(method, k -> new ArrayList<>())
                        .add(annotationCallBackMap.get(Component.class));
            }
        }

        // Method annotation handling
        var methods =
                new Reflections("org.example", Scanners.MethodsAnnotated).getMethodsAnnotatedWith(Transactional.class);
        for (Method method : methods) {
            result.computeIfAbsent(method, k -> new ArrayList<>())
                    .add(annotationCallBackMap.get(Transactional.class));
        }

        return result;
    }
}
