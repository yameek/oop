import java.util.HashSet;
import java.util.Set;

public class Solution02 {
    public static void main(String[] args) {
        // Task: Create a set of unique integers and perform various operations

        // Step 1: Create a HashSet
        Set<Integer> uniqueNumbers = new HashSet<>();

        // Step 2: Add elements to the set
        uniqueNumbers.add(10);
        uniqueNumbers.add(20);
        uniqueNumbers.add(30);
        uniqueNumbers.add(20); // Duplicate, will not be added

        // Step 3: Display the set
        System.out.println("Unique Numbers: " + uniqueNumbers);

        // Step 4: Check if a specific number is in the set
        int numberToCheck = 20;
        if (uniqueNumbers.contains(numberToCheck)) {
            System.out.println(numberToCheck + " is in the set.");
        } else {
            System.out.println(numberToCheck + " is not in the set.");
        }

        // Step 5: Remove an element from the set
        uniqueNumbers.remove(10);
        System.out.println("After removing 10: " + uniqueNumbers);

        // Step 6: Get the size of the set
        System.out.println("Size of the set: " + uniqueNumbers.size());

        // Step 7: Clear the set
        uniqueNumbers.clear();
        System.out.println("Set after clearing: " + uniqueNumbers);
    }
}