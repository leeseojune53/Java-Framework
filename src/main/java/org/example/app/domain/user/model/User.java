package org.example.app.domain.user.model;

import org.example.annotataion.Id;

public class User {

    @Id
    private String user_id;

    private String password;
    private String name;

    public User(String userId, String password, String name) {
        this.user_id = userId;
        this.password = password;
        this.name = name;
    }

    public User() {}

    public String getUserId() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
