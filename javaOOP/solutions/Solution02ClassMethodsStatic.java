/**
 * SOLUTION 2: Static Methods and Variables
 * =========================================
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Static variables: Shared among all instances of a class
 * 2. Static methods: Can be called without creating an object
 * 3. Instance variables: Unique to each object
 * 4. Instance methods: Called on specific objects
 * 
 * KEY TAKEAWAYS:
 * --------------
 * - Static members belong to the class, not to any specific instance
 * - Static methods can only access static members directly
 * - Instance methods can access both static and instance members
 * - Use static for shared data (like counters, constants)
 * - Use static methods for utility functions that don't need object state
 */

package solutions;

class Employee {
    // STATIC VARIABLES - shared by all Employee instances
    private static String companyName = "TechCorp";
    private static int employeeCount = 0;
    private static int nextId = 1;
    
    // INSTANCE VARIABLES - unique to each Employee object
    private String name;
    private int id;
    private double salary;
    
    /**
     * Constructor to create an Employee
     * Automatically assigns ID and increments counters
     * 
     * @param name Employee name
     * @param salary Employee salary
     */
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.id = nextId++;        // Assign current ID and increment for next
        employeeCount++;            // Increment total employee count
    }
    
    /**
     * Display information about this specific employee (instance method)
     */
    public void displayInfo() {
        System.out.println("\nEmployee #" + id);
        System.out.println("Name: " + name);
        System.out.println("Salary: $" + String.format("%.2f", salary));
        System.out.println("Company: " + companyName);
    }
    
    /**
     * Give a raise to this employee (instance method)
     * 
     * @param percentage Percentage increase (e.g., 10 for 10%)
     */
    public void giveRaise(double percentage) {
        if (percentage > 0) {
            double oldSalary = salary;
            salary = salary * (1 + percentage / 100);
            System.out.println("✓ " + name + " received a " + percentage + "% raise!");
            System.out.println("  Salary: $" + String.format("%.2f", oldSalary) + 
                             " → $" + String.format("%.2f", salary));
        } else {
            System.out.println("✗ Invalid raise percentage");
        }
    }
    
    // STATIC METHODS - can be called without creating an Employee object
    
    /**
     * Set the company name for all employees
     * 
     * @param name New company name
     */
    public static void setCompanyName(String name) {
        companyName = name;
        System.out.println("✓ Company name set to: " + companyName);
    }
    
    /**
     * Get the total number of employees created
     * 
     * @return Total employee count
     */
    public static int getEmployeeCount() {
        return employeeCount;
    }
    
    /**
     * Display information about the company
     */
    public static void displayCompanyInfo() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("COMPANY INFORMATION");
        System.out.println("=".repeat(40));
        System.out.println("Company Name: " + companyName);
        System.out.println("Total Employees: " + employeeCount);
        System.out.println("Next Employee ID: " + nextId);
        System.out.println("=".repeat(40));
    }
    
    // Getters
    public String getName() { return name; }
    public int getId() { return id; }
    public double getSalary() { return salary; }
    public static String getCompanyName() { return companyName; }
}

/**
 * Main class to test the Employee class
 */
