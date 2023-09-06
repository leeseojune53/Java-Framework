package org.example.aop.impl;

import java.lang.annotation.Annotation;

import org.example.annotataion.Component;
import org.example.aop.AopSpec;
import org.example.aop.MultiCallback;

public class ComponentInterceptor extends AopSpec {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return Component.class;
    }

    @Override
    protected MultiCallback getCallback() {
        return (obj, method, args, proxy, chain) -> {
            System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                    + " Component Annotated Method");
            chain.next(obj, method, args, proxy);
            return null;
        };
    }
}
