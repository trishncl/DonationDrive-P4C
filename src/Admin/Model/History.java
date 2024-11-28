package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Donor.Database.DatabaseConnection;

public class History {
    // Method to display donation history donated by donors
    public static void showDonationHistory() {
        String query = "SELECT DonationId, DonorId, date_time, DonationType, Status, Amount, NoOfItems, Item, ModeOfPayment, Delivery, OrganizationId FROM Donations;";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {
            System.out.println("+========================================================================================================================================================================+");
            System.out.println("|                                                                        DONATION HISTORY                                                                                |");
            System.out.println("+========================================================================================================================================================================+");
            System.out.println("| Donor ID    | Donation ID | Organization ID | Date & Time            | Type        | Amount / Items             | MOP              | MOS               | Status        |");
            System.out.println("+-------------+-------------+-----------------+------------------------+-------------+----------------------------+------------------+-------------------+---------------+");
            
            boolean hasHistory = false;
            while (rs.next()) {
                hasHistory = true;
                int donationId = rs.getInt("DonationId");
                String dateTime = rs.getString("date_time");
                int userId = rs.getInt("DonorId");
                String type = rs.getString("DonationType");
                String status = rs.getString("Status");
                String amountOrItems = type.equalsIgnoreCase("Monetary") ? 
                    String.format("Php %.2f",  rs.getDouble("Amount")) : 
                    rs.getInt("NoOfItems") + " " + rs.getString("Item");

                String mop = rs.getString("ModeOfPayment");
                String mos = rs.getString("Delivery");
                int organizationId = rs.getInt("OrganizationId");

                System.out.printf("| %-11d | %-11d | %-15d | %-22s | %-11s | %-26s | %-17s| %-18s| %-13s |\n",
                                    userId, donationId, organizationId, dateTime, type, amountOrItems, mop, mos, status);
            }

            if (!hasHistory) {
                System.out.println("|                                                           No donation history available for this user.                                                                 |");
            }
                System.out.println("+========================================================================================================================================================================+");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving donation history.");
        }
    }
}
