package org.example.aop.multi;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ClassMetadata {

    public static Object getProxy(Class clazz, Map<Method, List<MethodInterceptor>> methodAopFunction) {
        return Enhancer.create(clazz, (MethodInterceptor) (obj, method, args, proxy) -> {
            var functions = methodAopFunction.getOrDefault(method, List.of());

            for(MethodInterceptor interceptor : functions) {
                interceptor.intercept(obj, method, args, proxy);
            }

            // interceptor.intercept start

            // proxy.invokeSuper start

            // interceptor.intercept end

            return null;
        });

    }

}
