package org.example.db.connection;

public interface ArthurConnection {
    boolean isClose();

    boolean getAutoCommit();

    void setAutoCommit(boolean autoCommit);

    Object select(String query, Class<?> clazz);

    void commit();

    void rollback();

    void close();
}
