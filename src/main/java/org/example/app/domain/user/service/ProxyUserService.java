package org.example.app.domain.user.service;

import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

public class ProxyUserService extends UserService {

    public void doSomething() {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        try {
            transaction.begin();
            transaction.getConnection().select("select * from user", Object.class);
            super.doSomething();
            transaction.commit();
        } catch(Exception e) {
            transaction.rollback();
        }
    }

}
