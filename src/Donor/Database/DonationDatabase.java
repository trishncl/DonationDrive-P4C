package Donor.Database;

import Donor.Model.AnimalWelfareOrgManager;
import Donor.Model.Donation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonationDatabase {
    // Saves the donation data to the database and returns the donationId
    public int saveToDatabase(int userId, String refNumber, Donation donation, String organization) {
        int donationId = -1;
        // Get the organization ID based on the provided organization name
        int organizationId = AnimalWelfareOrgManager.getOrganizationId(organization);
        String insertDonationSQL = null;

        if ("monetary".equalsIgnoreCase(donation.getType())) {
            insertDonationSQL = "INSERT INTO Donations "
                    + "(DonorId, MonetaryRef, DonationType, ModeOfPayment, Amount, Status, OrganizationId, date_time) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        } else if ("in-kind".equalsIgnoreCase(donation.getType())) {
            insertDonationSQL = "INSERT INTO Donations "
                    + "(DonorId, InKindRef, DonationType, Item, NoOfItems, Delivery, Status, OrganizationId, date_time) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        } else {
            System.err.println("Unknown donation type: " + donation.getType());
            return donationId;
        }

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertDonationSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);   

            if ("monetary".equalsIgnoreCase(donation.getType())) {
                pstmt.setString(2, refNumber);  
                pstmt.setString(3, donation.getType());
                pstmt.setString(4, donation.getModeOfPayment());  
                pstmt.setDouble(5, donation.getAmount());
                String status = donation.getStatus() != null ? donation.getStatus() : "Pending";
                pstmt.setString(6, status); 
                pstmt.setInt(7, organizationId); 
            } else if ("in-kind".equalsIgnoreCase(donation.getType())) {
                pstmt.setString(2, refNumber);  
                pstmt.setString(3, donation.getType());
                pstmt.setString(4, donation.getItem());  
                pstmt.setInt(5, donation.getNoOfItems()); 
                pstmt.setString(6, donation.getDelivery()); 
                String status = donation.getStatus() != null ? donation.getStatus() : "Pending";
                pstmt.setString(7, status);
                pstmt.setInt(8, organizationId);
            }

            pstmt.executeUpdate();
            var rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                donationId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donationId;
    }
}
