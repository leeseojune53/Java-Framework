package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ComponentInterceptor {

    @RuntimeType
    public static Object intercept(@This Object self,
                                   @Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperCall Callable<?> origin) throws Throwable {

        System.out.println("COMPONENT");
            Object result = origin.call();
            return result;
    }

}
