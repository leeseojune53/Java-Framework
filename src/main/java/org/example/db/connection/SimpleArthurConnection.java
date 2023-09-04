package org.example.db.connection;

import org.example.annotataion.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public Object save(Object object) {
        try {
            StringBuilder query = new StringBuilder("INSERT INTO user (");
            for(var field : object.getClass().getDeclaredFields()) {
                query.append(field.getName()).append(", ");
            }

            query.delete(query.length() - 2, query.length());

            query.append(") VALUES (");

            for(var field : object.getClass().getDeclaredFields()) {
                query.append("?, ");
            }

            query.delete(query.length() - 2, query.length());

            query.append(")");

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());

            int index = 1;
            for(var field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                preparedStatement.setObject(index++, field.get(object));
            }

            preparedStatement.executeUpdate();

            return object;
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
            this.rollback();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            this.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Object object, Class<?> clazz) {
        try {
            String idFieldName = Arrays.stream(clazz.getDeclaredFields()).filter(it -> it.isAnnotationPresent(Id.class)).findFirst().orElseThrow(() -> new RuntimeException("Id Field doesn't exist")).getName();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE " + idFieldName + " = ?");

            preparedStatement.setObject(1, object);

            preparedStatement.executeUpdate();
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
            List returnValue = new ArrayList();

            List<String> fieldNames = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                fieldNames.add(field.getName());
            }

            while (resultSet.next()) {
                var newInstance = clazz.getDeclaredConstructor().newInstance();
                for (String fieldName : fieldNames) {
                    var field = newInstance.getClass().getDeclaredField(fieldName);
                    if(!columnIsExist(resultSet, fieldName)) {
                        continue;
                    }
                    field.setAccessible(true);
                    field.set(newInstance, resultSet.getObject(fieldName));
                }
                returnValue.add(newInstance);
            }

            return returnValue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean columnIsExist(ResultSet resultSet, String columnName) {
        try {
            resultSet.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
