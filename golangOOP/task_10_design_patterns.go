/*
TASK 10: Design Patterns in Go
===============================
Difficulty: Advanced

Learn about: Singleton, Factory, Builder, Strategy Patterns

PROBLEM:
--------
Implement common design patterns in idiomatic Go.

PART A - SINGLETON PATTERN:
---------------------------
1. Create a struct 'Database' with:
   - host string
   - port int
   - connected bool

2. Implement Singleton pattern using:
   - Package-level variable
   - sync.Once for thread-safe initialization
   - GetInstance() function

3. Add methods to Database:
   - Connect() error
   - Disconnect() error
   - Query(sql string) string

PART B - FACTORY PATTERN:
--------------------------
4. Create interface 'Animal' with:
   - Speak() string
   - Type() string

5. Create structs: Dog, Cat, Bird implementing Animal

6. Create a factory function 'NewAnimal(animalType string) (Animal, error)' that:
   - Returns appropriate animal based on type
   - Returns error for unknown types

PART C - BUILDER PATTERN:
--------------------------
7. Create struct 'Computer' with many fields:
   - CPU, RAM, Storage, GPU, etc.

8. Create 'ComputerBuilder' with:
   - Fluent interface for setting fields
   - Build() method that returns Computer
   - Validation in Build()

9. Methods like:
   - WithCPU(cpu string) *ComputerBuilder
   - WithRAM(ram int) *ComputerBuilder
   - Build() (*Computer, error)

PART D - STRATEGY PATTERN:
---------------------------
10. Create interface 'PaymentStrategy' with:
    - Pay(amount float64) error

11. Create implementations:
    - CreditCardStrategy
    - PayPalStrategy
    - CryptoStrategy

12. Create 'ShoppingCart' that:
    - Holds items and total
    - SetPaymentStrategy(strategy PaymentStrategy)
    - Checkout() processes payment using current strategy

PART E - OBSERVER PATTERN:
---------------------------
13. Create interface 'Observer' with:
    - Update(data string)

14. Create struct 'Subject' with:
    - observers []Observer
    - Methods: Attach, Detach, Notify

15. Create concrete observers:
    - EmailNotifier
    - SMSNotifier
    - LogNotifier

TEST YOUR CODE:
---------------
SINGLETON:
- Get instance multiple times, verify same object
- Test thread safety

FACTORY:
- Create different animals
- Test error handling

BUILDER:
- Build computers with different configurations
- Test validation

STRATEGY:
- Use different payment strategies
- Switch strategies dynamically

OBSERVER:
- Attach multiple observers
- Test notification distribution

NOTES:
------
- Go favors composition over inheritance
- Use interfaces for abstraction
- Singleton uses sync.Once for thread safety
- Builder pattern great for complex object construction
- Strategy pattern uses interface substitution
*/

package main

import (
	"errors"
	"fmt"
	"sync"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
