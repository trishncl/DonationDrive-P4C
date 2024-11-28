package Donor.Model;

public class Donation {
    private String donorName;
    private double amount; // For monetary donations
    private String item; // For in-kind donations
    private int noOfItems; 
    private String type; // "monetary" or "in-kind"
    private String modeOfPayment; // e.g. "gcash" or "paymaya"
    private String delivery; // e.g. "pickup", "delivery"
    private String message; // optional
    private String organization;
    private int organizationId;
    private String status;
    private int userId;

    // Constructor for monetary donations
    public Donation(String donorName, double amount, String modeOfPayment, String message, String organization){
        validateAmount(amount);
        this.donorName = donorName;
        this.amount = amount;
        this.type = "monetary";
        this.modeOfPayment = modeOfPayment;
        this.item = null;
        this.noOfItems = 0;
        this.delivery = null;
        this.organization = organization;
        this.organizationId = AnimalWelfareOrgManager.getOrganizationId(organization);
        this.message = message;
    }

    // Constructor for in-kind donations
    public Donation(String donorName, String item, int noOfItems, String delivery, String message, String organization){
        this.donorName = donorName;
        this.item = item;
        this.noOfItems = noOfItems;
        this.type = "in-kind";
        this.amount = 0.00;
        this.modeOfPayment = null;
        this.delivery = delivery;
        this.organization = organization;
        this.organizationId = AnimalWelfareOrgManager.getOrganizationId(organization);
        this.message = message;
    }

    // Validates that the donation amount is non-negative
    private void validateAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
    }

    // Getters; returns the desired value
    public String getDonorName() { return donorName; }
    public double getAmount() { return amount; }
    public String getItem() { return item; }
    public int getNoOfItems() { return noOfItems; }
    public String getType() { return type; }
    public String getModeOfPayment() { return modeOfPayment; }
    public String getDelivery() { return delivery; }
    public String getOrganization() { return organization; }
    public int getOrganizationId() { return organizationId; }
    public String getMessage() { return message; }
    public int getUserId() { return userId; }
    public String getStatus() { return status; }

    // Setters; updates the desired value
    public void setAmount(double amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public void setItem(String item) { this.item = item; }
    public void setNoOfItems(int noOfItems) {
        if (noOfItems < 0) {
            throw new IllegalArgumentException("Number of items must be non-negative.");
        }
        this.noOfItems = noOfItems;
    }
    public void setModeOfPayment(String modeOfPayment) { this.modeOfPayment = modeOfPayment; }
    public void setDelivery(String delivery) { this.delivery = delivery; }
    public void setOrganization(String organization) { this.organization = organization; }
    public void setMessage(String message) { this.message = message; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setStatus(String status) { this.status = status; }
}