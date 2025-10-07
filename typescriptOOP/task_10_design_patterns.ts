/**
 * TASK 10: Design Patterns (Singleton + Factory)
 * =============================================
 * Difficulty: Advanced
 *
 * Learn about: Singleton pattern, Factory pattern, dependency inversion
 *
 * SCENARIO
 * --------
 * Implement two classic design patterns for a notification system:
 *    1. A `Logger` singleton that ensures only one instance writes logs.
 *    2. A `NotificationFactory` that creates notification channels based on
 *       configuration data.
 *
 * REQUIREMENTS
 * ------------
 * 1. Create a `Logger` class that:
 *    - Has a private static instance.
 *    - Exposes a public static `getInstance()` method to return the singleton.
 *    - Has an instance method `log(message: string): void` that prefixes the
 *      current ISO timestamp.
 *
 * 2. Define an interface `Notification` with a method `send(message: string): void`.
 *
 * 3. Implement at least three notification classes (`EmailNotification`,
 *    `SmsNotification`, `PushNotification`). Each should accept any required
 *    configuration through its constructor and use the shared `Logger` singleton
 *    when `send` is invoked.
 *
 * 4. Implement a `NotificationFactory` with a static method
 *    `create(type: string, config: Record<string, unknown>): Notification` that
 *    returns the appropriate notification instance. Throw an error for unknown
 *    types.
 *
 * 5. Demonstrate the setup by:
 *    - Creating multiple notifications via the factory.
 *    - Sending messages and showing that all logs use the same logger instance.
 *
 * BONUS (optional)
 * ---------------
 * - Cache created notifications inside the factory for reuse.
 * - Support batch sending by passing an array of messages.
 * - Add a decorator that wraps notifications to add retries or metrics.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_10_design_patterns.ts`
 */

// Write your solution below this line 👇

