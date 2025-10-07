/**
 * SOLUTION 6: Abstract Classes and Template Method Pattern
 * ========================================================
 * Demonstrates using an abstract base class to define common structure while
 * letting subclasses supply specific implementations.
 */
package solutions;

abstract class Shape {
    private final String color;

    protected Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract double calculateArea();

    public abstract double calculatePerimeter();

    public void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName());
        System.out.println("Color: " + color);
        System.out.printf("Area: %.2f%n", calculateArea());
        System.out.printf("Perimeter: %.2f%n", calculatePerimeter());
        System.out.println();
    }
}

class Circle extends Shape {
    private final double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
}

class Rectangle extends Shape {
    private final double length;
    private final double width;

    public Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
}

class Triangle extends Shape {
    private final double side1;
    private final double side2;
    private final double side3;

    public Triangle(String color, double side1, double side2, double side3) {
        super(color);
        validateTriangle(side1, side2, side3);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    private void validateTriangle(double a, double b, double c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Invalid triangle side lengths");
        }
    }

    @Override
    public double calculateArea() {
        double s = calculatePerimeter() / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }
}

public class Solution06AbstractClasses {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle("Red", 5),
                new Rectangle("Blue", 10, 5),
                new Triangle("Green", 6, 7, 8)
        };

        double totalArea = 0;
        System.out.println("=== SHAPE REPORT ===\n");
        for (Shape shape : shapes) {
            shape.displayInfo();
            totalArea += shape.calculateArea();
        }
        System.out.printf("Total area of all shapes: %.2f%n", totalArea);
    }
}
