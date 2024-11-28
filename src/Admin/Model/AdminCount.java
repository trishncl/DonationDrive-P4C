package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Donor.Database.DatabaseConnection;
import java.util.Scanner;

public class AdminCount {
    public static void countDonors() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("+====================================================================+");
        System.out.println("|                                                                    |");
        System.out.println("+====================================================================+");
        System.out.println(  "  1. Count all registered donors");
        System.out.println(  "  2. Count how many monetary donations were made");
        System.out.println(  "  3. Count how many in-kind donations were made");
        System.out.println(  "  4. Count how many donated to a specific organization");
        System.out.println(  "  5. Back");
        System.out.println("+====================================================================+");
        System.out.print(    "  Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        System.out.println("+====================================================================+");

        try (Connection conn = DatabaseConnection.getConnection()) {
            switch (choice) {
                case 1:
                    countAllDonors(conn);
                    break;
                case 2:
                    countMonetaryDonors(conn);
                    break;
                case 3:
                    countInKindDonors(conn);
                    break;
                case 4:
                    countDonorsByOrganization(conn, scanner);
                    break;
                case 5:
                    AdminMenu.showAdminMenu();
                default:
                    System.out.println("Invalid choice, please enter a number between 1 and 4.");
                    countDonors();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to the database.");
        }
    }

    private static void countAllDonors(Connection conn) throws SQLException {
        String query = "SELECT COUNT(DISTINCT DonorId) FROM Donations";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int donorCount = rs.getInt(1);
                System.out.println("  Total registered donors: " + donorCount);
            }
        }
    }

    private static void countMonetaryDonors(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM Donations WHERE DonationType = 'Monetary'";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int monetaryDonors = rs.getInt(1);
                System.out.println("  Total monetary donations made: " + monetaryDonors);
            }
        }
    }

    private static void countInKindDonors(Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM Donations WHERE DonationType = 'In-kind'";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int inKindDonors = rs.getInt(1);
                System.out.println("  Total in-kind donations made: " + inKindDonors);
            }
        }
    }

    private static void countDonorsByOrganization(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("  Enter the Organization ID to count donors: ");
        int organizationId = scanner.nextInt();

        String query = "SELECT COUNT(DISTINCT d.DonorId) " +
                    "FROM Donations d " +
                    "WHERE d.OrganizationId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, organizationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int donorsCount = rs.getInt(1);
                    System.out.println("  Total donors who donated to organization ID " + organizationId + ": " + donorsCount);
                }
            }
        }
    }
}
