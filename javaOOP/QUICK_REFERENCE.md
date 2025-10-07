# Java OOP Quick Reference Guide

## Table of Contents
1. [Basic Class Syntax](#basic-class-syntax)
2. [Instance vs Static Variables](#instance-vs-static-variables)
3. [Method Types](#method-types)
4. [Constructors](#constructors)
5. [Inheritance](#inheritance)
6. [Encapsulation](#encapsulation)
7. [Polymorphism](#polymorphism)
8. [Abstract Classes](#abstract-classes)
9. [Interfaces](#interfaces)
10. [Special Methods](#special-methods)
11. [Common Patterns](#common-patterns)

---

## Basic Class Syntax

```java
public class MyClass {
    // Instance variables
    private int value;
    private String name;
    
    // Constructor
    public MyClass(int value, String name) {
        this.value = value;
        this.name = name;
    }
    
    // Instance method
    public int getValue() {
        return value;
    }
    
    // Instance method
    public void setValue(int value) {
        this.value = value;
    }
}

// Creating an instance
MyClass obj = new MyClass(10, "Example");
int val = obj.getValue();  // Returns 10
```

---

## Instance vs Static Variables

```java
public class Example {
    // Static variable - shared by all instances
    public static String classVar = "I'm shared by all instances";
    
    // Instance variable - unique per instance
    private String instanceVar;
    
    public Example(String value) {
        this.instanceVar = value;
    }
    
    // Static method - can only access static members
    public static void staticMethod() {
        System.out.println(classVar);  // OK
        // System.out.println(instanceVar);  // ERROR!
    }
    
    // Instance method - can access both static and instance members
    public void instanceMethod() {
        System.out.println(classVar);      // OK
        System.out.println(instanceVar);   // OK
    }
}

// Access static variable via class
Example.classVar;

// Each instance has its own instanceVar
Example obj1 = new Example("A");
Example obj2 = new Example("B");
obj1.classVar == obj2.classVar;  // true (same for all)
```

---

## Method Types

```java
public class MethodTypes {
    private int instanceVar = 10;
    private static int staticVar = 20;
    
    // Instance method - needs an object
    public void instanceMethod() {
        System.out.println(instanceVar);
        System.out.println(staticVar);
    }
    
    // Static method - no object needed
    public static void staticMethod() {
        System.out.println(staticVar);
        // System.out.println(instanceVar);  // ERROR!
    }
    
    // Method with parameters
    public int add(int a, int b) {
        return a + b;
    }
    
    // Method overloading - same name, different parameters
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
}
```

---

## Constructors

```java
public class Person {
    private String name;
    private int age;
    
    // Default constructor
    public Person() {
        this.name = "Unknown";
        this.age = 0;
    }
    
    // Parameterized constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Constructor overloading
    public Person(String name) {
        this(name, 0);  // Call another constructor
    }
    
    // Copy constructor
    public Person(Person other) {
        this.name = other.name;
        this.age = other.age;
    }
}

// Usage
Person p1 = new Person();                    // Default
Person p2 = new Person("Alice", 25);         // Parameterized
Person p3 = new Person("Bob");               // Name only
Person p4 = new Person(p2);                  // Copy
```

---

## Inheritance

```java
// Parent class (superclass)
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    public void makeSound() {
        System.out.println("Some generic sound");
    }
}

// Child class (subclass)
public class Dog extends Animal {
    private String breed;
    
    public Dog(String name, String breed) {
        super(name);  // Call parent constructor
        this.breed = breed;
    }
    
    // Method overriding
    @Override
    public void makeSound() {
        System.out.println(name + " barks: Woof!");
    }
    
    // New method specific to Dog
    public void wagTail() {
        System.out.println(name + " wags tail");
    }
}

// Usage
Dog dog = new Dog("Buddy", "Golden Retriever");
dog.eat();        // Inherited from Animal
dog.makeSound();  // Overridden in Dog
dog.wagTail();    // Specific to Dog
```

---

## Encapsulation

```java
public class BankAccount {
    // Private fields - cannot be accessed directly
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    // Getter - read access
    public double getBalance() {
        return balance;
    }
    
    // Setter with validation - controlled write access
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}
```

---

## Polymorphism

```java
// Runtime polymorphism (Method Overriding)
class Shape {
    public void draw() {
        System.out.println("Drawing a shape");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

class Rectangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }
}

// Usage - polymorphism in action
Shape shape1 = new Circle();
Shape shape2 = new Rectangle();
shape1.draw();  // Outputs: "Drawing a circle"
shape2.draw();  // Outputs: "Drawing a rectangle"

// Compile-time polymorphism (Method Overloading)
class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

---

## Abstract Classes

```java
// Abstract class - cannot be instantiated
public abstract class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void makeSound();
    
    // Concrete method - inherited as-is
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Concrete class - must implement abstract methods
public class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow!");
    }
}

// Usage
// Animal a = new Animal("Test");  // ERROR! Cannot instantiate abstract class
Cat cat = new Cat("Whiskers");
cat.makeSound();  // OK
cat.sleep();      // OK
```

---

## Interfaces

```java
// Interface - defines a contract
public interface Drawable {
    // Abstract method (implicitly public and abstract)
    void draw();
    
    // Default method (Java 8+)
    default void display() {
        System.out.println("Displaying...");
    }
    
    // Static method (Java 8+)
    static void info() {
        System.out.println("Drawable interface");
    }
}

// Implementing an interface
public class Circle implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}

// Multiple interface implementation
public interface Movable {
    void move();
}

public class Ball implements Drawable, Movable {
    @Override
    public void draw() {
        System.out.println("Drawing a ball");
    }
    
    @Override
    public void move() {
        System.out.println("Ball is moving");
    }
}
```

---

## Special Methods

```java
public class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    // toString - for string representation
    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + "}";
    }
    
    // equals - for comparing objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Double.compare(product.price, price) == 0 &&
               name.equals(product.name);
    }
    
    // hashCode - for hash-based collections
    @Override
    public int hashCode() {
        int result = name.hashCode();
        long temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

// Comparable - for natural ordering
public class Student implements Comparable<Student> {
    private String name;
    private int grade;
    
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.grade, other.grade);
    }
}
```

---

## Common Patterns

### Singleton Pattern
```java
public class Database {
    private static Database instance;
    
    private Database() {
        // Private constructor
    }
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
}
```

### Factory Pattern
```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() { System.out.println("Circle"); }
}

class Rectangle implements Shape {
    public void draw() { System.out.println("Rectangle"); }
}

class ShapeFactory {
    public static Shape createShape(String type) {
        if (type.equals("circle")) return new Circle();
        if (type.equals("rectangle")) return new Rectangle();
        return null;
    }
}
```

### Builder Pattern
```java
public class Person {
    private String name;
    private int age;
    private String address;
    
    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.address = builder.address;
    }
    
    public static class Builder {
        private String name;
        private int age;
        private String address;
        
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder setAge(int age) {
            this.age = age;
            return this;
        }
        
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }
        
        public Person build() {
            return new Person(this);
        }
    }
}

// Usage
Person person = new Person.Builder()
    .setName("Alice")
    .setAge(25)
    .setAddress("123 Main St")
    .build();
```

---

## Access Modifiers

| Modifier    | Class | Package | Subclass | World |
|-------------|-------|---------|----------|-------|
| public      | ✓     | ✓       | ✓        | ✓     |
| protected   | ✓     | ✓       | ✓        | ✗     |
| default     | ✓     | ✓       | ✗        | ✗     |
| private     | ✓     | ✗       | ✗        | ✗     |

---

## Tips and Best Practices

1. **Naming Conventions**
   - Classes: PascalCase (e.g., `MyClass`)
   - Methods/Variables: camelCase (e.g., `myMethod`)
   - Constants: UPPER_SNAKE_CASE (e.g., `MAX_VALUE`)

2. **Encapsulation**
   - Keep fields private
   - Provide public getters/setters
   - Validate in setters

3. **Inheritance**
   - Use when there's an "is-a" relationship
   - Favor composition over inheritance
   - Don't inherit just for code reuse

4. **Interfaces**
   - Use for "can-do" relationships
   - Keep interfaces focused and small
   - Code to interfaces, not implementations

5. **General**
   - One class per file
   - DRY (Don't Repeat Yourself)
   - SOLID principles
   - Write Javadoc comments
