package Admin.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Donor.Database.DatabaseConnection;

public class AdminProfile {
    // Method to display all donor profiles
    public static void showUserProfile() {
        String query = "SELECT * FROM Donors";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery()) {
            System.out.println("+=========================================================================================================================================================================================+");
            System.out.println("|                                                                                      DONOR PROFILES                                                                                     |");
            System.out.println("+=========================================================================================================================================================================================+");
            System.out.println("| User ID    | Name                       | Address                                      | Phone Number   | Email                      | Password           | Date Created        | Role  |");
            System.out.println("+------------+----------------------------+----------------------------------------------+----------------+----------------------------+--------------------+---------------------+-------+");

            boolean found = false;
            
            // Loop through the result set and print donor details
            while(rs.next()) {
                found = true;
                int userId = rs.getInt("DonorId");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String dateCreated = rs.getString("CreatedAt");
                String role = rs.getString("Role");

                System.out.printf("| %-10d | %-26s | %-44s | %-14s | %-26s | %-18s | %-14s | %-4s |\n",
                        userId, name, address, phoneNumber, email, password, dateCreated, role);
            }

            if (!found) {
                System.out.println("|                                                                                 No user profiles found.                                                                                 |");
            }
            
            System.out.println("+=========================================================================================================================================================================================+");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving user profiles");
        }
    }
}
