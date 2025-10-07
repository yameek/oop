/*
TASK 6: Type Assertions and Type Switches
==========================================
Difficulty: Advanced

Learn about: Type Assertions, Type Switches, Interface{}, Type Safety

PROBLEM:
--------
Create a payment processing system with type assertions.

Requirements:
1. Create interface 'Payment' with:
   - Process() error
   - GetAmount() float64

2. Create struct 'CreditCard' with:
   - CardNumber string
   - CardHolder string
   - Amount float64
   - CVV int

3. Implement Payment for CreditCard
   Add a special method 'ValidateCard() bool' unique to CreditCard

4. Create struct 'PayPal' with:
   - Email string
   - Amount float64
   - AccountID string

5. Implement Payment for PayPal
   Add a special method 'ValidateEmail() bool' unique to PayPal

6. Create struct 'Crypto' with:
   - WalletAddress string
   - Amount float64
   - CryptoType string

7. Implement Payment for Crypto
   Add a special method 'ValidateWallet() bool' unique to Crypto

8. Create function 'ProcessPayment(p Payment)' that:
   - Uses type assertion to check actual type
   - Calls type-specific validation methods
   - Processes the payment
   - Prints payment-specific information

9. Create function 'ProcessPayments(payments []Payment)' that:
   - Uses type switch to handle each payment type differently
   - Accumulates total amount
   - Returns summary by payment type

10. Create function 'DescribePayment(p interface{})' that:
    - Uses type switch on empty interface
    - Handles different payment types
    - Handles non-payment types gracefully

TEST YOUR CODE:
---------------
- Create various payment types
- Test ProcessPayment with each type
- Test ProcessPayments with mixed types
- Test DescribePayment with payments and non-payments
- Test failed type assertions

NOTES:
------
- Type assertion: value, ok := i.(Type)
- Type switch: switch v := i.(type) { case Type: ... }
- interface{} accepts any type
- Always check "ok" value for safety
*/

package main

import (
	"fmt"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
