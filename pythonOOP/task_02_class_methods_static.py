"""
TASK 2: Class Methods and Static Methods
=========================================
Difficulty: Beginner to Intermediate

Learn about: @classmethod, @staticmethod, class variables

PROBLEM:
--------
Create a class called 'Employee' to manage employee records.

Requirements:
1. Class variable 'company_name' set to "TechCorp"
2. Class variable 'employee_count' to track total employees (starts at 0)

3. Instance attributes:
   - name (string)
   - position (string)
   - salary (float)
   - employee_id (auto-generated, increment for each new employee)

4. Create a class method 'get_employee_count()' that returns the total number of employees

5. Create a class method 'set_company_name(new_name)' that updates the company name

6. Create a static method 'is_valid_salary(salary)' that:
   - Returns True if salary is between 20000 and 500000
   - Returns False otherwise

7. Create an instance method 'give_raise(percentage)' that increases salary by the given percentage

8. Create an instance method 'display_info()' to show employee details

TEST YOUR CODE:
---------------
- Create 3 employees
- Test all methods including class methods and static methods
- Verify employee_count is correct
"""

# Write your solution below:

