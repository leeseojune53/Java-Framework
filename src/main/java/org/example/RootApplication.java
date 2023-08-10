package org.example;

import org.example.db.SessionManager;

public class RootApplication {

    public static void main(String[] args) {


        // service layer

        SessionManager.setDefaultSessionManager();
        SessionManager sessionManager = SessionManager.getSessionManager();
        sessionManager.getTransaction().begin();

        Object selectIdFromTblExtensionApply = sessionManager.getTransaction().getConnection().select("SELECT id FROM tbl_weekend_meal", IdClass.class);

        sessionManager.getTransaction().commit();

        // business logic


    }

    public static class IdClass {
        public String id;
    }

}
