// Task 01: List Operations
// 
// Objective: Create a simple program that demonstrates the basic operations of the List interface in Java.
// 
// Requirements:
// 1. Create a List of Strings.
// 2. Add five names to the list.
// 3. Print the list.
// 4. Remove the second name from the list.
// 5. Print the updated list.
// 6. Iterate through the list and print each name on a new line.
// 
// Expected Outcome:
// The program should display the original list, the updated list after removal, and each name printed individually.

import java.util.ArrayList;
import java.util.List;

public class Task01 {
    public static void main(String[] args) {
        // Step 1: Create a List of Strings
        List<String> names = new ArrayList<>();

        // Step 2: Add five names to the list
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("Diana");
        names.add("Ethan");

        // Step 3: Print the list
        System.out.println("Original List: " + names);

        // Step 4: Remove the second name from the list
        names.remove(1); // Removes "Bob"

        // Step 5: Print the updated list
        System.out.println("Updated List: " + names);

        // Step 6: Iterate through the list and print each name on a new line
        System.out.println("Names in the list:");
        for (String name : names) {
            System.out.println(name);
        }
    }
}