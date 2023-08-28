package org.example.db.connection;

import java.util.List;

public interface ArthurConnection {
    boolean isClose();

    boolean getAutoCommit();

    void setAutoCommit(boolean autoCommit);

    List<Object> select(String query, Class<?> clazz);

    void commit();

    void rollback();

    void close();
}
