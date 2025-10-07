/**
 * TASK 10: Design Patterns
 * =========================
 * Difficulty: Very Advanced ⭐⭐⭐⭐⭐
 * 
 * Learn about: Singleton, Factory, Builder patterns
 * 
 * PROBLEM:
 * --------
 * Implement three common design patterns.
 * 
 * PART 1: SINGLETON PATTERN
 * --------------------------
 * Create a DatabaseConnection class:
 * - Only one instance can exist (singleton)
 * - Private constructor
 * - Static getInstance() method
 * - Thread-safe implementation (optional: use synchronized or eager initialization)
 * - Methods: connect(), disconnect(), query(String sql)
 * 
 * PART 2: FACTORY PATTERN
 * ------------------------
 * Create a notification system:
 * 
 * 1. Interface 'Notification':
 *    - Method: send(String message)
 * 
 * 2. Concrete classes:
 *    - EmailNotification implements Notification
 *    - SMSNotification implements Notification
 *    - PushNotification implements Notification
 * 
 * 3. NotificationFactory class:
 *    - Static method: createNotification(String type)
 *    - Returns appropriate Notification based on type
 * 
 * PART 3: BUILDER PATTERN
 * ------------------------
 * Create a User class with builder:
 * - Many optional fields: username, email, firstName, lastName, age, phone
 * - Use Builder pattern for flexible object creation
 * - Validate required fields (username and email)
 * 
 * 1. User class:
 *    - Private constructor taking Builder
 *    - All fields with getters
 *    - Static inner Builder class
 * 
 * 2. Builder class:
 *    - Methods for each field (returning Builder for chaining)
 *    - build() method returns User
 *    - Validates required fields
 * 
 * TEST YOUR CODE:
 * ---------------
 * Singleton:
 * - Get multiple instances and verify they're the same
 * - Connect to database and run queries
 * 
 * Factory:
 * - Create different notification types
 * - Send messages using each type
 * - Demonstrate polymorphism
 * 
 * Builder:
 * - Build users with different combinations of fields
 * - Demonstrate method chaining
 * - Try to build without required fields
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Singleton: Ensure only one instance exists
 * - Factory: Encapsulate object creation logic
 * - Builder: Construct complex objects step by step
 * - When to use each pattern
 * - Benefits of design patterns
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * [SINGLETON]
 * Database instances are same: true
 * Connected to database
 * Executing query: SELECT * FROM users
 * 
 * [FACTORY]
 * Sending Email: Hello via Email
 * Sending SMS: Hello via SMS
 * Sending Push: Hello via Push Notification
 * 
 * [BUILDER]
 * User{username='john_doe', email='john@example.com', firstName='John', lastName='Doe'}
 * User{username='jane', email='jane@example.com', age=25}
 */

// Write your solution below:

