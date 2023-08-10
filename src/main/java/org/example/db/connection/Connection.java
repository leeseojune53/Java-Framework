package org.example.db.connection;

public interface Connection {
    boolean isClose();
    boolean getAutoCommit();
    void setAutoCommit(boolean autoCommit);

    void commit();
    void rollback();
    void close();
}
