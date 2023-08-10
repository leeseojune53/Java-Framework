package org.example.db.transaction;

import org.example.db.connection.Connection;

public class SimpleTransaction implements Transaction {

    private final Connection connection;

    public SimpleTransaction(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Transaction begin() {
        return this;
    }

    @Override
    public Transaction commit() {
        connection.commit();
        return this;
    }

    @Override
    public Transaction rollback() {
        connection.rollback();
        return this;
    }
}
