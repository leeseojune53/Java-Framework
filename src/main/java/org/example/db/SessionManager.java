package org.example.db;

import org.example.db.connection.ConnectionFactory;
import org.example.db.transaction.SimpleTransaction;
import org.example.db.transaction.Transaction;

public class SessionManager {

    private static final ThreadLocal<SessionManager> SESSION_MANAGER = new ThreadLocal<>();
    private SimpleTransaction transaction;

    public SessionManager() {
    }

    public static SessionManager getSessionManager() {
        return SESSION_MANAGER.get();
    }

    public static void setDefaultSessionManager() {
        SESSION_MANAGER.set(new SessionManager());
    }

    public static void setSessionManager(SessionManager sessionManager) {
        SESSION_MANAGER.set(sessionManager);
    }

    public Transaction getTransaction() {
        if(transaction == null)
            transaction = new SimpleTransaction(ConnectionFactory.getConnection());

        return transaction;
    }

    public void close() {
    }

}
