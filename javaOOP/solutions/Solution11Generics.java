/**
 * SOLUTION 11: Generics
 * ======================
 *
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Generic Class      — class Foo<T> { T value; }  → T is a type placeholder
 * 2. Generic Method     — <T> void method(T arg)     → T declared before return type
 * 3. Bounded Type       — <T extends Number>         → T must be Number or subclass
 * 4. Wildcard           — List<?>                    → unknown type; read-only safe
 * 5. Type Erasure       — Generics are compile-time only; at runtime, T becomes Object
 *
 * KEY TAKEAWAYS:
 * --------------
 * - Generics eliminate ClassCastException at runtime by enforcing types at compile time
 * - Use <T extends SomeClass> when you need to call methods of SomeClass on T
 * - Use <?> (wildcard) when you only need to READ from a collection, not add to it
 * - Static factory methods (Pair.of()) are a common generic pattern (see Map.entry())
 * - EmptyStackException is in java.util — always throw on invalid state
 */

package solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;

// ─────────────────────────────────────────────────────────────────────────────
// PART A: Generic Pair
// ─────────────────────────────────────────────────────────────────────────────

class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst()  { return first; }
    public S getSecond() { return second; }

    // Static factory — caller never needs to repeat the types
    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PART B: Generic Stack
// ─────────────────────────────────────────────────────────────────────────────

class GenericStack<T> {
    private final List<T> elements = new ArrayList<>();

    public void push(T item) {
        elements.add(item);
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        return elements.remove(elements.size() - 1);
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return elements.get(elements.size() - 1);
    }

    public boolean isEmpty() { return elements.isEmpty(); }
    public int size()        { return elements.size(); }

    @Override
    public String toString() {
        if (isEmpty()) return "Stack[empty]";
        return "Stack" + elements.toString().replace("]", " ← top]");
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PART D: Bounded Type Parameter
// ─────────────────────────────────────────────────────────────────────────────

class NumberBox<T extends Number> {
    private final T value;

    public NumberBox(T value) { this.value = value; }

    public T getValue() { return value; }

    // Number guarantees .doubleValue() — only possible because of <T extends Number>
    public double getDoubleValue() { return value.doubleValue(); }

    public boolean isGreaterThan(NumberBox<?> other) {
        return this.getDoubleValue() > other.getDoubleValue();
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main + PART C (generic method swap) + PART E (wildcard printList)
// ─────────────────────────────────────────────────────────────────────────────

public class Solution11Generics {

    // PART C: Generic method — <T> declared before return type
    public static <T> void swap(T[] array, int i, int j) {
        T temp   = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // PART E: Wildcard — works with List<String>, List<Integer>, List<anything>
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        // ── PART A ──────────────────────────────────────────────────────────
        System.out.println("=== PART A: Pair ===");
        Pair<String, Integer> person = Pair.of("Alice", 30);
        Pair<String, String>  capital = Pair.of("Japan", "Tokyo");
        System.out.println("Person  : " + person);
        System.out.println("Capital : " + capital);
        System.out.println("First   : " + person.getFirst() + ", Second: " + person.getSecond());

        // ── PART B ──────────────────────────────────────────────────────────
        System.out.println("\n=== PART B: GenericStack ===");
        GenericStack<Integer> stack = new GenericStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("After pushes : " + stack);
        System.out.println("Peek         : " + stack.peek());
        System.out.println("Pop          : " + stack.pop());
        System.out.println("Stack now    : " + stack);
        System.out.println("Size         : " + stack.size());

        // Test EmptyStackException
        GenericStack<String> emptyStack = new GenericStack<>();
        try {
            emptyStack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException on empty stack ✓");
        }

        // ── PART C ──────────────────────────────────────────────────────────
        System.out.println("\n=== PART C: Generic swap ===");
        String[] names = {"Alice", "Bob", "Charlie"};
        System.out.println("Before swap: " + Arrays.toString(names));
        swap(names, 0, 1);
        System.out.println("After swap(0,1): " + Arrays.toString(names));

        Integer[] nums = {1, 2, 3, 4, 5};
        swap(nums, 1, 3);
        System.out.println("Integer array after swap(1,3): " + Arrays.toString(nums));

        // ── PART D ──────────────────────────────────────────────────────────
        System.out.println("\n=== PART D: NumberBox ===");
        NumberBox<Integer> box1 = new NumberBox<>(42);
        NumberBox<Double>  box2 = new NumberBox<>(3.14);
        System.out.println("Box1 double value : " + box1.getDoubleValue());
        System.out.println("Box2 double value : " + box2.getDoubleValue());
        System.out.println("Box1 > Box2?       " + box1.isGreaterThan(box2));
        System.out.println("Box2 > Box1?       " + box2.isGreaterThan(box1));

        // ── PART E ──────────────────────────────────────────────────────────
        System.out.println("\n=== PART E: printList (Wildcard) ===");
        List<String> strings = List.of("Alice", "Bob", "Charlie");
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        List<Double> doubles  = List.of(1.1, 2.2, 3.3);
        System.out.print("String list  : "); printList(strings);
        System.out.print("Integer list : "); printList(integers);
        System.out.print("Double list  : "); printList(doubles);

        /*
         * KEY TAKEAWAYS:
         * ─────────────
         * ✅ Pair<F,S>         → Two type params; static factory avoids repeating types
         * ✅ GenericStack<T>   → Generic class backed by ArrayList; type-safe push/pop
         * ✅ swap(T[] ...)     → Generic METHOD — <T> before return type, works on any array
         * ✅ NumberBox<T extends Number> → Bounded type lets us call .doubleValue()
         * ✅ printList(List<?>) → Wildcard — read-only, accepts any List subtype
         *
         * COMMON MISTAKE:
         * ───────────────
         * You CANNOT do: new T()  or  T.class  inside a generic class.
         * Generics are erased at runtime (Type Erasure). They are compile-time only.
         */
    }
}
