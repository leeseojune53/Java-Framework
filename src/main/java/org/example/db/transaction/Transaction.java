package org.example.db.transaction;

import org.example.db.connection.ArthurConnection;

public interface Transaction {
    Transaction begin();

    Transaction commit();

    Transaction rollback();

    ArthurConnection getConnection();
}
