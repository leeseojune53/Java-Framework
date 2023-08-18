package org.example.db.transaction;

import org.example.db.connection.ArthurConnection;

public class SimpleTransaction implements Transaction {

    private final ArthurConnection arthurConnection;

    public SimpleTransaction(ArthurConnection arthurConnection) {
        this.arthurConnection = arthurConnection;
    }

    @Override
    public Transaction begin() {
        arthurConnection.setAutoCommit(false);
        return this;
    }

    @Override
    public Transaction commit() {
        arthurConnection.commit();
        return this;
    }

    @Override
    public Transaction rollback() {
        arthurConnection.rollback();
        return this;
    }

    @Override
    public ArthurConnection getConnection() {
        return arthurConnection;
    }
}
