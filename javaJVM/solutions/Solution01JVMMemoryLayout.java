import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

/**
 * SOLUTION 1: JVM Memory Layout
 * =============================
 * Demonstrates live memory pool inspection, StackOverflow handling, and heap allocation tracking.
 */
public class Solution01JVMMemoryLayout {

    public static void main(String[] args) {
        System.out.println("=== 1. Live Memory Pools Inspection ===");
        printMemoryPools();

        System.out.println("\n=== 2. Simulating Thread Stack Frames (StackOverflowError) ===");
        try {
            triggerStackOverflow(1);
        } catch (StackOverflowError e) {
            System.out.println("--> Successfully caught StackOverflowError! Stack frame limit reached.");
        }

        System.out.println("\n=== 3. Simulating Heap Allocation ===");
        simulateHeapAllocation();
    }

    private static void printMemoryPools() {
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        
        System.out.printf("%-30s | %-10s | %-10s | %-10s | %-10s%n", 
                          "Pool Name", "Type", "Used (MB)", "Committed", "Max (MB)");
        System.out.println("----------------------------------------------------------------------------------");

        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage usage = pool.getUsage();
            long usedMB = usage.getUsed() / (1024 * 1024);
            long committedMB = usage.getCommitted() / (1024 * 1024);
            long maxMB = usage.getMax() > 0 ? usage.getMax() / (1024 * 1024) : -1;

            System.out.printf("%-30s | %-10s | %-10d | %-10d | %-10s%n",
                    pool.getName(),
                    pool.getType(),
                    usedMB,
                    committedMB,
                    maxMB == -1 ? "Unlimited" : String.valueOf(maxMB)
            );
        }
    }

    private static void triggerStackOverflow(int depth) {
        if (depth % 5000 == 0) {
            System.out.printf("Stack Frame Depth: %,d frames%n", depth);
        }
        triggerStackOverflow(depth + 1);
    }

    private static void simulateHeapAllocation() {
        List<byte[]> allocationTracker = new ArrayList<>();
        int chunkSize = 10 * 1024 * 1024; // 10 MB

        for (int i = 1; i <= 5; i++) {
            allocationTracker.add(new byte[chunkSize]);
            MemoryUsage heapUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            long usedMB = heapUsage.getUsed() / (1024 * 1024);
            long committedMB = heapUsage.getCommitted() / (1024 * 1024);

            System.out.printf("Allocated Chunk #%d (10 MB) -> Total Heap Used: %d MB | Committed: %d MB%n",
                    i, usedMB, committedMB);
        }
        
        // Help GC for next tasks
        allocationTracker.clear();
    }
}
