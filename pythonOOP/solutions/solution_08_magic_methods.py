"""
SOLUTION 8: Magic/Dunder Methods
=================================

CONCEPTS EXPLAINED:
-------------------
1. Magic Methods: Special methods with double underscores (__method__)
2. Also called Dunder methods (double underscore)
3. Called automatically by Python in certain situations
4. Make objects behave like built-in types
5. Enable operator overloading and special behaviors
6. Common ones: __init__, __str__, __repr__, __len__, __add__, etc.

"""

class Product:
    """Represents a product with magic methods"""
    
    def __init__(self, name, price, quantity=1):
        """
        Initialize a Product
        
        NOTE: __init__ is a magic method called when creating an object
        """
        self.name = name
        self.price = price
        self.quantity = quantity
    
    def __str__(self):
        """
        String representation for end users (print())
        
        NOTE: Called by str() and print()
        Should be readable and user-friendly
        
        Returns:
        --------
        str : Human-readable string
        """
        return f"{self.name} - ${self.price:.2f} (Qty: {self.quantity})"
    
    def __repr__(self):
        """
        String representation for developers (debugging)
        
        NOTE: Called by repr() and in interactive console
        Should be unambiguous and ideally eval-able
        
        Returns:
        --------
        str : Developer-friendly representation
        """
        return f"Product('{self.name}', {self.price}, {self.quantity})"
    
    def __eq__(self, other):
        """
        Check equality based on product name
        
        NOTE: Called when using == operator
        
        Parameters:
        -----------
        other : Product
            Other product to compare
        
        Returns:
        --------
        bool : True if products have same name
        """
        if not isinstance(other, Product):
            return False
        return self.name == other.name
    
    def __lt__(self, other):
        """
        Less than comparison based on price
        
        NOTE: Called when using < operator
        Enables sorting of products
        
        Parameters:
        -----------
        other : Product
            Other product to compare
        
        Returns:
        --------
        bool : True if this product is cheaper
        """
        if not isinstance(other, Product):
            return NotImplemented
        return self.price < other.price
    
    def get_total_price(self):
        """Calculate total price for this product"""
        return self.price * self.quantity


