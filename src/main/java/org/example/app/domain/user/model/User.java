package org.example.app.domain.user.model;

public class User {

    private String userId;
    public String password;
    public String name;

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
