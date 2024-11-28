package Donor.Database;

import java.sql.*;

import Donor.Model.Donor;

public class UserDatabase {
    // Authenticates a user based on email and password
    public static int authenticateUser(String email, String password) {
        int userId = -1;
        String query = "SELECT DonorId FROM Donors WHERE Email = ? AND Password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    userId = rs.getInt("DonorId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

     // Retrieves a Donor object by UserId, I tried this method to avoid the NullPointerException
    public static Donor getDonorById(int userId) {
        Donor donor = null;
        String query = "SELECT * FROM Donors WHERE DonorId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String role = "Donor";
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    String phoneNumber = rs.getString("PhoneNumber");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");

                    donor = new Donor(role, name, address, phoneNumber, email, password, userId);
                    donor.setUserId(userId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donor;
    }
}