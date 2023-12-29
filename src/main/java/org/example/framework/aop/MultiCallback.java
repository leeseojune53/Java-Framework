package org.example.framework.aop;

import java.lang.reflect.Method;

public interface MultiCallback {
    Object chain(Object obj, java.lang.reflect.Method method, Object[] args, Method proxy, MethodChain chain);
}
