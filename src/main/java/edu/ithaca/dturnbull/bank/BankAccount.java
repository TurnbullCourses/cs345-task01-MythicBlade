package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance) {
        if (isEmailValid(email)) {
            this.email = email;
            if (isAmountValid(startingBalance)) {
                this.balance = startingBalance;
            } else {
                throw new IllegalArgumentException("Starting balance cannot invalid amount");
            }
        } else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    /**
     * @returns account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @returns account email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller
     *       than balance
     * @throws IllegalArgumentException   if withdraw amount is negative or zero
     * @throws InsufficientFundsException if withdraw amount is greater than balance
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Cannot withdraw invalid amount");
        }

        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * @returns False email is not valid.
     */
    public static boolean isEmailValid(String email) {

        String specialCharacters = "!#$%&'*+/=?^_`{|}~.\\/";

        int atCount = 0;

        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                atCount++;
            }
        }
        if (atCount != 1) {
            return false;
        }

        if (email.indexOf('@') == -1) {
            return false;
        } else {
            if (email.indexOf('@') == 0) {
                return false;
            } else {
                for (int i = 0; i < email.length() - 1; i++) {
                    char currentChar = email.charAt(i);
                    char nextChar = email.charAt(i + 1);
                    if (specialCharacters.indexOf(currentChar) != -1 && !Character.isLetterOrDigit(nextChar)) {
                        return false;
                    }
                }
            }

        }

        if (specialCharacters.indexOf(email.charAt(0)) != -1) {
            return false;
        }

        int atIndex = email.indexOf('@');
        int dotIndex = email.indexOf('.', atIndex);

        if (dotIndex == -1 || email.length() - dotIndex <= 2) {
            return false;
        }

        return true;
    }

    public static boolean isEmailValidOwensVersion(String email) {
        var emailRegex = "(?=.*\\w@.*)(?!.*\\.\\..*)^\\w[\\w!#$%&'*+-/=.?^_`{|}~]*(\\+\\w+)?@(([\\w-]+\\.\\w\\w+)|(\\[(IPv4:)?\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\.\\d\\d?\\d?\\])|(\\[(IPv6:)(([A-Z\\d][A-Z\\d][A-Z\\d][A-Z\\d]:?)*)\\]))";

        if (email.matches(emailRegex)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @returns False if withdraw amount is negative or zero or has too many decimal places
     */
    public static boolean isAmountValid(double amount) {
        String text = Double.toString(Math.abs(amount));
        int integerPlaces = text.indexOf('.');
        int decimalPlaces = text.length() - integerPlaces - 1;
        if ((decimalPlaces > 2) || (amount <= 0)) {
            return false;
        }
        return true;
    }

    /**
     * @post deposits the amount into the account
     * @throws IllegalArgumentException   if withdraw amount is negative or zero or has too many decimal places
     */
    public void deposit(double amount) {
        if (isAmountValid(amount)) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("Cannot deposit invalid amount");
        }
    }

    /**
     * @post reduces the balance of the account and tranfers the funds to provided account
     * @throws IllegalArgumentException   if withdraw amount is negative or zero or has too many decimal places
     * @throws InsufficientFundsException if withdraw amount is greater than balance
     */
    public void transfer(BankAccount account, double amount) throws InsufficientFundsException {
        this.withdraw(amount);
        account.deposit(amount);
    }

}
