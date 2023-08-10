package org.example.db.transaction;

import org.example.db.connection.ArthurConnection;

import java.sql.Connection;

public interface Transaction {
    Transaction begin();
    Transaction commit();
    Transaction rollback();

    ArthurConnection getConnection();
}
