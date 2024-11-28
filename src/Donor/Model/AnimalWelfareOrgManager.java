package Donor.Model;

import java.util.*; // arraylist, list, scanner

import Donor.Database.DatabaseConnection;
import Utils.Utils;

import java.sql.*;


public class AnimalWelfareOrgManager {
    // list to hold the names of organizations
    private List<String> organizations;
    private static final String URL = "jdbc:mysql://localhost:3306/pawsitiveimpact_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static String selectedOrg;
    private static Scanner scanner = new Scanner(System.in);
    private Connection connection;

    // Initializes the connection and loads organizations into the list
    public AnimalWelfareOrgManager() {
        organizations = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            loadAllOrganizations();
        } catch (SQLException e) {
            System.out.println("Error initializing database connection: " + e.getMessage());
        }
    }

    // Checks if an organization exists in the database; prompts to add if not
    public void checkOrg(String orgName) {
        try {
            String query = "SELECT Name FROM Organizations WHERE Name = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, orgName);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        System.out.println("Organization exists. Proceeding with donation...");
                    } else {
                        System.out.print("Organization does not exist. Would you like to add this organization? (yes/no)");
                        String response = scanner.nextLine();
                        if (response.equalsIgnoreCase("yes")) {
                            addOrganization(orgName);
                            System.out.println("Organization added: " + orgName);
                        } else {
                            System.out.println("Donation cancelled.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error adding organization to the database: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking organization in the database: " + e.getMessage());
        }
    }

    // Adds a new organization to the database and updates the list
    private void addOrganization(String orgName) {
        try {
            String query = "INSERT INTO organizations (Name) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, orgName);
                statement.executeUpdate();
                organizations.add(orgName);
            }
        } catch (SQLException e) {
            System.out.println("Error adding organization to the database: " + e.getMessage());
        }
    }

    // Prints all organizations from the database in a formatted table
    public static void printAllOrganizations() {
        String query = "SELECT id, name FROM Organizations";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {
            System.out.println("+==================================================================+");
            System.out.println("|                    ANIMAL WELFARE ORGANIZATIONS                  |");
            System.out.println("+----+-------------------------------------------------------------+");
            System.out.println("| id | name                                                        |");
            System.out.println("+----+-------------------------------------------------------------+");

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");

                System.out.printf("| %-2d | %-59s |\n", id, name);
            }

            if (!found) {
                System.out.println("|                    No organizations found.                       |");
            }
            System.out.println("+----+-------------------------------------------------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving organizations.");
        }
    }

    // Loads all organizations names from the database into the list
    private void loadAllOrganizations() {
        try {
            String query = "SELECT Name FROM organizations";
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    organizations.add(resultSet.getString("Name"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading organizations from the database: " + e.getMessage());
        }
    }

    // Checks if an organization exists in list
    public boolean organizationExists(String orgName) {
        return organizations.contains(orgName);
    }

    // Retrieves the ID of an organization from the database
    public static int getOrganizationId(String organization) {
        String sql = "SELECT id FROM organizations WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
                pstmt.setString(1, organization);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.out.println("Organization not found.");
                return 0; 
            }
        } catch (SQLException e) {
            return 0;
        }
    }

    // Handles the process of selecting or adding an organization for donations
    public static void orgProcess() {
        System.out.print("Show all Animal Welfare Organization (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        AnimalWelfareOrgManager manager = new AnimalWelfareOrgManager();
        if (choice.equals("yes")) {
            printAllOrganizations();
        } else if (choice.equals("no")) {
            System.out.println("Proceeding without listing organizations...");
            Utils.loadScreen(); 
        } else {
            System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            return; 
        }

        System.out.print("\nEnter the name of the organization you want to donate to: ");
        selectedOrg = scanner.nextLine();

        if (manager.organizationExists(selectedOrg)) {
            System.out.println("Organization exists. Proceeding with donation...");
            Utils.loadScreen();
            Utils.clearScreen();
        } else {
            System.out.print("Organization does not exist. Would you like to add this organization? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                manager.addOrganization(selectedOrg);
            } else if (response.equalsIgnoreCase("no")) {
                System.out.println("Proceeding with the donation...");
                Utils.loadScreen();
                Utils.clearScreen();
            } else {
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                return;
            }
        }

        manager.closeConnection();
    }

    // Returns the selected organization name
    public static String getOrganization() {
        return selectedOrg;
    }

    // Closes the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }
}