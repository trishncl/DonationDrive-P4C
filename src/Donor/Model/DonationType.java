package Donor.Model;

public abstract class DonationType {
    protected Donation donation;
    protected int user;

    public DonationType(Donation donation, int user) {
        this.donation = donation;
        this.user = user;
    }

    // Abstract method to confirm and process donation (specific to each type)
    public abstract void confirmAndProcessDonation();
}
