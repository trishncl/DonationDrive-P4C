package Donor.Model;

import java.util.Scanner;
import Donor.Database.DatabaseConnection;
import Donor.Database.UserDatabase;
import Utils.Utils;

public class SignUp {
    private static Scanner input = new Scanner(System.in);

    // Handles the user sign-up process by collecting details, validating inputs, and saving the user details to the database.
    public static void signUp() {
        System.out.println("+=======================================================================+");
        System.out.println("|                        ----- * SIGNUP * -----                         |");
        System.out.println("+=======================================================================+");
        System.out.print("  Name: ");
        String name = input.nextLine();
        String firstName = Utils.getFirstName(name);

        System.out.print("  Address: ");
        String address = input.nextLine();

        System.out.println("  Format: xxxx xxx xxxx");
        System.out.print("  Phone Number: ");
        String phoneNumber = input.nextLine();

        while(!UserValidator.isValidPhoneNumber(phoneNumber)){
            System.out.println("  Invalid phone number. Please enter a valid phone number.");
            System.out.print("  Phone Number: ");
            phoneNumber = input.nextLine();
        }

        System.out.print("  Email: ");
        String email = input.nextLine();

        while (!UserValidator.isValidEmail(email)) {
            System.out.println("  Invalid email format. Please enter a valid email address.");
            System.out.print("  Email: ");
            email = input.nextLine();
        }

        System.out.print("  Password: ");
        String password = input.nextLine();

        while (!UserValidator.isValidPassword(password)) {
            System.out.println("  Invalid password format. It must be at least 8 characters long and include a special character.");
            System.out.print("  Password: ");
            password = input.nextLine();
        }
        System.out.println("+=======================================================================+");

        // // Get the logged-in user ID from Donor class
        // int userId = Donor.getLoggedInUserId();
        // Create a new Donor object
        Donor donor = new Donor("Donor", name, address, phoneNumber, email, password, 0);
        boolean isRegistered = DatabaseConnection.registerUser(donor);
        
        System.out.println("  Please wait, registering user...");
        Utils.loadScreen();

        if (isRegistered) {
            int userId = UserDatabase.authenticateUser(email, password);
            donor.setUserId(userId);
            Donor.setLoggedInDonor(donor);
            System.out.println("  \nSign Up Successful! Welcome, " + firstName);
            Utils.loadScreen();
            Utils.clearScreen();
            LogIn.login();
            Utils.donationType();
        } else {
            System.out.println("  Sign Up failed. Please try again.");
        }
    }
}
