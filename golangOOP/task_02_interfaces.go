/*
TASK 2: Interfaces
==================
Difficulty: Beginner to Intermediate

Learn about: Interfaces, Interface Implementation, Polymorphism

PROBLEM:
--------
Create a shape calculation system using interfaces.

Requirements:
1. Create an interface 'Shape' with methods:
   - Area() float64
   - Perimeter() float64

2. Create a struct 'Rectangle' with fields:
   - Length float64
   - Width float64

3. Implement the Shape interface for Rectangle:
   - Area() returns length * width
   - Perimeter() returns 2 * (length + width)

4. Create a struct 'Circle' with field:
   - Radius float64

5. Implement the Shape interface for Circle:
   - Area() returns π * radius²  (use math.Pi)
   - Perimeter() returns 2 * π * radius

6. Create a function 'PrintShapeInfo(s Shape)' that:
   - Takes any Shape interface
   - Prints its area and perimeter
   - Demonstrates polymorphism

7. Create a function 'CompareShapes(s1, s2 Shape)' that:
   - Compares two shapes by area
   - Prints which is larger

TEST YOUR CODE:
---------------
- Create rectangles and circles
- Pass them to PrintShapeInfo()
- Compare different shapes

NOTES:
------
- Go interfaces are implicitly implemented
- No "implements" keyword needed
- Any type with the required methods satisfies the interface
*/

package main

import (
	"fmt"
	"math"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
