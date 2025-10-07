# Go (Golang) OOP Learning Tasks

Welcome to your comprehensive Go Object-Oriented Programming learning journey! This repository contains 10 carefully designed tasks that will teach you OOP concepts in Go's unique way.

## 📚 Structure

```
golangOOP/
├── README.md                              # This file
├── QUICK_REFERENCE.md                     # Go OOP syntax reference
├── PROGRESS_TRACKER.md                    # Track your learning
├── START_HERE.go                          # Welcome program
├── task_01_structs_methods.go             # Task 1: Structs and Methods
├── task_02_interfaces.go                  # Task 2: Interfaces
├── task_03_embedding.go                   # Task 3: Struct Embedding (Composition)
├── task_04_encapsulation.go               # Task 4: Encapsulation and Packages
├── task_05_interface_composition.go       # Task 5: Interface Composition
├── task_06_type_assertions.go             # Task 6: Type Assertions and Type Switches
├── task_07_custom_types.go                # Task 7: Custom Types and Methods
├── task_08_error_handling.go              # Task 8: Custom Errors
├── task_09_generics.go                    # Task 9: Generics (Go 1.18+)
├── task_10_design_patterns.go             # Task 10: Design Patterns
└── solutions/
    ├── solution_01_structs_methods.go
    ├── solution_02_interfaces.go
    └── ... (all solutions)
```

## 🎯 Learning Path

### Beginner Level (Tasks 1-2)
1. **Task 1: Structs and Methods** ⭐
   - Learn: Structs, Methods, Receivers, Constructors
   - Build: A library book management system
   - Time: 30-45 minutes

2. **Task 2: Interfaces** ⭐⭐
   - Learn: Interfaces, Implicit Implementation, Polymorphism
   - Build: A shape calculation system
   - Time: 45-60 minutes

### Intermediate Level (Tasks 3-5)
3. **Task 3: Struct Embedding (Composition)** ⭐⭐
   - Learn: Embedding, Composition, Method Promotion
   - Build: A vehicle system
   - Time: 60-75 minutes

4. **Task 4: Encapsulation and Packages** ⭐⭐⭐
   - Learn: Exported/Unexported, Getters, Error Handling
   - Build: A secure bank account system
   - Time: 60-90 minutes

5. **Task 5: Interface Composition** ⭐⭐⭐
   - Learn: Interface Embedding, Multiple Interfaces
   - Build: A media player system
   - Time: 60-90 minutes

### Advanced Level (Tasks 6-10)
6. **Task 6: Type Assertions and Type Switches** ⭐⭐⭐⭐
   - Learn: Type Assertions, Type Switches, interface{}
   - Build: A payment processing system
   - Time: 75-90 minutes

7. **Task 7: Custom Types and Methods** ⭐⭐⭐⭐
   - Learn: Type Definitions, Methods on Any Type, fmt.Stringer
   - Build: A temperature conversion system
   - Time: 75-90 minutes

8. **Task 8: Custom Errors and Error Handling** ⭐⭐⭐⭐
   - Learn: Error Interface, Custom Errors, Sentinel Errors
   - Build: A user registration system
   - Time: 90-120 minutes

9. **Task 9: Generics (Go 1.18+)** ⭐⭐⭐⭐⭐
   - Learn: Type Parameters, Constraints, Generic Functions
   - Build: A generic data structure library
   - Time: 90-120 minutes

10. **Task 10: Design Patterns in Go** ⭐⭐⭐⭐⭐
    - Learn: Singleton, Factory, Builder, Strategy, Observer
    - Build: Multiple pattern implementations
    - Time: 120+ minutes

## 🚀 How to Use This Repository

### Prerequisites
- Go 1.18 or later installed
- Basic understanding of Go syntax
- Familiarity with functions and basic data types

### For Each Task:

1. **Read the Task File**
   ```bash
   cat task_01_structs_methods.go
   ```

2. **Write Your Solution**
   - Edit the task file
   - Write code after the instructions
   - Follow the requirements

