package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import Donor.Database.DatabaseConnection;

public class AdminUpdate {
    private static Scanner scanner = new Scanner(System.in);
    
    // Method to update the donation status in the database
    public static void updateDonationStatus() {
        History.showDonationHistory();
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("  Donation ID: ");
            int donationId = Integer.parseInt(scanner.nextLine());

            System.out.print("  New status: ");
            String newStatus = scanner.nextLine();

            String updateQuery = "UPDATE Donations SET Status = ? WHERE DonationId = ? AND Status = 'Pending'";

            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, donationId);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("  Donation status updated successfully.");
                } else {
                    System.out.println("  No matching pending donations found for the provided Donation ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("  Error updating donation status.");
        } catch (NumberFormatException e) {
            System.out.println("  Invalid Donation ID. Please enter a valid numeric value.");
            updateDonationStatus();
        }
    }
}
