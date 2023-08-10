package org.example;

import org.example.db.SessionManager;

public class RootApplication {

    public static void main(String[] args) {


        // service layer

        SessionManager.getSessionManager().get().getTransaction().begin();

        // business logic

        SessionManager.getSessionManager().get().getTransaction().commit();
    }

}
