package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.SuperMethod;

import java.lang.reflect.Method;
import java.util.List;

public class MethodChain {

    private int index;

    private final List<MultiCallback> callbacks;

    public MethodChain(List<MultiCallback> callbacks) {
        this.index = 0;
        this.callbacks = callbacks;
    }

    public boolean next(Object obj, java.lang.reflect.Method method, Object[] args, @SuperMethod Method proxy) {
        if (index < callbacks.size()) {
            callbacks.get(index++).intercept(obj, method, args, proxy, this);
        } else {
            try {
                proxy.invoke(obj, args);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return false;
    }
}
