package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
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
        
            if (email.indexOf('@') == -1){
                return false;
            }
            else {
                if (email.indexOf('@') == 0){
                    return false;
                }
                else{
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
}