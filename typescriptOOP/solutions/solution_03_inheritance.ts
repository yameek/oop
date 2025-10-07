/**
 * SOLUTION 3: Inheritance & Method Overriding
 * ===========================================
 *
 * Concepts covered:
 * - Protected helpers in base classes
 * - Method overriding with `override`
 * - Polymorphic arrays of base types
 */

class Vehicle {
  constructor(
    protected readonly brand: string,
    protected readonly model: string,
    private readonly baseRate: number
  ) {}

  describe(): string {
    return `${this.brand} ${this.model}`;
  }

  pricePerDay(): number {
    return this.baseRate;
  }

  protected formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount);
  }

  summary(): string {
    return `${this.describe()} → ${this.formatCurrency(this.pricePerDay())} per day`;
  }
}

class Car extends Vehicle {
  constructor(brand: string, model: string, baseRate: number, private readonly doors: number) {
    super(brand, model, baseRate);
  }

  override describe(): string {
    return `${super.describe()} (${this.doors} doors)`;
  }

  override pricePerDay(): number {
    return Math.round(super.pricePerDay() * 1.15 * 100) / 100;
  }
}

class Motorcycle extends Vehicle {
  constructor(
    brand: string,
    model: string,
    baseRate: number,
    private readonly hasSidecar: boolean
  ) {
    super(brand, model, baseRate);
  }

  override describe(): string {
    const sidecarInfo = this.hasSidecar ? "with sidecar" : "solo";
    return `${super.describe()} (${sidecarInfo})`;
  }

  override pricePerDay(): number {
    return Math.round(super.pricePerDay() * 0.9 * 100) / 100;
  }
}

class Truck extends Vehicle {
  constructor(
    brand: string,
    model: string,
    baseRate: number,
    private readonly maxPayloadKg: number
  ) {
    super(brand, model, baseRate);
  }

  override describe(): string {
    return `${super.describe()} (payload ${this.maxPayloadKg}kg)`;
  }

  override pricePerDay(): number {
    const payloadFactor = Math.min(this.maxPayloadKg / 1000, 2);
    return super.pricePerDay() + payloadFactor * 35;
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const fleet: Vehicle[] = [
  new Car("Tesla", "Model 3", 95, 4),
  new Car("Toyota", "Corolla", 55, 4),
  new Motorcycle("Harley-Davidson", "Street 750", 70, true),
  new Motorcycle("Honda", "CBR500R", 65, false),
  new Truck("Ford", "F-150", 120, 1500),
];

console.log("Fleet pricing overview:\n");
fleet.forEach((vehicle, index) => {
  console.log(`${index + 1}. ${vehicle.summary()}`);
});

export {};
