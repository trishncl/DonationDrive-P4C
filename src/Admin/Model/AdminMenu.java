package Admin.Model;

import java.util.Scanner;

import Donor.Model.AnimalWelfareOrgManager;
import Utils.Utils;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);

    // Method to display the Admin menu and handle the user's choice
    public static void showAdminMenu() {
        System.out.println("+====================================================================+");
        System.out.println("|                   ---------- * MENU * ----------                   |");
        System.out.println("+====================================================================+");
        System.out.println(  "  1. Show Donation History");
        System.out.println(  "  2. Search Donation");
        System.out.println(  "  3. Update Donation Status");
        System.out.println(  "  4. Delete Donation");
        System.out.println(  "  5. Show Donor Profile");
        System.out.println(  "  6. Show Animal Welfare Organizations");
        System.out.println(  "  7. Count Categories");
        System.out.println(  "  8. Sum of Donation Amount");
        System.out.println(  "  9. Back");
        System.out.println("+====================================================================+");
        System.out.print("  Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Utils.clearScreen();

        switch(choice) {
            case 1:
                History.showDonationHistory();
                returnAdminMenu();
                break;
            case 2:
                AdminSearch.searchDonations();
                returnAdminMenu();
                break;
            case 3:
                AdminUpdate.updateDonationStatus();
                returnAdminMenu();
                break;
            case 4:
                AdminDelete.deleteDonations();
                returnAdminMenu();
                break;
            case 5:
                AdminProfile.showUserProfile();
                returnAdminMenu();
                break;
            case 6:
                AnimalWelfareOrgManager.printAllOrganizations();
                returnAdminMenu();
                break;
            case 7:
                AdminCount.countDonors();
                returnAdminMenu();
                break;
            case 8:
                AdminSum.displayDonationSummary();
                returnAdminMenu();
                break;
            case 9:
                Utils.printuserRoles();
            default:
                System.out.println("Invalid choice. Please try again.");
                showAdminMenu();
        }
    }

    // Method to display the login header for admin login
    public static void loginHeader() {
        System.out.println("+=========================================================+");
        System.out.println("|               ----- * LOGIN ADMIN * -----               |");
        System.out.println("+=========================================================+");
    }

    // Method to ask if the admin wants to return to the Admin Menu or exit
    public static void returnAdminMenu() {
        System.out.print("\nReturn to Admin Menu? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase(); 
        
        if ("yes".equalsIgnoreCase(response)) {
            System.out.println("Returning to Admin Menu...");
            Utils.loadScreen();
            Utils.clearScreen();
            showAdminMenu();
        } else if ("no".equalsIgnoreCase(response)) {
            Utils.clearScreen();
            Utils.exit();
            System.exit(0);
        } else {
            System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            Utils.clearScreen();
            returnAdminMenu(); 
        }
    }
}
