package db;

import db.connection.ConnectionFactory;
import db.transaction.SimpleTransaction;
import db.transaction.Transaction;

public class SessionManager {

    private static ThreadLocal<SessionManager> sessionManager = new ThreadLocal<SessionManager>();
    private SimpleTransaction transaction;

    public SessionManager() {
    }

    public static ThreadLocal<SessionManager> getSessionManager() {
        return sessionManager;
    }

    public Transaction getTransaction() {
        if(transaction == null)
            transaction = new SimpleTransaction(ConnectionFactory.getConnection());

        return transaction;
    }

    public void close() {
    }

}
