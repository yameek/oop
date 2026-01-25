package main

import (
	"errors"
	"fmt"
	"strings"
)

// Solution for Task 6: TYPE ASSERTIONS AND TYPE SWITCHES
// ======================================================

// Payment interface
type Payment interface {
	Process() error
	GetAmount() float64
}

// CreditCard payment
type CreditCard struct {
	CardNumber string
	CardHolder string
	Amount     float64
	CVV        int
}

func (c *CreditCard) Process() error {
	if !c.ValidateCard() {
		return errors.New("invalid credit card")
	}
	fmt.Printf("  [CreditCard] Charged $%.2f to card ending in %s\n",
		c.Amount, c.CardNumber[len(c.CardNumber)-4:])
	return nil
}

func (c *CreditCard) GetAmount() float64 { return c.Amount }

func (c *CreditCard) ValidateCard() bool {
	return len(c.CardNumber) == 16 && c.CVV >= 100 && c.CVV <= 999
}

// PayPal payment
type PayPal struct {
	Email     string
	Amount    float64
	AccountID string
}

func (p *PayPal) Process() error {
	if !p.ValidateEmail() {
		return errors.New("invalid PayPal email")
	}
	fmt.Printf("  [PayPal] Charged $%.2f to %s\n", p.Amount, p.Email)
	return nil
}

func (p *PayPal) GetAmount() float64 { return p.Amount }

func (p *PayPal) ValidateEmail() bool {
	return strings.Contains(p.Email, "@")
}

// Crypto payment
type Crypto struct {
	WalletAddress string
	Amount        float64
	CryptoType    string
}

func (c *Crypto) Process() error {
	if !c.ValidateWallet() {
		return errors.New("invalid wallet address")
	}
	fmt.Printf("  [Crypto] Sent $%.2f in %s to %s...\n",
		c.Amount, c.CryptoType, c.WalletAddress[:8])
	return nil
}

func (c *Crypto) GetAmount() float64 { return c.Amount }

func (c *Crypto) ValidateWallet() bool {
	return len(c.WalletAddress) >= 26
}

// ProcessPayment uses type assertion
func ProcessPayment(p Payment) {
	// Type assertion with comma-ok idiom
	if cc, ok := p.(*CreditCard); ok {
		fmt.Println("  Validating credit card...")
		if cc.ValidateCard() {
			cc.Process()
		}
		return
	}

	if pp, ok := p.(*PayPal); ok {
		fmt.Println("  Validating PayPal account...")
		if pp.ValidateEmail() {
			pp.Process()
		}
		return
	}

	if cr, ok := p.(*Crypto); ok {
		fmt.Println("  Validating crypto wallet...")
		if cr.ValidateWallet() {
			cr.Process()
		}
		return
	}

	fmt.Println("  Unknown payment type")
}

// ProcessPayments uses type switch
func ProcessPayments(payments []Payment) map[string]float64 {
	totals := make(map[string]float64)

	for _, p := range payments {
		switch v := p.(type) {
		case *CreditCard:
			v.Process()
			totals["CreditCard"] += v.Amount
		case *PayPal:
			v.Process()
			totals["PayPal"] += v.Amount
		case *Crypto:
			v.Process()
			totals["Crypto"] += v.Amount
		default:
			fmt.Printf("  Unknown payment type: %T\n", v)
		}
	}
	return totals
}

// DescribePayment handles empty interface
func DescribePayment(p interface{}) {
	switch v := p.(type) {
	case *CreditCard:
		fmt.Printf("  Credit Card ending in %s\n", v.CardNumber[len(v.CardNumber)-4:])
	case *PayPal:
		fmt.Printf("  PayPal account: %s\n", v.Email)
	case *Crypto:
		fmt.Printf("  %s wallet: %s...\n", v.CryptoType, v.WalletAddress[:8])
	case string:
		fmt.Printf("  Not a payment, just a string: %s\n", v)
	case int:
		fmt.Printf("  Not a payment, just an int: %d\n", v)
	default:
		fmt.Printf("  Unknown type: %T\n", v)
	}
}

func main() {
	fmt.Println("SOLUTION 6: TYPE ASSERTIONS AND TYPE SWITCHES")
	fmt.Println("==============================================")

	// Create payments
	cc := &CreditCard{"1234567890123456", "Alice", 99.99, 123}
	pp := &PayPal{"alice@example.com", 49.99, "ACC123"}
	cr := &Crypto{"bc1qar0srrr7xfkvy5l643lydnw9re59gtzzwf5mdq", 199.99, "Bitcoin"}

	// Test type assertion
	fmt.Println("\n1. ProcessPayment (Type Assertion):")
	ProcessPayment(cc)
	ProcessPayment(pp)
	ProcessPayment(cr)

	// Test type switch
	fmt.Println("\n2. ProcessPayments (Type Switch):")
	payments := []Payment{cc, pp, cr}
	totals := ProcessPayments(payments)
	fmt.Println("\n  Totals by type:")
	for k, v := range totals {
		fmt.Printf("    %s: $%.2f\n", k, v)
	}

	// Test empty interface
	fmt.Println("\n3. DescribePayment (Empty Interface):")
	DescribePayment(cc)
	DescribePayment(pp)
	DescribePayment("Hello")
	DescribePayment(42)
}
