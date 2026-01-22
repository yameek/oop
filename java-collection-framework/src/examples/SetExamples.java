import java.util.HashSet;
import java.util.Set;

public class SetExamples {
    
    public static void main(String[] args) {
        demonstrateSetOperations();
    }

    private static void demonstrateSetOperations() {
        // Create a HashSet
        Set<String> set = new HashSet<>();

        // Adding elements to the set
        set.add("Apple");
        set.add("Banana");
        set.add("Orange");
        set.add("Grapes");
        set.add("Apple"); // Duplicate element, will not be added

        System.out.println("Set after adding elements: " + set);

        // Removing an element from the set
        set.remove("Banana");
        System.out.println("Set after removing 'Banana': " + set);

        // Checking if an element exists in the set
        boolean hasApple = set.contains("Apple");
        System.out.println("Set contains 'Apple': " + hasApple);

        // Iterating over the set
        System.out.println("Iterating over the set:");
        for (String fruit : set) {
            System.out.println(fruit);
        }

        // Size of the set
        System.out.println("Size of the set: " + set.size());

        // Clearing the set
        set.clear();
        System.out.println("Set after clearing: " + set);
    }
}