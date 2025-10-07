/**
 * SOLUTION 6: Abstract Classes
 * ============================
 *
 * Concepts covered:
 * - Abstract members and concrete helpers
 * - Template method (`describe`) calling abstract methods
 * - Static utilities shared across subclasses
 */

abstract class Shape {
  constructor(protected readonly color: string) {}

  abstract area(): number;
  abstract perimeter(): number;

  describe(): string {
    return `${this.color} shape → area: ${this.format(this.area())}, perimeter: ${this.format(this.perimeter())}`;
  }

  protected format(value: number): string {
    return value.toFixed(2);
  }

  static compareArea(a: Shape, b: Shape): Shape {
    return a.area() >= b.area() ? a : b;
  }
}

class Rectangle extends Shape {
  constructor(color: string, private readonly width: number, private readonly height: number) {
    super(color);
  }

  override area(): number {
    return this.width * this.height;
  }

  override perimeter(): number {
    return 2 * (this.width + this.height);
  }
}

class Circle extends Shape {
  constructor(color: string, private readonly radius: number) {
    super(color);
  }

  override area(): number {
    return Math.PI * this.radius ** 2;
  }

  override perimeter(): number {
    return 2 * Math.PI * this.radius;
  }
}

class Triangle extends Shape {
  constructor(color: string, private readonly a: number, private readonly b: number, private readonly c: number) {
    super(color);
    if (!this.isValid()) {
      throw new Error("Invalid triangle side lengths");
    }
  }

  override area(): number {
    const s = this.perimeter() / 2;
    return Math.sqrt(s * (s - this.a) * (s - this.b) * (s - this.c));
  }

  override perimeter(): number {
    return this.a + this.b + this.c;
  }

  private isValid(): boolean {
    return this.a + this.b > this.c && this.a + this.c > this.b && this.b + this.c > this.a;
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const shapes: Shape[] = [
  new Rectangle("Blue", 10, 5),
  new Circle("Red", 7),
  new Triangle("Green", 3, 4, 5),
];

shapes.forEach((shape) => console.log(shape.describe()));

const largest = shapes.reduce((prev, current) => Shape.compareArea(prev, current));
console.log(`\nLargest area belongs to: ${largest.describe()}`);

export {};
