package Donor.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Receipt {

    // Prints the donation receipt with the provided details
    public static void printReceipt(String donorName, String donationType, String mopOrmos, double amount, String item, int quantity, String organization, String message) {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        // Generate a random reference number (e.g., REF-123456)
        String referenceNumber = generateReferenceNumber();

        System.out.println("+=======================================================+");
        System.out.println("|                   DONATION RECEIPT                    |");
        System.out.println("+=======================================================+");
        System.out.println("  Date & Time: " + formattedDate);
        System.out.println("  Reference No.: " + referenceNumber);
        System.out.println("  Donor Name: " + donorName);
        System.out.println("  Donation Type: " + donationType);
        
        // If monetary, display the amount, else display the item description
        if (donationType.equalsIgnoreCase("monetary")) {
            System.out.println("  Mode of Payment: " + mopOrmos);
            System.out.printf("  Amount Donated: Php %.2f\n", amount);
        } else {
            System.out.println("  Mode of Shipment: " + mopOrmos);
            System.out.println("  Item Donated: " + item);
            System.out.println("  Quantity: " + quantity);
        }

        System.out.println("  Organization: " + organization);
        System.out.println("  Message: " + (message.isEmpty() ? "No message provided" : message));
        System.out.println("+======================================================+");
        System.out.println("         Thank you for your generous donation!");
        System.out.println("+======================================================+");
    }

    // Helper method to generate a reference number
    public static String generateReferenceNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);  // Generates a 6-digit number
        return "REF-" + number;
    }
}
