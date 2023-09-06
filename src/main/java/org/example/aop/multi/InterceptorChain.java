package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class InterceptorChain {

    @RuntimeType
    public static Object intercept(
            @This Object self, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method superMethod) {
        var callBacks = AnnotationAOPProcessor.getMethodAopFunction().get(method);

        var chain = new MethodChain(callBacks);
        return chain.next(self, method, args, superMethod);
    }
}
