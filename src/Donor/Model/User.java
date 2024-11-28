package Donor.Model;

import java.util.*;

// Import database-related classes
import Donor.Database.*;

public abstract class User {
    private String role; 
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;
    private String pin;
    private int userId;

    // Constructor for creating a new User object
    public User(String role, String name, String address, String phoneNumber, String email, String password, int userId){
        this.role = role;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.pin = generatePin();
        this.userId = userId;
    }

    // Getter and Setter methods for user information
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getPin() { return this.pin; }

    // Generates a random 4-digit PIN for user verification.
    private String generatePin() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));
    }

    // Verifies if the entered PIN matches the stored PIN
    public boolean verifyPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    // Authenticates the user using email and password
    public static int authenticateUser(String email, String password) {
        return UserDatabase.authenticateUser(email, password);
    }

}

