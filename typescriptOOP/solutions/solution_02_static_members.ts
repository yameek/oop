/**
 * SOLUTION 2: Static Members & Factory Methods
 * ============================================
 *
 * Concepts covered:
 * - Private constructors + factory helpers
 * - Static counters & registries
 * - Literal union type for roles
 */

type EmployeeRole = "Engineer" | "Manager" | "Designer";

class Employee {
  private static nextId = 1;
  private static readonly registry: Employee[] = [];

  private constructor(
    private readonly name: string,
    private readonly role: EmployeeRole,
    readonly id: number
  ) {}

  static createEngineer(name: string): Employee {
    return this.register(new Employee(name, "Engineer", this.nextId++));
  }

  static createManager(name: string): Employee {
    return this.register(new Employee(name, "Manager", this.nextId++));
  }

  static createDesigner(name: string): Employee {
    return this.register(new Employee(name, "Designer", this.nextId++));
  }

  static totalEmployees(): number {
    return this.registry.length;
  }

  static all(): ReadonlyArray<Employee> {
    return [...this.registry];
  }

  private static register(employee: Employee): Employee {
    this.registry.push(employee);
    return employee;
  }

  toString(): string {
    return `[#${this.id}] ${this.name} — ${this.role}`;
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const team = [
  Employee.createEngineer("Alex"),
  Employee.createEngineer("Sam"),
  Employee.createManager("Priya"),
  Employee.createDesigner("Morgan"),
];

console.log("Team Roster:");
team.forEach((member) => console.log(member.toString()));
console.log(`\nTotal employees: ${Employee.totalEmployees()}`);

console.log("\nRegistry snapshot:");
Employee.all().forEach((member, index) => {
  console.log(`${index + 1}. ${member.toString()}`);
});

export {};
