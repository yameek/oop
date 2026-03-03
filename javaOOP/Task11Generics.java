/**
 * TASK 11: Generics
 * ==================
 * Difficulty: Advanced ⭐⭐⭐⭐
 *
 * Learn about: Generic classes, Generic methods, Bounded type parameters, Wildcards
 *
 * PROBLEM:
 * --------
 * You are building a reusable data utility library. Because you want these utilities
 * to work with ANY type (Integer, String, Student, Product...), you'll use Generics.
 *
 * PART A — Generic Class: Pair<F, S>
 * ------------------------------------
 * Create a generic class called Pair<F, S> that holds two values of potentially
 * different types (like a key-value pair).
 *
 * Requirements:
 * 1. Fields: first (type F), second (type S)
 * 2. Constructor that takes both values
 * 3. Getters: getFirst(), getSecond()
 * 4. toString() → "Pair(first, second)"
 * 5. Static factory method: Pair.of(first, second) — returns a new Pair
 *
 * PART B — Generic Class: GenericStack<T>
 * ----------------------------------------
 * Create a generic stack (LIFO structure) backed by an ArrayList.
 *
 * Requirements:
 * 1. push(T item)     — adds item to top
 * 2. pop()            — removes and returns top item; throw EmptyStackException if empty
 * 3. peek()           — returns top item without removing; throw EmptyStackException if empty
 * 4. isEmpty()        — returns true if stack has no elements
 * 5. size()           — returns number of elements
 * 6. toString()       — shows all elements bottom→top, e.g. "Stack[1, 2, 3 ← top]"
 *
 * PART C — Generic Method: swap()
 * --------------------------------
 * Write a generic static method: swap(T[] array, int i, int j)
 * Swaps two elements in any type of array.
 *
 * PART D — Bounded Type Parameter: NumberBox<T extends Number>
 * -------------------------------------------------------------
 * Create a generic class NumberBox<T extends Number> that:
 * 1. Holds a value of type T
 * 2. Has getDoubleValue() — returns the value as a double
 * 3. Has isGreaterThan(NumberBox<?> other) — compares double values
 *
 * PART E — Wildcard utility method
 * ----------------------------------
 * Write a static method: printList(List<?> list)
 * That prints every element of any List regardless of type.
 *
 * TEST YOUR CODE:
 * ---------------
 * In main():
 * - Create Pair<String, Integer> for name+age, Pair<String, String> for country+capital
 * - Push integers onto a GenericStack<Integer>, pop and peek
 * - Swap elements in a String array
 * - Create NumberBox<Integer> and NumberBox<Double>, compare them
 * - Call printList() with a List<String> and a List<Integer>
 *
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Why generics? → Type safety at compile time; avoid casting
 * - T, E, K, V, N are just naming conventions (any letter works)
 * - Bounded types (<T extends Number>) restrict what types are allowed
 * - Wildcard <?> means "any type" — used when you don't need to know the specific type
 * - Generic methods have <T> before the return type: public static <T> void swap(...)
 *
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Pair: Pair(Alice, 30)
 * Stack after pushes: Stack[10, 20, 30 ← top]
 * Peek: 30
 * Pop: 30
 * Stack now: Stack[10, 20 ← top]
 * Swapped array: [Bob, Alice, Charlie]
 * Box1 double value: 42.0
 * Box2 double value: 3.14
 * Box1 > Box2? true
 * Printing list: Alice Bob Charlie
 * Printing list: 1 2 3
 */

// Write your solution below:
