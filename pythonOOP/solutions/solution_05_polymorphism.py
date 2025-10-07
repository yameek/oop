"""
SOLUTION 5: Polymorphism
=========================

CONCEPTS EXPLAINED:
-------------------
1. Polymorphism: "Many forms" - same interface, different implementations
2. Method Overriding: Child classes provide their own implementation
3. Duck Typing: "If it walks like a duck and quacks like a duck..."
4. Interface: Common set of methods across different classes
5. Runtime Polymorphism: Method to call is determined at runtime

"""

import random
from datetime import datetime

class Payment:
    """Base class for all payment types"""
    
    def __init__(self, amount, transaction_id=None):
        """
        Initialize a Payment object
        
        Parameters:
        -----------
        amount : float
            Payment amount
        transaction_id : str, optional
            Unique transaction identifier
        """
        self.amount = amount
        self.transaction_id = transaction_id or self._generate_transaction_id()
        self.timestamp = datetime.now()
        self.status = "pending"
    
    def _generate_transaction_id(self):
        """Generate a unique transaction ID"""
        return f"TXN{random.randint(100000, 999999)}"
    
    def process_payment(self):
        """
        Process the payment - must be overridden by subclasses
        
        NOTE: This is an abstract method that should be implemented
        by all child classes. We raise NotImplementedError to enforce this.
        """
        raise NotImplementedError("Subclasses must implement process_payment()")
    
    def display_receipt(self):
        """Display payment receipt"""
        print(f"\n{'=' * 50}")
        print(f"PAYMENT RECEIPT")
        print(f"{'=' * 50}")
        print(f"Transaction ID: {self.transaction_id}")
        print(f"Amount: ${self.amount:,.2f}")
        print(f"Status: {self.status}")
        print(f"Timestamp: {self.timestamp.strftime('%Y-%m-%d %H:%M:%S')}")
        print(f"{'=' * 50}")


class CreditCardPayment(Payment):
    """Credit card payment implementation"""
    
    def __init__(self, amount, card_number, card_holder_name, transaction_id=None):
        """
        Initialize a CreditCardPayment object
        
        Parameters:
        -----------
        amount : float
            Payment amount
        card_number : str
            Last 4 digits of card number
        card_holder_name : str
            Name on the card
        """
        super().__init__(amount, transaction_id)
        self.card_number = card_number
        self.card_holder_name = card_holder_name
        self.payment_type = "Credit Card"
    
    def validate_card(self):
        """
        Validate card number format
        
        Returns:
        --------
        bool : True if card number is valid (4 digits)
        """
        return len(str(self.card_number)) == 4 and str(self.card_number).isdigit()
    
    def process_payment(self):
        """
        Process credit card payment
        
        NOTE: This overrides the base class method
        """
        print(f"\n🔄 Processing Credit Card Payment...")
        
        if not self.validate_card():
            print("❌ Invalid card number format")
            self.status = "failed"
            return False
        
        # Simulate payment processing
        print(f"   Card: **** **** **** {self.card_number}")
        print(f"   Card Holder: {self.card_holder_name}")
        print(f"   Amount: ${self.amount:,.2f}")
        print(f"   Authorizing...")
        
        # Simulate successful processing
        self.status = "completed"
        print(f"✓ Credit Card payment processed successfully!")
        return True
    
    def display_receipt(self):
        """Override to include credit card details"""
        super().display_receipt()
        print(f"Payment Method: {self.payment_type}")
        print(f"Card Number: **** **** **** {self.card_number}")
        print(f"Card Holder: {self.card_holder_name}")
        print(f"{'=' * 50}")


class PayPalPayment(Payment):
    """PayPal payment implementation"""
    
    def __init__(self, amount, email, account_id, transaction_id=None):
        """Initialize a PayPalPayment object"""
        super().__init__(amount, transaction_id)
        self.email = email
        self.account_id = account_id
        self.payment_type = "PayPal"
    
    def validate_email(self):
        """
        Validate email format
        
        Returns:
        --------
        bool : True if email contains @
        """
        return "@" in self.email and len(self.email) > 3
    
    def process_payment(self):
        """Process PayPal payment"""
        print(f"\n🔄 Processing PayPal Payment...")
        
        if not self.validate_email():
            print("❌ Invalid email format")
            self.status = "failed"
            return False
        
        # Simulate payment processing
        print(f"   PayPal Email: {self.email}")
        print(f"   Account ID: {self.account_id}")
        print(f"   Amount: ${self.amount:,.2f}")
        print(f"   Connecting to PayPal...")
        
        # Simulate successful processing
        self.status = "completed"
        print(f"✓ PayPal payment processed successfully!")
        return True
    
    def display_receipt(self):
        """Override to include PayPal details"""
        super().display_receipt()
        print(f"Payment Method: {self.payment_type}")
        print(f"PayPal Email: {self.email}")
        print(f"Account ID: {self.account_id}")
        print(f"{'=' * 50}")


