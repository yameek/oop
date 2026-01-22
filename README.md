# Java Collection Framework - Learning Tasks

Welcome to the Java Collection Framework learning module! This section provides a comprehensive set of examples, tasks, and solutions to help you master Java's powerful collections library.

## 📚 Structure

The directory is organized to facilitate a clear learning path:

```
javaCollections/
├── README.md                   # This guide
├── examples/                   # Comprehensive, runnable examples for each major collection
│   ├── C01_ArrayListExample.java
│   ├── C02_LinkedListExample.java
│   ├── C03_HashSetExample.java
│   ├── C04_HashMapExample.java
│   └── ...
├── tasks/                      # Hands-on exercises to test your understanding
│   ├── Task01_ListManipulation.java
│   ├── Task02_SetOperations.java
│   └── ...
└── solutions/                  # Reference implementations for each task
    ├── Solution01_ListManipulation.java
    ├── Solution02_SetOperations.java
    └── ...
```

## 🎯 Learning Goals

By working through these examples and tasks, you will:
- Understand the core collection interfaces: `List`, `Set`, `Map`, `Queue`, and `Deque`.
- Learn the characteristics and use cases for common implementations (`ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `HashMap`, `TreeMap`).
- Practice common operations: adding, removing, iterating, searching, and sorting elements.
- Use the Stream API for powerful data processing on collections.
- Understand generics and how they provide type safety.
- Learn about `equals()` and `hashCode()` and their importance in collections.

## 🚀 How to Use This Module

1.  **Study the Examples**:
    - Start in the `examples/` directory.
    - Each file (e.g., `C01_ArrayListExample.java`) is a self-contained demonstration of a specific collection class.
    - Compile and run them to see how they work:
      ```bash
      javac javaCollections/examples/C01_ArrayListExample.java
      java -cp javaCollections/.. javaCollections.examples.C01_ArrayListExample
      ```
      *(Note: You may need to adjust the classpath depending on your package structure)*

2.  **Attempt the Tasks**:
    - Move to the `tasks/` directory.
    - Open a task file (e.g., `Task01_ListManipulation.java`). It will contain a problem description and a place for you to write your code.
    - Implement your solution directly in the task file.

3.  **Compile and Test Your Solution**:
    - From the root `oop/` directory, compile and run your task file:
      ```bash
      javac javaCollections/tasks/Task01_ListManipulation.java
      java -cp javaCollections/.. javaCollections.tasks.Task01_ListManipulation
      ```

4.  **Review the Solutions**:
    - After you've solved the task (or if you get stuck), look at the corresponding file in the `solutions/` directory.
    - Compare your approach to the reference solution and read the comments to understand the key concepts.

## 📖 Learning Path

A recommended order for tackling the concepts:

1.  **Lists (`List`)**:
    - `ArrayList`: Fast access by index.
    - `LinkedList`: Fast insertions and deletions.
    - **Task**: Manipulating a shopping list.

2.  **Sets (`Set`)**:
    - `HashSet`: Unordered, unique elements. Fast.
    - `TreeSet`: Ordered, unique elements.
    - **Task**: Finding unique and common items between two groups.

3.  **Maps (`Map`)**:
    - `HashMap`: Unordered key-value pairs. Fast.
    - `TreeMap`: Sorted key-value pairs.
    - **Task**: Building a word counter or a student gradebook.

4.  **Queues & Deques (`Queue`, `Deque`)**:
    - `LinkedList` (as a Queue/Deque): First-In-First-Out (FIFO) or Last-In-First-Out (LIFO) structures.
    - `PriorityQueue`: Elements are ordered by priority.
    - **Task**: Simulating a ticketing queue.

5.  **Advanced Topics**:
    - **Streams API**: Functional-style operations on collections.
    - **Comparators & Comparables**: Custom sorting logic.
    - **`equals()` & `hashCode()`**: Correct behavior for custom objects in Sets and Maps.

---

**Happy Coding! ☕✨**

Start with the `examples/` directory to build a solid foundation, then challenge yourself with the `tasks/`.