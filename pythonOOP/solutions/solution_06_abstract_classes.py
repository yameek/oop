"""
SOLUTION 6: Abstract Base Classes (ABC)
========================================

CONCEPTS EXPLAINED:
-------------------
1. ABC (Abstract Base Class): Cannot be instantiated directly
2. @abstractmethod: Must be implemented by subclasses
3. @abstractproperty: Abstract property that must be implemented
4. Contract: ABC defines what methods subclasses must have
5. Enforces implementation: Prevents incomplete subclasses
6. Interface definition: Defines common interface for related classes

"""

from abc import ABC, abstractmethod
import math

class Shape(ABC):
    """
    Abstract base class for all shapes
    
    NOTE: Inheriting from ABC makes this an abstract class
    It cannot be instantiated directly - only through subclasses
    """
    
    @abstractmethod
    def calculate_area(self):
        """
        Calculate area of the shape
        
        NOTE: @abstractmethod decorator makes this method abstract
        All subclasses MUST implement this method or they will also be abstract
        
        Returns:
        --------
        float : Area of the shape
        """
        pass
    
    @abstractmethod
    def calculate_perimeter(self):
        """
        Calculate perimeter of the shape
        
        Returns:
        --------
        float : Perimeter of the shape
        """
        pass
    
    @property
    @abstractmethod
    def name(self):
        """
        Get the name of the shape
        
        NOTE: This is an abstract property - subclasses must implement it
        
        Returns:
        --------
        str : Name of the shape
        """
        pass
    
    def display(self):
        """
        Display shape information
        
        NOTE: This is a CONCRETE method (not abstract)
        Subclasses can use it without implementing it
        It uses abstract methods, which will be implemented by subclasses
        """
        print(f"\n{'=' * 50}")
        print(f"Shape: {self.name}")
        print(f"Area: {self.calculate_area():.2f} square units")
        print(f"Perimeter: {self.calculate_perimeter():.2f} units")
        print(f"{'=' * 50}")


class Rectangle(Shape):
    """Rectangle implementation of Shape"""
    
    def __init__(self, length, width):
        """
        Initialize a Rectangle
        
        Parameters:
        -----------
        length : float
            Length of the rectangle
        width : float
            Width of the rectangle
        """
        self.length = length
        self.width = width
    
    def calculate_area(self):
        """
        Calculate rectangle area
        
        Formula: length × width
        """
        return self.length * self.width
    
    def calculate_perimeter(self):
        """
        Calculate rectangle perimeter
        
        Formula: 2 × (length + width)
        """
        return 2 * (self.length + self.width)
    
    @property
    def name(self):
        """Return the name of this shape"""
        return "Rectangle"
    
    def is_square(self):
        """
        Check if rectangle is also a square
        
        Returns:
        --------
        bool : True if length equals width
        """
        return self.length == self.width


class Circle(Shape):
    """Circle implementation of Shape"""
    
    PI = 3.14159  # Class constant for π
    
    def __init__(self, radius):
        """
        Initialize a Circle
        
        Parameters:
        -----------
        radius : float
            Radius of the circle
        """
        self.radius = radius
    
    def calculate_area(self):
        """
        Calculate circle area
        
        Formula: π × r²
        """
        return self.PI * self.radius ** 2
    
    def calculate_perimeter(self):
        """
        Calculate circle perimeter (circumference)
        
        Formula: 2 × π × r
        """
        return 2 * self.PI * self.radius
    
    @property
    def name(self):
        """Return the name of this shape"""
        return "Circle"
    
    def get_diameter(self):
        """
        Get circle diameter
        
        Returns:
        --------
        float : Diameter of the circle
        """
        return 2 * self.radius


class Triangle(Shape):
    """Triangle implementation of Shape"""
    
    def __init__(self, side_a, side_b, side_c):
        """
        Initialize a Triangle
        
        Parameters:
        -----------
        side_a : float
            First side of the triangle
        side_b : float
            Second side of the triangle
        side_c : float
            Third side of the triangle
        """
        self.side_a = side_a
        self.side_b = side_b
        self.side_c = side_c
        
        # Validate triangle inequality
        if not self._is_valid_triangle():
            raise ValueError("Invalid triangle: sides don't satisfy triangle inequality")
    
    def _is_valid_triangle(self):
        """
        Check if sides can form a valid triangle
        
        Triangle inequality: sum of any two sides > third side
        
        Returns:
        --------
        bool : True if valid triangle
        """
        return (self.side_a + self.side_b > self.side_c and
                self.side_a + self.side_c > self.side_b and
                self.side_b + self.side_c > self.side_a)
    
    def calculate_area(self):
        """
        Calculate triangle area using Heron's formula
        
        Formula: √[s(s-a)(s-b)(s-c)]
        where s = (a+b+c)/2 (semi-perimeter)
        """
        s = (self.side_a + self.side_b + self.side_c) / 2
        area = math.sqrt(s * (s - self.side_a) * (s - self.side_b) * (s - self.side_c))
        return area
    
    def calculate_perimeter(self):
        """
        Calculate triangle perimeter
        
        Formula: a + b + c
        """
        return self.side_a + self.side_b + self.side_c
    
    @property
    def name(self):
        """Return the name of this shape"""
        return "Triangle"
    
    def get_type(self):
        """
        Determine the type of triangle
        
        Returns:
        --------
        str : Type of triangle (equilateral, isosceles, or scalene)
        """
        if self.side_a == self.side_b == self.side_c:
            return "Equilateral"
        elif self.side_a == self.side_b or self.side_b == self.side_c or self.side_a == self.side_c:
            return "Isosceles"
        else:
            return "Scalene"


