package org.example.aop.multi;

import java.lang.annotation.Annotation;

import org.example.annotataion.Component;

public class ComponentInterceptor extends AopSpec {

    @Override
    Class<? extends Annotation> getAnnotation() {
        return Component.class;
    }

    @Override
    MultiCallback getCallback() {
        return (obj, method, args, proxy, chain) -> {
            System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                    + " Component Annotated Method");
            chain.next(obj, method, args, proxy);
            return null;
        };
    }
}
