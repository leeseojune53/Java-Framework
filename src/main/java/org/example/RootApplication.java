package org.example;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.example.aop.multi.ClassMetadata;
import org.example.aop.multi.AnnotationAOPProcessor;
import org.example.aop.multi.ClassMetadata.ProxyPart;
import org.example.app.domain.auth.service.AuthService;
import org.example.app.domain.user.service.UserService;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.util.ArrayList;
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

        var annotationTarget = new AnnotationAOPProcessor(
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

        var aopFunction = annotationTarget.getMethodAopFunction();

        List<ProxyPart> proxyParts = new ArrayList<>();
        for(var key : aopFunction.keySet()) {
            proxyParts.add(new ProxyPart(key, aopFunction.get(key)));
        }

        Map<Class, Object> services = new HashMap<>(ClassMetadata.getProxy(proxyParts));



        UserService userService = (UserService) services.get(UserService.class);
        AuthService authService = (AuthService) services.get(AuthService.class);

        userService.doSomething();

        System.out.println("=====================================");

        userService.doSomethingWithTransaction();

        System.out.println(userService.getClass().getName());
        System.out.println(authService.userService.getClass().getName());

        authService.doSomething();


        // business logic


    }

    public static class IdClass {
        public String id;
    }

}
