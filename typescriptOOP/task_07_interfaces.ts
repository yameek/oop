/**
 * TASK 7: Interfaces & Structural Typing
 * ======================================
 * Difficulty: Advanced
 *
 * Learn about: interface composition, `implements`, structural typing, mixins
 *
 * SCENARIO
 * --------
 * Create a notification system that can send email, SMS, or push notifications
 * using a common contract. Demonstrate structural typing by using object
 * literals that satisfy the interface without explicit classes.
 *
 * REQUIREMENTS
 * ------------
 * 1. Define an interface `NotificationChannel` with:
 *    - `type: string` (literal strings like "email", "sms", ...)
 *    - `send(recipient: string, message: string): void`
 *
 * 2. Create at least three concrete classes that `implements NotificationChannel`
 *    (e.g., `EmailNotification`, `SmsNotification`, `PushNotification`). Each
 *    should log a meaningful message.
 *
 * 3. Write a `NotificationService` class that:
 *    - Accepts an array of `NotificationChannel` via its constructor.
 *    - Exposes a method `broadcast(message: string): void` that sends the
 *      message to a default recipient for every channel.
 *    - Exposes a method `send(type: string, recipient: string, message: string)`
 *      that finds a matching channel (case-insensitive) and sends the message.
 *      Throw an error if the channel type is unknown.
 *
 * 4. Demonstrate structural typing by passing an object literal that matches the
 *    interface shape (without a class) into the service.
 *
 * BONUS (optional)
 * ---------------
 * - Use interface extension to model shared configuration (e.g., `Configurable`).
 * - Add `readonly` or optional properties to the interface.
 * - Implement a mixin or higher-order function that enhances channels with
 *   logging or retry behavior.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_07_interfaces.ts`
 */

// Write your solution below this line 👇

