"""
TASK 5: Polymorphism
====================
Difficulty: Intermediate to Advanced

Learn about: Method Overriding, Duck Typing, Polymorphism

PROBLEM:
--------
Create a payment processing system that demonstrates polymorphism.

Requirements:
1. Create a base class 'Payment' with:
   - Attributes: amount, transaction_id
   - Abstract method: process_payment() - should raise NotImplementedError
   - Method: display_receipt() - shows payment details

2. Create class 'CreditCardPayment' inheriting from Payment with:
   - Additional attributes: card_number (last 4 digits), card_holder_name
   - Override process_payment() to simulate credit card processing
   - Add method: validate_card() - returns True if card_number is 4 digits

3. Create class 'PayPalPayment' inheriting from Payment with:
   - Additional attributes: email, account_id
   - Override process_payment() to simulate PayPal processing
   - Add method: validate_email() - basic email validation (contains @)

4. Create class 'CryptoPayment' inheriting from Payment with:
   - Additional attributes: wallet_address, crypto_type (BTC/ETH/etc)
   - Override process_payment() to simulate crypto processing
   - Add method: validate_wallet() - returns True if wallet_address length > 20

5. Create a function 'process_all_payments(payment_list)' that:
   - Takes a list of different payment objects
   - Processes each payment (demonstrating polymorphism)
   - Prints total amount processed

TEST YOUR CODE:
---------------
- Create multiple payment objects of different types
- Store them in a list
- Use process_all_payments() to demonstrate polymorphism
- Test individual validation methods
"""

# Write your solution below:

