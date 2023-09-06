package org.example.aop;

import java.lang.reflect.Method;

public interface MultiCallback {
    Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, Method proxy, MethodChain chain);
}
