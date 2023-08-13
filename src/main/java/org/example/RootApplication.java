package org.example;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.MethodInterceptor;
import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.example.aop.ComponentAOP;
import org.example.aop.TransactionAOP;
import org.example.aop.multi.ClassMetadata;
import org.example.aop.multi.MultiCallback;
import org.example.aop.multi.QueryAnnotatedTarget;
import org.example.app.domain.user.service.UserService;
import org.example.app.domain.user.service.UserServiceCGLIB;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootApplication {

    public static void main(String[] argss
    ) throws NoSuchMethodException {


        // service layer

//        var components = ComponentAOP.makeComponent();
//        var services = TransactionAOP.makeTransactionProxyClass(components);
//
//        var proxyUserService = (UserService) services.get(UserService.class);
//
//        if(proxyUserService != null) {
//            proxyUserService.doSomething();
//        }

        Map<Class, Object> services = new HashMap<>();

        var annotationTarget = new QueryAnnotatedTarget(
                Map.of(
                        Component.class,
                        (obj, method, args, proxy, chain) -> {
                            System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName() + " Component Annotated Method");
                            chain.next(obj, method, args, proxy);
                            return null;
                        },

                        Transactional.class,
                        (obj, method, args, proxy, chain) -> {
                            Transaction transaction = SessionManager.getSessionManager().getTransaction();
                            try {
                                transaction.begin();
                                System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName() + "CGLIB Transaction Begin");
                                transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                                chain.next(obj, method, args, proxy);
                                transaction.commit();
                                System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName() + "CGLIB Transaction Commit");
                                return null;
                            } catch(Exception e) {
                                transaction.rollback();
                                throw new RuntimeException();
                            }
                        }

                )
        );

        var aopFunction = annotationTarget.getMethodAopFunction(UserService.class);

        for(var key : aopFunction.keySet()) {
            services.put(key, ClassMetadata.getProxy(key, aopFunction.get(key)));
        }

        UserService service = (UserService) services.get(UserService.class);

        service.doSomething();

        System.out.println("=====================================");

        service.doSomethingWithTransaction();

        // business logic


    }

    public static class IdClass {
        public String id;
    }

}
