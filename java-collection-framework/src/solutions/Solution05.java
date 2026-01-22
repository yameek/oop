// This file contains the solution for Task05, demonstrating the use of deques to achieve the desired outcomes.

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution05 {
    public static void main(String[] args) {
        // Create a deque
        Deque<String> deque = new ArrayDeque<>();

        // Add elements to the deque
        deque.add("First");
        deque.add("Second");
        deque.addFirst("Zero"); // Adding to the front
        deque.addLast("Third"); // Adding to the end

        // Display the deque
        System.out.println("Deque after additions: " + deque);

        // Remove elements from the deque
        String removedFirst = deque.removeFirst(); // Removes "Zero"
        String removedLast = deque.removeLast();   // Removes "Third"

        // Display the deque after removals
        System.out.println("Deque after removals: " + deque);
        System.out.println("Removed First: " + removedFirst);
        System.out.println("Removed Last: " + removedLast);

        // Peek at the first and last elements
        String firstElement = deque.peekFirst();
        String lastElement = deque.peekLast();

        // Display the first and last elements
        System.out.println("First Element: " + firstElement);
        System.out.println("Last Element: " + lastElement);
    }
}