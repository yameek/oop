"""
TASK 6: Abstract Base Classes (ABC)
====================================
Difficulty: Advanced

Learn about: ABC module, abstractmethod, abstract properties

PROBLEM:
--------
Create a shape calculation system using Abstract Base Classes.

Requirements:
1. Import ABC and abstractmethod from abc module

2. Create an abstract class 'Shape' with:
   - Abstract method: calculate_area()
   - Abstract method: calculate_perimeter()
   - Abstract property: name
   - Concrete method: display() - prints name, area, and perimeter

3. Create class 'Rectangle' inheriting from Shape:
   - Attributes: length, width
   - Implement all abstract methods/properties
   - name property should return "Rectangle"

4. Create class 'Circle' inheriting from Shape:
   - Attribute: radius
   - Implement all abstract methods/properties
   - name property should return "Circle"
   - Use π = 3.14159

5. Create class 'Triangle' inheriting from Shape:
   - Attributes: side_a, side_b, side_c
   - Implement all abstract methods/properties
   - name property should return "Triangle"
   - Use Heron's formula for area: √[s(s-a)(s-b)(s-c)] where s = (a+b+c)/2

6. Create a function 'compare_shapes(shape1, shape2)' that:
   - Compares two shapes by area
   - Prints which shape is larger or if they're equal

TEST YOUR CODE:
---------------
- Create objects of each shape type
- Calculate and display their areas and perimeters
- Compare different shapes
- Try to instantiate Shape directly (should fail)
"""

# Write your solution below:

