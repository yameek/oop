/**
 * TASK 2: Synchronization Basics
 * ================================
 * Difficulty: Easy-Medium ⭐⭐
 * 
 * Learn about: synchronized keyword, race conditions, critical sections,
 *              static synchronized, intrinsic locks (monitors), reentrancy
 * 
 * PROBLEM:
 * --------
 * Build a "Bank Account" system that demonstrates race conditions and how
 * synchronized fixes them.
 * 
 * Requirements:
 * 
 * Part A — Demonstrate the Race Condition:
 * 1. Create a class BankAccount with:
 *    - private int balance (initialized in constructor)
 *    - withdraw(int amount): subtracts from balance if sufficient funds
 *    - deposit(int amount): adds to balance
 *    - getBalance(): returns current balance
 * 2. In main(), create ONE BankAccount with balance = 1000
 * 3. Create 2 threads that each withdraw 100, 10 times (total 2000 withdrawal)
 * 4. Run WITHOUT synchronization — observe balance goes negative or is wrong
 * 
 * Part B — Fix with synchronized:
 * 5. Create SafeBankAccount with synchronized methods
 * 6. Run the same test — balance should never go negative
 * 
 * Part C — Synchronized block (finer control):
 * 7. Create a TransferService class with a method:
 *    transfer(SafeBankAccount from, SafeBankAccount to, int amount)
 * 8. Use synchronized blocks (not methods) to lock only what's needed
 * 
 * Part D — Static synchronized & Instance counter:
 * 9. Add a static int totalAccounts counter to SafeBankAccount
 * 10. Use static synchronized to safely increment it from multiple threads
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Race condition: multiple threads access shared data without synchronization
 * - Critical section: code that accesses shared resources
 * - synchronized method: locks 'this' (entire object)
 * - synchronized block: locks a specific object (finer granularity)
 * - static synchronized: locks the Class object (Class-level lock)
 * - Intrinsic lock (monitor): every Java object has one built-in lock
 * - Reentrancy: a thread can re-acquire a lock it already holds
 * - Thread interleaving: threads can switch at any point between bytecodes
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Race Condition Demo ===
 * [WARNING] Expected balance: 0, Actual: -200 ← RACE CONDITION!
 * 
 * === Part B: Synchronized Fix ===
 * [OK] Expected balance: 0, Actual: 0 ← CORRECT!
 * 
 * === Part C: Transfer Demo ===
 * Before: Account A=1000, Account B=1000
 * After 100 transfers of $10 each way...
 * After: Account A=1000, Account B=1000 (total preserved)
 * 
 * === Part D: Static Synchronized ===
 * Total accounts created: 5 (correct across threads)
 */

// Write your solution below:

