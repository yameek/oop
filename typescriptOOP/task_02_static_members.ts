/**
 * TASK 2: Static Members & Factory Methods
 * ========================================
 * Difficulty: Beginner / Intermediate
 *
 * Learn about: `static` fields, factory methods, private constructors, enums
 *
 * SCENARIO
 * --------
 * Design an `Employee` class that tracks every hire and provides helper
 * factories for creating different roles.
 *
 * REQUIREMENTS
 * ------------
 * 1. Create an `Employee` class with:
 *    - A private constructor that accepts `name: string` and `role: string`.
 *    - A read-only instance `id: number` provided automatically.
 *    - Public getters for `name` and `role` (or mark them as `public readonly`).
 *
 * 2. Maintain a static counter for generating unique IDs.
 *
 * 3. Provide at least two static factory methods:
 *    - `Employee.createEngineer(name: string)` → role should be "Engineer"
 *    - `Employee.createManager(name: string)` → role should be "Manager"
 *    - feel free to add more roles if you like.
 *
 * 4. Add a static method `totalEmployees()` that returns how many employees have
 *    been created.
 *
 * 5. Demonstrate usage by creating multiple employees through the factory
 *    methods and logging their details and the total count.
 *
 * BONUS (optional)
 * ---------------
 * - Extract roles into a literal type or `enum` for tighter typing.
 * - Provide a `toString()` instance method that prints "[#3] Alex — Engineer".
 * - Keep a static registry (array) of all employees and expose a read-only copy.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_02_static_members.ts`
 */

// Write your solution below this line 👇

