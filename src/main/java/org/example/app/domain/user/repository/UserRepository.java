package org.example.app.domain.user.repository;

import org.example.framework.annotataion.Component;
import org.example.app.domain.user.model.User;
import org.example.framework.db.SessionManager;
import org.example.framework.db.transaction.Transaction;

@Component
public class UserRepository {

    public User findByUserId(String userId) {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        // TODO
        var queryResult = transaction
                .getConnection()
                .select("SELECT name, password FROM user WHERE user_id = 'TEST'", User.class);

        if (queryResult.isEmpty()) {
            throw new RuntimeException();
        }

        // queryResult's Type is Not User
        if (queryResult.get(0) instanceof User user) {
            return user;
        }

        throw new RuntimeException();
    }

    public User save(User user) {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        // TODO
        return (User) transaction.getConnection().save(user);
    }

    public void deleteById(Object id, Class<?> clazz) {
        Transaction transaction = SessionManager.getSessionManager().getTransaction();
        transaction.getConnection().deleteById(id, clazz);
    }
}
