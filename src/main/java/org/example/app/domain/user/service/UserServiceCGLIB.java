package org.example.app.domain.user.service;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

public class UserServiceCGLIB {

    public static UserService getUserService() {
        return (UserService) Enhancer.create(UserService.class, (MethodInterceptor) (obj, method, args, proxy) -> {
            Transaction transaction = SessionManager.getSessionManager().getTransaction();
            try {
                transaction.begin();
                System.out.println("User Service CGLIB Transaction Begin");
                transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                Object result = proxy.invokeSuper(obj, args);
                transaction.commit();
                System.out.println("User Service CGLIB Transaction Commit");
                return result;
            } catch(Exception e) {
                transaction.rollback();
                throw new RuntimeException();
            }
        });
    }

}
