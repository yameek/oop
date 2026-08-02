/**
 * SOLUTION 2: Synchronization Basics
 * =====================================
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Race condition: multiple threads read/write shared data without coordination
 * 2. synchronized method: acquires intrinsic lock on 'this' (instance)
 * 3. synchronized block: acquires lock on specified object (finer control)
 * 4. static synchronized: acquires lock on the Class object
 * 5. Intrinsic lock (monitor): every Java object has exactly one built-in lock
 * 6. Reentrancy: same thread can re-acquire a lock it already holds
 * 
 * KEY TAKEAWAYS:
 * --------------
 * - Race conditions are SILENT bugs — code "works" but gives wrong results
 * - synchronized is the simplest solution but locks the entire object/method
 * - Prefer synchronized blocks over synchronized methods (lock only what's needed)
 * - static synchronized locks the CLASS, not any instance
 */

package solutions;

import java.util.concurrent.atomic.AtomicInteger;

// ─── Part A: UNSAFE Bank Account (demonstrates race condition) ───────────────
class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    // NOT synchronized — RACE CONDITION!
    // Thread interleaving example for withdraw(100) when balance = 200:
    // Thread-A reads balance (200) → context switch →
    // Thread-B reads balance (200) → Thread-B writes 100 → 
    // Thread-A writes 100 (should be 0, but both saw 200!)
    public void withdraw(int amount) {
        if (balance >= amount) {          // Check
            // ⚠️ Another thread can run between check and update!
            balance -= amount;            // Act
        }
    }

    public void deposit(int amount) {
        balance += amount;  // ⚠️ Also not atomic! (read → add → write)
    }

    public int getBalance() { return balance; }
}

// ─── Part B: SAFE Bank Account (synchronized) ───────────────────────────────
class SafeBankAccount {
    private int balance;
    private static int totalAccounts = 0;  // Class-level shared state

    public SafeBankAccount(int balance) {
        this.balance = balance;
        incrementTotalAccounts();
    }

    // synchronized METHOD: locks 'this' (the entire instance)
    // Only ONE thread can execute ANY synchronized method on this object at a time
    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public synchronized int getBalance() {
        return balance;
    }

    // static synchronized: locks the CLASS object (SafeBankAccount.class)
    // Different from instance lock — protects static (shared) state
    public static synchronized void incrementTotalAccounts() {
        totalAccounts++;
    }

    public static synchronized int getTotalAccounts() {
        return totalAccounts;
    }
}

// ─── Part C: Transfer Service (synchronized blocks for finer control) ────────
class TransferService {

    // Using synchronized blocks instead of synchronized methods
    // This lets us lock ONLY the accounts involved, not the entire TransferService
    public void transfer(SafeBankAccount from, SafeBankAccount to, int amount) {
        // ⚠️ Lock ordering is CRITICAL to prevent deadlock!
        // Always lock in consistent order (e.g., by hashCode or account ID)
        // See Task 3 for detailed deadlock discussion
        Object firstLock = System.identityHashCode(from) < System.identityHashCode(to) ? from : to;
        Object secondLock = firstLock == from ? to : from;

        synchronized (firstLock) {         // Lock first account
            synchronized (secondLock) {     // Lock second account
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}

public class Solution02SynchronizationBasics {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 2: Synchronization Basics");
        System.out.println("=".repeat(60));

        // ═══════════════════════════════════════════════════════════════
        // Part A: Race Condition Demo
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n=== Part A: Race Condition Demo ===");
        BankAccount unsafeAccount = new BankAccount(1000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) unsafeAccount.withdraw(100);
        }, "Withdrawer-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) unsafeAccount.withdraw(100);
        }, "Withdrawer-2");

        t1.start(); t2.start();
        t1.join(); t2.join();

        // Expected: balance should be <=0 but with races, it might be negative!
        int result = unsafeAccount.getBalance();
        if (result < 0) {
            System.out.println("[WARNING] Expected: ≥0, Actual: " + result + " ← RACE CONDITION!");
        } else {
            System.out.println("[NOTE] Balance: " + result + " (race condition may not manifest every run)");
            System.out.println("       Run multiple times — it WILL eventually show wrong results");
        }

