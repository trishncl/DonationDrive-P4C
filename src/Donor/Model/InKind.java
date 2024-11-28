package Donor.Model;

import java.util.Scanner;
import Donor.Database.DonationDatabase;
import Donor.Database.InKindDatabase;
import Utils.Utils;

public class InKind extends DonationType {
    private static Scanner scanner = new Scanner(System.in);

    // Constructor for InKind class, validates that the donation type is in-kind before initializing.
    public InKind(Donation donation, int user) {
        super(donation, user);
        if (!"in-kind".equals(donation.getType())) {
            throw new IllegalArgumentException("Donation type must be 'in-kind'.");
        }
    }

    @Override
    // Confirms the donation details with the user and processes the donation
    public void confirmAndProcessDonation() {
        System.out.println("+==============================================================+");
        System.out.println("|               ----- * DONATION DETAILS * -----               |");
        System.out.println("+==============================================================+");
        String refNumber = Receipt.generateReferenceNumber();
        System.out.println("  Reference Number: " + refNumber);
        System.out.println("  Donor Name: " + donation.getDonorName());
        System.out.println("  Item: " + donation.getItem());
        System.out.println("  Quantity: " + donation.getNoOfItems());
        System.out.println("  Mode of Shipment: " + donation.getDelivery());
        System.out.println("  Organization: " + donation.getOrganization());
        System.out.println("  Message: " + (donation.getMessage().isEmpty() ? "No message provided" : donation.getMessage()));
        System.out.println("+==============================================================+");
            
        System.out.print("\nAre you sure you want to proceed with this in-kind donation? (yes/no): ");
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
    
    // Processess the donation by saving it to the database and generating receipt.
    private void processDonation(String refNumber) {
        DonationDatabase donationDb = new DonationDatabase();
        int userId = Donor.getLoggedInUserId();

        // Get organization ID from the organization name
        int organizationId = AnimalWelfareOrgManager.getOrganizationId(donation.getOrganization());

        InKindDatabase inKindDb = new InKindDatabase();
        inKindDb.saveInKindDonation(refNumber, donation.getDonorName(), userId, donation.getItem(), donation.getNoOfItems(), donation.getDelivery(), organizationId);
            
        int donationId = donationDb.saveToDatabase(userId, refNumber, donation, donation.getOrganization());

        if (donationId != -1) {
            System.out.println("Donation successfully processed!");
            Utils.loadScreen();
            Utils.clearScreen();
            Receipt.printReceipt(donation.getDonorName(), donation.getType(), donation.getDelivery(), 0.00, donation.getItem(), donation.getNoOfItems(), donation.getOrganization(), donation.getMessage());
        } else {
            System.out.println("Failed to process donation.");
        }
    }
    
    // Method to collect in-kind donation details from the user and initiate the donation confirmation and processing.
    public static void InKindDonation() {
        System.out.println("+==============================================================+");
        System.out.println("|               ----- * IN-KIND DONATION * -----               |");
        System.out.println("+==============================================================+");
        System.out.print("  Donor Name: ");
        String donorName = scanner.nextLine();

        System.out.print("  Item/s: ");
        String item = scanner.nextLine();

        System.out.print("  Quantity: ");
        int noOfItems = scanner.nextInt();

        scanner.nextLine();

        System.out.println("  \"Standard Shipping, Freight Shipping, Drop-Off, J&T Express\"");
        System.out.print("  Mode of Shipment: ");
        String delivery = scanner.nextLine();

        System.out.print("  Message (optional): ");
        String message = scanner.nextLine();

        AnimalWelfareOrgManager.orgProcess();
        String organization = AnimalWelfareOrgManager.getOrganization();

        Donation inKindDonation = new Donation(donorName, item, noOfItems, delivery, message, organization);
        int user = Donor.getLoggedInUserId();

        // Create the Monetary object and confirm donation
        InKind inKind = new InKind(inKindDonation, user);
        inKind.confirmAndProcessDonation();
    }
}
