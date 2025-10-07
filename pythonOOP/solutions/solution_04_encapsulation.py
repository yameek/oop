"""
SOLUTION 4: Encapsulation and Properties
=========================================

CONCEPTS EXPLAINED:
-------------------
1. Encapsulation: Hiding internal details and restricting access
2. Private attributes: Prefix with __ (double underscore)
3. @property: Makes method accessible like an attribute (getter)
4. @attribute.setter: Allows controlled modification (setter)
5. Name mangling: Python renames __attr to _ClassName__attr
6. Information hiding: Protects data from unauthorized access

"""

class BankAccount:
    """Represents a bank account with encapsulation"""
    
    def __init__(self, account_number, account_holder, pin, initial_balance=0):
        """
        Initialize a BankAccount object
        
        Parameters:
        -----------
        account_number : str
            Unique account identifier
        account_holder : str
            Name of account holder
        pin : int
            4-digit PIN for authentication
        initial_balance : float, optional
            Starting balance (default 0)
        
        NOTE: Using __ prefix makes attributes private
        They cannot be accessed directly from outside the class
        """
        # Private attributes (name mangling applied)
        self.__account_number = account_number
        self.__account_holder = account_holder
        self.__balance = initial_balance
        self.__pin = pin
        
        # Validate PIN on creation
        if not self.__is_valid_pin(pin):
            raise ValueError("PIN must be exactly 4 digits")
    
    def __is_valid_pin(self, pin):
        """
        Private method to validate PIN
        
        NOTE: Private methods (starting with __) are for internal use only
        """
        return 1000 <= pin <= 9999
    
    # Property decorators create "getters" - read-only access
    @property
    def account_number(self):
        """
        Read-only property for account number
        
        NOTE: Using @property allows access like: account.account_number
        instead of: account.get_account_number()
        """
        return self.__account_number
    
    @property
    def account_holder(self):
        """Read-only property for account holder name"""
        return self.__account_holder
    
    @property
    def balance(self):
        """
        Read-only property for balance
        
        NOTE: Balance can only be viewed, not directly modified
        Use deposit() or withdraw() methods instead
        """
        return self.__balance
    
    def deposit(self, amount, pin):
        """
        Deposit money into account
        
        Parameters:
        -----------
        amount : float
            Amount to deposit
        pin : int
            PIN for authentication
        
        Returns:
        --------
        bool : True if successful, False otherwise
        """
        if pin != self.__pin:
            print("❌ Error: Incorrect PIN")
            return False
        
        if amount <= 0:
            print("❌ Error: Deposit amount must be positive")
            return False
        
        self.__balance += amount
        print(f"✓ Deposited ${amount:,.2f}. New balance: ${self.__balance:,.2f}")
        return True
    
    def withdraw(self, amount, pin):
        """
        Withdraw money from account
        
        Parameters:
        -----------
        amount : float
            Amount to withdraw
        pin : int
            PIN for authentication
        
        Returns:
        --------
        bool : True if successful, False otherwise
        """
        if pin != self.__pin:
            print("❌ Error: Incorrect PIN")
            return False
        
        if amount <= 0:
            print("❌ Error: Withdrawal amount must be positive")
            return False
        
        if amount > self.__balance:
            print(f"❌ Error: Insufficient funds. Available: ${self.__balance:,.2f}")
            return False
        
        self.__balance -= amount
        print(f"✓ Withdrew ${amount:,.2f}. Remaining balance: ${self.__balance:,.2f}")
        return True
    
    def check_balance(self, pin):
        """
        Check account balance
        
        Parameters:
        -----------
        pin : int
            PIN for authentication
        
        Returns:
        --------
        float or None : Balance if PIN correct, None otherwise
        """
        if pin != self.__pin:
            print("❌ Error: Incorrect PIN")
            return None
        
        print(f"💰 Current balance: ${self.__balance:,.2f}")
        return self.__balance
    
    def change_pin(self, old_pin, new_pin):
        """
        Change account PIN
        
        Parameters:
        -----------
        old_pin : int
            Current PIN
        new_pin : int
            New PIN (must be 4 digits)
        
        Returns:
        --------
        bool : True if successful, False otherwise
        """
        if old_pin != self.__pin:
            print("❌ Error: Incorrect current PIN")
            return False
        
        if not self.__is_valid_pin(new_pin):
            print("❌ Error: New PIN must be exactly 4 digits (1000-9999)")
            return False
        
        self.__pin = new_pin
        print("✓ PIN changed successfully")
        return True
    
    def transfer(self, amount, pin, recipient_account):
        """
        Transfer money to another account
        
        Parameters:
        -----------
        amount : float
            Amount to transfer
        pin : int
            PIN for authentication
        recipient_account : BankAccount
            Recipient's account object
        
        Returns:
        --------
        bool : True if successful, False otherwise
        """
        if pin != self.__pin:
            print("❌ Error: Incorrect PIN")
            return False
        
        if not isinstance(recipient_account, BankAccount):
            print("❌ Error: Invalid recipient account")
            return False
        
        # Withdraw from current account
        if self.withdraw(amount, pin):
            # Deposit to recipient account (using their internal method)
            # Note: We access private attribute here because it's between BankAccount instances
            recipient_account.__balance += amount
            print(f"✓ Transferred ${amount:,.2f} to {recipient_account.account_holder}")
            return True
        
        return False
    
    def display_info(self):
        """Display account information (without sensitive data)"""
        print(f"\n{'=' * 50}")
        print(f"Account Number: {self.__account_number}")
        print(f"Account Holder: {self.__account_holder}")
        print(f"Balance: ${self.__balance:,.2f}")
        print(f"{'=' * 50}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING BANK ACCOUNT CLASS - ENCAPSULATION")
    print("=" * 60)
    
    # Create bank accounts
    print("\n1. Creating bank accounts:")
    account1 = BankAccount("ACC001", "Alice Johnson", 1234, 1000)
    account2 = BankAccount("ACC002", "Bob Smith", 5678, 500)
    
    print("✓ Accounts created successfully")
    
    # Test property access (read-only)
    print("\n2. Testing read-only properties:")
    print(f"Account 1 Number: {account1.account_number}")
    print(f"Account 1 Holder: {account1.account_holder}")
    print(f"Account 1 Balance: ${account1.balance:,.2f}")
    
    # Try to modify balance directly (will fail)
    print("\n3. Attempting to modify balance directly:")
    try:
        account1.balance = 999999  # This will create a NEW attribute, not modify __balance
        print(f"Balance after direct assignment: ${account1.balance:,.2f}")
        print("NOTE: This creates a new 'balance' attribute, doesn't modify __balance!")
    except AttributeError as e:
        print(f"Error: {e}")
    
    # Test deposit
    print("\n4. Testing deposit:")
    account1.deposit(500, 1234)  # Correct PIN
    account1.deposit(100, 9999)  # Wrong PIN
    
    # Test withdraw
    print("\n5. Testing withdraw:")
    account1.withdraw(200, 1234)  # Correct PIN, sufficient funds
    account1.withdraw(2000, 1234)  # Correct PIN, insufficient funds
    account1.withdraw(100, 9999)  # Wrong PIN
    
    # Test check_balance
    print("\n6. Testing check_balance:")
    account1.check_balance(1234)  # Correct PIN
    account1.check_balance(9999)  # Wrong PIN
    
    # Test change_pin
    print("\n7. Testing change_pin:")
    account1.change_pin(1234, 4321)  # Correct old PIN
    account1.check_balance(4321)  # Test new PIN
    account1.change_pin(1234, 5555)  # Wrong old PIN
    account1.change_pin(4321, 123)  # Invalid new PIN (not 4 digits)
    
    # Test transfer
    print("\n8. Testing transfer:")
    print(f"\nBefore transfer:")
    print(f"Account 1 balance: ${account1.balance:,.2f}")
    print(f"Account 2 balance: ${account2.balance:,.2f}")
    
    account1.transfer(300, 4321, account2)
    
    print(f"\nAfter transfer:")
    print(f"Account 1 balance: ${account1.balance:,.2f}")
    print(f"Account 2 balance: ${account2.balance:,.2f}")
    
    # Display account info
    print("\n9. Display account information:")
    account1.display_info()
    account2.display_info()
    
    # Demonstrate name mangling
    print("\n10. Demonstrating name mangling:")
    print(f"Accessing private attribute via mangled name:")
    print(f"account1._BankAccount__balance = ${account1._BankAccount__balance:,.2f}")
    print("NOTE: You CAN access it this way, but you SHOULDN'T!")
    print("This defeats the purpose of encapsulation.")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Use __ prefix for private attributes and methods")
    print("2. @property creates read-only attributes (getters)")
    print("3. @attribute.setter creates controlled write access (setters)")
    print("4. Encapsulation protects data from invalid modifications")
    print("5. Name mangling: __attr becomes _ClassName__attr")
    print("6. Private attributes encourage using methods for data access")
    print("7. Encapsulation is about data protection, not absolute hiding")
