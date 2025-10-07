# Python vs Go OOP - Key Differences

This guide helps you understand how OOP concepts translate between Python and Go.

## Fundamental Differences

| Concept | Python | Go |
|---------|--------|-----|
| **Primary Construct** | Classes | Structs |
| **Inheritance** | Yes (single & multiple) | No (use composition) |
| **Interfaces** | Implicit (duck typing) | Explicit definition, implicit implementation |
| **Encapsulation** | `_private`, `__private` | lowercase = unexported, Capital = exported |
| **Constructors** | `__init__()` | `NewTypeName()` functions |
| **Methods** | `def method(self)` | `func (r Receiver) Method()` |
| **Polymorphism** | Duck typing, inheritance | Interfaces |

---

## Class / Struct

### Python
```python
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age
    
    def greet(self):
        print(f"Hi, I'm {self.name}")

p = Person("Alice", 30)
```

### Go
```go
type Person struct {
    Name string
    Age  int
}

func NewPerson(name string, age int) *Person {
    return &Person{Name: name, Age: age}
}

func (p Person) Greet() {
    fmt.Printf("Hi, I'm %s\n", p.Name)
}

p := NewPerson("Alice", 30)
```

---

## Inheritance vs Composition

### Python (Inheritance)
```python
class Animal:
    def __init__(self, name):
        self.name = name
    
    def speak(self):
        pass

class Dog(Animal):
    def speak(self):
        return "Woof!"

dog = Dog("Buddy")
print(dog.speak())  # "Woof!"
print(dog.name)     # "Buddy" (inherited)
```

### Go (Composition via Embedding)
```go
type Animal struct {
    Name string
}

type Dog struct {
    Animal  // Embedded
}

func (d Dog) Speak() string {
    return "Woof!"
}

dog := Dog{Animal: Animal{Name: "Buddy"}}
fmt.Println(dog.Speak())  // "Woof!"
fmt.Println(dog.Name)     // "Buddy" (promoted)
```

---

## Interfaces

### Python (Duck Typing)
```python
# No explicit interface definition
class Duck:
    def quack(self):
        return "Quack!"

class Person:
    def quack(self):
        return "I'm quacking!"

def make_it_quack(thing):
    print(thing.quack())

make_it_quack(Duck())    # Works
make_it_quack(Person())  # Works
```

### Go (Explicit Interface, Implicit Implementation)
```go
// Explicit interface definition
type Quacker interface {
    Quack() string
}

type Duck struct{}

func (d Duck) Quack() string {
    return "Quack!"
}

type Person struct{}

func (p Person) Quack() string {
    return "I'm quacking!"
}

func MakeItQuack(q Quacker) {
    fmt.Println(q.Quack())
}

MakeItQuack(Duck{})    // Works (Duck implements Quacker)
MakeItQuack(Person{})  // Works (Person implements Quacker)
```

---

## Encapsulation

### Python
```python
class BankAccount:
    def __init__(self, balance):
        self._balance = balance  # Protected (convention)
        self.__pin = 1234       # Private (name mangling)
    
    @property
    def balance(self):
        return self._balance
    
    def deposit(self, amount):
        self._balance += amount

account = BankAccount(100)
print(account.balance)      # OK (via property)
# account.balance = 500     # Would work (setter not defined)
```

### Go
```go
package bank

type account struct {
    balance float64  // unexported (private to package)
    pin     int      // unexported
}

func NewAccount(balance float64, pin int) *account {
    return &account{balance: balance, pin: pin}
}

func (a *account) GetBalance() float64 {
    return a.balance
}

func (a *account) Deposit(amount float64) {
    a.balance += amount
}

// From outside package:
// acc.balance  // Compile error! unexported
// acc.GetBalance()  // OK
```

---

## Method Receivers

### Python (self)
```python
class Counter:
    def __init__(self):
        self.count = 0
    
    def increment(self):
        self.count += 1  # Modifies self
```

