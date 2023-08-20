package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.lang.reflect.Method;

public class ComponentInterceptor {

    @RuntimeType
    public static Object intercept(@This Object self,
                                   @Origin Method method,
                                   @AllArguments Object[] args,
                                   @SuperMethod Method superMethod) throws Throwable {

        System.out.println("COMPONENT");
            Object result = superMethod.invoke(self, args);
            return result;
    }

}