def compare_shapes(shape1, shape2):
    """
    Compare two shapes by area
    
    Parameters:
    -----------
    shape1 : Shape
        First shape to compare
    shape2 : Shape
        Second shape to compare
    """
    print(f"\n{'=' * 60}")
    print("COMPARING SHAPES")
    print(f"{'=' * 60}")
    
    area1 = shape1.calculate_area()
    area2 = shape2.calculate_area()
    
    print(f"{shape1.name} area: {area1:.2f} square units")
    print(f"{shape2.name} area: {area2:.2f} square units")
    
    if area1 > area2:
        difference = area1 - area2
        print(f"\n✓ {shape1.name} is larger by {difference:.2f} square units")
    elif area2 > area1:
        difference = area2 - area1
        print(f"\n✓ {shape2.name} is larger by {difference:.2f} square units")
    else:
        print(f"\n✓ Both shapes have equal area!")
    
    print(f"{'=' * 60}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING ABSTRACT BASE CLASSES")
    print("=" * 60)
    
    # Try to instantiate abstract class (will fail)
    print("\n1. Attempting to instantiate abstract Shape class:")
    try:
        shape = Shape()
        print("❌ This shouldn't happen!")
    except TypeError as e:
        print(f"✓ Cannot instantiate: {e}")
    
    # Create concrete shapes
    print("\n2. Creating concrete shape objects:")
    
    rectangle = Rectangle(10, 5)
    circle = Circle(7)
    triangle = Triangle(3, 4, 5)  # This is a right triangle
    square = Rectangle(6, 6)
    
    print("✓ Created Rectangle, Circle, Triangle, and Square")
    
    # Display all shapes
    print("\n3. Displaying shape information:")
    
    rectangle.display()
    circle.display()
    triangle.display()
    square.display()
    
    # Test shape-specific methods
    print("\n4. Testing shape-specific methods:")
    
    print(f"\nRectangle is square? {rectangle.is_square()}")
    print(f"Square is square? {square.is_square()}")
    
    print(f"\nCircle diameter: {circle.get_diameter():.2f} units")
    
    print(f"\nTriangle type: {triangle.get_type()}")
    
    # Create more triangles to test types
    equilateral = Triangle(5, 5, 5)
    isosceles = Triangle(5, 5, 7)
    print(f"Equilateral triangle type: {equilateral.get_type()}")
    print(f"Isosceles triangle type: {isosceles.get_type()}")
    
    # Compare shapes
    print("\n5. Comparing shapes:")
    
    compare_shapes(rectangle, circle)
    compare_shapes(triangle, square)
    compare_shapes(equilateral, isosceles)
    
    # Test invalid triangle
    print("\n6. Testing invalid triangle:")
    try:
        invalid_triangle = Triangle(1, 2, 10)
        print("❌ This shouldn't happen!")
    except ValueError as e:
        print(f"✓ Caught invalid triangle: {e}")
    
    # Demonstrate polymorphism with abstract class
    print("\n7. Demonstrating polymorphism:")
    
    shapes = [rectangle, circle, triangle, square, equilateral]
    
    print("\nCalculating total area of all shapes:")
    total_area = sum(shape.calculate_area() for shape in shapes)
    print(f"Total area: {total_area:.2f} square units")
    
    print("\nAll shape names:")
    for shape in shapes:
        print(f"  - {shape.name}")
    
    # Check if objects are instances of Shape
    print("\n8. Type checking:")
    print(f"rectangle is instance of Shape? {isinstance(rectangle, Shape)}")
    print(f"circle is instance of Shape? {isinstance(circle, Shape)}")
    print(f"rectangle is instance of Rectangle? {isinstance(rectangle, Rectangle)}")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. ABC classes cannot be instantiated directly")
    print("2. @abstractmethod must be implemented by subclasses")
    print("3. Abstract classes define a contract/interface")
    print("4. Ensures all subclasses implement required methods")
    print("5. Can have both abstract and concrete methods")
    print("6. Use ABC when you want to enforce a common interface")
    print("7. Abstract properties work like abstract methods")
    print("8. Provides compile-time checking for implementation")
