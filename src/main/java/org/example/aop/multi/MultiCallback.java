package org.example.aop.multi;

import net.sf.cglib.proxy.MethodProxy;

public interface MultiCallback {
    Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy, MethodChain chain);
}
