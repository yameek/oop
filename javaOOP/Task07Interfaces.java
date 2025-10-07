/**
 * TASK 7: Interfaces and Multiple Inheritance
 * ============================================
 * Difficulty: Advanced ⭐⭐⭐⭐
 * 
 * Learn about: Interfaces, Multiple interface implementation, Default methods
 * 
 * PROBLEM:
 * --------
 * Create a university system with different types of personnel.
 * 
 * Requirements:
 * 
 * 1. Create interface 'Teachable':
 *    - Method: teach(String subject) - prints teaching message
 *    - Method: gradeAssignment() - prints grading message
 * 
 * 2. Create interface 'Researchable':
 *    - Method: conductResearch(String topic) - prints research message
 *    - Method: publishPaper(String title) - prints publishing message
 * 
 * 3. Create class 'Person':
 *    - Instance variables: name (String), age (int)
 *    - Constructor and getters
 *    - Method: displayInfo()
 * 
 * 4. Create class 'Professor' extends Person implements Teachable, Researchable:
 *    - Additional variable: department (String)
 *    - Constructor taking name, age, department
 *    - Implement all interface methods
 *    - Override displayInfo() to include department
 * 
 * 5. Create class 'TeachingAssistant' extends Person implements Teachable:
 *    - Additional variable: supervisorName (String)
 *    - Constructor taking name, age, supervisorName
 *    - Implement Teachable methods
 *    - Override displayInfo() to include supervisor
 * 
 * 6. Create class 'ResearchAssistant' extends Person implements Researchable:
 *    - Additional variable: labName (String)
 *    - Constructor taking name, age, labName
 *    - Implement Researchable methods
 *    - Override displayInfo() to include lab
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create instances of each class
 * - Demonstrate that Professor implements both interfaces
 * - Store different types in appropriate interface arrays
 * - Call methods polymorphically
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Interfaces (contract that classes must fulfill)
 * - Multiple interface implementation
 * - Interface vs abstract class
 * - "Can-do" relationship (interfaces) vs "is-a" (inheritance)
 * - Polymorphism with interfaces
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Professor: Dr. Smith, Department: Computer Science
 * Teaching: Data Structures
 * Grading assignments...
 * Researching: Machine Learning
 * Publishing paper: AI in Education
 * 
 * TA: Alice, Supervisor: Dr. Smith
 * Teaching: Java Programming Lab
 */

// Write your solution below:

