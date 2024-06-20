package com.app.utils;

import java.util.regex.Pattern;

public class UtilsValidation {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z].*)(?=.*[!@#$%^&*(),.?\":{}|<>].*).{2,}$");
    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Z]$");
    private static final Pattern NUMBER_ACCOUNT_PATTERN = Pattern.compile("\\d{10}");


    /**
     * Validates the given username by checking if it matches the specified pattern.
     *
     * @param  username  the username to be validated
     * @return           true if the username matches the pattern, false otherwise
     */
    public static boolean validateUsername(String username) {
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Validates the given password by checking if it matches the specified pattern.
     *
     * @param  password  the password to be validated
     * @return           true if the password matches the pattern, false otherwise
     */
    public static boolean validatePassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Validates a DNI number by checking if it matches the specified pattern.
     *
     * @param  dni  the DNI number to be validated
     * @return      true if the DNI number matches the pattern, false otherwise
     */
    public static boolean validateDni(String dni) {
        return DNI_PATTERN.matcher(dni).matches();
    }

    /**
     * Validates an account number by checking if it matches the specified pattern.
     *
     * @param  accountNumber  the account number to be validated
     * @return                 true if the account number matches the pattern, false otherwise
     */
    public static boolean validateAccountNumber(String accountNumber) {
        return NUMBER_ACCOUNT_PATTERN.matcher(accountNumber).matches();
    }
}
