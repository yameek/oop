"""
SOLUTION 7: Multiple Inheritance and MRO
=========================================

CONCEPTS EXPLAINED:
-------------------
1. Multiple Inheritance: Class inherits from multiple parent classes
2. MRO (Method Resolution Order): Order Python searches for methods
3. C3 Linearization: Algorithm Python uses for MRO
4. Diamond Problem: When multiple paths lead to same base class
5. super(): Follows MRO, not just immediate parent
6. __mro__: Attribute showing the method resolution order

"""

class Person:
    """Base class representing a person"""
    
    def __init__(self, name, age, email):
        """
        Initialize a Person
        
        Parameters:
        -----------
        name : str
            Person's name
        age : int
            Person's age
        email : str
            Person's email address
        """
        self.name = name
        self.age = age
        self.email = email
        print(f"Person.__init__ called for {name}")
    
    def introduce(self):
        """Introduce the person"""
        print(f"\n👋 Hi! I'm {self.name}, {self.age} years old.")
        print(f"   Email: {self.email}")


class Student(Person):
    """Student class inheriting from Person"""
    
    def __init__(self, name, age, email, student_id, major, gpa):
        """
        Initialize a Student
        
        Parameters:
        -----------
        student_id : str
            Student ID number
        major : str
            Student's major/field of study
        gpa : float
            Grade Point Average
        """
        # Call Person's __init__
        super().__init__(name, age, email)
        self.student_id = student_id
        self.major = major
        self.gpa = gpa
        print(f"Student.__init__ called for {name}")
    
    def introduce(self):
        """Override to include student information"""
        super().introduce()
        print(f"   👨‍🎓 Student ID: {self.student_id}")
        print(f"   📚 Major: {self.major}")
        print(f"   📊 GPA: {self.gpa}")
    
    def study(self, subject):
        """Student studying activity"""
        print(f"📖 {self.name} is studying {subject}...")


class Employee(Person):
    """Employee class inheriting from Person"""
    
    def __init__(self, name, age, email, employee_id, department, salary):
        """
        Initialize an Employee
        
        Parameters:
        -----------
        employee_id : str
            Employee ID number
        department : str
            Department name
        salary : float
            Annual salary
        """
        # Call Person's __init__
        super().__init__(name, age, email)
        self.employee_id = employee_id
        self.department = department
        self.salary = salary
        print(f"Employee.__init__ called for {name}")
    
    def introduce(self):
        """Override to include employee information"""
        super().introduce()
        print(f"   💼 Employee ID: {self.employee_id}")
        print(f"   🏢 Department: {self.department}")
        print(f"   💰 Salary: ${self.salary:,.2f}")
    
    def work(self):
        """Employee working activity"""
        print(f"💼 {self.name} is working in {self.department}...")


class TeachingAssistant(Student, Employee):
    """
    TeachingAssistant inherits from BOTH Student and Employee
    
    NOTE: Order matters! (Student, Employee)
    This affects the MRO (Method Resolution Order)
    """
    
    def __init__(self, name, age, email, student_id, major, gpa,
                 employee_id, department, salary, courses_assisting, hours_per_week):
        """
        Initialize a TeachingAssistant
        
        NOTE: We need to call both parent __init__ methods
        Using super() follows the MRO
        """
        # Initialize Student part (which will call Person via MRO)
        Student.__init__(self, name, age, email, student_id, major, gpa)
        
        # Initialize Employee part (but skip Person as it's already initialized)
        # We directly set Employee-specific attributes
        self.employee_id = employee_id
        self.department = department
        self.salary = salary
        
        # TeachingAssistant-specific attributes
        self.courses_assisting = courses_assisting
        self.hours_per_week = hours_per_week
        
        print(f"TeachingAssistant.__init__ called for {name}")
    
    def introduce(self):
        """
        Override to combine information from both parent classes
        
        NOTE: super().introduce() will follow MRO:
        TeachingAssistant -> Student -> Employee -> Person
        """
        print(f"\n{'=' * 60}")
        print(f"TEACHING ASSISTANT INTRODUCTION")
        print(f"{'=' * 60}")
        
        # Call Student's introduce (which includes Person info)
        Student.introduce(self)
        
        # Add Employee info manually
        print(f"   💼 Employee ID: {self.employee_id}")
        print(f"   🏢 Department: {self.department}")
        print(f"   💰 Salary: ${self.salary:,.2f}")
        
        # Add TA-specific info
        print(f"\n   🎓 Teaching Assistant Details:")
        print(f"   📋 Courses Assisting: {', '.join(self.courses_assisting)}")
        print(f"   ⏰ Hours per Week: {self.hours_per_week}")
        print(f"{'=' * 60}")
    
    def assist(self, course):
        """TA assisting activity"""
        if course in self.courses_assisting:
            print(f"👨‍🏫 {self.name} is assisting students in {course}...")
        else:
            print(f"❌ {self.name} is not assigned to {course}")
    
    def show_mro(self):
        """Display the Method Resolution Order"""
        print(f"\n{'=' * 60}")
        print(f"METHOD RESOLUTION ORDER (MRO)")
        print(f"{'=' * 60}")
        for i, cls in enumerate(self.__class__.__mro__, 1):
            print(f"{i}. {cls.__name__}")
        print(f"{'=' * 60}")


