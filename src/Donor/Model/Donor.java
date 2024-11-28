package Donor.Model;

import java.util.Scanner;
import javax.swing.JOptionPane;
import Donor.Model.Donor;
import Utils.Utils;
import Donor.Database.DatabaseConnection;

public class Donor extends User{
    private static Scanner scanner = new Scanner(System.in);
    public static Donor donor;

    // Constructor for donor
    public Donor(String role, String name, String address, String phoneNumber, String email, String password, int userId){
        super(role, name, address, phoneNumber, email, password, userId);
    }

    // Displays the donor's profile
    public static void donorProfile() {
        System.out.println("+=================================================================+");
        System.out.println("|                     ----- * PROFILE * -----                     |");
        System.out.println("+=================================================================+");
        System.out.println("  Name: " + donor.getName());
        System.out.println("  Address: " + donor.getAddress());
        System.out.println("  Phone Number: " + donor.getPhoneNumber());
        System.out.println("  Email: " + donor.getEmail());
        System.out.println("  Password: [Hidden]");
        System.out.print("  Would you like to view your password? (yes/no): ");
        String response = scanner.nextLine().trim();

        while (!"yes".equalsIgnoreCase(response) && !"no".equalsIgnoreCase(response)) {
            System.out.println("  Invalid input. Please enter 'yes' or 'no'.");
            System.out.print("  Would you like to view your password? (yes/no): ");
            response = scanner.nextLine().trim();
        }
                
        if ("yes".equalsIgnoreCase(response)) {
            while (true) {
                JOptionPane.showMessageDialog(null, "Your PIN is: " + donor.getPin(), "PIN", JOptionPane.INFORMATION_MESSAGE);
                        
                System.out.print("\n  Enter the PIN to view your password: ");
                String enteredPin = scanner.nextLine();
                
                if (donor.verifyPin(enteredPin)) {
                    System.out.println("  Password: " + donor.getPassword());
                    break;
                } else {
                    System.out.println("  Incorrect PIN. Password not shown.");
                }
            }
        } else {
            System.out.println("  Password not shown.");
        }

        System.out.print("\n   Would you like to update your profile? (yes/no): ");
        response = scanner.nextLine();
        while (!"yes".equalsIgnoreCase(response) && !"no".equalsIgnoreCase(response)) {
            System.out.println("  Invalid input. Please enter 'yes' or 'no'.");
        }
        
        if("yes".equals(response.toLowerCase())){
            Utils.loadScreen();
            Utils.clearScreen();
            updateProfile();
        } else if ("no".equals(response.toLowerCase())) {
            Utils.returnHomePage();
        } 
    }

    // Sets the currently logged-in donor instance; donorInstance the Donor object representing the logged-in user
    public static void setLoggedInDonor(Donor donorInstance) {
        donor = donorInstance;
    }

    // Gets the user ID of the logged-in donor
    public static int getLoggedInUserId() {
        return donor.getUserId();
    }

    // Allows the donor to update their profile details; updates are saved to the database.
    public static void updateProfile() {
        if (donor == null) {
            System.out.println("Error: No donor profile found. Please log in first.");
            return;
        }

        System.out.println("+==========================================================================+");
        System.out.println("|                      ----- * UPDATE PROFILE * -----                      |");
        System.out.println("+==========================================================================+");

        System.out.println("  Which field would you like to update?");
        System.out.println("   1. Name");
        System.out.println("   2. Address");
        System.out.println("   3. Phone Number");
        System.out.println("   4. Email");
        System.out.println("   5. Password");
        System.out.println("   6. Back");
        System.out.println("+--------------------------------------------------------------------------+");
        System.out.print("  Enter the number corresponding to the field: ");
        int choice = scanner.nextInt();

        scanner.nextLine(); 

        switch (choice) {
            case 1:
                System.out.print("  Enter new Name: ");
                String newName = scanner.nextLine();
                donor.setName(newName);
                break;
            case 2:
                System.out.print("  Enter new Address: ");
                String newAddress = scanner.nextLine();
                donor.setAddress(newAddress);
                break;
            case 3:
                System.out.print("  Enter new Phone Number: ");
                String newPhoneNumber = scanner.nextLine();
                donor.setPhoneNumber(newPhoneNumber);
                break;
            case 4:
                System.out.print("  Enter new Email: ");
                String newEmail = scanner.nextLine();
                donor.setEmail(newEmail);
                break;
            case 5:
                System.out.print("  Enter new Password: ");
                String newPassword = scanner.nextLine();
                donor.setPassword(newPassword);
                break;
            case 6:
                Utils.clearScreen();
                donorProfile();
            default:
                System.out.println("  Invalid choice. No changes made.");
                return;
        }

        // Save updated profile to the database
        boolean success = DatabaseConnection.updateDonorProfile(
            donor.getUserId(),
            donor.getName(),
            donor.getAddress(),
            donor.getPhoneNumber(),
            donor.getEmail(),
            donor.getPassword()
        );

        System.out.println(success ? "  Your profile has been updated." : "  An error occurred while updating your profile.");
    }
}