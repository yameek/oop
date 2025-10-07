/**
 * SOLUTION 1: Basic Classes and Objects
 * ======================================
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Class: A blueprint for creating objects (instances)
 * 2. Constructor: Special method that initializes object attributes
 * 3. this: Refers to the current instance of the class
 * 4. Instance variables: Variables that belong to each object
 * 5. Methods: Functions defined inside a class
 * 
 * KEY TAKEAWAYS:
 * --------------
 * - Classes define the structure and behavior of objects
 * - Constructors have the same name as the class and no return type
 * - 'this' is used to differentiate between instance variables and parameters
 * - Each object has its own copy of instance variables
 * - Methods can modify the state of an object
 */

package solutions;

class Book {
    // Instance variables - each Book object has its own copy
    private String title;
    private String author;
    private int pages;
    private boolean isAvailable;
    
    /**
     * Constructor to initialize a Book object
     * 
     * @param title The title of the book
     * @param author The author of the book
     * @param pages Number of pages in the book
     */
    public Book(String title, String author, int pages) {
        // 'this' refers to the instance variable
        // Without 'this', it would refer to the parameter
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.isAvailable = true;  // Default value
    }
    
    /**
     * Display all information about the book
     */
    public void displayInfo() {
        String status = isAvailable ? "Available" : "Borrowed";
        System.out.println("\n--- Book Information ---");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Pages: " + pages);
        System.out.println("Status: " + status);
        System.out.println("------------------------");
    }
    
    /**
     * Borrow the book if it's available
     * Changes isAvailable to false
     */
    public void borrow() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("✓ '" + title + "' borrowed successfully!");
        } else {
            System.out.println("✗ Sorry, '" + title + "' is already borrowed.");
        }
    }
    
    /**
     * Return the book to the library
     * Changes isAvailable to true
     */
    public void returnBook() {
        isAvailable = true;
        System.out.println("✓ '" + title + "' returned successfully!");
    }
}

/**
 * Main class to test the Book class
 */
public class Solution01BasicClasses {
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("TESTING BOOK CLASS");
        System.out.println("=".repeat(50));
        
        // Creating Book objects (instances of Book class)
        Book book1 = new Book("Python Crash Course", "Eric Matthes", 544);
        Book book2 = new Book("Clean Code", "Robert Martin", 464);
        Book book3 = new Book("The Pragmatic Programmer", "Hunt & Thomas", 352);
        
        // Test displayInfo()
        System.out.println("\n1. Testing displayInfo():");
        book1.displayInfo();
        book2.displayInfo();
        
        // Test borrow()
        System.out.println("\n2. Testing borrow():");
        book1.borrow();  // Should succeed
        book1.borrow();  // Should fail (already borrowed)
        book2.borrow();  // Should succeed
        
        // Check status after borrowing
        System.out.println("\n3. Status after borrowing:");
        book1.displayInfo();
        book2.displayInfo();
        
        // Test returnBook()
        System.out.println("\n4. Testing returnBook():");
        book1.returnBook();
        book1.displayInfo();
        
        // Test with third book
        System.out.println("\n5. Testing with third book:");
        book3.displayInfo();
        book3.borrow();
        book3.displayInfo();
        book3.returnBook();
        book3.displayInfo();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TESTING COMPLETE!");
        System.out.println("=".repeat(50));
        
        // IMPORTANT CONCEPTS DEMONSTRATED:
        System.out.println("\n📚 Key Concepts Demonstrated:");
        System.out.println("✓ Class definition (Book)");
        System.out.println("✓ Instance variables (title, author, pages, isAvailable)");
        System.out.println("✓ Constructor (Book constructor)");
        System.out.println("✓ 'this' keyword usage");
        System.out.println("✓ Instance methods (displayInfo, borrow, returnBook)");
        System.out.println("✓ Object creation (book1, book2, book3)");
        System.out.println("✓ Method invocation (calling methods on objects)");
        System.out.println("✓ Encapsulation (private variables, public methods)");
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * 1. CLASS vs OBJECT:
 *    - Class is a template/blueprint
 *    - Object is an instance created from that blueprint
 *    - Example: "Book" is the class, "book1" is an object
 * 
 * 2. CONSTRUCTOR:
 *    - Same name as the class
 *    - No return type (not even void)
 *    - Called automatically when creating an object with 'new'
 *    - Can be overloaded (multiple constructors with different parameters)
 * 
 * 3. 'this' KEYWORD:
 *    - Refers to the current object
 *    - Used to avoid confusion between instance variables and parameters
 *    - Example: this.title = title;
 *              (instance var) = (parameter)
 * 
 * 4. INSTANCE VARIABLES:
 *    - Declared inside the class but outside methods
 *    - Each object gets its own copy
 *    - Exist as long as the object exists
 *    - Default values if not initialized (0 for int, false for boolean, null for objects)
 * 
 * 5. METHODS:
 *    - Define the behavior of objects
 *    - Can access and modify instance variables
 *    - Can be called on objects using dot notation (object.method())
 * 
 * 6. ACCESS MODIFIERS:
 *    - private: Only accessible within the class
 *    - public: Accessible from anywhere
 *    - This is part of encapsulation - hiding internal details
 * 
 * COMMON MISTAKES TO AVOID:
 * =========================
 * 1. Forgetting 'this' when parameter names match instance variable names
 * 2. Trying to add a return type to constructor
 * 3. Not initializing instance variables (though Java provides defaults)
 * 4. Confusing class methods with instance methods
 * 5. Trying to access private variables from outside the class
 * 
 * BEST PRACTICES:
 * ===============
 * 1. Use meaningful names for classes, variables, and methods
 * 2. Follow naming conventions (class names in PascalCase, methods in camelCase)
 * 3. Keep instance variables private (encapsulation)
 * 4. Provide public methods to interact with private variables
 * 5. Initialize all instance variables in the constructor
 * 6. Write Javadoc comments for classes and methods
 * 7. One class per file (for top-level classes)
 */
