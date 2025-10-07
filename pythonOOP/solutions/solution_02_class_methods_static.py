"""
SOLUTION 2: Class Methods and Static Methods
=============================================

CONCEPTS EXPLAINED:
-------------------
1. Class variables: Shared by all instances of the class
2. Instance variables: Unique to each instance
3. @classmethod: Works with the class itself, not instances
4. @staticmethod: Doesn't access class or instance data
5. cls: Refers to the class (like self refers to instance)

"""

class Employee:
    """Manages employee records for a company"""
    
    # Class variables - shared by ALL employees
    company_name = "TechCorp"
    employee_count = 0
    
    def __init__(self, name, position, salary):
        """
        Initialize an Employee object
        
        Parameters:
        -----------
        name : str
            Employee's name
        position : str
            Job position
        salary : float
            Annual salary
        """
        # Instance variables - unique to each employee
        self.name = name
        self.position = position
        self.salary = salary
        
        # Increment class variable and assign employee ID
        Employee.employee_count += 1
        self.employee_id = Employee.employee_count
    
    @classmethod
    def get_employee_count(cls):
        """
        Class method to get total number of employees
        
        Returns:
        --------
        int : Total number of employees
        
        NOTE: Use @classmethod when you need to access or modify class variables
        'cls' refers to the class itself (Employee)
        """
        return cls.employee_count
    
    @classmethod
    def set_company_name(cls, new_name):
        """
        Class method to update company name
        
        Parameters:
        -----------
        new_name : str
            New company name
        
        NOTE: This changes the company name for ALL employees
        """
        cls.company_name = new_name
        print(f"Company name updated to: {cls.company_name}")
    
    @staticmethod
    def is_valid_salary(salary):
        """
        Static method to validate salary range
        
        Parameters:
        -----------
        salary : float
            Salary amount to validate
        
        Returns:
        --------
        bool : True if salary is valid, False otherwise
        
        NOTE: Use @staticmethod when the method doesn't need access to
        class or instance data. It's like a regular function but
        belongs logically to the class.
        """
        return 20000 <= salary <= 500000
    
    def give_raise(self, percentage):
        """
        Increase employee's salary by given percentage
        
        Parameters:
        -----------
        percentage : float
            Percentage increase (e.g., 10 for 10%)
        """
        if percentage < 0:
            print("Error: Percentage cannot be negative")
            return
        
        old_salary = self.salary
        self.salary = self.salary * (1 + percentage / 100)
        print(f"Salary increased from ${old_salary:,.2f} to ${self.salary:,.2f}")
    
    def display_info(self):
        """Display all employee information"""
        print(f"\n{'=' * 50}")
        print(f"Company: {Employee.company_name}")
        print(f"Employee ID: {self.employee_id}")
        print(f"Name: {self.name}")
        print(f"Position: {self.position}")
        print(f"Salary: ${self.salary:,.2f}")
        print(f"{'=' * 50}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING EMPLOYEE CLASS")
    print("=" * 60)
    
    # Test static method before creating any employees
    print("\n1. Testing static method (salary validation):")
    print(f"Is $50,000 valid? {Employee.is_valid_salary(50000)}")  # True
    print(f"Is $15,000 valid? {Employee.is_valid_salary(15000)}")  # False
    print(f"Is $600,000 valid? {Employee.is_valid_salary(600000)}")  # False
    
    # Creating employee objects
    print("\n2. Creating employees:")
    emp1 = Employee("Alice Johnson", "Software Engineer", 85000)
    emp2 = Employee("Bob Smith", "Data Scientist", 95000)
    emp3 = Employee("Carol White", "Product Manager", 105000)
    
    # Display employee information
    print("\n3. Employee Details:")
    emp1.display_info()
    emp2.display_info()
    emp3.display_info()
    
    # Test class method - get employee count
    print("\n4. Testing class method (get employee count):")
    print(f"Total employees: {Employee.get_employee_count()}")
    
    # Test class method - set company name
    print("\n5. Testing class method (set company name):")
    Employee.set_company_name("InnovateTech Solutions")
    
    # Notice company name changed for all employees
    print("\n6. After company name change:")
    emp1.display_info()
    
    # Test give_raise method
    print("\n7. Testing give_raise():")
    print(f"\nBefore raise - {emp1.name}'s salary: ${emp1.salary:,.2f}")
    emp1.give_raise(10)  # 10% raise
    print(f"After raise - {emp1.name}'s salary: ${emp1.salary:,.2f}")
    
    # Create more employees to test employee_count
    print("\n8. Creating more employees:")
    emp4 = Employee("David Brown", "DevOps Engineer", 90000)
    print(f"Total employees now: {Employee.get_employee_count()}")
    
    # Access class variables
    print("\n9. Accessing class variables:")
    print(f"Via class: {Employee.company_name}")
    print(f"Via instance: {emp1.company_name}")
    print(f"Employee count: {Employee.employee_count}")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Class variables are shared among all instances")
    print("2. @classmethod receives the class (cls) as first parameter")
    print("3. @staticmethod doesn't receive class or instance")
    print("4. Class methods can modify class state")
    print("5. Static methods are utility functions within the class")
    print("6. Access class variables via ClassName.variable")
