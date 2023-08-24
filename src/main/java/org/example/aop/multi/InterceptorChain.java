package org.example.aop.multi;

import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class InterceptorChain {

    private static Map<Method, List<Implementation>> implementations;

    public InterceptorChain(Map<Method, List<Implementation>> implementations) {
        InterceptorChain.implementations = implementations;
    }

    //Interceptor Chain

    // 재귀호출?
    @RuntimeType
    public static Object intercept(
            @This Object self, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method superMethod)
            throws Throwable {

        List<Implementation> interceptors = implementations.get(method);

        if(interceptors.isEmpty()) {
            return superMethod.invoke(self, args);
        }

        //before
        superMethod.invoke(self, args);
        //after


    }

}
