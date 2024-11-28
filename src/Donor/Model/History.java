package Donor.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Donor.Database.DatabaseConnection;

public class History {
    // Retrieves and displays the donation history of the logged-in user.
    public static void displayHistory() {
        // Get the ID of the currently logged-in user
        int userId = Donor.getLoggedInUserId();
        String query = "SELECT d.DonationId, COALESCE(d.MonetaryRef, d.InKindRef) AS Ref_number, d.DonationType, d.ModeOfPayment, d.Amount, " +
                        "d.Item, d.NoOfItems, d.Delivery, d.Status, d.date_time, d.OrganizationId, " + 
                        "o.name AS organizations " +
                        "FROM Donations d " +
                        "LEFT JOIN organizations o ON d.OrganizationId = o.id " + 
                        "WHERE d.DonorId = ? ORDER BY d.date_time DESC";

        System.out.println("+===============================================================================================================================================================+");
        System.out.println("|                                                                          DONATION HISTORY                                                                     |");
        System.out.println("+===============================================================================================================================================================+");
        System.out.println("| Ref. No.   | Donation ID | Organization ID | Date & Time             | Type       | Amount / Items   | MOP              | MOS              | Status           |");
        System.out.println("+------------+-------------+-----------------+-------------------------+------------+------------------+------------------+------------------+------------------+");

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            boolean hasHistory = false;
            while (rs.next()) {
                hasHistory = true;
                int donationId = rs.getInt("DonationId");
                int organizationId = rs.getInt("OrganizationId");
                String refNumber = rs.getString("Ref_number");
                String dateTime = rs.getString("date_time");
                String type = rs.getString("DonationType");
                String status = rs.getString("Status");
                String amountOrItems = type.equalsIgnoreCase("Monetary") ? 
                    String.format ("Php %.2f", rs.getDouble("Amount")) : 
                    rs.getInt("NoOfItems") + " " + rs.getString("Item");

                String mop = rs.getString("ModeOfPayment");
                String mos = rs.getString("Delivery");

                System.out.printf("| %-8s | %-11d | %-15d | %-23s | %-11s| %-16s | %-17s| %-17s| %-16s |\n",
                                refNumber, donationId, organizationId, dateTime, type, amountOrItems, mop, mos, status);
            }

            if (!hasHistory) {
                System.out.println("|                                                            No donation history available for this user.                                                       |");
            }

            System.out.println("+===============================================================================================================================================================+");
            System.out.println(" ".repeat(60) + "Thank you for supporting Paws for a Cause!");
            System.out.println("+===============================================================================================================================================================+");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving donation history.");
        }
    }
}
