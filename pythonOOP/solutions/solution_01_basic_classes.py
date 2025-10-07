"""
SOLUTION 1: Basic Classes and Objects
======================================

CONCEPTS EXPLAINED:
-------------------
1. Class: A blueprint for creating objects (instances)
2. __init__: Constructor method that initializes object attributes
3. self: Refers to the instance of the class
4. Instance variables: Variables that belong to each object
5. Methods: Functions defined inside a class

"""

class Book:
    """Represents a book in a library"""
    
    def __init__(self, title, author, pages):
        """
        Initialize a Book object
        
        Parameters:
        -----------
        title : str
            The title of the book
        author : str
            The author of the book
        pages : int
            Number of pages in the book
        """
        # Instance variables - each book object has its own copy
        self.title = title
        self.author = author
        self.pages = pages
        self.is_available = True  # Default value
    
    def display_info(self):
        """Display all information about the book"""
        status = "Available" if self.is_available else "Borrowed"
        print(f"\n--- Book Information ---")
        print(f"Title: {self.title}")
        print(f"Author: {self.author}")
        print(f"Pages: {self.pages}")
        print(f"Status: {status}")
        print(f"------------------------")
    
    def borrow(self):
        """
        Borrow the book if it's available
        Changes is_available to False
        """
        if self.is_available:
            self.is_available = False
            print(f"✓ '{self.title}' borrowed successfully!")
        else:
            print(f"✗ Sorry, '{self.title}' is already borrowed.")
    
    def return_book(self):
        """
        Return the book to the library
        Changes is_available to True
        """
        self.is_available = True
        print(f"✓ '{self.title}' returned successfully!")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 50)
    print("TESTING BOOK CLASS")
    print("=" * 50)
    
    # Creating book objects (instances of Book class)
    book1 = Book("Python Crash Course", "Eric Matthes", 544)
    book2 = Book("Clean Code", "Robert Martin", 464)
    book3 = Book("The Pragmatic Programmer", "Hunt & Thomas", 352)
    
    # Test display_info()
    print("\n1. Testing display_info():")
    book1.display_info()
    book2.display_info()
    
    # Test borrow()
    print("\n2. Testing borrow():")
    book1.borrow()  # Should succeed
    book1.borrow()  # Should fail (already borrowed)
    book2.borrow()  # Should succeed
    
    # Check status after borrowing
    print("\n3. Status after borrowing:")
    book1.display_info()
    book2.display_info()
    
    # Test return_book()
    print("\n4. Testing return_book():")
    book1.return_book()
    book1.display_info()
    
    # Test borrowing again after return
    print("\n5. Borrowing again after return:")
    book1.borrow()  # Should succeed now
    book1.display_info()
    
    print("\n" + "=" * 50)
    print("KEY TAKEAWAYS:")
    print("=" * 50)
    print("1. Classes are created using the 'class' keyword")
    print("2. __init__ is called automatically when creating an object")
    print("3. 'self' represents the instance and must be first parameter")
    print("4. Each object has its own copy of instance variables")
    print("5. Methods are functions that operate on object data")
