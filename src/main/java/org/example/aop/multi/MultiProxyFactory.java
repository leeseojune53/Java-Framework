package org.example.aop.multi;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import org.example.app.domain.user.service.UserService;

public class MultiProxyFactory {

    // ByteBuddy's proxy method is overridden by latest interceptor.
    public static Object generate(Class clazz) throws InstantiationException, IllegalAccessException {

        var buddy = new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(InterceptorChain.class))
                .make()
                .load(UserService.class.getClassLoader())
                .getLoaded();

        return buddy.newInstance();
    }
}
