import java.util.*;

/**
 * GenericsExamples.java
 * ----------------------
 * Demonstrates how Generics and the Java Collection Framework work together.
 * Generics are what make List<String>, Map<K,V>, etc. type-safe.
 *
 * Run: javac GenericsExamples.java && java GenericsExamples
 */
public class GenericsExamples {

    public static void main(String[] args) {
        GenericsExamples ex = new GenericsExamples();
        ex.genericListExample();
        ex.genericMapExample();
        ex.boundedTypeExample();
        ex.wildcardExample();
        ex.genericMethodExample();
    }

    // ── 1. Generic List ────────────────────────────────────────────────────
    // Without generics: List raw = new ArrayList(); → no type safety
    // With generics:    List<String> → compiler rejects wrong types
    private void genericListExample() {
        System.out.println("=== Generic List ===");

        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        // names.add(42);  ← compile error! Type safety at work.

        for (String name : names) {
            System.out.println("  " + name.toUpperCase()); // no cast needed
        }
    }

    // ── 2. Generic Map ─────────────────────────────────────────────────────
    private void genericMapExample() {
        System.out.println("\n=== Generic Map<String, Integer> ===");

        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 82);
        scores.put("Charlie", 88);

        scores.forEach((name, score) ->
            System.out.printf("  %-10s → %d%n", name, score));

        // Map.Entry gives you typed key and value
        Optional<Map.Entry<String, Integer>> top = scores.entrySet().stream()
            .max(Map.Entry.comparingByValue());
        top.ifPresent(e ->
            System.out.println("  Top scorer: " + e.getKey() + " (" + e.getValue() + ")"));
    }

    // ── 3. Bounded Type Parameter ──────────────────────────────────────────
    // <T extends Number> means T can be Integer, Double, Long, etc.
    // Without the bound, we couldn't call .doubleValue() on T.
    private <T extends Number & Comparable<T>> T findMax(List<T> list) {
        if (list.isEmpty()) throw new NoSuchElementException("Empty list");
        T max = list.get(0);
        for (T item : list) {
            if (item.compareTo(max) > 0) max = item;
        }
        return max;
    }

    private void boundedTypeExample() {
        System.out.println("\n=== Bounded Type <T extends Number & Comparable<T>> ===");

        List<Integer> ints    = List.of(3, 1, 4, 1, 5, 9, 2, 6);
        List<Double>  doubles = List.of(1.1, 3.3, 2.2, 5.5, 4.4);

        System.out.println("  Max integer : " + findMax(ints));
        System.out.println("  Max double  : " + findMax(doubles));
    }

    // ── 4. Wildcard <?> ────────────────────────────────────────────────────
    // Use <?> when you want to accept any List but only READ from it.
    // You CANNOT add elements to a List<?> (except null).
    private double sumList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) sum += n.doubleValue();
        return sum;
    }

    private void wildcardExample() {
        System.out.println("\n=== Wildcard List<? extends Number> ===");

        List<Integer> ints    = List.of(1, 2, 3, 4, 5);
        List<Double>  doubles = List.of(1.5, 2.5, 3.5);
        List<Long>    longs   = List.of(100L, 200L, 300L);

        // Same method handles all three!
        System.out.println("  Sum of ints   : " + sumList(ints));
        System.out.println("  Sum of doubles: " + sumList(doubles));
        System.out.println("  Sum of longs  : " + sumList(longs));
    }

    // ── 5. Generic Method with Collections ────────────────────────────────
    // <T extends Comparable<T>> lets us compare any type with itself
    private <T extends Comparable<T>> List<T> filterAbove(List<T> list, T threshold) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (item.compareTo(threshold) > 0) result.add(item);
        }
        return result;
    }

    private void genericMethodExample() {
        System.out.println("\n=== Generic Method filterAbove ===");

        List<Integer> nums   = Arrays.asList(5, 10, 3, 8, 15, 1, 12);
        List<String>  words  = Arrays.asList("banana", "apple", "cherry", "date", "fig");

        System.out.println("  Integers above 7 : " + filterAbove(nums, 7));
        System.out.println("  Strings after 'c': " + filterAbove(words, "c"));
    }
}
