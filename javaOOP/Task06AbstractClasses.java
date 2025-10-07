/**
 * TASK 6: Abstract Classes
 * =========================
 * Difficulty: Advanced ⭐⭐⭐⭐
 * 
 * Learn about: Abstract classes, Abstract methods, Template method pattern
 * 
 * PROBLEM:
 * --------
 * Create a shape calculation system using abstract classes.
 * 
 * Requirements:
 * 
 * 1. Create an abstract class 'Shape':
 *    - Instance variable: color (String)
 *    - Constructor taking color
 *    - Abstract method: calculateArea() - returns double
 *    - Abstract method: calculatePerimeter() - returns double
 *    - Concrete method: displayInfo() - prints shape info and calls abstract methods
 *    - Concrete method: getColor() - returns color
 * 
 * 2. Create concrete class 'Circle' extends Shape:
 *    - Instance variable: radius (double)
 *    - Constructor taking color and radius
 *    - Implement calculateArea() - π * r²
 *    - Implement calculatePerimeter() - 2 * π * r
 * 
 * 3. Create concrete class 'Rectangle' extends Shape:
 *    - Instance variables: length (double), width (double)
 *    - Constructor taking color, length, width
 *    - Implement calculateArea() - length * width
 *    - Implement calculatePerimeter() - 2 * (length + width)
 * 
 * 4. Create concrete class 'Triangle' extends Shape:
 *    - Instance variables: side1, side2, side3 (double)
 *    - Constructor taking color and three sides
 *    - Implement calculateArea() - use Heron's formula
 *    - Implement calculatePerimeter() - sum of all sides
 *    - Add validation: sides must form valid triangle
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create different shapes
 * - Store in Shape array (polymorphism)
 * - Calculate total area of all shapes
 * - Display info for each shape
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Abstract classes (cannot be instantiated)
 * - Abstract methods (must be implemented by subclasses)
 * - Concrete methods in abstract classes
 * - Template method pattern
 * - When to use abstract classes vs interfaces
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Shape: Circle
 * Color: Red
 * Area: 78.54
 * Perimeter: 31.42
 * 
 * Shape: Rectangle
 * Color: Blue
 * Area: 50.00
 * Perimeter: 30.00
 * 
 * Total area of all shapes: 128.54
 */

// Write your solution below:

