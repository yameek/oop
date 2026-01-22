import java.util.ArrayDeque;
import java.util.Deque;

public class DequeExamples {
    public static void main(String[] args) {
        // Create a Deque using ArrayDeque
        Deque<String> deque = new ArrayDeque<>();

        // Adding elements to the deque
        deque.add("First");
        deque.add("Second");
        deque.addFirst("Zero");
        deque.addLast("Third");

        // Displaying the deque
        System.out.println("Deque after additions: " + deque);

        // Removing elements from the deque
        String removedFirst = deque.removeFirst();
        String removedLast = deque.removeLast();
        System.out.println("Removed from front: " + removedFirst);
        System.out.println("Removed from back: " + removedLast);

        // Displaying the deque after removals
        System.out.println("Deque after removals: " + deque);

        // Peeking at the elements
        String peekFirst = deque.peekFirst();
        String peekLast = deque.peekLast();
        System.out.println("First element: " + peekFirst);
        System.out.println("Last element: " + peekLast);
        System.out.println("Deque after peak: " + deque);

    }
}