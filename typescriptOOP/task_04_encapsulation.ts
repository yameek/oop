/**
 * TASK 4: Encapsulation & Accessors
 * ==================================
 * Difficulty: Intermediate
 *
 * Learn about: access modifiers, getters/setters, validation, private fields
 *
 * SCENARIO
 * --------
 * Model a `BankAccount` with controlled balance updates and clear statements.
 *
 * REQUIREMENTS
 * ------------
 * 1. Create a `BankAccount` class with:
 *    - A private `#balance: number` field (use ECMAScript private field syntax).
 *    - A `readonly accountNumber: string` set in the constructor.
 *    - A protected `ownerName: string` so subclasses can reference it.
 *
 * 2. Expose a getter `balance` that returns the current balance.
 *
 * 3. Add methods:
 *    - `deposit(amount: number): void` → validate positive amounts before adding.
 *    - `withdraw(amount: number): void` → throw an error if funds are
 *      insufficient.
 *    - `printStatement(): void` → log the owner name, account number, and
 *      balance using a formatted string.
 *
 * 4. Demonstrate the account by creating an instance, depositing, withdrawing,
 *    handling an invalid operation, and printing a statement.
 *
 * BONUS (optional)
 * ---------------
 * - Add a protected `recordTransaction(type: string, amount: number)` method to
 *   maintain a history array.
 * - Create a subclass `SavingsAccount` that applies interest via a new method.
 * - Use getters/setters to enforce account holder name formatting.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_04_encapsulation.ts`
 */

// Write your solution below this line 👇

