package org.example.framework.aop;

import java.lang.reflect.Method;
import java.util.List;

public class MethodChain {

    private int index;

    private final List<MultiCallback> callbacks;

    public MethodChain(List<MultiCallback> callbacks) {
        this.index = 0;
        this.callbacks = callbacks;
    }

    /**
     * This method execute next method in the chain.
     * If method call finally, that execute original method.
     * @param obj
     * @param method
     * @param args
     * @param superMethod
     * @return
     */
    public Object next(Object obj, java.lang.reflect.Method method, Object[] args, Method superMethod) {
        if (index < callbacks.size()) {
            return callbacks.get(index++).chain(obj, method, args, superMethod, this);
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
