/**
 * BankAccount.java — The class we will write tests for.
 *
 * This is a simplified, self-contained version of the bank account
 * from Task12. It uses RuntimeExceptions (unchecked) to keep the
 * example clean for learning JUnit.
 */
public class BankAccount {

    private final String id;
    private double balance;
    private boolean frozen;

    public BankAccount(String id, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                "Initial balance cannot be negative: " + initialBalance);
        }
        this.id      = id;
        this.balance = initialBalance;
        this.frozen  = false;
    }

    public void deposit(double amount) {
        validateAmount(amount);
        checkNotFrozen();
        balance += amount;
    }

    public void withdraw(double amount) {
        validateAmount(amount);
        checkNotFrozen();
        if (balance < amount) {
            throw new IllegalStateException(
                String.format("Insufficient funds: balance=%.2f, requested=%.2f", balance, amount));
        }
        balance -= amount;
    }

    public void transfer(BankAccount target, double amount) {
        this.withdraw(amount);
        try {
            target.deposit(amount);
        } catch (Exception e) {
            this.balance += amount; // rollback
            throw new IllegalStateException("Transfer failed, rolled back: " + e.getMessage(), e);
        }
    }

    public void freeze()   { this.frozen = true; }
    public void unfreeze() { this.frozen = false; }

    public String getId()      { return id; }
    public double getBalance() { return balance; }
    public boolean isFrozen()  { return frozen; }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive: " + amount);
        }
    }

    private void checkNotFrozen() {
        if (frozen) {
            throw new IllegalStateException("Account " + id + " is frozen.");
        }
    }
}
