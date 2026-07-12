package database;

import models.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO {
    private Connection connection;

    public BookDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void saveBook(Book book, String category) {
        String sql = "INSERT INTO books (book_id, title, author, isbn, " +
                "publisher, edition, shelf_location, is_available, category) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, book.getBookId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setString(5, book.getPublisher());
            stmt.setString(6, book.getEdition());
            stmt.setString(7, book.getShelfLocation());
            stmt.setBoolean(8, book.isAvailable());
            stmt.setString(9, category);
            stmt.executeUpdate();
            System.out.println("Book saved to DB: " + book.getBookId());
        } catch (SQLException e) {
            System.err.println("Error saving book: " + e.getMessage());
        }
    }

    public void updateAvailability(String bookId, boolean isAvailable) {
        String sql = "UPDATE books SET is_available = ? WHERE book_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBoolean(1, isAvailable);
            stmt.setString(2, bookId);
            stmt.executeUpdate();
            System.out.println("Book availability updated: " + bookId);
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
        }
    }
}