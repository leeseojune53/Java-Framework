package db.connection;

public class ConnectionFactory {

    public static Connection getConnection() {
        return new SimpleConnection();
    }

}