3. **Run Your Solution**
   ```bash
   go run task_01_structs_methods.go
   ```

4. **If You Get Stuck**
   - Check the QUICK_REFERENCE.md
   - Review Go documentation
   - Then check the solution:
   ```bash
   go run solutions/solution_01_structs_methods.go
   ```

5. **Learn from the Solution**
   - Understand WHY it works
   - Note different approaches
   - Try the concepts in new programs

## 📖 Key Differences from Traditional OOP

Go is NOT a traditional OOP language, but supports OOP concepts in unique ways:

### No Classes
- Go uses **structs** instead of classes
- Structs are data containers

### No Inheritance
- Go uses **composition** via embedding
- "Favor composition over inheritance" built into the language
- No class hierarchies

### Interfaces Are Implicit
- No `implements` keyword
- Any type with the right methods satisfies the interface
- Called "duck typing" or "structural typing"

### Methods via Receivers
- Methods are functions with receiver parameters
- Receiver can be value or pointer

### Exported vs Unexported
- Capital letter = exported (public)
- Lowercase letter = unexported (private)
- Package-level encapsulation

## 💡 Go OOP Principles

1. **Composition Over Inheritance**: Use embedding, not class hierarchies
2. **Interfaces for Abstraction**: Define behavior, not data
3. **Implicit Implementation**: No explicit "implements"
4. **Small Interfaces**: Prefer many small interfaces over large ones
5. **Accept Interfaces, Return Structs**: Function design pattern
6. **Error Handling**: Explicit error values, not exceptions

## 🧪 Running and Testing

### Run a Single File
```bash
go run task_01_structs_methods.go
```

### Run a Solution
```bash
go run solutions/solution_01_structs_methods.go
```

### Format Code (Go Standard)
```bash
go fmt task_01_structs_methods.go
```

### Check for Common Mistakes
```bash
go vet task_01_structs_methods.go
```

## 📝 Best Practices You'll Learn

- ✅ When to use value vs pointer receivers
- ✅ How to design effective interfaces
- ✅ When to use embedding vs separate fields
- ✅ Proper error handling patterns
- ✅ Naming conventions (idiomatic Go)
- ✅ Package organization
- ✅ Using generics effectively (Go 1.18+)
- ✅ Implementing design patterns in Go

## 🎓 After Completing All Tasks

Next steps:

1. **Build a Project**: Create a complete Go application using these concepts
2. **Read Real Code**: Study popular Go libraries (stdlib, gorilla, gin)
3. **Learn Concurrency**: Goroutines and channels
4. **Study More Patterns**: Context, Options, Functional Options
5. **Contribute**: Contribute to open-source Go projects

## 📚 Additional Resources

- [Official Go Tour](https://tour.golang.org/)
- [Effective Go](https://golang.org/doc/effective_go)
- [Go by Example](https://gobyexample.com/)
- [Go Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments)
- [Go Proverbs](https://go-proverbs.github.io/)

## 🤝 Getting Help

If stuck:
1. Read error messages carefully (Go errors are helpful!)
2. Check the task requirements
3. Review QUICK_REFERENCE.md
4. Search Go documentation
5. Check the solution file
6. Understand the WHY, not just the WHAT

## 🏆 Challenge Yourself

After each task:

- **Extend it**: Add new features
- **Optimize**: Make it more idiomatic
- **Test it**: Write unit tests
- **Benchmark**: Use Go's benchmarking
- **Document**: Write godoc comments

---

## Important Notes on Go OOP

### Go is Different
Go doesn't have:
- Classes
- Inheritance
- Constructors (by convention, use NewTypeName)
- Public/Private keywords (uses capitalization)
- Abstract classes (uses interfaces)

### Go Philosophy
- Simplicity over complexity
- Composition over inheritance
- Explicit over implicit
- Pragmatism over purity

**Happy Coding with Go! 🚀**

Remember: Go's approach to OOP is different, but powerful. Embrace the Go way!
