/**
 * TASK 6: Abstract Classes
 * ========================
 * Difficulty: Advanced
 *
 * Learn about: `abstract class`, abstract methods, template method pattern
 *
 * SCENARIO
 * --------
 * Build a shape calculator with a base `Shape` class and multiple concrete
 * implementations that compute area and perimeter.
 *
 * REQUIREMENTS
 * ------------
 * 1. Define an abstract class `Shape` with:
 *    - A constructor that accepts a `color: string` (stored as `protected`).
 *    - Abstract methods `area(): number` and `perimeter(): number`.
 *    - A concrete method `describe(): string` that returns a formatted message
 *      including the color, area, and perimeter.
 *
 * 2. Implement two subclasses:
 *    - `Rectangle` with `width` and `height`.
 *    - `Circle` with `radius`.
 *
 * 3. Ensure each subclass correctly implements `area()` and `perimeter()`.
 *
 * 4. Instantiate the subclasses, call `describe()`, and print the results to the
 *    console.
 *
 * BONUS (optional)
 * ---------------
 * - Add a `Triangle` class that validates side lengths (triangle inequality).
 * - Create a static helper in `Shape` to compare areas of two shapes.
 * - Format numbers to two decimal places using a protected utility method.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_06_abstract_classes.ts`
 */

// Write your solution below this line 👇

