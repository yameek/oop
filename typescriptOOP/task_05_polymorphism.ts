/**
 * TASK 5: Polymorphism & Union Types
 * ==================================
 * Difficulty: Intermediate / Advanced
 *
 * Learn about: interfaces, discriminated unions, type guards, polymorphic lists
 *
 * SCENARIO
 * --------
 * Implement a payment processing system that can handle multiple payment
 * methods using a common interface and union types.
 *
 * REQUIREMENTS
 * ------------
 * 1. Create an interface `PaymentMethod` with:
 *    - `type` property whose value is a string literal ("card", "cash", ...).
 *    - `charge(amount: number): string` method that returns a confirmation text.
 *
 * 2. Implement at least three concrete payment classes that fulfil the
 *    interface, each with its own fields and logic (e.g., `CardPayment`,
 *    `CashPayment`, `CryptoPayment`).
 *
 * 3. Create a union type `SupportedPayment = CardPayment | CashPayment | ...`
 *    and a processing function `processPayment(payment: SupportedPayment,
 *    amount: number): void` that:
 *    - Uses type guards to branch per payment type.
 *    - Logs the status message returned by `charge`.
 *
 * 4. Demonstrate polymorphism by storing different payment types in an array,
 *    then iterating through the array and processing each one.
 *
 * BONUS (optional)
 * ---------------
 * - Add validation for card expiry dates using getters/setters.
 * - Return a `Result` object (union) representing success/failure.
 * - Include asynchronous simulation with `setTimeout` or `Promise`.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_05_polymorphism.ts`
 */

// Write your solution below this line 👇

