package Utils;

import java.util.Scanner;

import Donor.Model.*;
import Admin.Model.Admin;

public class Utils {
    private static Scanner input = new Scanner(System.in);
    public static int userId;
    public static Donation donation;
    public static Donor donor;
    public static Admin admin;
    
    // Displays the user role options (Donor, Admin, Exit)
    public static void printuserRoles() {
        System.out.println("+============================================+");
        System.out.println("|             ----- * USER * -----           |");
        System.out.println("+==============++=============++=============+");
        System.out.println("|      Donor   ||    Admin    ||     Exit    |");
        System.out.println("+==============++=============++=============+");
        System.out.print(" ".repeat(21) + "");
        String userRole = input.nextLine().trim().toLowerCase();

        Utils.clearScreen();
        if (userRole.equals("donor")) {
            Utils.register();
        } else if (userRole.equals("admin")) {
            Admin.adminProfile();
        } else if (userRole.equals("exit")) {
            exit();
        } else {
            System.out.println("Please choose 'donor' or 'admin'.");
            printuserRoles();
        }
    }

    // Displays registration options for the Donor (Signup, Login, Back)
    public static void register() {
        System.out.println("+============================================+");
        System.out.println("|            ----- * DONOR * -----           |");
        System.out.println("+==============++=============++=============+");
        System.out.println("|     Signup   ||    Login    ||     Back    |");
        System.out.println("+=============================++=============+");

        System.out.print(" ".repeat(21) + "");
        String registerChoice = input.nextLine().trim().toLowerCase();

        clearScreen();
            switch (registerChoice) {
                case "signup":
                    SignUp.signUp();
                    break;
                case "login":
                    LogIn.login();
                    break;
                case "back":
                    printuserRoles();
                    return;
                default:
                    System.out.println("Invalid input. Please choose 'signup', 'login', or 'back'.");
                    register();
                    break;
            }
    }

    // Displays donation type options for the Donor (Monetary, In-kind, History, Profile, Organization, Back)
    public static void donationType() {
        System.out.println("+==========================================================================================+");
        System.out.println("|                                    ----- * DONATION * -----                              |");
        System.out.println("+==============++=============++=============++=============++==============++=============+");
        System.out.println("|   Monetary   ||   In-kind   ||   History   ||   Profile   || Organization ||    Back     |");
        System.out.println("+=============================++=============++=============++==============++=============+");

        String preFilledType = donation != null && donation.getType() != null 
        ? donation.getType().toLowerCase() : "";

        System.out.print(" ".repeat(43) + preFilledType);
        String typeChoice = input.nextLine().trim().toLowerCase();
        
        clearScreen();
            switch (typeChoice) {
                case "monetary":
                    Monetary.MonetaryDonation();
                    Utils.returnHomePage();
                    break;
                case "in-kind":
                    InKind.InKindDonation();
                    Utils.returnHomePage();
                    break;
                case "history":
                    History.displayHistory();
                    Utils.returnHomePage();
                    break;
                case "profile":
                    Donor.donorProfile();
                    Utils.returnHomePage();
                    break;
                case "back":
                    register();
                    Utils.returnHomePage();
                    break;
                case "organization":
                    AnimalWelfareOrgManager.printAllOrganizations();
                    Utils.returnHomePage();
                    break;
                default:
                    System.out.println("Invalid input. Please choose 'monetary', 'in-kind', or 'back'");
                    System.out.println("Returning to the selection...");
                    loadScreen();
                    donationType();
                    break;
            }
    }

    // Extracts and returns the first name from the full name
    public static String getFirstName(String fullName) {
        String[] nameParts = fullName.split(" ");
        
        return nameParts[0];
    }

    // Clears the screan based on the operating system (Windows or Unix-based)
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Windows
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor(); // Unix/Linux
            }
        } catch (Exception e) {
            System.out.println("Could not clear screen.");
        }
        header();
    }

    // Pauses the execution for 3 seconds (to simulate loading screen)
    public static void loadScreen() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Asks the user if they want to return to the home page after completing an action
    public static void returnHomePage() {
        System.out.print("\nReturn to Home Page? (yes/no): ");
        String response = input.nextLine().trim().toLowerCase(); 
        
        if ("yes".equalsIgnoreCase(response)) {
            System.out.println("Returning to Home Page...");
            loadScreen();
            clearScreen();
            donationType();
        } else if ("no".equalsIgnoreCase(response)) {
            clearScreen();
            printuserRoles();
            exit();
            System.exit(0);
        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            clearScreen();
            returnHomePage(); 
        }
    }

    // Prints the header for the application
    public static void header() {
        System.out.println("+============================================================================================================================================================================+");
        System.out.println("|                                                            --------------- * PAWS FOR A CAUSE * ---------------                                                            |");
        System.out.println("+============================================================================================================================================================================+\n");
    }

    // Exits the program and displays a thank you message
    public static void exit() {
        System.out.println(" ".repeat(30) + "+=========================================================================================================+");
        System.out.println(" ".repeat(64) + "Thank you for using Paws for a Cause!");
        System.out.println(" ".repeat(36) + "\"The best way to find yourself is to lose yourself in the service of others.\" - Mahatma Gandhi");
        System.out.println(" ".repeat(30) + "+=========================================================================================================+");
        System.exit(0);
    }
}
