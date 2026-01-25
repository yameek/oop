package main

import (
	"errors"
	"fmt"
)

// Solution for Task 4: ENCAPSULATION AND PACKAGES
// ===============================================

// BankAccount with unexported fields for encapsulation
type BankAccount struct {
	accountNumber string
	accountHolder string
	balance       float64
	pin           int
}

// Sentinel errors
var (
	ErrInvalidPin        = errors.New("invalid PIN")
	ErrInvalidAmount     = errors.New("amount must be positive")
	ErrInsufficientFunds = errors.New("insufficient funds")
	ErrPinMustBe4Digits  = errors.New("PIN must be 4 digits (1000-9999)")
)

// NewBankAccount constructor with validation
func NewBankAccount(number, holder string, pin int) (*BankAccount, error) {
	if pin < 1000 || pin > 9999 {
		return nil, ErrPinMustBe4Digits
	}
	return &BankAccount{
		accountNumber: number,
		accountHolder: holder,
		balance:       0,
		pin:           pin,
	}, nil
}

// Getters
func (b *BankAccount) GetAccountNumber() string { return b.accountNumber }
func (b *BankAccount) GetAccountHolder() string { return b.accountHolder }

func (b *BankAccount) GetBalance(pin int) (float64, error) {
	if pin != b.pin {
		return 0, ErrInvalidPin
	}
	return b.balance, nil
}

// Deposit adds money to the account
func (b *BankAccount) Deposit(amount float64, pin int) error {
	if pin != b.pin {
		return ErrInvalidPin
	}
	if amount <= 0 {
		return ErrInvalidAmount
	}
	b.balance += amount
	return nil
}

// Withdraw removes money from the account
func (b *BankAccount) Withdraw(amount float64, pin int) error {
	if pin != b.pin {
		return ErrInvalidPin
	}
	if amount <= 0 {
		return ErrInvalidAmount
	}
	if amount > b.balance {
		return ErrInsufficientFunds
	}
	b.balance -= amount
	return nil
}

// Transfer moves money between accounts
func (b *BankAccount) Transfer(amount float64, pin int, recipient *BankAccount) error {
	if err := b.Withdraw(amount, pin); err != nil {
		return fmt.Errorf("transfer failed: %w", err)
	}
	// Deposit doesn't require PIN for recipient (bank internal operation)
	recipient.balance += amount
	return nil
}

// ChangePin updates the account PIN
func (b *BankAccount) ChangePin(oldPin, newPin int) error {
	if oldPin != b.pin {
		return ErrInvalidPin
	}
	if newPin < 1000 || newPin > 9999 {
		return ErrPinMustBe4Digits
	}
	b.pin = newPin
	return nil
}

func main() {
	fmt.Println("SOLUTION 4: ENCAPSULATION AND PACKAGES")
	fmt.Println("=======================================")

	// Create accounts
	alice, err := NewBankAccount("ACC001", "Alice Smith", 1234)
	if err != nil {
		fmt.Println("Error creating account:", err)
		return
	}
	bob, _ := NewBankAccount("ACC002", "Bob Jones", 5678)

	fmt.Printf("\n1. Created accounts for %s and %s\n", alice.GetAccountHolder(), bob.GetAccountHolder())

	// Test deposit
	fmt.Println("\n2. Testing Deposit:")
	alice.Deposit(1000, 1234)
	balance, _ := alice.GetBalance(1234)
	fmt.Printf("Alice's balance after $1000 deposit: $%.2f\n", balance)

	// Test wrong PIN
	fmt.Println("\n3. Testing Wrong PIN:")
	err = alice.Withdraw(100, 9999)
	fmt.Printf("Withdraw with wrong PIN: %v\n", err)

	// Test transfer
	fmt.Println("\n4. Testing Transfer:")
	alice.Transfer(250, 1234, bob)
	aliceBal, _ := alice.GetBalance(1234)
	bobBal, _ := bob.GetBalance(5678)
	fmt.Printf("After transfer: Alice=$%.2f, Bob=$%.2f\n", aliceBal, bobBal)

	// Test PIN change
	fmt.Println("\n5. Testing PIN Change:")
	alice.ChangePin(1234, 4321)
	_, err = alice.GetBalance(1234) // Old PIN
	fmt.Printf("Old PIN after change: %v\n", err)
	balance, _ = alice.GetBalance(4321) // New PIN
	fmt.Printf("New PIN works: $%.2f\n", balance)
}
