import java.util.LinkedList;
import java.util.Queue;

public class QueueExamples {
    public static void main(String[] args) {
        // Create a Queue using LinkedList
        Queue<String> queue = new LinkedList<>();

        // Adding elements to the Queue
        queue.add("Element 1");
        queue.add("Element 2");
        queue.add("Element 3");
        System.out.println("Queue after adding elements: " + queue);

        // Accessing the head of the Queue
        String head = queue.peek();
        System.out.println("Head of the Queue: " + head);

        // Removing elements from the Queue
        String removedElement = queue.poll();
        System.out.println("Removed Element: " + removedElement);
        System.out.println("Queue after removing an element: " + queue);

        // Checking if the Queue contains a specific element
        boolean containsElement = queue.contains("Element 2");
        System.out.println("Queue contains 'Element 2': " + containsElement);

        // Iterating over the elements in the Queue
        System.out.println("Iterating over the Queue:");
        for (String element : queue) {
            System.out.println(element);
        }
    }
}