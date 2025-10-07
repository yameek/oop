/**
 * TASK 2: Static Methods and Variables
 * =====================================
 * Difficulty: Beginner-Intermediate ⭐⭐
 * 
 * Learn about: Static variables, Static methods, Class-level members
 * 
 * PROBLEM:
 * --------
 * Create an Employee class for a company's HR system.
 * 
 * Requirements:
 * 1. Instance variables:
 *    - name (String)
 *    - id (int)
 *    - salary (double)
 * 
 * 2. Static variables:
 *    - companyName (String) - shared by all employees
 *    - employeeCount (int) - tracks total number of employees created
 *    - nextId (int) - auto-generates unique IDs
 * 
 * 3. Constructor:
 *    - Takes name and salary as parameters
 *    - Auto-assigns ID from nextId and increments it
 *    - Increments employeeCount
 * 
 * 4. Instance methods:
 *    - displayInfo() - prints employee details
 *    - giveRaise(double percentage) - increases salary by percentage
 * 
 * 5. Static methods:
 *    - setCompanyName(String name) - sets the company name
 *    - getEmployeeCount() - returns total employees created
 *    - displayCompanyInfo() - prints company name and employee count
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Set company name
 * - Create 3+ employees
 * - Give raises to some employees
 * - Display company info
 * - Display individual employee info
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Static vs instance variables
 * - Static vs instance methods
 * - When to use static
 * - How static members are shared across all instances
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Company: TechCorp
 * Total Employees: 3
 * 
 * Employee #1
 * Name: Alice
 * Salary: $75000.00
 * 
 * Employee #2 (after 10% raise)
 * Name: Bob
 * Salary: $88000.00
 */

// Write your solution below:

