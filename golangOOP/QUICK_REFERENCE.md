# Go OOP Quick Reference Guide

## Table of Contents
1. [Structs](#structs)
2. [Methods](#methods)
3. [Interfaces](#interfaces)
4. [Embedding](#embedding)
5. [Encapsulation](#encapsulation)
6. [Type Assertions](#type-assertions)
7. [Custom Types](#custom-types)
8. [Error Handling](#error-handling)
9. [Generics](#generics)

---

## Structs

### Basic Struct
```go
type Person struct {
    Name string
    Age  int
}

// Create instance
p1 := Person{Name: "Alice", Age: 30}
p2 := Person{"Bob", 25}  // positional

// Access fields
fmt.Println(p1.Name)
```

### Constructor Function
```go
func NewPerson(name string, age int) *Person {
    return &Person{
        Name: name,
        Age:  age,
    }
}

p := NewPerson("Alice", 30)
```

### Anonymous Structs
```go
person := struct {
    Name string
    Age  int
}{
    Name: "Alice",
    Age:  30,
}
```

---

## Methods

### Value Receiver
```go
type Rectangle struct {
    Width, Height float64
}

// Value receiver - doesn't modify original
func (r Rectangle) Area() float64 {
    return r.Width * r.Height
}

rect := Rectangle{Width: 10, Height: 5}
area := rect.Area()
```

### Pointer Receiver
```go
// Pointer receiver - can modify original
func (r *Rectangle) Scale(factor float64) {
    r.Width *= factor
    r.Height *= factor
}

rect := &Rectangle{Width: 10, Height: 5}
rect.Scale(2) // rect is now 20x10
```

### When to Use Each
- **Pointer receiver**: Need to modify, large struct, or convention
- **Value receiver**: Small struct, read-only, primitive-like behavior

---

## Interfaces

### Define Interface
```go
type Shape interface {
    Area() float64
    Perimeter() float64
}
```

### Implement Interface (Implicit)
```go
type Circle struct {
    Radius float64
}

// Circle implements Shape by having these methods
func (c Circle) Area() float64 {
    return math.Pi * c.Radius * c.Radius
}

func (c Circle) Perimeter() float64 {
    return 2 * math.Pi * c.Radius
}
```

### Using Interfaces
```go
func PrintArea(s Shape) {
    fmt.Println(s.Area())
}

c := Circle{Radius: 5}
PrintArea(c)  // Works! Circle satisfies Shape
```

### Empty Interface
```go
// interface{} or any (Go 1.18+) accepts anything
func Print(v interface{}) {
    fmt.Println(v)
}

Print(42)
Print("hello")
Print(Circle{Radius: 5})
```

### Common Interfaces
```go
// fmt.Stringer
type Stringer interface {
    String() string
}

// error
type error interface {
    Error() string
}

// io.Reader
type Reader interface {
    Read(p []byte) (n int, err error)
}
```

---

## Embedding

### Struct Embedding
```go
type Person struct {
    Name string
    Age  int
}

type Employee struct {
    Person  // Embedded - promoted fields
    Company string
}

emp := Employee{
    Person:  Person{Name: "Alice", Age: 30},
    Company: "TechCorp",
}

// Access promoted fields directly
fmt.Println(emp.Name)     // From Person
fmt.Println(emp.Company)  // From Employee
```

### Method Promotion
```go
func (p Person) Introduce() {
    fmt.Printf("Hi, I'm %s\n", p.Name)
}

emp := Employee{Person: Person{Name: "Alice", Age: 30}}
emp.Introduce()  // Calls Person's method
```

### Interface Embedding
```go
type Reader interface {
    Read() string
}

type Writer interface {
    Write(string) error
}

// Embed multiple interfaces
type ReadWriter interface {
    Reader
    Writer
}
```

---

## Encapsulation

### Exported vs Unexported
```go
type BankAccount struct {
    // Exported (public) - capital letter
    AccountNumber string
    
    // Unexported (private) - lowercase
    balance float64
}

// Getter
func (b *BankAccount) GetBalance() float64 {
    return b.balance
}

// Setter with validation
func (b *BankAccount) Deposit(amount float64) error {
    if amount <= 0 {
        return errors.New("amount must be positive")
    }
    b.balance += amount
    return nil
}
```

### Package-Level Encapsulation
```go
// In package "bank"

// Exported
func NewAccount() *account { ... }

// Unexported
type account struct { ... }

func (a *account) validate() error { ... }
```

---

## Type Assertions

### Basic Type Assertion
```go
var i interface{} = "hello"

// Type assertion
s := i.(string)
fmt.Println(s)

// Safe type assertion
s, ok := i.(string)
if ok {
    fmt.Println(s)
}
```

### Type Switch
```go
func describe(i interface{}) {
    switch v := i.(type) {
    case int:
        fmt.Printf("Integer: %d\n", v)
    case string:
        fmt.Printf("String: %s\n", v)
    case Circle:
        fmt.Printf("Circle with radius %f\n", v.Radius)
    default:
        fmt.Printf("Unknown type: %T\n", v)
    }
}
```

---

## Custom Types

### Type Definition
```go
type Celsius float64
type Fahrenheit float64

// Methods on custom types
func (c Celsius) ToFahrenheit() Fahrenheit {
    return Fahrenheit(c*9/5 + 32)
}

temp := Celsius(100)
f := temp.ToFahrenheit()
```

### Type Alias
```go
// Alias - same as original type
type MyInt = int

// Definition - new type
type MyInt2 int
```

### Implement Interfaces for Custom Types
```go
type Celsius float64

// Implement fmt.Stringer
func (c Celsius) String() string {
    return fmt.Sprintf("%.2f°C", c)
}

temp := Celsius(25.5)
fmt.Println(temp)  // "25.50°C"
```

---

## Error Handling

### Creating Errors
```go
import "errors"

// Simple error
err := errors.New("something went wrong")

// Formatted error
err := fmt.Errorf("user %s not found", username)
```

### Custom Error Types
```go
type ValidationError struct {
    Field   string
    Message string
}

func (e *ValidationError) Error() string {
    return fmt.Sprintf("%s: %s", e.Field, e.Message)
}

// Using
err := &ValidationError{
    Field:   "email",
    Message: "invalid format",
}
```

### Sentinel Errors
```go
var (
    ErrNotFound     = errors.New("not found")
    ErrUnauthorized = errors.New("unauthorized")
)

// Check with errors.Is()
if errors.Is(err, ErrNotFound) {
    // handle not found
}
```

### Error Wrapping (Go 1.13+)
```go
// Wrap error
err := fmt.Errorf("failed to save user: %w", originalErr)

// Check wrapped error
if errors.Is(err, ErrNotFound) {
    // ...
}

// Extract wrapped error type
var validErr *ValidationError
if errors.As(err, &validErr) {
    fmt.Println(validErr.Field)
}
```

---

## Generics (Go 1.18+)

### Generic Function
```go
func Print[T any](s []T) {
    for _, v := range s {
        fmt.Println(v)
    }
}

Print([]int{1, 2, 3})
Print([]string{"a", "b", "c"})
```

### Generic Type
```go
type Stack[T any] struct {
    items []T
}

func (s *Stack[T]) Push(item T) {
    s.items = append(s.items, item)
}

func (s *Stack[T]) Pop() (T, error) {
    if len(s.items) == 0 {
        var zero T
        return zero, errors.New("stack empty")
    }
    item := s.items[len(s.items)-1]
    s.items = s.items[:len(s.items)-1]
    return item, nil
}

// Usage
intStack := Stack[int]{}
intStack.Push(42)
```

### Constraints
```go
// Built-in constraints
func Sum[T constraints.Ordered](numbers []T) T {
    var sum T
    for _, n := range numbers {
        sum += n
    }
    return sum
}

// Custom constraint
type Number interface {
    int | int64 | float64
}

func Add[T Number](a, b T) T {
    return a + b
}
```

---

## Common Patterns

### Options Pattern
```go
type Server struct {
    host string
    port int
}

type Option func(*Server)

func WithHost(host string) Option {
    return func(s *Server) {
        s.host = host
    }
}

func WithPort(port int) Option {
    return func(s *Server) {
        s.port = port
    }
}

func NewServer(opts ...Option) *Server {
    s := &Server{
        host: "localhost",
        port: 8080,
    }
    for _, opt := range opts {
        opt(s)
    }
    return s
}

// Usage
server := NewServer(WithHost("0.0.0.0"), WithPort(3000))
```

### Builder Pattern
```go
type Computer struct {
    CPU, RAM, Storage string
}

type ComputerBuilder struct {
    computer Computer
}

func (b *ComputerBuilder) CPU(cpu string) *ComputerBuilder {
    b.computer.CPU = cpu
    return b
}

func (b *ComputerBuilder) RAM(ram string) *ComputerBuilder {
    b.computer.RAM = ram
    return b
}

func (b *ComputerBuilder) Build() Computer {
    return b.computer
}

// Usage
pc := (&ComputerBuilder{}).
    CPU("Intel i7").
    RAM("16GB").
    Build()
```

---

## Quick Tips

1. **Return pointers from constructors**: `func NewThing() *Thing`
2. **Use interfaces for flexibility**: Accept interfaces, return structs
3. **Small interfaces are better**: io.Reader has one method
4. **Check errors**: Always handle returned errors
5. **Use defer for cleanup**: `defer file.Close()`
6. **Pointer receivers for modification**: Use `*Type` to modify
7. **Capital = exported**: `Name` is public, `name` is private
8. **Composition over embedding**: Only embed when it makes sense
9. **Error messages lowercase**: "failed to connect" not "Failed to connect"
10. **gofmt is your friend**: Always format code with `go fmt`

---

**Keep this guide handy while working on tasks!**
