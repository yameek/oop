/**
 * TASK 12: Exceptions and Error Handling
 * ========================================
 * Difficulty: Intermediate ⭐⭐⭐
 *
 * Learn about: try-catch-finally, custom exceptions, checked vs unchecked,
 *              multi-catch, try-with-resources, exception chaining
 *
 * PROBLEM:
 * --------
 * Build a robust BankAccount system that uses proper exception handling
 * so that invalid operations are caught cleanly instead of crashing.
 *
 * PART A — Custom Exceptions
 * ---------------------------
 * Create the following exception classes:
 *
 * 1. BankException (extends Exception)             ← checked, base class
 * 2. InsufficientFundsException (extends BankException)
 *    - Constructor: (double balance, double amount)
 *    - getMessage() should say:
 *      "Insufficient funds: tried to withdraw 500.00 but balance is 200.00"
 * 3. InvalidAmountException (extends BankException)
 *    - Constructor: (double amount)
 *    - getMessage(): "Invalid amount: -50.00. Amount must be positive."
 * 4. AccountFrozenException (extends BankException)
 *    - Constructor: (String accountId)
 *    - getMessage(): "Account ACC001 is frozen. Contact support."
 *
 * PART B — BankAccount class
 * ---------------------------
 * Create a BankAccount class with:
 * - Fields: accountId (String), balance (double), isFrozen (boolean)
 * - Constructor: BankAccount(String accountId, double initialBalance)
 *   throws InvalidAmountException if initialBalance < 0
 *
 * Methods (each throws appropriate exception(s)):
 * 1. deposit(double amount)
 *    - throws InvalidAmountException if amount <= 0
 *    - throws AccountFrozenException if account is frozen
 * 2. withdraw(double amount)
 *    - throws InvalidAmountException if amount <= 0
 *    - throws AccountFrozenException if account is frozen
 *    - throws InsufficientFundsException if balance < amount
 * 3. freeze() — sets isFrozen = true, prints confirmation
 * 4. unfreeze() — sets isFrozen = false, prints confirmation
 * 5. getBalance() — returns balance
 * 6. transfer(BankAccount target, double amount)
 *    - Withdraws from this account, deposits to target
 *    - If withdraw succeeds but deposit fails, the withdraw should be REVERSED
 *      (hint: catch the deposit exception, add money back, re-throw)
 *
 * PART C — Transaction Logger (try-with-resources)
 * --------------------------------------------------
 * Create a class TransactionLogger that implements AutoCloseable:
 * - Constructor: TransactionLogger(String filename) — prints "Opening log: filename"
 * - log(String message) — prints "[LOG] message"
 * - close() — prints "Closing log: filename"
 *
 * Use try-with-resources to log a series of transactions.
 *
 * TEST YOUR CODE:
 * ---------------
 * In main():
 * - Create an account with negative balance → catch InvalidAmountException
 * - Create a valid account, deposit, withdraw successfully
 * - Try to overdraw → catch InsufficientFundsException
 * - Freeze account, try deposit → catch AccountFrozenException
 * - Use multi-catch: try both invalid amount and frozen in same catch block
 * - Test transfer() where target account is frozen (verify rollback)
 * - Use try-with-resources with TransactionLogger
 * - Show finally block always running
 *
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Checked exceptions (extend Exception): caller MUST handle or declare throws
 * - Unchecked exceptions (extend RuntimeException): optional to catch
 * - finally block: ALWAYS runs, even if exception is thrown or caught
 * - try-with-resources: auto-calls close() on AutoCloseable objects
 * - multi-catch: catch (ExA | ExB e) — handles multiple exception types
 * - Exception chaining: new Exception("msg", cause) — preserves original cause
 *
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * ✗ InvalidAmountException: Invalid amount: -100.00. Amount must be positive.
 * ✓ Deposited 500.00. Balance: 500.00
 * ✓ Withdrew 200.00. Balance: 300.00
 * ✗ InsufficientFundsException: Insufficient funds: tried to withdraw 500.00 but balance is 300.00
 * Account ACC001 frozen.
 * ✗ AccountFrozenException: Account ACC001 is frozen. Contact support.
 * Finally block always runs!
 * Opening log: transactions.log
 * [LOG] Deposit: 1000.00
 * [LOG] Withdraw: 250.00
 * Closing log: transactions.log
 */

// Write your solution below:
