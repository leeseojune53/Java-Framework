package org.example.db.connection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SimpleArthurConnection implements ArthurConnection {

    private final Connection connection;
    private boolean autoCommit = false;
    private boolean isClose = false; // If Connection commit, then Connection is close.

    public SimpleArthurConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean getAutoCommit() {
        return autoCommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Object> select(String query, Class<?> clazz) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getResultSet(resultSet, clazz);
        } catch (SQLException e) {
            this.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        this.close();
        try {
            connection.commit();
        } catch (SQLException e) {
            this.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        this.close();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public void close() {
        this.isClose = true;
    }

    private List<Object> getResultSet(ResultSet resultSet, Class<?> clazz) {
        try {
            List result = new ArrayList();

            List<String> fieldNames = new ArrayList<>();
            for (Field field : clazz.getFields()) {
                fieldNames.add(field.getName());
            }
            while (resultSet.next()) {
                for (String fieldName : fieldNames) {
                    result.add(resultSet.getObject(fieldName));
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