### Go (Value vs Pointer Receivers)
```go
type Counter struct {
    Count int
}

// Value receiver - doesn't modify original
func (c Counter) GetCount() int {
    return c.Count
}

// Pointer receiver - can modify original
func (c *Counter) Increment() {
    c.Count++
}

counter := &Counter{}
counter.Increment()  // Modifies counter
```

---

## Magic Methods vs Interfaces

### Python
```python
class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y
    
    def __str__(self):
        return f"({self.x}, {self.y})"
    
    def __add__(self, other):
        return Point(self.x + other.x, self.y + other.y)

p1 = Point(1, 2)
p2 = Point(3, 4)
print(p1)        # (1, 2)
print(p1 + p2)   # (4, 6)
```

### Go
```go
type Point struct {
    X, Y int
}

// Implement fmt.Stringer interface
func (p Point) String() string {
    return fmt.Sprintf("(%d, %d)", p.X, p.Y)
}

// Add is just a regular method (no operator overloading)
func (p Point) Add(other Point) Point {
    return Point{p.X + other.X, p.Y + other.Y}
}

p1 := Point{1, 2}
p2 := Point{3, 4}
fmt.Println(p1)           // (1, 2)
fmt.Println(p1.Add(p2))   // (4, 6)
```

---

## Multiple Inheritance vs Interface Composition

### Python
```python
class Reader:
    def read(self):
        return "reading"

class Writer:
    def write(self, data):
        print(f"writing: {data}")

class ReadWriter(Reader, Writer):  # Multiple inheritance
    pass

rw = ReadWriter()
rw.read()
rw.write("data")
```

### Go
```go
type Reader interface {
    Read() string
}

type Writer interface {
    Write(data string)
}

type ReadWriter interface {
    Reader   // Interface composition
    Writer
}

type File struct{}

func (f File) Read() string {
    return "reading"
}

func (f File) Write(data string) {
    fmt.Printf("writing: %s\n", data)
}

// File automatically implements ReadWriter
var rw ReadWriter = File{}
rw.Read()
rw.Write("data")
```

---

## Abstract Base Classes vs Interfaces

### Python
```python
from abc import ABC, abstractmethod

class Shape(ABC):
    @abstractmethod
    def area(self):
        pass

class Circle(Shape):
    def __init__(self, radius):
        self.radius = radius
    
    def area(self):
        return 3.14 * self.radius ** 2

# shape = Shape()  # Error: can't instantiate
circle = Circle(5)
```

### Go
```go
type Shape interface {
    Area() float64
}

type Circle struct {
    Radius float64
}

func (c Circle) Area() float64 {
    return 3.14 * c.Radius * c.Radius
}

// No way to instantiate interface directly
// Just define behavior
var s Shape = Circle{Radius: 5}
```

---

## Error Handling

### Python
```python
class ValidationError(Exception):
    pass

def validate_age(age):
    if age < 0:
        raise ValidationError("Age must be positive")
    return True

try:
    validate_age(-5)
except ValidationError as e:
    print(f"Error: {e}")
```

### Go
```go
type ValidationError struct {
    Field   string
    Message string
}

func (e ValidationError) Error() string {
    return fmt.Sprintf("%s: %s", e.Field, e.Message)
}

func ValidateAge(age int) error {
    if age < 0 {
        return ValidationError{"age", "must be positive"}
    }
    return nil
}

if err := ValidateAge(-5); err != nil {
    fmt.Printf("Error: %v\n", err)
}
```

---

## Summary

### Python Strengths:
- More traditional OOP (familiar to Java/C++ programmers)
- Magic methods for operator overloading
- Multiple inheritance
- Duck typing flexibility

### Go Strengths:
- Simpler, more explicit
- Composition over inheritance (less complex hierarchies)
- Fast compilation and execution
- No implicit behavior (easier to reason about)
- Better suited for concurrent programming

### Philosophy:
- **Python**: "There should be one-- and preferably only one --obvious way to do it."
- **Go**: "Less is more" - simplicity and clarity over features

Both are excellent, just different approaches to OOP!
