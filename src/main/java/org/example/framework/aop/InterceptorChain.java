package org.example.framework.aop;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class InterceptorChain {

    /**
     * This method intercept call of method's in some class(object)
     * intercept and execute some method(Ex.ComponentInterceptor, TransactionalInterceptor)
     * @see org.example.aop.impl.ComponentInterceptor
     * @see org.example.aop.impl.TransactionalInterceptor
     * @param self
     * @param method
     * @param args
     * @param superMethod
     * @return
     */
    @RuntimeType
    public static Object intercept(
            @This Object self, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method superMethod) {
        var callBacks = AnnotationAOPProcessor.getMethodAopFunction().get(method);

        var chain = new MethodChain(callBacks);
        return chain.next(self, method, args, superMethod);
    }
}