        // ═══════════════════════════════════════════════════════════════
        // Part B: Synchronized Fix
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n=== Part B: Synchronized Fix ===");
        SafeBankAccount safeAccount = new SafeBankAccount(1000);

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10; i++) safeAccount.withdraw(100);
        }, "SafeWithdrawer-1");

        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 10; i++) safeAccount.withdraw(100);
        }, "SafeWithdrawer-2");

        t3.start(); t4.start();
        t3.join(); t4.join();

        System.out.println("[OK] Expected: 0 or positive, Actual: " + safeAccount.getBalance() + " ✓");

        // ═══════════════════════════════════════════════════════════════
        // Part C: Transfer with synchronized blocks
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n=== Part C: Transfer Demo ===");
        SafeBankAccount accountA = new SafeBankAccount(1000);
        SafeBankAccount accountB = new SafeBankAccount(1000);
        TransferService service = new TransferService();

        System.out.println("Before: Account A=" + accountA.getBalance() + ", Account B=" + accountB.getBalance());

        // Transfer $10 from A→B and B→A, 100 times each (simultaneously)
        Thread transferAB = new Thread(() -> {
            for (int i = 0; i < 100; i++) service.transfer(accountA, accountB, 10);
        });
        Thread transferBA = new Thread(() -> {
            for (int i = 0; i < 100; i++) service.transfer(accountB, accountA, 10);
        });

        transferAB.start(); transferBA.start();
        transferAB.join(); transferBA.join();

        int totalAfter = accountA.getBalance() + accountB.getBalance();
        System.out.println("After: Account A=" + accountA.getBalance() + ", Account B=" + accountB.getBalance());
        System.out.println("Total preserved: " + totalAfter + " (should be 2000) " +
                (totalAfter == 2000 ? "✓" : "✗"));

        // ═══════════════════════════════════════════════════════════════
        // Part D: static synchronized
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n=== Part D: Static Synchronized ===");
        // Reset for clean test
        Thread[] creators = new Thread[5];
        for (int i = 0; i < 5; i++) {
            creators[i] = new Thread(() -> {
                new SafeBankAccount(100);
            });
            creators[i].start();
        }
        for (Thread creator : creators) creator.join();
        // Note: totalAccounts includes accounts from earlier parts too
        System.out.println("Total accounts created (including earlier): " + SafeBankAccount.getTotalAccounts());

        // ═══════════════════════════════════════════════════════════════
        // Reentrancy demo
        // ═══════════════════════════════════════════════════════════════
        System.out.println("\n=== Reentrancy Demo ===");
        System.out.println("A synchronized method can call another synchronized method");
        System.out.println("on the SAME object — the thread already holds the lock!");
        System.out.println("Example: synchronized deposit() calling synchronized getBalance()");
        System.out.println("Without reentrancy, this would deadlock!");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ Race condition: unsynchronized access to shared mutable state");
        System.out.println("✓ synchronized method: locks 'this' (instance lock)");
        System.out.println("✓ synchronized block: locks a specific object (finer control)");
        System.out.println("✓ static synchronized: locks the Class object");
        System.out.println("✓ Intrinsic lock: every Java object has one built-in lock");
        System.out.println("✓ Reentrancy: thread can re-acquire a lock it already holds");
        System.out.println("=".repeat(60));
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * 1. WHAT IS A RACE CONDITION?
 *    When the correctness of a program depends on the TIMING of thread execution.
 *    Example: check-then-act (if balance >= amount → withdraw)
 *    Between the check and the act, another thread might change the balance!
 * 
 * 2. SYNCHRONIZED KEYWORD:
 *    - Acquires the intrinsic lock (monitor) of the object
 *    - Only ONE thread can hold the lock at a time
 *    - Other threads trying to acquire the same lock → BLOCKED state
 *    - Lock is automatically released when block/method exits (even on exception!)
 * 
 * 3. INTRINSIC LOCK (MONITOR):
 *    Every Java object has exactly ONE intrinsic lock.
 *    - synchronized(obj) acquires obj's lock
 *    - synchronized method acquires 'this' lock
 *    - static synchronized acquires ClassName.class lock
 *    - These are THREE DIFFERENT LOCKS!
 * 
 * 4. REENTRANCY:
 *    If Thread-A holds lock L, and calls another method that requires L,
 *    Java allows it (the thread already has the lock). Count is tracked:
 *    - acquire: count++ 
 *    - release: count--
 *    - Fully released when count reaches 0
 * 
 * COMMON MISTAKES TO AVOID:
 * =========================
 * 1. Synchronizing on the wrong object (different instances = different locks!)
 * 2. Forgetting that synchronized methods lock 'this', not the method itself
 * 3. Using synchronized on a field that gets reassigned (lock object changes!)
 * 4. Over-synchronizing (locking too broadly = poor concurrency)
 * 5. Under-synchronizing (missing ONE access = race condition)
 */
