package org.example.framework.db.connection;

import java.util.List;

public interface ArthurConnection {
    boolean isClose();

    boolean getAutoCommit();

    void setAutoCommit(boolean autoCommit);

    List<Object> select(String query, Class<?> clazz);

    Object save(Object object);

    void deleteById(Object object, Class<?> clazz);

    void commit();

    void rollback();

    void close();
}
