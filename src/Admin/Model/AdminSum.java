package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Donor.Database.DatabaseConnection;

public class AdminSum {
    public static void displayDonationSummary() {
        String query = "SELECT " +
                    "SUM(CASE WHEN ? = 0 THEN Amount ELSE NULL END) AS total_donations, " +
                    "SUM(CASE WHEN id = ? THEN Amount ELSE NULL END) AS organization_donations " +
                    "FROM Monetary";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            Scanner scanner = new Scanner(System.in);

            // Admin chooses between all donations or a specific organization
            System.out.print("Do you want to see donations for all organizations? (yes/no): ");
            String choice = scanner.nextLine();

            int orgId = 0; // Default: 0 means all organizations
            if ("no".equalsIgnoreCase(choice)) {
                System.out.print("Enter the Organization ID to filter donations: ");
                orgId = scanner.nextInt();
            }

            // Set the parameters
            stmt.setInt(1, orgId == 0 ? 0 : orgId); // First parameter: Show all if orgId is 0
            stmt.setInt(2, orgId); // Second parameter: Filter by Organization ID

            ResultSet rs = stmt.executeQuery();

            // Display results
            if (rs.next()) {
                double totalDonations = rs.getDouble("total_donations");
                double organizationDonations = rs.getDouble("organization_donations");

                System.out.println("+==============================================================+");
                System.out.println("|                   DONATION SUMMARY                           |");
                System.out.println("+==============================================================+");
                if (orgId == 0) {
                    System.out.printf("  Total Donations: Php %.2f \n", totalDonations);
                } else {
                    System.out.printf("  Donations for Organization (ID: %d): Php %.2f \n", orgId, organizationDonations);
                }
                System.out.println("+==============================================================+");
            } else {
                System.out.println("No donations found for the specified criteria.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving donation summary.");
        }
    }
}
