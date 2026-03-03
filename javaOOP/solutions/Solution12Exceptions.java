/**
 * SOLUTION 12: Exceptions and Error Handling
 * ============================================
 *
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Checked Exception    — extends Exception; must be declared with 'throws' or caught
 * 2. Unchecked Exception  — extends RuntimeException; no compiler enforcement
 * 3. Custom Exceptions    — provide meaningful, domain-specific error messages
 * 4. finally              — always executes; great for cleanup (closing resources)
 * 5. try-with-resources   — AutoCloseable; close() called automatically
 * 6. multi-catch          — catch (TypeA | TypeB e) reduces boilerplate
 * 7. Exception chaining   — new Exception("msg", cause) preserves root cause
 *
 * KEY TAKEAWAYS:
 * --------------
 * - Use checked exceptions for recoverable conditions the caller should handle
 * - Use unchecked (RuntimeException) for programming errors (null, out of bounds)
 * - Always close resources in finally or use try-with-resources
 * - Never swallow exceptions with empty catch blocks
 * - Custom exceptions make stack traces immediately understandable
 */

package solutions;

// ─────────────────────────────────────────────────────────────────────────────
// PART A: Custom Exception Hierarchy
// ─────────────────────────────────────────────────────────────────────────────

// Base checked exception for all bank errors
class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
    // Exception chaining constructor
    public BankException(String message, Throwable cause) {
        super(message, cause);
    }
}

class InsufficientFundsException extends BankException {
    private final double balance;
    private final double amount;

    public InsufficientFundsException(double balance, double amount) {
        super(String.format(
            "Insufficient funds: tried to withdraw %.2f but balance is %.2f",
            amount, balance));
        this.balance = balance;
        this.amount  = amount;
    }

    public double getBalance() { return balance; }
    public double getAmount()  { return amount; }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(double amount) {
        super(String.format("Invalid amount: %.2f. Amount must be positive.", amount));
    }
}

class AccountFrozenException extends BankException {
    public AccountFrozenException(String accountId) {
        super("Account " + accountId + " is frozen. Contact support.");
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PART B: BankAccount
// ─────────────────────────────────────────────────────────────────────────────

class BankAccount {
    private final String accountId;
    private double balance;
    private boolean isFrozen;

    public BankAccount(String accountId, double initialBalance) throws InvalidAmountException {
        if (initialBalance < 0) throw new InvalidAmountException(initialBalance);
        this.accountId = accountId;
        this.balance   = initialBalance;
        this.isFrozen  = false;
    }

    public void deposit(double amount) throws InvalidAmountException, AccountFrozenException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (isFrozen)   throw new AccountFrozenException(accountId);
        balance += amount;
        System.out.printf("✓ Deposited %.2f. Balance: %.2f%n", amount, balance);
    }

    public void withdraw(double amount)
            throws InvalidAmountException, AccountFrozenException, InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (isFrozen)    throw new AccountFrozenException(accountId);
        if (balance < amount) throw new InsufficientFundsException(balance, amount);
        balance -= amount;
        System.out.printf("✓ Withdrew %.2f. Balance: %.2f%n", amount, balance);
    }

    /**
     * Transfer with rollback: if deposit on target fails, we undo the withdrawal.
     * This simulates an atomic operation using exception chaining.
     */
    public void transfer(BankAccount target, double amount) throws BankException {
        withdraw(amount);   // may throw — if so, nothing needs rollback
        try {
            target.deposit(amount);
        } catch (BankException e) {
            // Rollback: give money back (direct field access within package)
            this.balance += amount;
            System.out.printf("⚠ Transfer failed, rolled back %.2f to %s%n", amount, accountId);
            // Chain original exception so caller still gets the root cause
            throw new BankException("Transfer to " + target.accountId + " failed: " + e.getMessage(), e);
        }
        System.out.printf("✓ Transferred %.2f from %s to %s%n", amount, accountId, target.accountId);
    }

    public void freeze() {
        isFrozen = true;
        System.out.println("Account " + accountId + " frozen.");
    }

    public void unfreeze() {
        isFrozen = false;
        System.out.println("Account " + accountId + " unfrozen.");
    }

    public double getBalance() { return balance; }
    public String getAccountId() { return accountId; }

