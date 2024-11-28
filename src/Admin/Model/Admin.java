package Admin.Model;

import Donor.Model.Donor;
import Utils.Utils;

import java.sql.*;
import java.util.Scanner;

public class Admin {
    public static Donor donor;
    public static Connection connection;
    public static Admin admin;
    private static Scanner scanner = new Scanner(System.in);
    final static String ADMIN_EMAIL = "adminpawsforacause@gmail.com";
    final static String ADMIN_PASSWORD = "admin@pawsforacause_222";

    // Method to display the admin login screen and authenticate the admin
    public static void adminProfile() {
        AdminMenu.loginHeader();
        System.out.print("  Email: ");
        String email = scanner.nextLine();
    
        System.out.print("  Password: ");
        String password = scanner.nextLine();
        System.out.println("+=========================================================+");

        System.out.println("Please wait, authenticating user...");
        Utils.loadScreen();

        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("\nLogin successful! Welcome Admin to Paws for a Cause!");
            Utils.loadScreen();
            Utils.clearScreen();
            AdminMenu.showAdminMenu();
        } else {
            System.out.println("\nInvalid email or password.");
            Utils.loadScreen();
            Utils.clearScreen();
            adminProfile();
        }
    }
}
