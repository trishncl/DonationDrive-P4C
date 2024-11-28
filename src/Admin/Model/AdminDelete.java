package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Donor.Database.DatabaseConnection;

public class AdminDelete {
    private static Scanner scanner = new Scanner(System.in);

    // Method to delete donations that are marked as "Cancelled"
    public static void deleteDonations() {
        History.showDonationHistory();

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.print("  Enter Donation ID to delete (Cancelled only): ");
            int donationId = scanner.nextInt();
            scanner.nextLine();

            String checkQuery = "SELECT Status FROM Donations WHERE DonationId = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, donationId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("Status");
                        if ("Cancelled".equalsIgnoreCase(status)) {
                            String deleteQuery = "DELETE FROM Donations WHERE DonationId = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                                deleteStmt.setInt(1, donationId);
                                int rowsDeleted = deleteStmt.executeUpdate();
                                if (rowsDeleted > 0) {
                                    System.out.println("  Donation with ID " + donationId + " successfully deleted.");
                                } else {
                                    System.out.println("  Failed to delete the donation.");
                                }
                            }
                        } else {
                            System.out.println("  Only cancelled donations can be deleted.");
                        }
                    } else {
                        System.out.println("  Donation ID not found.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("  Error occurred while trying to delete the donation.");
        }
    }
}
