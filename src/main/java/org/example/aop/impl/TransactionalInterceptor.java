package org.example.aop.impl;

import java.lang.annotation.Annotation;

import org.example.annotataion.Transactional;
import org.example.aop.AopSpec;
import org.example.aop.MultiCallback;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

public class TransactionalInterceptor extends AopSpec {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return Transactional.class;
    }

    @Override
    protected MultiCallback getCallback() {
        return (obj, method, args, proxy, chain) -> {
            Transaction transaction = SessionManager.getSessionManager().getTransaction();
            try {
                transaction.begin();
                System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                        + "CGLIB Transaction Begin");
                transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                chain.next(obj, method, args, proxy);
                transaction.commit();
                System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                        + "CGLIB Transaction Commit");
                return null;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException();
            }
        };
    }
}