class ShoppingCart:
    """Shopping cart with comprehensive magic methods"""
    
    def __init__(self, customer_name):
        """
        Initialize a ShoppingCart
        
        Parameters:
        -----------
        customer_name : str
            Name of the customer
        """
        self.customer_name = customer_name
        self.items = []
    
    def __str__(self):
        """
        User-friendly string representation
        
        Returns:
        --------
        str : Summary of the cart
        """
        if not self.items:
            return f"{self.customer_name}'s cart is empty"
        
        item_count = len(self.items)
        total = self.get_total()
        return f"{self.customer_name}'s cart: {item_count} items, Total: ${total:.2f}"
    
    def __repr__(self):
        """
        Developer-friendly representation
        
        Returns:
        --------
        str : Detailed representation
        """
        return f"ShoppingCart('{self.customer_name}', items={len(self.items)})"
    
    def __len__(self):
        """
        Return total number of items (sum of quantities)
        
        NOTE: Called by len() function
        
        Returns:
        --------
        int : Total quantity of all items
        """
        return sum(item.quantity for item in self.items)
    
    def __getitem__(self, index):
        """
        Get item at index
        
        NOTE: Called when using cart[index]
        Enables indexing: cart[0], cart[1], etc.
        
        Parameters:
        -----------
        index : int
            Index of item to retrieve
        
        Returns:
        --------
        Product : Product at the given index
        """
        return self.items[index]
    
    def __setitem__(self, index, product):
        """
        Set item at index
        
        NOTE: Called when using cart[index] = product
        
        Parameters:
        -----------
        index : int
            Index to set
        product : Product
            Product to place at index
        """
        if not isinstance(product, Product):
            raise TypeError("Item must be a Product")
        self.items[index] = product
    
    def __iter__(self):
        """
        Make cart iterable
        
        NOTE: Called when using for item in cart
        
        Returns:
        --------
        iterator : Iterator over items
        """
        return iter(self.items)
    
    def __add__(self, other_cart):
        """
        Combine two carts into a new cart
        
        NOTE: Called when using cart1 + cart2
        Creates a NEW cart with items from both
        
        Parameters:
        -----------
        other_cart : ShoppingCart
            Other cart to combine with
        
        Returns:
        --------
        ShoppingCart : New combined cart
        """
        if not isinstance(other_cart, ShoppingCart):
            raise TypeError("Can only add ShoppingCart objects")
        
        # Create new cart
        new_cart = ShoppingCart(f"{self.customer_name} & {other_cart.customer_name}")
        
        # Add items from both carts
        new_cart.items = self.items.copy() + other_cart.items.copy()
        
        return new_cart
    
    def __iadd__(self, product):
        """
        Add product to cart using +=
        
        NOTE: Called when using cart += product
        Modifies the cart in-place
        
        Parameters:
        -----------
        product : Product
            Product to add
        
        Returns:
        --------
        ShoppingCart : Self (for chaining)
        """
        self.add_item(product)
        return self
    
    def __contains__(self, product_name):
        """
        Check if product is in cart
        
        NOTE: Called when using "product_name" in cart
        
        Parameters:
        -----------
        product_name : str
            Name of product to search for
        
        Returns:
        --------
        bool : True if product is in cart
        """
        return any(item.name == product_name for item in self.items)
    
    def __bool__(self):
        """
        Check if cart is non-empty
        
        NOTE: Called when using if cart or bool(cart)
        
        Returns:
        --------
        bool : False if cart is empty, True otherwise
        """
        return len(self.items) > 0
    
    # Regular methods
    
    def add_item(self, product):
        """
        Add a product to the cart
        
        Parameters:
        -----------
        product : Product
            Product to add
        """
        if not isinstance(product, Product):
            raise TypeError("Can only add Product objects")
        
        # Check if product already in cart
        for item in self.items:
            if item == product:  # Uses Product.__eq__
                item.quantity += product.quantity
                print(f"✓ Updated quantity for {product.name}")
                return
        
        # Add new product
        self.items.append(product)
        print(f"✓ Added {product.name} to cart")
    
    def remove_item(self, product_name):
        """
        Remove a product by name
        
        Parameters:
        -----------
        product_name : str
            Name of product to remove
        
        Returns:
        --------
        bool : True if removed, False if not found
        """
        for i, item in enumerate(self.items):
            if item.name == product_name:
                removed = self.items.pop(i)
                print(f"✓ Removed {removed.name} from cart")
                return True
        
        print(f"❌ Product '{product_name}' not found in cart")
        return False
    
    def get_total(self):
        """
        Calculate total price
        
        Returns:
        --------
        float : Total price of all items
        """
        return sum(item.get_total_price() for item in self.items)
    
    def clear(self):
        """Empty the cart"""
        self.items = []
        print("✓ Cart cleared")
    
    def display(self):
        """Display detailed cart contents"""
        print(f"\n{'=' * 60}")
        print(f"SHOPPING CART - {self.customer_name}")
        print(f"{'=' * 60}")
        
        if not self.items:
            print("Cart is empty")
        else:
            for i, item in enumerate(self.items, 1):
                print(f"{i}. {item} = ${item.get_total_price():.2f}")
            print(f"{'-' * 60}")
            print(f"Total Items: {len(self)}")
            print(f"Total Price: ${self.get_total():.2f}")
        
        print(f"{'=' * 60}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING MAGIC/DUNDER METHODS")
    print("=" * 60)
    
    # Create products
    print("\n1. Creating products:")
    laptop = Product("Laptop", 999.99, 1)
    mouse = Product("Mouse", 25.50, 2)
    keyboard = Product("Keyboard", 75.00, 1)
    monitor = Product("Monitor", 299.99, 1)
    headphones = Product("Headphones", 89.99, 1)
    
    print("✓ Created 5 products")
    
    # Test __str__ and __repr__
    print("\n2. Testing __str__ (user-friendly):")
    print(laptop)  # Calls __str__
    
    print("\n3. Testing __repr__ (developer-friendly):")
    print(repr(laptop))  # Calls __repr__
    print([laptop, mouse])  # repr used in lists
    
    # Test __eq__ (equality)
    print("\n4. Testing __eq__ (equality comparison):")
    laptop2 = Product("Laptop", 1099.99, 1)  # Same name, different price
    print(f"laptop == laptop2: {laptop == laptop2}")  # True (same name)
    print(f"laptop == mouse: {laptop == mouse}")  # False
    
    # Test __lt__ (less than) and sorting
    print("\n5. Testing __lt__ (less than) and sorting:")
    products = [laptop, monitor, mouse, keyboard, headphones]
    print("Original order:")
    for p in products:
        print(f"  {p.name}: ${p.price}")
    
    products.sort()  # Uses __lt__
    print("\nSorted by price:")
    for p in products:
        print(f"  {p.name}: ${p.price}")
    
    # Create shopping cart
    print("\n6. Creating shopping cart:")
    cart1 = ShoppingCart("Alice")
    print(cart1)  # Uses __str__
    
    # Test __iadd__ (+=)
    print("\n7. Testing __iadd__ (cart += product):")
    cart1 += laptop
    cart1 += mouse
    cart1 += keyboard
    
    # Test __len__
    print("\n8. Testing __len__:")
    print(f"Total items in cart: {len(cart1)}")  # Calls __len__
    
    # Test __getitem__ (indexing)
    print("\n9. Testing __getitem__ (cart[index]):")
    print(f"First item: {cart1[0]}")  # Calls __getitem__
    print(f"Second item: {cart1[1]}")
    
    # Test __setitem__
    print("\n10. Testing __setitem__ (cart[index] = product):")
    cart1[1] = headphones  # Replace mouse with headphones
    print(f"After replacement: {cart1[1]}")
    
    # Test __iter__ (iteration)
    print("\n11. Testing __iter__ (for item in cart):")
    for item in cart1:  # Calls __iter__
        print(f"  {item}")
    
    # Test __contains__ (in operator)
    print("\n12. Testing __contains__ ('item' in cart):")
    print(f"'Laptop' in cart: {'Laptop' in cart1}")  # Calls __contains__
    print(f"'Mouse' in cart: {'Mouse' in cart1}")
    
    # Test __bool__
    print("\n13. Testing __bool__ (if cart):")
    empty_cart = ShoppingCart("Bob")
    print(f"cart1 is truthy: {bool(cart1)}")  # True (has items)
    print(f"empty_cart is truthy: {bool(empty_cart)}")  # False (empty)
    
    if cart1:
        print("cart1 has items!")
    if not empty_cart:
        print("empty_cart is empty!")
    
    # Test __add__ (combining carts)
    print("\n14. Testing __add__ (cart1 + cart2):")
    cart2 = ShoppingCart("Bob")
    cart2 += monitor
    cart2 += mouse
    
    print(f"cart1: {cart1}")
    print(f"cart2: {cart2}")
    
    combined_cart = cart1 + cart2  # Calls __add__
    print(f"combined cart: {combined_cart}")
    
    # Display cart details
    print("\n15. Displaying cart contents:")
    cart1.display()
    
    # Test regular methods
    print("\n16. Testing regular methods:")
    cart1.add_item(Product("USB Cable", 12.99, 3))
    cart1.remove_item("Headphones")
    cart1.display()
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. __str__: User-friendly string (print, str)")
    print("2. __repr__: Developer-friendly (repr, console)")
    print("3. __len__: len() function support")
    print("4. __getitem__/__setitem__: Indexing support (obj[i])")
    print("5. __iter__: Makes object iterable (for loop)")
    print("6. __add__/__iadd__: Operator overloading (+ and +=)")
    print("7. __eq__/__lt__: Comparison operators (==, <)")
    print("8. __contains__: 'in' operator support")
    print("9. __bool__: Truth value testing (if, bool)")
    print("10. Magic methods make custom objects behave like built-ins")
