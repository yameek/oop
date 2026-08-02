import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 1: JVM Memory Layout
 * =========================
 * Difficulty: Easy-Medium ⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. Heap Memory:
 *    - Young Generation: Eden Space, Survivor Spaces (S0 / S1)
 *    - Old Generation (Tenured Space)
 * 2. Non-Heap Memory:
 *    - Metaspace: Class metadata, constant pool, method definitions (replaced PermGen in Java 8)
 *    - Code Cache: JIT compiled native instructions
 * 3. Thread Stack:
 *    - Each thread has its own Stack containing Stack Frames (Local Variables, Operand Stack, Frame Data)
 * 4. TLAB (Thread-Local Allocation Buffer):
 *    - A region inside Eden assigned to a specific thread to avoid lock synchronization during object allocation.
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You are tasked with building a "JVM Memory Region Profiler" that inspects live memory pools
 * and simulates memory behavior across different regions:
 * 
 * Requirements:
 * 1. Programmatically inspect and print all active MemoryPoolMXBeans (Name, Type, Memory Used vs Committed vs Max).
 * 2. Simulate Stack Overflow: Write a recursive function `triggerStackOverflow(int depth)` to demonstrate
 *    how stack frames consume stack space until `java.lang.StackOverflowError` is caught.
 * 3. Simulate Heap Object Allocation: Gradually allocate byte arrays into a list, printing how heap usage increases
 *    and observing which memory pool (Eden / Old Gen) handles the allocation.
 * 4. Classify each memory pool into Young Gen, Old Gen, Metaspace, or Code Cache.
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Committed Memory: Amount of memory guaranteed to be available by the OS to the JVM.
 * - Used Memory: Amount of memory currently occupied by live objects/data.
 * - Max Memory: Upper bound limit specified by flags like -Xmx or -XX:MaxMetaspaceSize.
 * 
 * TO RUN:
 * javac Task01JVMMemoryLayout.java && java Task01JVMMemoryLayout
 */
public class Task01JVMMemoryLayout {

    public static void main(String[] args) {
        System.out.println("=== 1. Live Memory Pools Inspection ===");
        printMemoryPools();

        System.out.println("\n=== 2. Simulating Thread Stack Frames (StackOverflowError) ===");
        try {
            triggerStackOverflow(1);
        } catch (StackOverflowError e) {
            System.out.println("--> Successfully caught StackOverflowError! Stack depth limit reached.");
        }

        System.out.println("\n=== 3. Simulating Heap Allocation ===");
        simulateHeapAllocation();
    }

    /**
     * TODO: Implement printMemoryPools()
     * Iterate over ManagementFactory.getMemoryPoolMXBeans() and print:
     * - Pool Name
     * - Type (Heap vs Non-Heap)
     * - Used Memory (in MB)
     * - Committed Memory (in MB)
     * - Max Memory (in MB)
     */
    private static void printMemoryPools() {
        // TODO: Implement using ManagementFactory.getMemoryPoolMXBeans()
    }

    /**
     * TODO: Implement triggerStackOverflow(int depth)
     * Recursively call itself until StackOverflowError is thrown.
     * Print depth every 5,000 frames.
     */
    private static void triggerStackOverflow(int depth) {
        // TODO: Implement recursive call
    }

    /**
     * TODO: Implement simulateHeapAllocation()
     * Create a List<byte[]> and add 10MB chunks (10 * 1024 * 1024 bytes) in a loop (5 iterations).
     * Print total heap used after each allocation using ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().
     */
    private static void simulateHeapAllocation() {
        // TODO: Implement heap allocation simulation
    }
}
