package main

import (
	"errors"
	"fmt"
	"sync"
)

// Solution for Task 10: DESIGN PATTERNS IN GO
// ===========================================

// ============================================
// PART A: SINGLETON PATTERN
// ============================================

type Database struct {
	host      string
	port      int
	connected bool
}

var (
	dbInstance *Database
	dbOnce     sync.Once
)

func GetDatabaseInstance() *Database {
	dbOnce.Do(func() {
		fmt.Println("  [Singleton] Creating database instance...")
		dbInstance = &Database{host: "localhost", port: 5432}
	})
	return dbInstance
}

func (db *Database) Connect() error {
	db.connected = true
	fmt.Printf("  [DB] Connected to %s:%d\n", db.host, db.port)
	return nil
}

func (db *Database) Query(sql string) string {
	if !db.connected {
		return "Error: not connected"
	}
	return fmt.Sprintf("Result of: %s", sql)
}

// ============================================
// PART B: FACTORY PATTERN
// ============================================

type Animal interface {
	Speak() string
	Type() string
}

type Dog struct{}
func (d *Dog) Speak() string { return "Woof!" }
func (d *Dog) Type() string  { return "Dog" }

type Cat struct{}
func (c *Cat) Speak() string { return "Meow!" }
func (c *Cat) Type() string  { return "Cat" }

type Bird struct{}
func (b *Bird) Speak() string { return "Tweet!" }
func (b *Bird) Type() string  { return "Bird" }

func NewAnimal(animalType string) (Animal, error) {
	switch animalType {
	case "dog":
		return &Dog{}, nil
	case "cat":
		return &Cat{}, nil
	case "bird":
		return &Bird{}, nil
	default:
		return nil, errors.New("unknown animal type")
	}
}

// ============================================
// PART C: BUILDER PATTERN
// ============================================

type Computer struct {
	CPU     string
	RAM     int
	Storage int
	GPU     string
}

type ComputerBuilder struct {
	cpu     string
	ram     int
	storage int
	gpu     string
}

func NewComputerBuilder() *ComputerBuilder {
	return &ComputerBuilder{}
}

func (b *ComputerBuilder) WithCPU(cpu string) *ComputerBuilder {
	b.cpu = cpu
	return b
}

func (b *ComputerBuilder) WithRAM(ram int) *ComputerBuilder {
	b.ram = ram
	return b
}

func (b *ComputerBuilder) WithStorage(storage int) *ComputerBuilder {
	b.storage = storage
	return b
}

func (b *ComputerBuilder) WithGPU(gpu string) *ComputerBuilder {
	b.gpu = gpu
	return b
}

func (b *ComputerBuilder) Build() (*Computer, error) {
	if b.cpu == "" {
		return nil, errors.New("CPU is required")
	}
	if b.ram < 4 {
		return nil, errors.New("minimum 4GB RAM required")
	}
	return &Computer{CPU: b.cpu, RAM: b.ram, Storage: b.storage, GPU: b.gpu}, nil
}

// ============================================
// PART D: STRATEGY PATTERN
// ============================================

type PaymentStrategy interface {
	Pay(amount float64) error
}

type CreditCardStrategy struct {
	CardNumber string
}

func (c *CreditCardStrategy) Pay(amount float64) error {
	fmt.Printf("  [CreditCard] Paid $%.2f using card %s\n", amount, c.CardNumber[len(c.CardNumber)-4:])
	return nil
}

type PayPalStrategy struct {
	Email string
}

func (p *PayPalStrategy) Pay(amount float64) error {
	fmt.Printf("  [PayPal] Paid $%.2f via %s\n", amount, p.Email)
	return nil
}

type ShoppingCart struct {
	items    []string
	total    float64
	strategy PaymentStrategy
}

func (s *ShoppingCart) AddItem(item string, price float64) {
	s.items = append(s.items, item)
	s.total += price
}

func (s *ShoppingCart) SetPaymentStrategy(strategy PaymentStrategy) {
	s.strategy = strategy
}

func (s *ShoppingCart) Checkout() error {
	if s.strategy == nil {
		return errors.New("no payment strategy set")
	}
	return s.strategy.Pay(s.total)
}

// ============================================
// PART E: OBSERVER PATTERN
// ============================================

type Observer interface {
	Update(data string)
}

type Subject struct {
	observers []Observer
}

func (s *Subject) Attach(o Observer) {
	s.observers = append(s.observers, o)
}

func (s *Subject) Notify(data string) {
	for _, o := range s.observers {
		o.Update(data)
	}
}

type EmailObserver struct{ Email string }
func (e *EmailObserver) Update(data string) {
	fmt.Printf("  [Email] Sending to %s: %s\n", e.Email, data)
}

type SMSObserver struct{ Phone string }
func (s *SMSObserver) Update(data string) {
	fmt.Printf("  [SMS] Sending to %s: %s\n", s.Phone, data)
}

// ============================================
// MAIN
// ============================================

func main() {
	fmt.Println("SOLUTION 10: DESIGN PATTERNS IN GO")
	fmt.Println("===================================")

	// Singleton
	fmt.Println("\n1. Singleton Pattern:")
	db1 := GetDatabaseInstance()
	db2 := GetDatabaseInstance()
	fmt.Printf("  Same instance? %v\n", db1 == db2)
	db1.Connect()

	// Factory
	fmt.Println("\n2. Factory Pattern:")
	animals := []string{"dog", "cat", "bird"}
	for _, t := range animals {
		a, _ := NewAnimal(t)
		fmt.Printf("  %s says: %s\n", a.Type(), a.Speak())
	}

	// Builder
	fmt.Println("\n3. Builder Pattern:")
	computer, err := NewComputerBuilder().
		WithCPU("Intel i7").
		WithRAM(16).
		WithStorage(512).
		WithGPU("RTX 4080").
		Build()
	if err == nil {
		fmt.Printf("  Built: %+v\n", computer)
	}

	// Strategy
	fmt.Println("\n4. Strategy Pattern:")
	cart := &ShoppingCart{}
	cart.AddItem("Laptop", 999.99)
	cart.AddItem("Mouse", 29.99)
	cart.SetPaymentStrategy(&CreditCardStrategy{CardNumber: "1234567890123456"})
	cart.Checkout()
	cart.SetPaymentStrategy(&PayPalStrategy{Email: "user@example.com"})
	cart.Checkout()

	// Observer
	fmt.Println("\n5. Observer Pattern:")
	subject := &Subject{}
	subject.Attach(&EmailObserver{Email: "user@example.com"})
	subject.Attach(&SMSObserver{Phone: "+1234567890"})
	subject.Notify("Order shipped!")
}
