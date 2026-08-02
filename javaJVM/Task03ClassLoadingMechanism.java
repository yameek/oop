import java.lang.reflect.Method;

/**
 * TASK 3: Class Loading Mechanism & Custom ClassLoader
 * ====================================================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. ClassLoader Hierarchy & Parent Delegation Model:
 *    - Bootstrap ClassLoader: Written in native C/C++, loads core runtime classes (java.base). Represented as `null` in Java.
 *    - Platform ClassLoader (formerly Extension): Loads platform module APIs.
 *    - System / Application ClassLoader: Loads application classes from classpath.
 *    - Parent Delegation: When requested to load a class, a ClassLoader delegates to its parent FIRST before attempting to load it itself.
 * 2. Class Loading Lifecycle:
 *    - Loading: Finding class binary data (bytecode bytes).
 *    - Linking:
 *      a. Verification: Ensures bytecode complies with JVM safety rules.
 *      b. Preparation: Allocates memory for static fields and initializes default values (0, null).
 *      c. Resolution: Replaces symbolic references in constant pool with direct memory pointers.
 *    - Initialization: Executes static initializer blocks `<clinit>` and assigns initial static values.
 * 3. Class.forName() vs ClassLoader.loadClass():
 *    - `Class.forName("Foo")`: Loads, links, AND initializes the class (triggers static blocks).
 *    - `classLoader.loadClass("Foo")`: Loads and links, but DOES NOT initialize the class until first instantiation/access.
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You will build a ClassLoader Inspector and implement a Custom Memory ClassLoader:
 * 
 * Requirements:
 * 1. Print the ClassLoader hierarchy for `String.class`, `java.sql.DriverManager.class` (if present) or `Task03ClassLoadingMechanism.class`.
 * 2. Demonstrate `Class.forName()` vs `ClassLoader.loadClass()` initialization behavior using a class with a static initializer.
 * 3. Implement a `CustomByteArrayClassLoader` extending `ClassLoader` that accepts raw class byte arrays and calls `defineClass()`.
 * 
 * TO RUN:
 * javac Task03ClassLoadingMechanism.java && java Task03ClassLoadingMechanism
 */
public class Task03ClassLoadingMechanism {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Inspecting ClassLoader Hierarchy ===");
        inspectClassLoaders();

        System.out.println("\n=== 2. Class.forName() vs ClassLoader.loadClass() ===");
        testClassInitialization();

        System.out.println("\n=== 3. Custom ClassLoader Implementation ===");
        testCustomClassLoader();
    }

    /**
     * Dummy class to observe static initialization execution
     */
    public static class SampleClass {
        static {
            System.out.println("  [Static Initializer] SampleClass has been INITIALIZED!");
        }
    }

    /**
     * TODO: Implement inspectClassLoaders()
     * Print ClassLoader of String.class (Bootstrap / null)
     * Print ClassLoader of Task03ClassLoadingMechanism.class (Application ClassLoader)
     * Traverse up parent chain using getParent() and print parent names.
     */
    private static void inspectClassLoaders() {
        // TODO: Implement hierarchy printing
    }

    /**
     * TODO: Implement testClassInitialization()
     * 1. Call ClassLoader.getSystemClassLoader().loadClass("Task03ClassLoadingMechanism$SampleClass")
     *    Notice that static block IS NOT executed yet!
     * 2. Call Class.forName("Task03ClassLoadingMechanism$SampleClass")
     *    Notice that static block IS executed now!
     */
    private static void testClassInitialization() throws Exception {
        // TODO: Implement comparison
    }

    /**
     * TODO: Implement CustomByteArrayClassLoader
     * Create a inner class `CustomMemoryClassLoader extends ClassLoader`:
     * Override `findClass(String name)` or add `public Class<?> loadFromBytes(String name, byte[] bytecode)`
     * using `defineClass(name, bytecode, 0, bytecode.length)`.
     */
    private static void testCustomClassLoader() throws Exception {
        // TODO: Implement custom classloader test
    }
}
