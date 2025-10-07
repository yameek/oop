/**
 * TASK 1: Basic Classes and Objects
 * =================================
 * Difficulty: Beginner
 *
 * Learn about: Classes, constructors, parameter properties, instance methods
 *
 * SCENARIO
 * --------
 * Build a simple `Book` class to model a library item.
 *
 * REQUIREMENTS
 * ------------
 * 1. Define a `Book` class with the following public properties:
 *    - `title: string`
 *    - `author: string`
 *    - `pages: number`
 *    - `isAvailable: boolean` (default to `true` when a book is created)
 *
 * 2. Add a method `describe(): string` that returns a human-friendly summary,
 *    for example: "Clean Code by Robert C. Martin — 464 pages (Available)".
 *
 * 3. Add two methods to change availability:
 *    - `borrow(): void` → set `isAvailable` to false & log a success/error message
 *    - `return(): void` → set `isAvailable` to true & log a confirmation message
 *
 * 4. Create at least two `Book` instances and demonstrate the behavior:
 *    - Print book details via `describe()`
 *    - Borrow and return them to show state changes
 *
 * BONUS (optional)
 * ---------------
 * - Track how many times a book was borrowed using an additional counter field.
 * - Add a static helper `Book.printInventory(books: Book[]): void` to iterate
 *   through an array and log the summary of each book.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_01_basic_classes.ts`
 */

// Write your solution below this line 👇

