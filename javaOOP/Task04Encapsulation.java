/**
 * TASK 4: Encapsulation and Properties
 * =====================================
 * Difficulty: Intermediate-Advanced ⭐⭐⭐
 * 
 * Learn about: Private variables, Getters/Setters, Data validation, Access modifiers
 * 
 * PROBLEM:
 * --------
 * Create a secure BankAccount class.
 * 
 * Requirements:
 * 
 * 1. Private instance variables:
 *    - accountNumber (String)
 *    - accountHolder (String)
 *    - balance (double)
 *    - pin (int)
 * 
 * 2. Constructor:
 *    - Takes accountNumber, accountHolder, initialBalance, pin
 *    - Validates: initialBalance >= 0, pin is 4 digits (1000-9999)
 *    - Throws IllegalArgumentException if invalid
 * 
 * 3. Public methods:
 *    - getAccountNumber() - returns account number
 *    - getAccountHolder() - returns account holder
 *    - getBalance(int pin) - returns balance only if pin matches
 *    - deposit(double amount) - adds to balance if amount > 0
 *    - withdraw(double amount, int pin) - subtracts if pin matches and sufficient funds
 *    - changePin(int oldPin, int newPin) - changes pin if oldPin matches
 * 
 * 4. Validation rules:
 *    - Deposit amount must be positive
 *    - Withdraw amount must be positive and <= balance
 *    - PIN must be 4 digits
 *    - All operations should print success/failure messages
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create an account
 * - Try deposits with valid/invalid amounts
 * - Try withdrawals with correct/incorrect PINs
 * - Try checking balance with wrong PIN
 * - Try changing PIN
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Encapsulation (data hiding)
 * - Private vs public access modifiers
 * - Getters and setters
 * - Data validation
 * - Exception handling
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Account created: ACC123 for John Doe
 * ✓ Deposited $500.00. New balance: $1500.00
 * ✗ Invalid deposit amount
 * ✓ Withdrawn $200.00. New balance: $1300.00
 * ✗ Incorrect PIN
 * ✓ PIN changed successfully
 */

// Write your solution below:

