"""
TASK 7: Multiple Inheritance and MRO
=====================================
Difficulty: Advanced

Learn about: Multiple Inheritance, Method Resolution Order (MRO), super()

PROBLEM:
--------
Create a university system with multiple inheritance.

Requirements:
1. Create a class 'Person' with:
   - Attributes: name, age, email
   - Method: introduce() - prints person's introduction

2. Create a class 'Student' inheriting from Person with:
   - Additional attributes: student_id, major, gpa
   - Method: study(subject) - prints studying message
   - Override introduce() to include student info

3. Create a class 'Employee' inheriting from Person with:
   - Additional attributes: employee_id, department, salary
   - Method: work() - prints working message
   - Override introduce() to include employee info

4. Create a class 'TeachingAssistant' inheriting from BOTH Student and Employee:
   - Additional attributes: courses_assisting (list)
   - Additional attribute: hours_per_week
   - Method: assist(course) - prints assisting message
   - Override introduce() using super() properly to combine both parent introductions
   - Method: show_mro() - prints the Method Resolution Order

5. Create a class 'ResearchAssistant' inheriting from BOTH Student and Employee:
   - Additional attributes: research_topic, lab_name
   - Method: conduct_research() - prints research message
   - Override introduce() to include all relevant info

TEST YOUR CODE:
---------------
- Create a TeachingAssistant and ResearchAssistant
- Call introduce() on both and observe the difference
- Print MRO for TeachingAssistant class
- Test all methods from all parent classes
"""

# Write your solution below:

