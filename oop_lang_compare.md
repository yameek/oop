# Python vs Go vs Java vs TypeScript OOP - Key Differences

This guide helps you understand how core Object-Oriented Programming concepts translate between Python, Go, Java, and TypeScript.

## Fundamental Differences

| Concept | Python | Go | Java | TypeScript |
|---------|--------|-----|------|------------|
| **Primary Construct** | Classes | Structs | Classes | Classes |
| **Inheritance** | Yes (single & multiple) | No (use composition) | Single class inheritance, multiple interfaces | Single class inheritance, multiple interfaces |
| **Interfaces** | Implicit (duck typing) | Explicit definition, implicit implementation | Explicit definition, explicit implementation (`implements`) | Explicit definition, structural typing (implicit implementation) |
| **Encapsulation** | `_private`, `__private` by convention/name mangling | lowercase = unexported, Capital = exported | `private`, `protected`, `public` keywords | `private`, `protected`, `public`, `readonly` keywords |
| **Constructors** | `__init__()` special method | `NewTypeName()` factory functions | Class-named constructors | `constructor(...)` within class |
| **Methods** | `def method(self)` | `func (r Receiver) Method()` | `public void method()` with `this` | `methodName(): returnType { ... }` with `this` |
| **Polymorphism** | Duck typing, inheritance | Interfaces | Inheritance + interfaces | Inheritance + interfaces + structural typing |
| **Error Handling** | Exceptions (`try/except`) | Multiple return values with `error` | Checked & unchecked exceptions (`try/catch`) | Exceptions (`try/catch`) + union/`Result` patterns |

---

## Class / Struct / Class / Class

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

### Java
```java
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void greet() {
        System.out.printf("Hi, I'm %s%n", name);
    }
}

Person p = new Person("Alice", 30);
p.greet();
```

### TypeScript
```typescript
class Person {
    constructor(private name: string, private age: number) {}

    greet(): void {
        console.log(`Hi, I'm ${this.name}`);
    }
}

const person = new Person("Alice", 30);
person.greet();
```

---

## Inheritance vs Composition

### Python (Inheritance)
```python
class Animal:
    def __init__(self, name):
        self.name = name

    def speak(self):
        raise NotImplementedError

class Dog(Animal):
    def speak(self):
        return "Woof!"

dog = Dog("Buddy")
print(dog.speak())
print(dog.name)
```

### Go (Composition via Embedding)
```go
type Animal struct {
    Name string
}

type Dog struct {
    Animal // embedded
}

func (d Dog) Speak() string {
    return "Woof!"
}

dog := Dog{Animal: Animal{Name: "Buddy"}}
fmt.Println(dog.Speak())
fmt.Println(dog.Name)
```

### Java (Inheritance)
```java
class Animal {
    protected final String name;

    Animal(String name) {
        this.name = name;
    }

    String speak() {
        return "";
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    String speak() {
        return "Woof!";
    }
}

Dog dog = new Dog("Buddy");
System.out.println(dog.speak());
System.out.println(dog.name);
```

### TypeScript (Inheritance / Composition)
```typescript
class Animal {
    constructor(protected name: string) {}

    speak(): string {
        return "";
    }
}

class Dog extends Animal {
    speak(): string {
        return "Woof!";
    }
}

const dog = new Dog("Buddy");
console.log(dog.speak());
console.log((dog as any).name); // protected: accessible via casting but not recommended
```

---

## Interfaces

### Python (Duck Typing)
```python
class Duck:
    def quack(self):
        return "Quack!"

class Person:
    def quack(self):
        return "I'm quacking!"

def make_it_quack(thing):
    print(thing.quack())

make_it_quack(Duck())
make_it_quack(Person())
```

### Go (Explicit Interface, Implicit Implementation)
```go
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

MakeItQuack(Duck{})
MakeItQuack(Person{})
```

### Java (Explicit Interface + Implementation)
```java
interface Quacker {
    String quack();
}

class Duck implements Quacker {
    public String quack() {
        return "Quack!";
    }
}

class Person implements Quacker {
    public String quack() {
        return "I'm quacking!";
    }
}

static void makeItQuack(Quacker q) {
    System.out.println(q.quack());
}

makeItQuack(new Duck());
makeItQuack(new Person());
```

### TypeScript (Explicit Interface + Structural Typing)
```typescript
interface Quacker {
    quack(): string;
}

class Duck implements Quacker {
    quack(): string {
        return "Quack!";
    }
}

class Person implements Quacker {
    quack(): string {
        return "I'm quacking!";
    }
}

function makeItQuack(q: Quacker): void {
    console.log(q.quack());
}

makeItQuack(new Duck());
makeItQuack(new Person());

// Structural typing: object literal that matches the interface also works
makeItQuack({ quack: () => "Literal quack" });
```

---

## Encapsulation

### Python
```python
class BankAccount:
    def __init__(self, balance):
        self._balance = balance      # protected by convention
        self.__pin = 1234            # private (name mangling)

    @property
    def balance(self):
        return self._balance

