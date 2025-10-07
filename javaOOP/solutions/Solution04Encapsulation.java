/**
 * SOLUTION 4: Encapsulation and Data Validation
 * =============================================
 * Demonstrates protecting class state with private fields, controlled access
 * through getters/setters, and defensive programming via validation.
 */
package solutions;

class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private double balance;
    private int pin;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance, int pin) {
        validateInitialBalance(initialBalance);
        validatePin(pin);
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.pin = pin;
        System.out.printf("Account created: %s for %s%n", accountNumber, accountHolder);
    }

    private void validateInitialBalance(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance must be non-negative");
        }
    }

    private void validatePin(int pin) {
        if (pin < 1000 || pin > 9999) {
            throw new IllegalArgumentException("PIN must be a 4-digit number");
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public Double getBalance(int pin) {
        if (!isPinValid(pin)) {
            System.out.println("✗ Incorrect PIN. Balance unavailable.");
            return null;
        }
        System.out.printf("✓ Balance for %s: $%.2f%n", accountHolder, balance);
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("✗ Invalid deposit amount.");
            return;
        }
        balance += amount;
        System.out.printf("✓ Deposited $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public void withdraw(double amount, int pin) {
        if (!isPinValid(pin)) {
            System.out.println("✗ Incorrect PIN. Withdrawal denied.");
            return;
        }
        if (amount <= 0) {
            System.out.println("✗ Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("✗ Insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.printf("✓ Withdrawn $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public void changePin(int oldPin, int newPin) {
        if (!isPinValid(oldPin)) {
            System.out.println("✗ Incorrect current PIN. PIN not changed.");
            return;
        }
        try {
            validatePin(newPin);
            pin = newPin;
            System.out.println("✓ PIN changed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        }
    }

    private boolean isPinValid(int pin) {
        return this.pin == pin;
    }
}

public class Solution04Encapsulation {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("ACC123", "John Doe", 1000, 1234);

        account.deposit(500);
        account.deposit(-10);
        account.getBalance(4321);
        account.getBalance(1234);

        account.withdraw(200, 1234);
        account.withdraw(2000, 1234);
        account.withdraw(50, 1111);

        account.changePin(1111, 2222);
        account.changePin(1234, 99);
        account.changePin(1234, 9876);

        account.withdraw(100, 9876);
        account.getBalance(9876);
    }
}
