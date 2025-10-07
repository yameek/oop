# TypeScript OOP Learning Tasks

Welcome to the TypeScript Object-Oriented Programming (OOP) track! This folder mirrors the Python, Go, and Java journeys and gives you 10 structured challenges that take you from the basics of classes to advanced design patterns—while embracing TypeScript's static typing and modern language features.

## 📦 Structure

```
typescriptOOP/
├── README.md                           # This file
├── START_HERE.ts                       # Quick demo runner
├── task_01_basic_classes.ts            # Task 1: Basic Classes and Objects
├── task_02_static_members.ts           # Task 2: Static Members & Factory Methods
├── task_03_inheritance.ts              # Task 3: Inheritance & Method Overriding
├── task_04_encapsulation.ts            # Task 4: Encapsulation & Accessors
├── task_05_polymorphism.ts             # Task 5: Polymorphism & Union Types
├── task_06_abstract_classes.ts         # Task 6: Abstract Classes
├── task_07_interfaces.ts               # Task 7: Interfaces & Structural Typing
├── task_08_symbols_iterators.ts        # Task 8: Symbols, Iterators & Custom Behavior
├── task_09_composition.ts              # Task 9: Composition & Dependency Injection
├── task_10_design_patterns.ts          # Task 10: Design Patterns (Singleton + Factory)
├── PROGRESS_TRACKER.md                 # Mark off tasks and reflect on learnings
├── QUICK_REFERENCE.md                  # Cheat sheet for key TypeScript OOP syntax
├── package.json                        # Local dev dependencies & scripts
├── tsconfig.json                       # TypeScript compiler configuration
└── solutions/
    ├── solution_01_basic_classes.ts
    ├── solution_02_static_members.ts
    ├── solution_03_inheritance.ts
    ├── solution_04_encapsulation.ts
    ├── solution_05_polymorphism.ts
    ├── solution_06_abstract_classes.ts
    ├── solution_07_interfaces.ts
    ├── solution_08_symbols_iterators.ts
    ├── solution_09_composition.ts
    └── solution_10_design_patterns.ts
```

## 🧭 Learning Path

### Beginner (Tasks 1-3)
1. **Basic Classes & Objects**
   - Create and use classes, constructors, and instance methods
   - Understand `public`/`private` by default behavior

2. **Static Members & Factory Methods**
   - Learn when to use `static` fields/methods
   - Expose controlled object creation

3. **Inheritance & Method Overriding**
   - Extend base classes with `extends`
   - Use `super` and default parameter values

### Intermediate (Tasks 4-6)
4. **Encapsulation & Accessors**
   - Protect data with access modifiers, getters/setters
   - Explore `readonly` and private `#fields`

5. **Polymorphism & Union Types**
   - Practice substitution, discriminated unions, and type guards

6. **Abstract Classes**
   - Enforce contracts with abstract members and template methods

### Advanced (Tasks 7-10)
7. **Interfaces & Structural Typing**
   - Compose interfaces, leverage `implements`, and understand structural matching

8. **Symbols, Iterators & Custom Behavior**
   - Implement `Symbol.iterator`, `Symbol.toStringTag`, and custom `toString`

9. **Composition & Dependency Injection**
   - Favor HAS-A relationships, delegate responsibilities, and wire dependencies

10. **Design Patterns**
    - Implement Singleton and Factory patterns in idiomatic TypeScript

## 🚀 Getting Started

1. Ensure you have a recent Node.js (>= 18) installed.
2. Install dependencies from this directory:

```bash
npm install
```

3. Run the starter demo or any solution/task via `ts-node`:

```bash
# Run the quick showcase
npm run start

# Run an individual task file (after you implement it)
npm run task -- task_01_basic_classes.ts

# Run a reference solution
npm run solution -- solutions/solution_01_basic_classes.ts
```

> The scripts automatically compile in-memory with `ts-node` so you can iterate without generating JS files. Use `npm run build` to emit compiled output in `dist/` if you prefer.

## 🛠️ How to Use Each Task

1. **Read the top comment** for the scenario, requirements, and optional stretch goals.
2. **Implement your solution** in the same file under the comment banner.
3. **Run the file** using `npm run task -- TASK_FILENAME` to check your progress.
4. **Peek at `solutions/`** only after you’ve explored your own approach.
5. **Reflect in `PROGRESS_TRACKER.md`** on what you learned and what was tricky.
6. **Refer to `QUICK_REFERENCE.md`** whenever you need a syntax refresher.

## 🧰 TypeScript Highlights Covered

- Access modifiers (`public`, `private`, `protected`) and `readonly`
- Parameter properties, optional/default parameters
- Static members and instance tracking
- Inheritance, polymorphism, and method overriding
- Abstract classes, interfaces, structural typing
- Union types, discriminated unions, and type guards
- Symbols, iterables, async iteration, and custom string tags
- Composition vs inheritance and dependency injection patterns
- Classic design patterns adapted to TypeScript

## 🤝 Pair This with Other Tracks

Each task aligns conceptually with the Go, Java, and Python exercises. Jump between language folders to compare syntax, idioms, and implementation strategies.

Happy hacking! Type your solutions from scratch, experiment with features, and make the patterns your own.
