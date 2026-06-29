package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Lazy initialization holder using Thread-Safe Singleton Pattern
    private static Connection connection = null;

    // Database configurations
    private static final String URL = "jdbc:mysql://localhost:3306/library_management?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "maths123"; // <-- Put your real password here!

    // Private constructor prevents instantiation from other classes
    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        // Explicitly loading the MySQL driver class loaded in classpath
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        System.out.println("🚀 Database Connected Successfully!");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found! Check your classpath settings.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Connection failed! Verify server status, username, and password.");
            e.printStackTrace();
        }
        return connection;
    }
}