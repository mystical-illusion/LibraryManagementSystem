package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void saveTransaction(String transactionId, int memberId,
            String bookId, String issueDate) {
        String sql = "INSERT INTO transactions (transaction_id, member_id, " +
                "book_id, issue_date, return_date, fine_amount) " +
                "VALUES (?, ?, ?, ?, null, 0)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, transactionId);
            stmt.setInt(2, memberId);
            stmt.setString(3, bookId);
            stmt.setString(4, issueDate);
            stmt.executeUpdate();
            System.out.println("Transaction saved to DB: " + transactionId);
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    public void updateReturn(String transactionId, String returnDate, double fine) {
        String sql = "UPDATE transactions SET return_date = ?, " +
                "fine_amount = ? WHERE transaction_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, returnDate);
            stmt.setDouble(2, fine);
            stmt.setString(3, transactionId);
            stmt.executeUpdate();
            System.out.println("Return updated in DB: " + transactionId);
        } catch (SQLException e) {
            System.err.println("Error updating return: " + e.getMessage());
        }
    }
}