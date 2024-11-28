package Donor.Database;

import java.sql.*;

public class DatabaseConnection {
    // Database URL, user, and password for MySQL connection
    private static final String URL = "jdbc:mysql://localhost:3306/pawsitiveimpact_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Method to establish a connection to the database
    public static Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }

    // Method to register a new user (Donor) into the database
    public static boolean registerUser(Donor.Model.User user) {
        Connection conn = DatabaseConnection.getConnection();
        if(conn == null) {
            System.out.println("Database connection failed!");
            return false;
        }

        String query = "INSERT INTO Donors (Name, Address, PhoneNumber, Email, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to update the profile of an existing donor
    public static boolean updateDonorProfile(int userId, String newName, String newAddress, String newPhoneNumber, String newEmail, String newPassword) {
        Connection conn = getConnection();
        if (conn == null) {
            System.out.println("Database connection failed!");
            return false;
        }

        String query = "UPDATE Donors SET Name = ?, Address = ?, PhoneNumber = ?, Email = ?, Password = ? WHERE DonorId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setString(2, newAddress);
            stmt.setString(3, newPhoneNumber);
            stmt.setString(4, newEmail);
            stmt.setString(5, newPassword);
            stmt.setInt(6, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}