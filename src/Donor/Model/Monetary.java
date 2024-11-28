package Donor.Model;

import java.util.Scanner;

import Donor.Database.DonationDatabase;
import Donor.Database.MonetaryDatabase;
import Utils.Utils;

public class Monetary extends DonationType {
    private static Scanner scanner = new Scanner(System.in);

    // Constructor to initialize a Monetary donation; ensures the donation type is "monetary".
    public Monetary(Donation donation, int user) {
        super(donation, user);
        if (!"monetary".equals(donation.getType())) {
            throw new IllegalArgumentException("Donation type must be 'monetary'.");
        }
    }
    
    @Override
    // Displays the information provided by the user and confirm it to process their donation
    public void confirmAndProcessDonation() {
        System.out.println("+==============================================================+");
        System.out.println("|               ----- * DONATION DETAILS * -----               |");
        System.out.println("+==============================================================+");
        String refNumber = Receipt.generateReferenceNumber();
        System.out.println("  Reference Number: " + refNumber);
        System.out.println("  Donor Name: " + donation.getDonorName());
        System.out.println("  Mode of Payment: " + donation.getModeOfPayment());
        System.out.printf("  Amount: Php %.2f\n", donation.getAmount());
        System.out.println("  Organization: " + donation.getOrganization());
        System.out.println("  Message: " + (donation.getMessage().isEmpty() ? "No message provided" : donation.getMessage()));
        System.out.println("+==============================================================+");

    
        System.out.print("\nAre you sure you want to proceed with this monetary donation? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
    
        if ("yes".equalsIgnoreCase(response)) {
            processDonation(refNumber);
        } else if ("no".equalsIgnoreCase(response)) {
            System.out.println("Returning to the donation type selection...");
            Utils.loadScreen();
            Utils.clearScreen();
            Utils.donationType();
        } else {
            System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            return;
        }
    }
    
    // Processes the monetary donation by saving it to the database.
    private void processDonation(String refNumber) {
        DonationDatabase donationDb = new DonationDatabase();
        int userId = Donor.getLoggedInUserId();

        // Retrieve the organization ID associated with the chosen organization
        int organizationId = AnimalWelfareOrgManager.getOrganizationId(donation.getOrganization());

        // Save monetary donation details in the monetary donations database
        MonetaryDatabase monetaryDb = new MonetaryDatabase();
        monetaryDb.saveMonetaryDonation(refNumber, donation.getDonorName(), userId, donation.getAmount(), donation.getModeOfPayment(), organizationId);

        // Save general donation details and retrieve the donation ID
        int donationId = donationDb.saveToDatabase(userId, refNumber, donation, donation.getOrganization());
    
        if (donationId != -1) {
            System.out.println("Donation successfully processed!");
            Utils.loadScreen();
            Utils.clearScreen();
            Receipt.printReceipt(donation.getDonorName(), donation.getType(), donation.getModeOfPayment(), donation.getAmount(), null, 0, donation.getOrganization(), donation.getMessage());
        } else {
            System.out.println("Failed to process donation.");
        }
    }
    
    // Handles the creation of a monetary donation by prompting the user for input.
    public static void MonetaryDonation() {
        System.out.println("+===============================================================+");
        System.out.println("|               ----- * MONETARY DONATION * -----               |");
        System.out.println("+===============================================================+");
        
        System.out.print("  Donor Name: ");
        String donorName = scanner.nextLine();

        System.out.println("  GCash or PayMaya");
        System.out.print("  Mode of Payment: ");
        String modeOfPayment = scanner.nextLine();

        System.out.print("  Donation Amount (in PHP): ");
        double amount = Double.parseDouble(scanner.nextLine());
        
        System.out.print("  Message (optional): ");
        String message = scanner.nextLine();

        AnimalWelfareOrgManager.orgProcess();
        String organization = AnimalWelfareOrgManager.getOrganization();

        Donation monetaryDonation = new Donation(donorName, amount, modeOfPayment, message, organization);
        int user = Donor.getLoggedInUserId();

        // Create the Monetary object and confirm donation
        Monetary monetary = new Monetary(monetaryDonation, user);
        monetary.confirmAndProcessDonation();
    }
}
