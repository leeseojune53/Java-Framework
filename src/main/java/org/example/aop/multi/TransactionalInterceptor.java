package org.example.aop.multi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.SuperMethod;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.example.annotataion.Transactional;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

public class TransactionalInterceptor extends AopSpec {

    @Override
    Class<? extends Annotation> getAnnotation() {
        return Transactional.class;
    }

    @Override
    MultiCallback getCallback() {
        return (obj, method, args, proxy, chain) -> {
            Transaction transaction = SessionManager.getSessionManager().getTransaction();
            try {
                transaction.begin();
                System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
                        method.getName()
                        + "CGLIB Transaction Begin");
                transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                chain.next(obj, method, args, proxy);
                transaction.commit();
                System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
                        method.getName()
                        + "CGLIB Transaction Commit");
                return null;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException();
            }
        };
    }
}