class CryptoPayment(Payment):
    """Cryptocurrency payment implementation"""
    
    def __init__(self, amount, wallet_address, crypto_type, transaction_id=None):
        """Initialize a CryptoPayment object"""
        super().__init__(amount, transaction_id)
        self.wallet_address = wallet_address
        self.crypto_type = crypto_type
        self.payment_type = "Cryptocurrency"
    
    def validate_wallet(self):
        """
        Validate wallet address format
        
        Returns:
        --------
        bool : True if wallet address is valid (length > 20)
        """
        return len(self.wallet_address) > 20
    
    def process_payment(self):
        """Process cryptocurrency payment"""
        print(f"\n🔄 Processing {self.crypto_type} Payment...")
        
        if not self.validate_wallet():
            print("❌ Invalid wallet address")
            self.status = "failed"
            return False
        
        # Simulate payment processing
        print(f"   Cryptocurrency: {self.crypto_type}")
        print(f"   Wallet: {self.wallet_address[:10]}...{self.wallet_address[-10:]}")
        print(f"   Amount: ${self.amount:,.2f}")
        print(f"   Broadcasting to blockchain...")
        
        # Simulate successful processing
        self.status = "completed"
        print(f"✓ {self.crypto_type} payment processed successfully!")
        return True
    
    def display_receipt(self):
        """Override to include crypto details"""
        super().display_receipt()
        print(f"Payment Method: {self.payment_type}")
        print(f"Cryptocurrency: {self.crypto_type}")
        print(f"Wallet: {self.wallet_address[:10]}...{self.wallet_address[-10:]}")
        print(f"{'=' * 50}")


def process_all_payments(payment_list):
    """
    Process multiple payments demonstrating polymorphism
    
    Parameters:
    -----------
    payment_list : list
        List of Payment objects
    
    NOTE: This function demonstrates polymorphism - it can handle
    any object that has a process_payment() method, regardless of
    the specific payment type.
    """
    print("\n" + "=" * 60)
    print("PROCESSING ALL PAYMENTS (POLYMORPHISM IN ACTION)")
    print("=" * 60)
    
    total_processed = 0
    successful_payments = 0
    
    for payment in payment_list:
        # This calls the appropriate process_payment() method
        # based on the actual object type at runtime
        if payment.process_payment():
            total_processed += payment.amount
            successful_payments += 1
    
    print("\n" + "=" * 60)
    print("PAYMENT SUMMARY")
    print("=" * 60)
    print(f"Total Payments: {len(payment_list)}")
    print(f"Successful: {successful_payments}")
    print(f"Failed: {len(payment_list) - successful_payments}")
    print(f"Total Amount Processed: ${total_processed:,.2f}")
    print("=" * 60)


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING POLYMORPHISM WITH PAYMENT SYSTEM")
    print("=" * 60)
    
    # Create different payment objects
    print("\n1. Creating different payment types:")
    
    payment1 = CreditCardPayment(150.00, "1234", "Alice Johnson")
    payment2 = PayPalPayment(75.50, "bob@email.com", "PP123456")
    payment3 = CryptoPayment(
        200.00, 
        "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", 
        "BTC"
    )
    payment4 = CreditCardPayment(99.99, "5678", "Carol White")
    payment5 = PayPalPayment(45.00, "dave@email.com", "PP789012")
    
    print("✓ Created 5 payment objects of different types")
    
    # Test individual payment processing
    print("\n2. Testing individual payment processing:")
    payment1.process_payment()
    payment1.display_receipt()
    
    # Test validation methods
    print("\n3. Testing validation methods:")
    print(f"Credit Card validation: {payment1.validate_card()}")
    print(f"PayPal email validation: {payment2.validate_email()}")
    print(f"Crypto wallet validation: {payment3.validate_wallet()}")
    
    # Test invalid payment
    print("\n4. Testing invalid payment:")
    invalid_payment = CreditCardPayment(50.00, "12", "Invalid Card")
    invalid_payment.process_payment()
    
    # Demonstrate polymorphism
    print("\n5. Demonstrating polymorphism with mixed payment types:")
    
    # Store different payment types in a single list
    all_payments = [payment1, payment2, payment3, payment4, payment5]
    
    # Process all payments - polymorphism in action!
    # Each payment processes differently, but we use the same interface
    process_all_payments(all_payments)
    
    # Display all receipts
    print("\n6. Displaying all receipts:")
    for i, payment in enumerate(all_payments, 1):
        print(f"\nReceipt #{i}:")
        payment.display_receipt()
    
    # Demonstrate duck typing
    print("\n7. Demonstrating duck typing:")
    print("Duck typing: If it has process_payment(), it can be processed!")
    
    for payment in all_payments:
        # We don't check the type, we just call the method
        # "If it walks like a duck and quacks like a duck..."
        payment_type = payment.__class__.__name__
        print(f"   {payment_type}: ", end="")
        print(f"Has process_payment? {hasattr(payment, 'process_payment')}")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Polymorphism allows different classes to be treated uniformly")
    print("2. Same method name, different implementations")
    print("3. Base class defines the interface (contract)")
    print("4. Child classes provide specific implementations")
    print("5. Duck typing: Focus on behavior, not type")
    print("6. Makes code flexible and extensible")
    print("7. process_all_payments() works with ANY payment type!")
