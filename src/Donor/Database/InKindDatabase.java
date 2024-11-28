package Donor.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InKindDatabase extends DonationDatabase {
    // Saves an in-kind donation to the database
    public void saveInKindDonation(String refNumber, String donorName, int userId, String item, int quantity, String delivery, int organizationId) {
        String sql = "INSERT INTO InKind (Ref_number, DonorId, Item, NoOfItems, Delivery, OrgId) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, refNumber);
            pstmt.setInt(2, userId);
            pstmt.setString(3, item);
            pstmt.setInt(4, quantity);
            pstmt.setString(5, delivery);
            pstmt.setInt(6, organizationId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("In-kind donation saved successfully.");
            } else {
                System.out.println("Failed to save in-kind donation.");
            }
        } catch (SQLException e) {
            System.out.println("Error saving min-kind donation: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}
