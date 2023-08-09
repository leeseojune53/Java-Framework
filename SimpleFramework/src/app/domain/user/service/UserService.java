package app.domain.user.service;

import db.SessionManager;

public class UserService {

    /*
      This method code is Similar Spring's @Transactional annotation.
      and Spring use AOP for this. (CGLIB)
      So if we use Spring, we don't need to write this code.
     */
    public void doSomething() {
        SessionManager sessionManager = SessionManager.getSessionManager().get();
        try {
            sessionManager.getTransaction().begin();
            // doSomething
            sessionManager.getTransaction().commit();
        } catch(Exception e) {
            sessionManager.getTransaction().rollback();
        }
    }

    /**
     * springServiceLayerMethod2() is not work with @Transactional annotation.
     * because @Transactional annotation is work with AOP.
     * if Controller call springServiceLayerMethod1() then, that's on the proxy class. like above doSomething() method.
     * but springServiceLayerMethod2() is call by proxy class. so @Transactional annotation is not work.
     * read this : https://leeseojune53.tistory.com/75
     */
//    public void springServiceLayerMethod1() {
//        springServiceLayerMethod2();
//        // doSomething
//    }
//
//    @Transactional
//    public void springServiceLayerMethod2() {
//        // doSomething
//    }


}
