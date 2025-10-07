/**
 * TASK 1: Basic Classes and Objects
 * ==================================
 * Difficulty: Beginner ⭐
 * 
 * Learn about: Classes, Objects, Constructors, Instance Variables, Methods
 * 
 * PROBLEM:
 * --------
 * Create a class called 'Book' that represents a book in a library.
 * 
 * Requirements:
 * 1. The class should have the following attributes:
 *    - title (String)
 *    - author (String)
 *    - pages (int)
 *    - isAvailable (boolean, default true)
 * 
 * 2. Create a constructor that takes title, author, and pages as parameters
 *    (isAvailable should default to true)
 * 
 * 3. Create a method called 'displayInfo()' that prints all book information
 * 
 * 4. Create a method called 'borrow()' that:
 *    - Sets isAvailable to false if the book is available
 *    - Prints "Book borrowed successfully"
 *    - If not available, prints "Book is already borrowed"
 * 
 * 5. Create a method called 'returnBook()' that:
 *    - Sets isAvailable to true
 *    - Prints "Book returned successfully"
 * 
 * TEST YOUR CODE:
 * ---------------
 * In the main method:
 * - Create at least 2 Book objects
 * - Test all methods (displayInfo, borrow, returnBook)
 * - Try borrowing the same book twice to test the logic
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - What is a class? (Blueprint for creating objects)
 * - What is an object? (Instance of a class)
 * - What is a constructor? (Special method to initialize objects)
 * - What is 'this' keyword? (Refers to current object)
 * - Instance variables vs local variables
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * --- Book Information ---
 * Title: Python Crash Course
 * Author: Eric Matthes
 * Pages: 544
 * Status: Available
 * ------------------------
 * ✓ 'Python Crash Course' borrowed successfully!
 * ✗ Sorry, 'Python Crash Course' is already borrowed.
 * ✓ 'Python Crash Course' returned successfully!
 */

// Write your solution below:

