# TypeScript OOP Quick Reference

Keep this guide handy while working through the tasks. It focuses on TypeScript's class syntax, access modifiers, interfaces, abstract classes, and other core OOP tools.

## Table of Contents
1. [Class Basics](#class-basics)
2. [Fields & Access Modifiers](#fields--access-modifiers)
3. [Static Members](#static-members)
4. [Inheritance](#inheritance)
5. [Accessors & Encapsulation](#accessors--encapsulation)
6. [Interfaces & Structural Typing](#interfaces--structural-typing)
7. [Abstract Classes](#abstract-classes)
8. [Union Types & Type Guards](#union-types--type-guards)
9. [Symbols & Iteration](#symbols--iteration)
10. [Composition Patterns](#composition-patterns)

---

## Class Basics

```typescript
class Person {
  // Parameter properties automatically create & assign fields
  constructor(public name: string, private age: number) {}

  greet(): void {
    console.log(`Hi, I'm ${this.name}`);
  }
}

const person = new Person("Alice", 29);
person.greet();
```

- `public` is the default visibility for fields/methods.
- Parameter properties (`public name: string`) reduce boilerplate.

---

## Fields & Access Modifiers

```typescript
class Account {
  private balance = 0;        // Instance field
  protected history: number[] = [];
  readonly currency = "USD"; // Immutable after construction

  constructor(initial: number) {
    this.balance = initial;
  }
}

class SavingsAccount extends Account {
  printLatest(): void {
    const last = this.history.at(-1);
    console.log(`Latest change: ${last ?? "none"}`);
  }
}
```

- `private` fields are only accessible within the class.
- `protected` fields are accessible in subclasses.
- `readonly` prevents reassignment after construction.
- ECMAScript `#private` fields are available too.

---

## Static Members

```typescript
class User {
  private static nextId = 1;

  static createGuest(): User {
    return new User(`guest-${this.nextId++}`);
  }

  private constructor(public readonly username: string) {}
}

const guest = User.createGuest();
```

- Use `static` for class-level data and helper functions.
- Private constructors force use of factory methods.

---

## Inheritance

```typescript
class Vehicle {
  constructor(protected brand: string) {}

  start(): void {
    console.log(`${this.brand} starting...`);
  }
}

class Car extends Vehicle {
  constructor(brand: string, private doors: number) {
    super(brand);
  }

  start(): void {
    super.start();
    console.log(`Car with ${this.doors} doors ready to go!`);
  }
}

new Car("Tesla", 4).start();
```

- Use `extends` to inherit, `super()` to call the parent constructor.
- Methods can be overridden; call `super.method()` if needed.

---

## Accessors & Encapsulation

```typescript
class Temperature {
  #kelvin: number;

  constructor(celsius: number) {
    this.#kelvin = celsius + 273.15;
  }

  get celsius(): number {
    return this.#kelvin - 273.15;
  }

  set celsius(value: number) {
    if (value < -273.15) {
      throw new RangeError("Below absolute zero");
    }
    this.#kelvin = value + 273.15;
  }
}

const temp = new Temperature(20);
temp.celsius = 25;
```

- Use `get`/`set` for computed properties and validation.
- `#field` syntax creates true JavaScript private fields.

---

## Interfaces & Structural Typing

```typescript
interface Notifier {
  notify(message: string): void;
}

interface Logger {
  log(message: string): void;
}

class ConsoleNotifier implements Notifier, Logger {
  notify(message: string): void {
    console.log(`🔔 ${message}`);
  }

  log(message: string): void {
    console.log(`[log] ${message}`);
  }
}

function alertUser(target: Notifier): void {
  target.notify("Task complete");
}

alertUser(new ConsoleNotifier());

// Structural typing: object literal works if it matches the shape
alertUser({ notify: (msg) => console.log(`Literal ${msg}`) });
```

- Interfaces describe shapes; anything matching the shape is acceptable.
- Interfaces can `extends` other interfaces for composition.

---

## Abstract Classes

```typescript
abstract class Shape {
  constructor(protected color: string) {}

  abstract area(): number;

  describe(): string {
    return `${this.color} shape with area ${this.area()}`;
  }
}

class Rectangle extends Shape {
  constructor(color: string, private width: number, private height: number) {
    super(color);
  }

  area(): number {
    return this.width * this.height;
  }
}

const rect = new Rectangle("blue", 5, 3);
console.log(rect.describe());
```

- `abstract` classes can include implemented and abstract members.
- You cannot instantiate an abstract class directly.

---

## Union Types & Type Guards

```typescript
interface EmailNotification {
  type: "email";
  address: string;
}

interface SmsNotification {
  type: "sms";
  phone: string;
}

type Notification = EmailNotification | SmsNotification;

function sendNotification(n: Notification): void {
  if (n.type === "email") {
    console.log(`Emailing ${n.address}`);
  } else {
    console.log(`Texting ${n.phone}`);
  }
}
```

- Discriminated unions enable exhaustive switches.
- Type guards (`in`, `instanceof`, custom predicates) refine the type at runtime.

---

## Symbols & Iteration

```typescript
class Deck implements Iterable<string> {
  #cards: string[] = ["A♠", "K♦", "Q♣"];

  get [Symbol.toStringTag](): string {
    return "Deck";
  }

  toString(): string {
    return this.#cards.join(", ");
  }

  *[Symbol.iterator](): Iterator<string> {
    for (const card of this.#cards) {
      yield card;
    }
  }
}

for (const card of new Deck()) {
  console.log(card);
}
```

- Implement `Symbol.iterator` to support `for...of` and spread syntax.
- `Symbol.toStringTag` customizes `Object.prototype.toString.call(instance)`.

---

## Composition Patterns

```typescript
class Engine {
  start(): void {
    console.log("Engine started");
  }
}

class GPS {
  locate(): string {
    return "Current location";
  }
}

class Car {
  constructor(private engine: Engine, private gps: GPS) {}

  drive(): void {
    this.engine.start();
    console.log(this.gps.locate());
    console.log("Car is moving");
  }
}

const car = new Car(new Engine(), new GPS());
car.drive();
```

- Composition wires multiple small objects together to create behavior.
- Prefer injecting dependencies via the constructor for easier testing.

---

## Quick Tips

1. Use `strict` mode in `tsconfig.json` for better type safety.
2. Prefer interfaces for contracts; use abstract classes when you need shared implementation.
3. Avoid exposing mutable state; use `readonly`, accessors, or copies.
4. Lean on union types and exhaustiveness checks for safer branching.
5. Combine `async` functions with OOP patterns to model workflows cleanly.

Happy coding! Refer back anytime you need a refresher on TypeScript OOP syntax.
