// This file contains the solution for Task01, implementing the required functionality using lists.

import java.util.ArrayList;
import java.util.List;

public class Solution01 {
    public static void main(String[] args) {
        // Create a list of strings
        List<String> fruits = new ArrayList<>();

        // Add elements to the list
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");

        // Display the list
        System.out.println("Fruits List: " + fruits);

        // Modify an element in the list
        fruits.set(1, "Blueberry");
        System.out.println("Updated Fruits List: " + fruits);

        // Iterate over the list
        System.out.println("Iterating over the list:");
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        // Remove an element from the list
        fruits.remove("Cherry");
        System.out.println("Fruits List after removal: " + fruits);
    }
}