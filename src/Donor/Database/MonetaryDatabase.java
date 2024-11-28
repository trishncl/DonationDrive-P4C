package Donor.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MonetaryDatabase extends DonationDatabase {
     // Saves a monetary donation to the database
    public void saveMonetaryDonation(String refNumber, String donorName, int userId, double amount, String modeOfPayment, int organizationId) {
        String sql = "INSERT INTO Monetary (Ref_number, DonorId, Amount, ModeOfPayment, OrgId) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, refNumber);
            pstmt.setInt(2, userId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, modeOfPayment);
            pstmt.setInt(5, organizationId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Monetary donation saved successfully.");
            } else {
                System.out.println("Failed to save monetary donation.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving monetary donation: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}
