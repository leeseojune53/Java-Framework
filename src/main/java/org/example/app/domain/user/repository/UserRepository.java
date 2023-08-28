package org.example.app.domain.user.repository;

import org.example.annotataion.Component;
import org.example.app.domain.user.model.User;
import org.example.db.SessionManager;
import org.example.db.transaction.Transaction;

@Component
public class UserRepository {

    public User findByUserId(String userId) {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        // TODO
        var queryResult =
                transaction.getConnection().select("SELECT name FROM user WHERE user_id = 'TEST'", User.class);

        if (queryResult.isEmpty()) {
            throw new RuntimeException();
        }

        if (queryResult.get(0) instanceof User user) {
            return user;
        }

        throw new RuntimeException();
    }
}
