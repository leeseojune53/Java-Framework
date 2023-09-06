package org.example.aop;

import java.lang.reflect.Method;
import java.util.List;

public class MethodChain {

    private int index;

    private final List<MultiCallback> callbacks;

    public MethodChain(List<MultiCallback> callbacks) {
        this.index = 0;
        this.callbacks = callbacks;
    }

    public Object next(Object obj, java.lang.reflect.Method method, Object[] args, Method superMethod) {
        if (index < callbacks.size()) {
            return callbacks.get(index++).intercept(obj, method, args, superMethod, this);
        } else {
            try {
                return superMethod.invoke(obj, args);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                // TODO : 예외처리
                return null;
            }
        }
    }
}