class ResearchAssistant(Student, Employee):
    """ResearchAssistant inherits from BOTH Student and Employee"""
    
    def __init__(self, name, age, email, student_id, major, gpa,
                 employee_id, department, salary, research_topic, lab_name):
        """Initialize a ResearchAssistant"""
        # Initialize Student part
        Student.__init__(self, name, age, email, student_id, major, gpa)
        
        # Initialize Employee-specific attributes
        self.employee_id = employee_id
        self.department = department
        self.salary = salary
        
        # ResearchAssistant-specific attributes
        self.research_topic = research_topic
        self.lab_name = lab_name
        
        print(f"ResearchAssistant.__init__ called for {name}")
    
    def introduce(self):
        """Override to include all relevant information"""
        print(f"\n{'=' * 60}")
        print(f"RESEARCH ASSISTANT INTRODUCTION")
        print(f"{'=' * 60}")
        
        # Call Student's introduce
        Student.introduce(self)
        
        # Add Employee info
        print(f"   💼 Employee ID: {self.employee_id}")
        print(f"   🏢 Department: {self.department}")
        print(f"   💰 Salary: ${self.salary:,.2f}")
        
        # Add RA-specific info
        print(f"\n   🔬 Research Assistant Details:")
        print(f"   📝 Research Topic: {self.research_topic}")
        print(f"   🧪 Lab: {self.lab_name}")
        print(f"{'=' * 60}")
    
    def conduct_research(self):
        """RA research activity"""
        print(f"🔬 {self.name} is conducting research on '{self.research_topic}' in {self.lab_name}...")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING MULTIPLE INHERITANCE AND MRO")
    print("=" * 60)
    
    # Create a Teaching Assistant
    print("\n1. Creating a Teaching Assistant:")
    print("-" * 60)
    ta = TeachingAssistant(
        name="Alice Johnson",
        age=24,
        email="alice.j@university.edu",
        student_id="S12345",
        major="Computer Science",
        gpa=3.8,
        employee_id="E98765",
        department="CS Department",
        salary=25000,
        courses_assisting=["Python Programming", "Data Structures"],
        hours_per_week=20
    )
    
    # Create a Research Assistant
    print("\n2. Creating a Research Assistant:")
    print("-" * 60)
    ra = ResearchAssistant(
        name="Bob Smith",
        age=26,
        email="bob.s@university.edu",
        student_id="S67890",
        major="Physics",
        gpa=3.9,
        employee_id="E54321",
        department="Physics Department",
        salary=28000,
        research_topic="Quantum Computing",
        lab_name="Advanced Physics Lab"
    )
    
    # Call introduce() on both
    print("\n3. Introducing Teaching Assistant:")
    ta.introduce()
    
    print("\n4. Introducing Research Assistant:")
    ra.introduce()
    
    # Show MRO for TeachingAssistant
    print("\n5. Method Resolution Order for TeachingAssistant:")
    ta.show_mro()
    
    # Access MRO directly
    print("\n6. Accessing MRO via __mro__ attribute:")
    print(f"TeachingAssistant MRO: {[cls.__name__ for cls in TeachingAssistant.__mro__]}")
    print(f"ResearchAssistant MRO: {[cls.__name__ for cls in ResearchAssistant.__mro__]}")
    
    # Test methods from all parent classes
    print("\n7. Testing methods from Student class:")
    ta.study("Algorithms")
    ra.study("Quantum Mechanics")
    
    print("\n8. Testing methods from Employee class:")
    ta.work()
    ra.work()
    
    print("\n9. Testing TA-specific methods:")
    ta.assist("Python Programming")
    ta.assist("Advanced Mathematics")  # Not in their courses
    
    print("\n10. Testing RA-specific methods:")
    ra.conduct_research()
    
    # Demonstrate isinstance checks
    print("\n11. Type checking with isinstance:")
    print(f"ta is TeachingAssistant: {isinstance(ta, TeachingAssistant)}")
    print(f"ta is Student: {isinstance(ta, Student)}")
    print(f"ta is Employee: {isinstance(ta, Employee)}")
    print(f"ta is Person: {isinstance(ta, Person)}")
    
    print(f"\nra is ResearchAssistant: {isinstance(ra, ResearchAssistant)}")
    print(f"ra is Student: {isinstance(ra, Student)}")
    print(f"ra is Employee: {isinstance(ra, Employee)}")
    
    # Access attributes from different classes
    print("\n12. Accessing attributes from different parent classes:")
    print(f"\nTA attributes:")
    print(f"  Student ID: {ta.student_id} (from Student)")
    print(f"  Major: {ta.major} (from Student)")
    print(f"  Employee ID: {ta.employee_id} (from Employee)")
    print(f"  Department: {ta.department} (from Employee)")
    print(f"  Name: {ta.name} (from Person)")
    
    # Demonstrate the diamond problem resolution
    print("\n13. Diamond Problem Resolution:")
    print("Both Student and Employee inherit from Person.")
    print("TeachingAssistant inherits from both Student and Employee.")
    print("Python's MRO ensures Person.__init__ is called only once!")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Multiple inheritance: class Child(Parent1, Parent2)")
    print("2. MRO determines method lookup order")
    print("3. Order of parent classes matters!")
    print("4. super() follows MRO, not just immediate parent")
    print("5. Use __mro__ to see the method resolution order")
    print("6. Python uses C3 Linearization for MRO")
    print("7. MRO solves the diamond problem")
    print("8. Child class is instance of all parent classes")
