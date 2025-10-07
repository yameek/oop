/**
 * TASK 3: Inheritance & Method Overriding
 * =======================================
 * Difficulty: Intermediate
 *
 * Learn about: `extends`, `super`, method overriding, protected helpers
 *
 * SCENARIO
 * --------
 * Create a transport booking system with a base `Vehicle` class and two
 * subclasses: `Car` and `Motorcycle`.
 *
 * REQUIREMENTS
 * ------------
 * 1. Implement an abstract-ish base class `Vehicle` (regular class is fine) with:
 *    - Protected fields `brand: string` and `model: string`.
 *    - A constructor that sets these fields along with `baseRate: number`.
 *    - A public method `describe(): string` that returns a generic description.
 *    - A method `pricePerDay(): number` that returns the base rate (subclasses
 *      will override this to adjust pricing).
 *
 * 2. Create a `Car` subclass that:
 *    - Accepts an additional property `doors: number`.
 *    - Overrides `describe()` to include door count.
 *    - Overrides `pricePerDay()` to add a 15% surcharge to the base rate.
 *
 * 3. Create a `Motorcycle` subclass that:
 *    - Accepts an additional property `hasSidecar: boolean`.
 *    - Overrides `describe()` to mention the sidecar if present.
 *    - Overrides `pricePerDay()` to apply a 10% discount compared to base rate.
 *
 * 4. Demonstrate polymorphism by placing multiple `Vehicle` instances in an
 *    array and iterating through it, printing each description and price.
 *
 * BONUS (optional)
 * ---------------
 * - Add a helper method in the base class to format currency.
 * - Use `protected` getters in the base class to expose fields to subclasses.
 * - Add another subclass (e.g., `Truck`) that charges based on payload.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_03_inheritance.ts`
 */

// Write your solution below this line 👇

