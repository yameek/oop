import java.util.HashMap;
import java.util.Map;

public class Solution03 {
    public static void main(String[] args) {
        // Create a map to store student names and their corresponding grades
        Map<String, Integer> studentGrades = new HashMap<>();

        // Adding entries to the map
        studentGrades.put("Alice", 85);
        studentGrades.put("Bob", 92);
        studentGrades.put("Charlie", 78);
        studentGrades.put("Diana", 90);

        // Displaying the map entries
        System.out.println("Student Grades:");
        for (Map.Entry<String, Integer> entry : studentGrades.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Updating a grade
        studentGrades.put("Alice", 88);
        System.out.println("\nUpdated Grades:");
        System.out.println("Alice: " + studentGrades.get("Alice"));

        // Removing a student
        studentGrades.remove("Charlie");
        System.out.println("\nAfter removing Charlie:");
        for (Map.Entry<String, Integer> entry : studentGrades.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Checking if a student exists
        String studentToCheck = "Bob";
        if (studentGrades.containsKey(studentToCheck)) {
            System.out.println("\n" + studentToCheck + " is in the map with a grade of " + studentGrades.get(studentToCheck));
        } else {
            System.out.println("\n" + studentToCheck + " is not in the map.");
        }
    }
}