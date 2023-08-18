package org.example.app.domain.user.service;

import org.example.annotataion.Component;
import org.example.annotataion.Transactional;

@Component
public class UserService {

    /*
     This method code is Similar Spring's @Transactional annotation.
     and Spring use AOP for this. (CGLIB)
     So if we use Spring, we don't need to write this code.
    */
    public void doSomething() {
        System.out.println("HIHI");
        // doSomething
    }

    @Transactional
    public void doSomethingWithTransaction() {
        System.out.println("HIHI With Transaction");
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
