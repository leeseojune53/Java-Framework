package org.example;

import org.example.app.domain.user.service.UserService;

public class RootApplication {

    public static void main(String[] argss) throws InstantiationException, IllegalAccessException {

        //        var annotationTarget = new AnnotationAOPProcessor(Map.of(
        //                Component.class,
        //                (obj, method, args, proxy, chain) -> {
        //                    System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
        //                            + " Component Annotated Method");
        //                    chain.next(obj, method, args, proxy);
        //                    return null;
        //                },
        //                Transactional.class,
        //                (obj, method, args, proxy, chain) -> {
        //                    Transaction transaction = SessionManager.getSessionManager().getTransaction();
        //                    try {
        //                        transaction.begin();
        //                        System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
        // method.getName()
        //                                + "CGLIB Transaction Begin");
        //                        transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
        //                        chain.next(obj, method, args, proxy);
        //                        transaction.commit();
        //                        System.out.println("Class: " + obj.getClass().getName() + "  Method : " +
        // method.getName()
        //                                + "CGLIB Transaction Commit");
        //                        return null;
        //                    } catch (Exception e) {
        //                        transaction.rollback();
        //                        throw new RuntimeException();
        //                    }
        //                }));
        //
        //        var aopFunction = annotationTarget.getMethodAopFunction();
        //
        //        List<ProxyPart> proxyParts = new ArrayList<>();
        //        for (var key : aopFunction.keySet()) {
        //            proxyParts.add(new ProxyPart(key, aopFunction.get(key)));
        //        }
        //
        //        Map<Class, Object> applicationContext = ClassMetadata.getProxy(proxyParts);
        //
        //        ApplyService applyService = (ApplyService) applicationContext.get(ApplyService.class);
        //
        //        applyService.dependencyInjectionSuccess();

        //        MultiProxyFactory.generate(UserService.class);
        UserService userService = new UserService();

        userService.doSomething();
    }
}
