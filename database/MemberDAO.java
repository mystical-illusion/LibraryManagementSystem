package database;

import models.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MemberDAO {
    private Connection connection;

    public MemberDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void saveMember(Member member, String memberType) {
        // Check if member already exists!
        String checkSql = "SELECT member_id FROM members WHERE member_id = ?";
        try {
            PreparedStatement checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setInt(1, member.getMemberId());
            java.sql.ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Member already exists in DB: " + member.getMemberId());
                return; // skip insert!
            }
        } catch (SQLException e) {
            System.err.println("Error checking member: " + e.getMessage());
        }

        // If not exists, insert!
        String sql = "INSERT INTO members (member_id, name, department, email, member_type) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, member.getMemberId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getDepartment());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, memberType);
            stmt.executeUpdate();
            System.out.println("Member saved to DB: " + member.getName());
        } catch (SQLException e) {
            System.err.println("Error saving member: " + e.getMessage());
        }
    }

}