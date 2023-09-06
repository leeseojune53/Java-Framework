package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;
import org.example.annotataion.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;

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
