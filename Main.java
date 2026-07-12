import database.DatabaseConnection;
import facade.LibraryFacade;
import java.sql.Connection;
import javax.swing.SwingUtilities;

public class Main {
        public static void main(String[] args) {

                // Step 1 — Verify MySQL Database Connection
                Connection conn = DatabaseConnection.getConnection();
                if (conn == null) {
                        System.err.println("Database connection failed — exiting!");
                        return;
                }

                // Step 2 — System Ready
                System.out.println("=== Library Management System ===");
                System.out.println("=== System Active ===");
                System.out.println("Launching GUI...");

                // Step 3 — Launch GUI on Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                        new LibraryUI().setVisible(true);
                });
        }
}