"""
TASK 8: Magic/Dunder Methods
=============================
Difficulty: Advanced

Learn about: __str__, __repr__, __len__, __add__, __eq__, __lt__, etc.

PROBLEM:
--------
Create a 'ShoppingCart' class with various magic methods.

Requirements:
1. Create a class 'Product' with:
   - Attributes: name, price, quantity
   - Implement __str__() - human-readable string
   - Implement __repr__() - developer-friendly representation
   - Implement __eq__() - compare products by name
   - Implement __lt__() - compare products by price (for sorting)

2. Create a class 'ShoppingCart' with:
   - Attribute: items (list of Product objects)
   - Attribute: customer_name
   
3. Implement the following magic methods for ShoppingCart:
   - __init__(customer_name)
   - __str__() - returns a nice summary of the cart
   - __repr__() - returns detailed representation
   - __len__() - returns total number of items (sum of all quantities)
   - __getitem__(index) - allows cart[index] to access products
   - __setitem__(index, product) - allows cart[index] = product
   - __iter__() - makes cart iterable
   - __add__(other_cart) - combines two carts (returns new cart with all items)
   - __iadd__(product) - allows cart += product
   - __contains__(product_name) - allows "product_name" in cart
   - __bool__() - returns False if cart is empty, True otherwise

4. Add regular methods:
   - add_item(product) - adds a product to cart
   - remove_item(product_name) - removes product by name
   - get_total() - calculates total price
   - clear() - empties the cart

TEST YOUR CODE:
---------------
- Create multiple products
- Create shopping carts
- Test all magic methods (addition, length, iteration, contains, etc.)
- Print carts using print() and repr()
"""

# Write your solution below:

