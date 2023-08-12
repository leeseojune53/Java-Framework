package org.example;

import org.example.aop.TransactionAOP;
import org.example.app.domain.user.service.UserService;
import org.example.app.domain.user.service.UserServiceCGLIB;
import org.example.db.SessionManager;

public class RootApplication {

    public static void main(String[] args) {


        // service layer

        var services = TransactionAOP.makeTransactionProxyClass();

        var proxyUserService = (UserService) services.get(UserService.class);

        if(proxyUserService != null) {
            proxyUserService.doSomething();
        }



        // business logic


    }

    public static class IdClass {
        public String id;
    }

}
