/**
 * TASK 3: Inheritance
 * ===================
 * Difficulty: Intermediate ⭐⭐
 * 
 * Learn about: Parent classes, Child classes, super keyword, Method overriding
 * 
 * PROBLEM:
 * --------
 * Create a vehicle hierarchy system.
 * 
 * Requirements:
 * 
 * 1. Create a parent class 'Vehicle':
 *    - Instance variables: brand (String), year (int), price (double)
 *    - Constructor taking brand, year, price
 *    - Method: displayInfo() - prints vehicle details
 *    - Method: start() - prints "Vehicle is starting..."
 *    - Method: stop() - prints "Vehicle is stopping..."
 * 
 * 2. Create a child class 'Car' that extends Vehicle:
 *    - Additional instance variable: numberOfDoors (int)
 *    - Constructor taking brand, year, price, numberOfDoors
 *    - Must call parent constructor using super()
 *    - Override start() method - print "Car engine is starting..."
 *    - Override displayInfo() to include number of doors
 * 
 * 3. Create a child class 'Motorcycle' that extends Vehicle:
 *    - Additional instance variable: hasStorage (boolean)
 *    - Constructor taking brand, year, price, hasStorage
 *    - Override start() method - print "Motorcycle is revving..."
 *    - Override displayInfo() to include storage info
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create instances of Car and Motorcycle
 * - Call displayInfo() on both
 * - Call start() and stop() on both
 * - Demonstrate polymorphism (Vehicle reference to Car/Motorcycle object)
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Inheritance (is-a relationship)
 * - super keyword (calling parent constructor and methods)
 * - Method overriding (@Override annotation)
 * - Code reuse through inheritance
 * - Polymorphism basics
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Car - Brand: Toyota, Year: 2022, Price: $25000.00, Doors: 4
 * Car engine is starting...
 * Vehicle is stopping...
 * 
 * Motorcycle - Brand: Harley, Year: 2023, Price: $15000.00, Storage: true
 * Motorcycle is revving...
 * Vehicle is stopping...
 */

// Write your solution below:

