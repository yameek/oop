package main

import (
"fmt"
"math"
)

// Shape interface defines behavior for shapes
// NOTE: Interfaces are implicitly implemented in Go
type Shape interface {
Area() float64
Perimeter() float64
}

// Rectangle struct
type Rectangle struct {
Length float64
Width  float64
}

// Area implements Shape interface for Rectangle
func (r Rectangle) Area() float64 {
return r.Length * r.Width
}

// Perimeter implements Shape interface for Rectangle
func (r Rectangle) Perimeter() float64 {
return 2 * (r.Length + r.Width)
}

// Circle struct
type Circle struct {
Radius float64
}

// Area implements Shape interface for Circle
func (c Circle) Area() float64 {
return math.Pi * c.Radius * c.Radius
}

// Perimeter (circumference) implements Shape interface for Circle
func (c Circle) Perimeter() float64 {
return 2 * math.Pi * c.Radius
}

// PrintShapeInfo demonstrates polymorphism
// Takes any type that implements Shape interface
func PrintShapeInfo(s Shape) {
fmt.Println("\n" + "=".repeat(50))
fmt.Printf("Shape Information\n")
fmt.Println("=".repeat(50))
fmt.Printf("Area: %.2f square units\n", s.Area())
fmt.Printf("Perimeter: %.2f units\n", s.Perimeter())
fmt.Println("=".repeat(50))
}

// CompareShapes compares two shapes by area
func CompareShapes(s1, s2 Shape) {
fmt.Println("\nComparing Shapes:")
area1 := s1.Area()
area2 := s2.Area()

fmt.Printf("Shape 1 area: %.2f\n", area1)
fmt.Printf("Shape 2 area: %.2f\n", area2)

if area1 > area2 {
fmt.Printf("Shape 1 is larger by %.2f square units\n", area1-area2)
} else if area2 > area1 {
fmt.Printf("Shape 2 is larger by %.2f square units\n", area2-area1)
} else {
fmt.Println("Both shapes have equal area!")
}
}

func main() {
fmt.Println("TESTING INTERFACES - POLYMORPHISM")
fmt.Println("=".repeat(60))

// Create shapes
rect := Rectangle{Length: 10, Width: 5}
circle := Circle{Radius: 7}
square := Rectangle{Length: 6, Width: 6}

// Demonstrate polymorphism - same function works with different types
fmt.Println("\n1. Rectangle:")
PrintShapeInfo(rect)

fmt.Println("\n2. Circle:")
PrintShapeInfo(circle)

fmt.Println("\n3. Square (also a Rectangle):")
PrintShapeInfo(square)

// Compare shapes
fmt.Println("\n4. Comparing shapes:")
CompareShapes(rect, circle)
CompareShapes(circle, square)

// Store different shapes in slice of Shape interface
fmt.Println("\n5. Polymorphic slice:")
shapes := []Shape{rect, circle, square}

totalArea := 0.0
for i, shape := range shapes {
area := shape.Area()
totalArea += area
fmt.Printf("Shape %d area: %.2f\n", i+1, area)
}
fmt.Printf("Total area of all shapes: %.2f\n", totalArea)

fmt.Println("\nKEY TAKEAWAYS:")
fmt.Println("1. Interfaces define behavior (methods)")
fmt.Println("2. Types implicitly implement interfaces")
fmt.Println("3. No 'implements' keyword needed")
fmt.Println("4. Enables polymorphism")
fmt.Println("5. Interface values can hold any type that implements it")
}
