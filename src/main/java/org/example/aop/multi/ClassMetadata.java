package org.example.aop.multi;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClassMetadata {

    // ConcurrentModificationException handling
    // make a two list and retry
    private static final List<ProxyPart> retryList1 = new ArrayList<>();
    private static final List<ProxyPart> retryList2 = new ArrayList<>();

    public static Map<Class, Object> getProxy(List<ProxyPart> proxyParts) {
        Map<Class, Object> proxyServices = new HashMap<>();

        retryList1.addAll(proxyParts);

        // init end

        while (proxyServices.keySet().size() != proxyParts.size()) {
            for(ProxyPart proxyPart : retryList1) {
                Set<Class> constructorArgs = new HashSet<>();
                List<Object> constructorArgsList = new ArrayList<>();

                for(var constructor : proxyPart.getConstructors()) {
                    for(var parameter : constructor.getParameters()) {
                        constructorArgs.add(parameter.getType());
                    }
                }


                for(var constructorClass : constructorArgs) {
                    if(proxyServices.get(constructorClass) != null)
                        constructorArgsList.add(proxyServices.get(constructorClass));
                }

                if(constructorArgs.size() != constructorArgsList.size()) {
                    System.out.println("ADDDD");
                    retryList2.add(proxyPart);
                    continue;
                }

                var enhancer = new Enhancer();

                enhancer.setSuperclass(proxyPart.clazz);
                enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                    var functions = proxyPart.getCallBackByMethod(method);

                    var chain = new MethodChain(functions);
                    chain.next(obj, method, args, proxy);

                    return null;
                });

                proxyServices.put(proxyPart.clazz, enhancer.create(constructorArgs.toArray(new Class[]{}), constructorArgsList.toArray()));
            }
            for(ProxyPart proxyPart : retryList2) {
                System.out.println("---generate---" + proxyPart.clazz.getName());
                var enhancer = new Enhancer();

                enhancer.setSuperclass(proxyPart.clazz);
                enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                    var functions = proxyPart.getCallBackByMethod(method);

                    var chain = new MethodChain(functions);
                    chain.next(obj, method, args, proxy);

                    return null;
                });

                Set<Class> constructorArgs = new HashSet<>();

                for(var constructor : proxyPart.getConstructors()) {
                    for(var parameter : constructor.getParameters()) {
                        constructorArgs.add(parameter.getType());
                    }
                }

                List<Object> constructorArgsList = new ArrayList<>();

                for(var constructorClass : constructorArgs) {
                    if(proxyServices.get(constructorClass) != null)
                        constructorArgsList.add(proxyServices.get(constructorClass));
                }

                if(constructorArgs.size() != constructorArgsList.size()) {
                    System.out.println("ADDDD");
                    retryList1.add(proxyPart);
                    continue;
                }

                proxyServices.put(proxyPart.clazz, enhancer.create(constructorArgs.toArray(new Class[]{}), constructorArgsList.toArray()));
            }
        }



        return proxyServices;
    }

    public static final class ProxyPart {
        private final Class clazz;
        private final Map<Method, List<MultiCallback>> methodAopFunction;

        private int retryCount = 0;

        public ProxyPart(Class clazz, Map<Method, List<MultiCallback>> methodAopFunction) {
            this.clazz = clazz;
            this.methodAopFunction = methodAopFunction;
        }

        public ProxyPart(ProxyPart proxyPart) {
            this.clazz = proxyPart.clazz;
            this.methodAopFunction = proxyPart.methodAopFunction;
            this.retryCount = proxyPart.retryCount + 1;
        }

        public Constructor[] getConstructors() {
            return clazz.getConstructors();
        }

        public List<MultiCallback> getCallBackByMethod(Method method) {
            return methodAopFunction.getOrDefault(method, List.of());
        }
    }

}
