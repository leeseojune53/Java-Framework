package org.example.aop.multi;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AopMetadata {

    public Function<MethodInterceptor, Object> aopFunction;

    public Annotation annotation;

    // Function and Methods

}
