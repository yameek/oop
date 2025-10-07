/**
 * TASK 5: Polymorphism
 * =====================
 * Difficulty: Intermediate-Advanced ⭐⭐⭐
 * 
 * Learn about: Method overriding, Method overloading, Runtime polymorphism
 * 
 * PROBLEM:
 * --------
 * Create a payment processing system supporting multiple payment methods.
 * 
 * Requirements:
 * 
 * 1. Create a parent class 'Payment':
 *    - Instance variables: amount (double), description (String)
 *    - Constructor taking amount and description
 *    - Abstract-like method: processPayment() - prints "Processing payment..."
 *    - Method: displayInfo() - prints payment details
 * 
 * 2. Create child class 'CreditCardPayment' extends Payment:
 *    - Additional variables: cardNumber (String), cardHolder (String)
 *    - Constructor taking all parameters
 *    - Override processPayment() - print credit card specific message
 *    - Override displayInfo() to include card info (last 4 digits only)
 * 
 * 3. Create child class 'PayPalPayment' extends Payment:
 *    - Additional variable: email (String)
 *    - Constructor taking all parameters
 *    - Override processPayment() - print PayPal specific message
 *    - Override displayInfo() to include email
 * 
 * 4. Create child class 'CashPayment' extends Payment:
 *    - Additional variable: amountTendered (double)
 *    - Constructor taking all parameters
 *    - Override processPayment() - print cash specific message
 *    - Method: calculateChange() - returns change amount
 * 
 * 5. Demonstrate method overloading:
 *    - Create a PaymentProcessor class with multiple process() methods
 *    - process(Payment p)
 *    - process(Payment p, boolean sendReceipt)
 *    - process(Payment[] payments) - process multiple payments
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create different payment objects
 * - Store them in a Payment array (polymorphism!)
 * - Process all payments in a loop
 * - Demonstrate method overloading
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Polymorphism (parent reference to child object)
 * - Method overriding (same method, different behavior)
 * - Method overloading (same name, different parameters)
 * - Runtime vs compile-time polymorphism
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Processing Credit Card payment of $99.99
 * Card: **** **** **** 1234
 * 
 * Processing PayPal payment of $49.99
 * Email: user@example.com
 * 
 * Processing Cash payment of $25.00
 * Tendered: $30.00, Change: $5.00
 */

// Write your solution below:

