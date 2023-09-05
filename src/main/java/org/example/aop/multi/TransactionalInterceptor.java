package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

public class TransactionalInterceptor {

    @RuntimeType
    public static Object intercept(
            @This Object self, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method superMethod)
            throws Throwable {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        try {
            transaction.begin();
            System.out.println("Class: " + self.getClass().getName() + "  Method : " + method.getName()
                    + "CGLIB Transaction Begin");
            transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
            Object result = superMethod.invoke(self, args);
            transaction.commit();
            System.out.println("Class: " + self.getClass().getName() + "  Method : " + method.getName()
                    + "CGLIB Transaction Commit");
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException();
        }
    }
}
