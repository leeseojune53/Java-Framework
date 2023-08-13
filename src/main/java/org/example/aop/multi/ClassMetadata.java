package org.example.aop.multi;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ClassMetadata {

    public static Object getProxy(Class clazz, Map<Method, List<MultiCallback>> methodAopFunction) {
        return Enhancer.create(clazz, (MethodInterceptor) (obj, method, args, proxy) -> {
            var functions = methodAopFunction.getOrDefault(method, List.of());

            var chain = new MethodChain(functions);
            chain.next(obj, method, args, proxy);

            return null;
        });

    }

}
