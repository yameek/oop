/**
 * SOLUTION 7: Interfaces and Multiple Inheritance of Type
 * ========================================================
 * Demonstrates implementing multiple interfaces and using interface-based
 * polymorphism to treat different concrete types uniformly.
 */
package solutions;

interface Teachable {
    void teach(String subject);
    void gradeAssignment();
}

interface Researchable {
    void conductResearch(String topic);
    void publishPaper(String title);
}

class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void displayInfo() {
        System.out.printf("Person: %s, Age: %d%n", name, age);
    }
}

class Professor extends Person implements Teachable, Researchable {
    private final String department;

    public Professor(String name, int age, String department) {
        super(name, age);
        this.department = department;
    }

    @Override
    public void teach(String subject) {
        System.out.printf("Professor %s is teaching %s.%n", getName(), subject);
    }

    @Override
    public void gradeAssignment() {
        System.out.printf("Professor %s is grading assignments.%n", getName());
    }

    @Override
    public void conductResearch(String topic) {
        System.out.printf("Professor %s is conducting research on %s.%n", getName(), topic);
    }

    @Override
    public void publishPaper(String title) {
        System.out.printf("Professor %s published paper: %s.%n", getName(), title);
    }

    @Override
    public void displayInfo() {
        System.out.printf("Professor: %s, Age: %d, Department: %s%n", getName(), getAge(), department);
    }
}

class TeachingAssistant extends Person implements Teachable {
    private final String supervisorName;

    public TeachingAssistant(String name, int age, String supervisorName) {
        super(name, age);
        this.supervisorName = supervisorName;
    }

    @Override
    public void teach(String subject) {
        System.out.printf("TA %s is assisting in teaching %s.%n", getName(), subject);
    }

    @Override
    public void gradeAssignment() {
        System.out.printf("TA %s is grading lab assignments.%n", getName());
    }

    @Override
    public void displayInfo() {
        System.out.printf("Teaching Assistant: %s, Age: %d, Supervisor: %s%n", getName(), getAge(), supervisorName);
    }
}

class ResearchAssistant extends Person implements Researchable {
    private final String labName;

    public ResearchAssistant(String name, int age, String labName) {
        super(name, age);
        this.labName = labName;
    }

    @Override
    public void conductResearch(String topic) {
        System.out.printf("Research Assistant %s is gathering data on %s.%n", getName(), topic);
    }

    @Override
    public void publishPaper(String title) {
        System.out.printf("Research Assistant %s contributed to paper: %s.%n", getName(), title);
    }

    @Override
    public void displayInfo() {
        System.out.printf("Research Assistant: %s, Age: %d, Lab: %s%n", getName(), getAge(), labName);
    }
}

public class Solution07Interfaces {
    public static void main(String[] args) {
        Professor professor = new Professor("Dr. Smith", 50, "Computer Science");
        TeachingAssistant ta = new TeachingAssistant("Alice", 24, "Dr. Smith");
        ResearchAssistant ra = new ResearchAssistant("Bob", 27, "AI Lab");

        System.out.println("=== UNIVERSITY PERSONNEL ===");
        professor.displayInfo();
        ta.displayInfo();
        ra.displayInfo();
        System.out.println();

        Teachable[] teachables = {professor, ta};
        Researchable[] researchables = {professor, ra};

        System.out.println("--- Teaching Activities ---");
        for (Teachable teachable : teachables) {
            teachable.teach("Advanced Java");
            teachable.gradeAssignment();
        }

        System.out.println("\n--- Research Activities ---");
        for (Researchable researchable : researchables) {
            researchable.conductResearch("Machine Learning");
            researchable.publishPaper("AI in Education");
        }
    }
}
