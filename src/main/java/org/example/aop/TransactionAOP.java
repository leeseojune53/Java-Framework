package org.example.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.example.annotataion.Transactional;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionAOP {

    private static Map<Class, List<Method>> transactionProxyClassMap = new HashMap<>();

    // 스프링은 일단 ProxyService를 만들고, AOP를 적용해야하면 다시 만들었나?
    public static Map<Class, Object> makeTransactionProxyClass(Map<Class, Object> proxyServices) {
        var methods =  new Reflections("org.example", Scanners.MethodsAnnotated).getMethodsAnnotatedWith(Transactional.class);
        for(Method method : methods) {
            transactionProxyClassMap.computeIfAbsent(method.getDeclaringClass(), k -> new ArrayList<>()).add(method);
        }

        for(Class clazz : transactionProxyClassMap.keySet()) {
            proxyServices.put(clazz, getTransactionProxyClass(clazz, transactionProxyClassMap.get(clazz)));
        }

        return proxyServices;
    }

    private static Object getTransactionProxyClass(Class clazz, List<Method> methods) {

        return Enhancer.create(clazz, (MethodInterceptor) (obj, method, args, proxy) -> {
            if(methods.contains(method)) {
                Transaction transaction = SessionManager.getSessionManager().getTransaction();
                try {
                    transaction.begin();
                    System.out.println("Class: " + clazz.getName() + "  Method : " + method.getName() + "CGLIB Transaction Begin");
                    transaction.getConnection().select("SELECT id FROM tbl_weekend_meal", Object.class);
                    Object result = proxy.invokeSuper(obj, args);
                    transaction.commit();
                    System.out.println("Class: " + clazz.getName() + "  Method : " + method.getName() + "CGLIB Transaction Commit");
                    return result;
                } catch(Exception e) {
                    transaction.rollback();
                    throw new RuntimeException();
                }
            }
            System.out.println("Class: " + clazz.getName() + "  Method : " + method.getName() + "Not Transactional Annotated Method");
            return proxy.invokeSuper(obj, args);
        });

    }

}
