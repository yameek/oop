/*
TASK 4: Encapsulation and Packages
===================================
Difficulty: Intermediate

Learn about: Exported/Unexported, Getters/Setters, Encapsulation

PROBLEM:
--------
Create a bank account system with proper encapsulation.

Requirements:
1. Create a struct 'BankAccount' with fields:
   - accountNumber string (unexported)
   - accountHolder string (unexported)
   - balance float64 (unexported)
   - pin int (unexported)

2. Create a constructor 'NewBankAccount(number, holder string, pin int) *BankAccount'
   - Validates PIN is 4 digits (1000-9999)
   - Returns error if invalid
   - Initial balance is 0

3. Create getter methods (no setters for security):
   - GetAccountNumber() string
   - GetAccountHolder() string
   - GetBalance(pin int) (float64, error)  // requires PIN

4. Create a method 'Deposit(amount float64, pin int) error' that:
   - Validates PIN
   - Validates amount > 0
   - Adds amount to balance
   - Returns error if validation fails

5. Create a method 'Withdraw(amount float64, pin int) error' that:
   - Validates PIN
   - Validates amount > 0
   - Checks sufficient balance
   - Subtracts amount from balance
   - Returns appropriate errors

6. Create a method 'Transfer(amount float64, pin int, recipient *BankAccount) error' that:
   - Withdraws from current account
   - Deposits to recipient account
   - Returns error if any step fails

7. Create a method 'ChangePin(oldPin, newPin int) error' that:
   - Validates old PIN
   - Validates new PIN is 4 digits
   - Updates PIN
   - Returns error if validation fails

TEST YOUR CODE:
---------------
- Create bank accounts
- Test all operations with correct/incorrect PINs
- Test transfers between accounts
- Try accessing private fields (should not compile)

NOTES:
------
- Lowercase names are unexported (private)
- Uppercase names are exported (public)
- Use errors for validation and error handling
- Return multiple values (result, error)
*/

package main

import (
	"errors"
	"fmt"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
