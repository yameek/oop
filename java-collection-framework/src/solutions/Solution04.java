import java.util.LinkedList;
import java.util.Queue;

public class Solution04 {
    public static void main(String[] args) {
        // Create a Queue
        Queue<String> queue = new LinkedList<>();

        // Add elements to the Queue
        queue.offer("Element 1");
        queue.offer("Element 2");
        queue.offer("Element 3");

        // Display the Queue
        System.out.println("Queue: " + queue);

        // Remove an element from the Queue
        String removedElement = queue.poll();
        System.out.println("Removed Element: " + removedElement);

        // Display the Queue after removal
        System.out.println("Queue after removal: " + queue);

        // Peek at the front element of the Queue
        String frontElement = queue.peek();
        System.out.println("Front Element: " + frontElement);

        // Check if the Queue is empty
        boolean isEmpty = queue.isEmpty();
        System.out.println("Is the Queue empty? " + isEmpty);
    }
}