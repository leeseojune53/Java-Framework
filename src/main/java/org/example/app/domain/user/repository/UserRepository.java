package org.example.app.domain.user.repository;

import org.example.annotataion.Component;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

@Component
public class UserRepository {

    public void findByUserId(String userId) {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        // TODO
        transaction.getConnection().select("SELECT id FROM user WHERE user_id = " + userId, Object.class);
    }

}