    def deposit(self, amount):
        self._balance += amount

account = BankAccount(100)
print(account.balance)
```

### Go
```go
package bank

type account struct {
    balance float64 // unexported (package-private)
    pin     int
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

// Outside the package, direct field access is disallowed.
```

### Java
```java
public class BankAccount {
    private double balance;
    private final int pin;

    public BankAccount(double balance, int pin) {
        this.balance = balance;
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }
}

BankAccount account = new BankAccount(100, 1234);
System.out.println(account.getBalance());
```

### TypeScript
```typescript
class BankAccount {
    constructor(private balance: number, private readonly pin: number) {}

    getBalance(): number {
        return this.balance;
    }

    deposit(amount: number): void {
        this.balance += amount;
    }
}

const account = new BankAccount(100, 1234);
console.log(account.getBalance());
```

---

## Method Receivers and `self`/`this`

### Python (`self`)
```python
class Counter:
    def __init__(self):
        self.count = 0

    def increment(self):
        self.count += 1

c = Counter()
c.increment()
```

### Go (Value vs Pointer Receivers)
```go
type Counter struct {
    Count int
}

func (c Counter) Snapshot() int {
    return c.Count
}

func (c *Counter) Increment() {
    c.Count++
}

counter := &Counter{}
counter.Increment()
```

### Java (`this` and static methods)
```java
public class Counter {
    private int count;

    public void increment() {
        this.count++;
    }

    public int snapshot() {
        return count;
    }

    public static Counter zero() {
        return new Counter();
    }
}

Counter counter = Counter.zero();
counter.increment();
```

### TypeScript (`this` and static helpers)
```typescript
class Counter {
    private count = 0;

    increment(): void {
        this.count++;
    }

    snapshot(): number {
        return this.count;
    }

    static zero(): Counter {
        return new Counter();
    }
}

const counter = Counter.zero();
counter.increment();
```

---

## Magic Methods vs Interfaces vs Overrides vs Operator Alternatives

### Python (Magic Methods)
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
print(p1)
print(p1 + p2)
```

### Go (Interfaces)
```go
type Point struct {
    X, Y int
}

func (p Point) String() string {
    return fmt.Sprintf("(%d, %d)", p.X, p.Y)
}

func (p Point) Add(other Point) Point {
    return Point{p.X + other.X, p.Y + other.Y}
}

p1 := Point{1, 2}
p2 := Point{3, 4}
fmt.Println(p1)
fmt.Println(p1.Add(p2))
```

### Java (Method Overrides)
```java
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public Point add(Point other) {
        return new Point(x + other.x, y + other.y);
    }
}

Point p1 = new Point(1, 2);
Point p2 = new Point(3, 4);
System.out.println(p1);
System.out.println(p1.add(p2));
```

### TypeScript (Method Overrides + Helpers)
```typescript
class Point {
    constructor(private x: number, private y: number) {}

    toString(): string {
        return `(${this.x}, ${this.y})`;
    }

    add(other: Point): Point {
        return new Point(this.x + other.x, this.y + other.y);
    }
}

const p1 = new Point(1, 2);
const p2 = new Point(3, 4);
console.log(p1.toString());
console.log(p1.add(p2).toString());
```

---

## Multiple Inheritance vs Interface Composition vs Mixins

### Python
```python
class Reader:
    def read(self):
        return "reading"

class Writer:
    def write(self, data):
        print(f"writing: {data}")

class ReadWriter(Reader, Writer):
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
    Reader
    Writer
}

type File struct{}

func (File) Read() string { return "reading" }

func (File) Write(data string) {
    fmt.Printf("writing: %s\n", data)
}

var rw ReadWriter = File{}
rw.Read()
rw.Write("data")
```

### Java
```java
interface Reader {
    String read();
}

interface Writer {
    void write(String data);
}

class File implements Reader, Writer {
    public String read() {
        return "reading";
    }

    public void write(String data) {
        System.out.printf("writing: %s%n", data);
    }
}

Reader reader = new File();
Writer writer = new File();
```

### TypeScript (Interfaces + Mixins)
```typescript
interface Reader {
    read(): string;
}

interface Writer {
    write(data: string): void;
}

class File implements Reader, Writer {
    read(): string {
        return "reading";
    }

    write(data: string): void {
        console.log(`writing: ${data}`);
    }
}

const reader: Reader = new File();
const writer: Writer = new File();
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

var s Shape = Circle{Radius: 5}
```

### Java
```java
abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

Shape shape = new Circle(5);
```

### TypeScript
```typescript
abstract class Shape {
    abstract area(): number;
}

class Circle extends Shape {
    constructor(private radius: number) {
        super();
    }

    area(): number {
        return Math.PI * this.radius * this.radius;
    }
}

const shape: Shape = new Circle(5);
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
        return ValidationError{Field: "age", Message: "must be positive"}
    }
    return nil
}

if err := ValidateAge(-5); err != nil {
    fmt.Printf("Error: %v\n", err)
}
```

### Java
```java
class ValidationException extends Exception {
    ValidationException(String message) {
        super(message);
    }
}

static void validateAge(int age) throws ValidationException {
    if (age < 0) {
        throw new ValidationException("Age must be positive");
    }
}

try {
    validateAge(-5);
} catch (ValidationException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### TypeScript
```typescript
class ValidationError extends Error {}

function validateAge(age: number): void {
    if (age < 0) {
        throw new ValidationError("Age must be positive");
    }
}

try {
    validateAge(-5);
} catch (e) {
    if (e instanceof ValidationError) {
        console.log(`Error: ${e.message}`);
    }
}

// Alternative pattern with union types
function maybeValidateAge(age: number): { ok: true } | { ok: false; message: string } {
    if (age < 0) {
        return { ok: false, message: "Age must be positive" };
    }
    return { ok: true };
}
```

---

## Summary

### Python Strengths
- Traditional OOP model with rich runtime reflection
- Magic methods enable operator overloading and protocol-oriented design
- Multiple inheritance for mixing behaviors

### Go Strengths
- Simplicity via composition and interfaces
- Fast compile times and static binaries
- Concurrency primitives (goroutines, channels) complement interface-based design

### Java Strengths
- Mature class-based OOP with strong tooling and JVM performance
- Explicit access control and type system
- Rich standard library and ecosystem with frameworks (Spring, Jakarta EE)

### TypeScript Strengths
- Brings static typing to JavaScript with flexible structural typing
- Excellent developer tooling and rapid feedback in editors
- Interoperates with the entire JavaScript ecosystem (browser & Node.js)

### Philosophies
- **Python**: "There should be one—and preferably only one—obvious way to do it."
- **Go**: "Less is more" — prioritize simplicity and readability.
- **Java**: "Write once, run anywhere" — portability and consistency across platforms.
- **TypeScript**: "Gradual typing for scalable JavaScript" — type safety without losing JS flexibility.

All four languages embrace OOP, but they lean on different design philosophies—choose the one that aligns with your project's needs, runtime targets, and team preferences.
