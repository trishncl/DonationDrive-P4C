package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Donor.Database.DatabaseConnection;
import Utils.Utils;

public class AdminSearch {
    public static Connection connection;
    private static Scanner scanner = new Scanner(System.in);

    // Method to search for donations based on admin input
    public static void searchDonations() {
        try {
            System.out.println("+=======================================================================+");
            System.out.println("|               ---------- * SEARCH CRITERIA * ----------               |");
            System.out.println("|                        * Leave blank to skip *                        |");
            System.out.println("+=======================================================================+");

            System.out.print("  Would you like to view the history (yes/no): ");
            String choice = scanner.nextLine();

            if ("yes".equalsIgnoreCase(choice)) {
                History.showDonationHistory();
            } else if ("no".equalsIgnoreCase(choice)) {
                System.out.println("  Proceeding with the search criteria...");
                Utils.loadScreen();
            } else {
                System.out.println("  Invalid choice. Please enter 'yes' or 'no'.");
                searchDonations();
            }

            System.out.print("\n  Donor ID: ");
            String userIdinput = scanner.nextLine();
            Integer userId = userIdinput.isEmpty() ? null : Integer.parseInt(userIdinput);

            System.out.print("  Donation ID: ");
            String donationIdinput = scanner.nextLine();
            Integer donationId = donationIdinput.isEmpty() ? null : Integer.parseInt(donationIdinput);

            System.out.print("  Organization ID: ");
            String orgIdInput = scanner.nextLine();
            Integer organizationId = orgIdInput.isEmpty() ? null : Integer.parseInt(orgIdInput);

            System.out.print("  Donation Type: ");
            String donationType = scanner.nextLine();

            System.out.print("  Amount Value: ");
            String amountInput = scanner.nextLine().trim();
            Double amount = amountInput.isEmpty() ? null : Double.parseDouble(amountInput);
            
            System.out.println("  (<, >, =, <=, >=)");
            System.out.print("  Amount Condition: ");
            String amountCondition = scanner.nextLine();

            System.out.print("  Items: ");
            String items = scanner.nextLine();

            System.out.print("  Mode of Payment: ");
            String modeOfPayment = scanner.nextLine();

            System.out.print("  Mode of Shipment: ");
            String modeOfShipment = scanner.nextLine();

            System.out.print("  Status: ");
            String status = scanner.nextLine();

            System.out.print("  Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            System.out.println("\nSearching for donation(s)...");
            Utils.loadScreen();
            Utils.clearScreen();

            // Base query; start building the SQL query
            StringBuilder query = new StringBuilder("SELECT * From Donations WHERE 1=1");

            // Add conditions to the query based on user input
            if (userId != null) query.append(" AND DonorId = ?");
            if (donationId != null) query.append(" AND DonationId = ?");
            if (organizationId != null) query.append(" AND OrganizationId = ?");
            if (!donationType.isEmpty()) query.append(" AND DonationType = ?");
            if (amount != null && !amountCondition.isEmpty()) query.append(" AND Amount ").append(amountCondition).append(" ?");
            if (!items.isEmpty()) query.append(" AND Item = ?");
            if (!modeOfPayment.isEmpty()) query.append(" AND ModeOfPayment = ?");
            if (!modeOfShipment.isEmpty()) query.append(" AND Delivery = ?");
            if (!status.isEmpty()) query.append(" AND Status = ?");
            if (!date.isEmpty()) query.append(" AND date_time LIKE ?");

            try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
                
                // Set the prepared statement parameters based on user input
                int paramidx = 1;
                if (userId != null) pstmt.setInt(paramidx++, userId);
                if (donationId != null) pstmt.setInt(paramidx++, donationId);
                if (organizationId != null) pstmt.setInt(paramidx++, organizationId);
                if (!donationType.isEmpty()) pstmt.setString(paramidx++, donationType);
                if (amount != null) pstmt.setDouble(paramidx++, amount);
                if (!items.isEmpty()) pstmt.setString(paramidx++, items);
                if (!modeOfPayment.isEmpty()) pstmt.setString(paramidx++, modeOfPayment);
                if (!modeOfShipment.isEmpty()) pstmt.setString(paramidx++, modeOfShipment);
                if (!status.isEmpty()) pstmt.setString(paramidx++, status);
                if (!date.isEmpty()) pstmt.setString(paramidx++, date + "%");
                
                // Execute the query and display the results
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("+===================================================================================================================================================================================+");
                    System.out.println("|                                                                              DONATION INFORMATION                                                                                 |");
                    System.out.println("+===================================================================================================================================================================================+");
                    System.out.println("| Ref. No.   | Donation ID | User ID     | Org. ID     | Date & Time             | Type       | Amount / Items          | MOP              | MOS                 | Status           |");
                    System.out.println("+------------+-------------+-------------+-------------+-------------------------+------------+-------------------------+------------------+---------------------+------------------+");                    
                    
                    boolean res = false;
                    while (rs.next()) {
                        res = true;
                        String monetaryRef = rs.getString("MonetaryRef");
                        String inKindRef = rs.getString("InKindRef");
                        String refNumber = (monetaryRef != null) ? monetaryRef : inKindRef;
                        int rDonationId = rs.getInt("DonationId");
                        int rUserId = rs.getInt("DonorId");
                        int rOrgId = rs.getInt("OrganizationId");
                        String rDate = rs.getString("date_time");
                        String rDonationType = rs.getString("DonationType");
                        String rStatus = rs.getString("Status");

                        String amountOrItems = rDonationType.equalsIgnoreCase("Monetary") ?
                            String.format("Php %.2f", rs.getDouble("Amount")) :
                            rs.getString("Item");
                            
                        String rMOP = rs.getString("ModeOfPayment");
                        String rMOS = rs.getString("Delivery");

                        System.out.printf("| %-10s | %-11d | %-11d |  %-10d | %-23s | %-10s | %-23s | %-16s | %-19s | %-16s |\n",
                                        refNumber, rDonationId, rUserId, rOrgId, rDate, rDonationType, amountOrItems, rMOP, rMOS, rStatus);
                    }

                    if (!res) {
                        System.out.println("|                                                                   No donations matched the search criteria.                                                                       |");
                    }
                        System.out.println("+===================================================================================================================================================================================+");
                    AdminMenu.returnAdminMenu();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving donation information");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input. Please ensure User ID, Donation ID, and Amount are valid numbers.");
        }
    }
}
