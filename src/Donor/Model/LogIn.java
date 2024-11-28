package Donor.Model;

import java.util.Scanner;

import Donor.Database.UserDatabase;
import Utils.Utils;

public class LogIn {
    private static Scanner input = new Scanner(System.in);

    // Handles the user authentication process and prompts the user to enter their credentials
    public static void login() {
        while (true) {
            System.out.println("+====================================================+");
            System.out.println("|                ----- * LOGIN * -----               |");
            System.out.println("+====================================================+");
            System.out.print("  Email: ");
            String email = input.nextLine();

            System.out.print("  Password: ");
            String password = input.nextLine();
            System.out.println("+====================================================+");

            int userId = UserDatabase.authenticateUser(email, password);
            
            System.out.println("Please wait, authenticating user...");
            Utils.loadScreen();

            if (userId != -1) {
                System.out.println("\nLogin successful! Welcome back!");
                Donor donorInstance = UserDatabase.getDonorById(userId); // Retrieve the donor details for the logged-in user
                Donor.setLoggedInDonor(donorInstance); // Set the logged-in donor instance globally
                Utils.loadScreen();
                Utils.clearScreen();
                Utils.donationType();
            } else {
                System.out.println("\nInvalid email or password. Please try again.");
                Utils.loadScreen();
                Utils.clearScreen();
            }
        }
    }
}
