package org.example.aop.multi;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ClassMetadata {

    public static Object getProxy(Class clazz, Map<Method, List<MultiCallback>> methodAopFunction, Map<Class, Object> services) {

        var enhancer = new Enhancer();

        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            var functions = methodAopFunction.getOrDefault(method, List.of());

            var chain = new MethodChain(functions);
            chain.next(obj, method, args, proxy);

            return null;
        });

        Set<Class> constructorArgs = new HashSet<>();

        for(var constructor : clazz.getConstructors()) {
            for(var parameter : constructor.getParameters()) {
                constructorArgs.add(parameter.getType());
            }
        }

        List<Object> constructorArgsList = new ArrayList<>();

        for(var constructorClass : constructorArgs) {
            constructorArgsList.add(services.get(constructorClass));
        }

        return enhancer.create(constructorArgs.toArray(new Class[]{}), constructorArgsList.toArray());
    }

}