    @Override
    public String toString() {
        return String.format("BankAccount[%s, balance=%.2f, frozen=%b]", accountId, balance, isFrozen);
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PART C: TransactionLogger (AutoCloseable for try-with-resources)
// ─────────────────────────────────────────────────────────────────────────────

class TransactionLogger implements AutoCloseable {
    private final String filename;

    public TransactionLogger(String filename) {
        this.filename = filename;
        System.out.println("Opening log: " + filename);
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }

    @Override
    public void close() {
        // This is called automatically at the end of try-with-resources
        System.out.println("Closing log: " + filename);
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main: Demonstrating all exception handling patterns
// ─────────────────────────────────────────────────────────────────────────────

public class Solution12Exceptions {

    public static void main(String[] args) {

        // ── Invalid initial balance ─────────────────────────────────────────
        System.out.println("=== Invalid Account Creation ===");
        try {
            BankAccount bad = new BankAccount("BAD001", -100);
        } catch (InvalidAmountException e) {
            System.out.println("✗ InvalidAmountException: " + e.getMessage());
        }

        // ── Happy path ─────────────────────────────────────────────────────
        System.out.println("\n=== Normal Operations ===");
        BankAccount acc = null;
        try {
            acc = new BankAccount("ACC001", 0);
            acc.deposit(500);
            acc.withdraw(200);
        } catch (BankException e) {
            System.out.println("✗ " + e.getMessage());
        } finally {
            // finally ALWAYS runs — use for cleanup / logging
            System.out.println("Finally block always runs!");
        }

        // ── Overdraft ──────────────────────────────────────────────────────
        System.out.println("\n=== Overdraft ===");
        try {
            acc.withdraw(500); // balance is 300
        } catch (InsufficientFundsException e) {
            System.out.printf("✗ InsufficientFundsException: %s%n", e.getMessage());
            System.out.printf("  (short by %.2f)%n", e.getAmount() - e.getBalance());
        } catch (BankException e) {
            System.out.println("✗ BankException: " + e.getMessage());
        }

        // ── Frozen account ─────────────────────────────────────────────────
        System.out.println("\n=== Frozen Account ===");
        acc.freeze();
        try {
            acc.deposit(100);
        } catch (AccountFrozenException e) {
            System.out.println("✗ AccountFrozenException: " + e.getMessage());
        } catch (BankException e) {
            System.out.println("✗ BankException: " + e.getMessage());
        }

        // ── Multi-catch ────────────────────────────────────────────────────
        System.out.println("\n=== Multi-catch ===");
        acc.unfreeze();
        try {
            acc.deposit(-50);   // InvalidAmountException
        } catch (InvalidAmountException | AccountFrozenException e) {
            // Single handler for both types — e is effectively final
            System.out.println("✗ Caught via multi-catch: " + e.getMessage());
        } catch (BankException e) {
            System.out.println("✗ BankException: " + e.getMessage());
        }

        // ── Transfer with rollback ─────────────────────────────────────────
        System.out.println("\n=== Transfer with Rollback ===");
        try {
            BankAccount sender   = new BankAccount("SEND01", 1000);
            BankAccount receiver = new BankAccount("RECV01", 0);
            receiver.freeze(); // make the deposit fail

            double senderBalanceBefore = sender.getBalance();
            try {
                sender.transfer(receiver, 200);
            } catch (BankException e) {
                System.out.println("✗ Transfer error: " + e.getMessage());
                System.out.println("  Root cause: " + e.getCause().getMessage());
                System.out.printf("  Sender balance restored: %.2f (was %.2f)%n",
                    sender.getBalance(), senderBalanceBefore);
            }

            // Now do a successful transfer
            receiver.unfreeze();
            sender.transfer(receiver, 300);
            System.out.printf("  Sender: %.2f | Receiver: %.2f%n",
                sender.getBalance(), receiver.getBalance());

        } catch (BankException e) {
            System.out.println("✗ Setup error: " + e.getMessage());
        }

        // ── try-with-resources ─────────────────────────────────────────────
        System.out.println("\n=== try-with-resources ===");
        // TransactionLogger.close() is called automatically — even if exception thrown
        try (TransactionLogger logger = new TransactionLogger("transactions.log")) {
            logger.log("Deposit: 1000.00");
            logger.log("Withdraw: 250.00");
            logger.log("Transfer: 300.00 to RECV01");
            // logger.close() is automatically called here ↑
        }
        System.out.println("(Logger was closed automatically ✓)");

        /*
         * KEY TAKEAWAYS:
         * ─────────────
         * ✅ Custom exceptions carry domain context (balance, amount, accountId)
         * ✅ Checked exceptions force callers to acknowledge failure cases
         * ✅ finally always runs → ideal for releasing locks, connections, etc.
         * ✅ try-with-resources is cleaner than finally for AutoCloseable
         * ✅ multi-catch (A | B) keeps code DRY when handling identically
         * ✅ Exception chaining preserves root cause through getCause()
         * ✅ Rollback pattern: catch failure, undo side effects, re-throw
         *
         * ANTI-PATTERNS (never do these):
         * ───────────────────────────────
         * ✗ catch (Exception e) { }                 ← swallowing exceptions
         * ✗ catch (Exception e) { e.printStackTrace(); } ← unhandled in production
         * ✗ throw new Exception("error");            ← too generic, lose context
         */
    }
}
