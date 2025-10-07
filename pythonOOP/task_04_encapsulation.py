"""
TASK 4: Encapsulation and Properties
=====================================
Difficulty: Intermediate

Learn about: Private attributes, @property, getters, setters

PROBLEM:
--------
Create a 'BankAccount' class with proper encapsulation.

Requirements:
1. Private attributes:
   - __account_number (string)
   - __account_holder (string)
   - __balance (float, default 0)
   - __pin (4-digit integer)

2. Public methods:
   - deposit(amount, pin) - adds amount to balance if pin is correct
   - withdraw(amount, pin) - subtracts amount if pin is correct and sufficient balance
   - check_balance(pin) - returns balance if pin is correct

3. Use @property decorator for:
   - account_number (read-only)
   - account_holder (read-only)
   - balance (read-only, accessed via property)

4. Create a method 'change_pin(old_pin, new_pin)' that:
   - Changes PIN if old_pin is correct
   - New PIN must be exactly 4 digits
   - Returns True if successful, False otherwise

5. Create a method 'transfer(amount, pin, recipient_account)' that:
   - Withdraws from current account
   - Deposits to recipient account (recipient should also be a BankAccount object)
   - Returns True if successful

6. All operations should print appropriate messages (success/failure)

TEST YOUR CODE:
---------------
- Create 2 bank accounts
- Test deposits, withdrawals, transfers
- Try incorrect PINs
- Test all property accessors
"""

# Write your solution below:

