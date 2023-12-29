package org.example.framework.db.transaction;

import org.example.framework.db.connection.ArthurConnection;

public interface Transaction {
    Transaction begin();

    Transaction commit();

    Transaction rollback();

    ArthurConnection getConnection();
}
