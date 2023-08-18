package org.example;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;
import org.example.aop.multi.ClassMetadata;
import org.example.aop.multi.AnnotationAOPProcessor;
import org.example.aop.multi.ClassMetadata.ProxyPart;
import org.example.app.domain.apply.service.ApplyService;
import org.example.app.domain.auth.service.AuthService;
import org.example.app.domain.user.service.UserService;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootApplication {

    public static void main(String[] argss) {


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

        Map<Class, Object> applicationContext = ClassMetadata.getProxy(proxyParts);

        ApplyService applyService = (ApplyService) applicationContext.get(ApplyService.class);


        applyService.dependencyInjectionSuccess();

    }

}
