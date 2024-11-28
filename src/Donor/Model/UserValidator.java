package Donor.Model;

import java.util.regex.Pattern;

public class UserValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"; // Regex for validating email format
    private static final String PHONE_REGEX = "^\\d{4} \\d{3} \\d{4}$"; // Regex for validating phone number format: xxxx xxx xxxx
    private static final String PASSWORD_REGEX = ".*[!@#$%^&*()_+={}:;\"'<>,.?/\\|].*"; // Regex for validating passwords that must contain at least one special character

    // Validates the phone number
    public static boolean isValidPhoneNumber(String phoneNumber){
        return Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    // Validates the email
    public static boolean isValidEmail(String email){
        return Pattern.matches(EMAIL_REGEX, email);
    }

    // Validates the password
    public static boolean isValidPassword(String password){
        return Pattern.matches(PASSWORD_REGEX, password);
    }
}
