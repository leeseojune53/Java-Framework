package org.example.aop.multi;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.example.annotataion.Transactional;
import org.example.aop.multi.ClassMetadata.ProxyPart;
import org.example.app.domain.user.service.UserService;

public class MultiProxyFactory {

    // ByteBuddy's proxy method is override by latest interceptor.
    public static Object generate(Class clazz) throws InstantiationException, IllegalAccessException {

        var buddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(TransactionalInterceptor.class))
                .make()
                .load(UserService.class.getClassLoader())
                .getLoaded();

        var service = (UserService) buddy.newInstance();

        var buddy2 = new ByteBuddy()
                .subclass(service.getClass())
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(ComponentInterceptor.class))
                .make()
                .load(UserService.class.getClassLoader())
                .getLoaded();

        var service2 = (UserService) buddy2.newInstance();

        service2.doSomethingWithTransaction();

//        service.doSomethingWithTransaction();


        return null;

    }

}