public class Solution02ClassMethodsStatic {
    public static void main(String[] args) {
        System.out.println("=".repeat(50));
        System.out.println("TESTING EMPLOYEE CLASS - STATIC vs INSTANCE");
        System.out.println("=".repeat(50));
        
        // STATIC METHOD - called without creating an object
        System.out.println("\n1. Setting company name (static method):");
        Employee.setCompanyName("InnovateTech Solutions");
        
        // STATIC METHOD - get employee count before creating any employees
        System.out.println("\n2. Initial employee count: " + Employee.getEmployeeCount());
        
        // Creating employee objects
        System.out.println("\n3. Creating employees:");
        Employee emp1 = new Employee("Alice Johnson", 75000);
        System.out.println("✓ Created: " + emp1.getName());
        
        Employee emp2 = new Employee("Bob Smith", 80000);
        System.out.println("✓ Created: " + emp2.getName());
        
        Employee emp3 = new Employee("Carol Williams", 85000);
        System.out.println("✓ Created: " + emp3.getName());
        
        // STATIC METHOD - display company info
        System.out.println("\n4. Company information:");
        Employee.displayCompanyInfo();
        
        // INSTANCE METHODS - called on specific objects
        System.out.println("\n5. Employee details:");
        emp1.displayInfo();
        emp2.displayInfo();
        emp3.displayInfo();
        
        // Give raises
        System.out.println("\n6. Giving raises:");
        emp1.giveRaise(10);    // 10% raise for Alice
        emp2.giveRaise(15);    // 15% raise for Bob
        
        // Display updated info
        System.out.println("\n7. Updated employee info:");
        emp1.displayInfo();
        emp2.displayInfo();
        
        // Demonstrate static variable sharing
        System.out.println("\n8. Demonstrating static variable sharing:");
        System.out.println("All employees work for: " + Employee.getCompanyName());
        System.out.println("Changing company name...");
        Employee.setCompanyName("NextGen Technologies");
        System.out.println("Now all employees work for: " + Employee.getCompanyName());
        emp1.displayInfo();  // Will show new company name
        
        // Create more employees
        System.out.println("\n9. Creating more employees:");
        Employee emp4 = new Employee("David Brown", 70000);
        Employee emp5 = new Employee("Eva Martinez", 90000);
        System.out.println("✓ Created 2 more employees");
        
        // Final company info
        System.out.println("\n10. Final company information:");
        Employee.displayCompanyInfo();
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TESTING COMPLETE!");
        System.out.println("=".repeat(50));
        
        // IMPORTANT CONCEPTS DEMONSTRATED:
        System.out.println("\n📚 Key Concepts Demonstrated:");
        System.out.println("✓ Static variables (companyName, employeeCount, nextId)");
        System.out.println("✓ Instance variables (name, id, salary)");
        System.out.println("✓ Static methods (setCompanyName, getEmployeeCount, displayCompanyInfo)");
        System.out.println("✓ Instance methods (displayInfo, giveRaise)");
        System.out.println("✓ Automatic ID generation using static variable");
        System.out.println("✓ Shared data across all instances");
        System.out.println("✓ Calling static methods without objects");
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * STATIC vs INSTANCE:
 * -------------------
 * 
 * STATIC (Class-level):
 * - Belongs to the class itself
 * - Shared by all instances
 * - Can be accessed without creating an object
 * - Memory allocated once when class is loaded
 * - Example: Employee.getEmployeeCount()
 * 
 * INSTANCE (Object-level):
 * - Belongs to each individual object
 * - Each object has its own copy
 * - Requires an object to access
 * - Memory allocated when object is created
 * - Example: emp1.displayInfo()
 * 
 * WHEN TO USE STATIC:
 * -------------------
 * 1. Constants (final static variables)
 *    Example: public static final double PI = 3.14159;
 * 
 * 2. Counters/Shared data
 *    Example: employeeCount, nextId
 * 
 * 3. Utility methods (don't need object state)
 *    Example: Math.sqrt(), Integer.parseInt()
 * 
 * 4. Factory methods
 *    Example: Creating objects in a controlled way
 * 
 * 5. Singleton pattern
 *    Example: Ensuring only one instance exists
 * 
 * WHEN TO USE INSTANCE:
 * ---------------------
 * 1. Object-specific data
 *    Example: employee name, salary
 * 
 * 2. Methods that work with object state
 *    Example: giveRaise() modifies specific employee's salary
 * 
 * 3. Different values for different objects
 *    Example: Each employee has different name/salary
 * 
 * IMPORTANT RULES:
 * ----------------
 * 1. Static methods CANNOT access instance variables directly
 *    - They don't know which object's variables to use
 *    - Must create an object first or receive it as parameter
 * 
 * 2. Static methods CANNOT use 'this' keyword
 *    - 'this' refers to current object
 *    - Static methods don't belong to any object
 * 
 * 3. Instance methods CAN access both static and instance members
 *    - They belong to an object, which belongs to the class
 * 
 * 4. Static variables are initialized before instance variables
 *    - When class is loaded into memory
 * 
 * COMMON MISTAKES:
 * ================
 * 1. Trying to access instance variables from static methods
 *    ✗ public static void test() { System.out.println(name); }
 * 
 * 2. Making everything static when it shouldn't be
 *    - Overusing static defeats the purpose of OOP
 * 
 * 3. Not understanding that static variables are shared
 *    - Changing a static variable affects ALL instances
 * 
 * 4. Forgetting that static methods can be called without objects
 *    - Employee.setCompanyName("X"); // No object needed!
 * 
 * BEST PRACTICES:
 * ===============
 * 1. Use static for truly shared data
 * 2. Use static final for constants
 * 3. Keep most methods instance-based for proper OOP
 * 4. Use static methods for utilities that don't need object state
 * 5. Document when methods are static vs instance
 * 6. Be careful with static mutable state (thread safety)
 */
