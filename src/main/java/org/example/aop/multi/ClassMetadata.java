package org.example.aop.multi;

import net.bytebuddy.ByteBuddy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassMetadata {

    public static Map<Class, Object> getProxy(List<ProxyPart> proxyParts) {
        Map<Class, Object> proxyServices = new HashMap<>();

        getProxyParts(proxyParts, proxyServices, proxyParts.size());

        return proxyServices;
    }

    private static void getProxyParts(List<ProxyPart> proxyParts, Map<Class, Object> proxyServices, int size) {
//        List<ProxyPart> retryList = new ArrayList<>();
//        for (ProxyPart proxyPart : proxyParts) {
//            var enhancer = new ByteBuddy()
//                    .subclass(proxyPart.clazz)
//
//            enhancer.setSuperclass(proxyPart.clazz);
//            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
//                var functions = proxyPart.getCallBackByMethod(method);
//
//                var chain = new MethodChain(functions);
//                chain.next(obj, method, args, proxy);
//
//                return null;
//            });
//
//            Set<Class> constructorArgs = new HashSet<>();
//
//            for (var constructor : proxyPart.getConstructors()) {
//                for (var parameter : constructor.getParameters()) {
//                    constructorArgs.add(parameter.getType());
//                }
//            }
//
//            List<Object> constructorArgsList = new ArrayList<>();
//
//            for (var constructorClass : constructorArgs) {
//                if (proxyServices.get(constructorClass) != null)
//                    constructorArgsList.add(proxyServices.get(constructorClass));
//            }
//
//            if (constructorArgs.size() != constructorArgsList.size()) {
//                retryList.add(proxyPart);
//                continue;
//            }
//
//            proxyServices.put(
//                    proxyPart.clazz,
//                    enhancer.create(constructorArgs.toArray(new Class[] {}), constructorArgsList.toArray()));
//        }
//
//        if (proxyServices.keySet().size() != size) {
//            getProxyParts(retryList, proxyServices, size);
//        }
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

        public Class getClazz() {
            return clazz;
        }
    }
}
