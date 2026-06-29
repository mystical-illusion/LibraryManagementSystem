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
    String password = System.getenv("DB_PASSWORD"); // <-- Put your real password here!

    // Private constructor prevents instantiation from other classes
    private DatabaseConnection() {
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/library_db"; // Your DB URL
                String username = "root"; // Your DB Username

                // CORRECT: Explicitly declare the String and grab it from the system map
                String password = System.getenv("DB_PASSWORD");

                // If the environment variable isn't active yet, use your secure fallback
                if (password == null) {
                    password = "maths123";
                }

                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}