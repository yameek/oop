/**
 * SOLUTION 11: ForkJoinPool & Parallel Streams
 * ===============================================
 * 
 * KEY TAKEAWAYS:
 * - ForkJoinPool: divide-and-conquer parallelism
 * - RecursiveTask<V>: returns a value, RecursiveAction: no return
 * - Work-stealing: idle threads steal tasks from busy threads
 * - Parallel streams use the common ForkJoinPool by default
 * - Only parallelize CPU-bound work on LARGE data sets
 */

package solutions;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

// ─── Part A: RecursiveTask (Parallel Sum) ────────────────────────────────────
class ParallelSum extends RecursiveTask<Long> {
    private static final int THRESHOLD = 10_000;
    private final int[] array;
    private final int start;
    private final int end;

    public ParallelSum(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;

        // Base case: small enough to compute directly
        if (length <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }

        // Recursive case: split in half
        int mid = start + length / 2;
        ParallelSum leftTask = new ParallelSum(array, start, mid);
        ParallelSum rightTask = new ParallelSum(array, mid, end);

        leftTask.fork();          // Submit left to the pool (async)
        long rightResult = rightTask.compute();  // Compute right in THIS thread
        long leftResult = leftTask.join();       // Wait for left result

        return leftResult + rightResult;
    }
}

// ─── Part B: RecursiveAction (Parallel Array Fill) ───────────────────────────
class ParallelFill extends RecursiveAction {
    private static final int THRESHOLD = 10_000;
    private final int[] array;
    private final int start, end, value;

    public ParallelFill(int[] array, int start, int end, int value) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) array[i] = value;
            return;
        }
        int mid = start + (end - start) / 2;
        invokeAll(
            new ParallelFill(array, start, mid, value),
            new ParallelFill(array, mid, end, value)
        );
    }
}

public class Solution11ForkJoinAndParallelStreams {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 11: ForkJoinPool & Parallel Streams");
        System.out.println("=".repeat(60));

        partA_ParallelSum();
        partC_WorkStealing();
        partD_ParallelStreams();
        partE_CustomPoolForStreams();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: RecursiveTask — Parallel Sum
    // ═══════════════════════════════════════════════════════════════
    static void partA_ParallelSum() {
        System.out.println("\n=== Part A: Parallel Sum (RecursiveTask) ===\n");

        int size = 10_000_000;
        int[] array = new int[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) array[i] = random.nextInt(100);

        // Sequential sum
        long start = System.currentTimeMillis();
        long seqSum = 0;
        for (int val : array) seqSum += val;
        long seqTime = System.currentTimeMillis() - start;
        System.out.println("Sequential:     sum=" + seqSum + " in " + seqTime + "ms");

        // ForkJoin sum
        ForkJoinPool pool = new ForkJoinPool();
        start = System.currentTimeMillis();
        long fjSum = pool.invoke(new ParallelSum(array, 0, array.length));
        long fjTime = System.currentTimeMillis() - start;
        System.out.println("ForkJoin:       sum=" + fjSum + " in " + fjTime + "ms");

        // Parallel stream sum
        start = System.currentTimeMillis();
        long psSum = Arrays.stream(array).parallel().asLongStream().sum();
        long psTime = System.currentTimeMillis() - start;
        System.out.println("Parallel stream: sum=" + psSum + " in " + psTime + "ms");

        System.out.println("\nAll sums match: " + (seqSum == fjSum && fjSum == psSum ? "✓" : "✗"));
        pool.shutdown();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: Work-Stealing Algorithm
    // ═══════════════════════════════════════════════════════════════
    static void partC_WorkStealing() {
        System.out.println("\n=== Part C: Work-Stealing Algorithm ===\n");

        ForkJoinPool pool = new ForkJoinPool();

        // Run a task to generate some stats
        int[] bigArray = new int[1_000_000];
        pool.invoke(new ParallelSum(bigArray, 0, bigArray.length));

        System.out.println("ForkJoinPool stats:");
        System.out.println("  Parallelism:  " + pool.getParallelism());
        System.out.println("  Pool size:    " + pool.getPoolSize());
        System.out.println("  Steal count:  " + pool.getStealCount());

        System.out.println("\nHow work-stealing works:");
        System.out.println("  1. Each worker thread has its own DEQUE (double-ended queue)");
        System.out.println("  2. Thread pushes/pops tasks from its OWN deque (LIFO — cache-friendly)");
        System.out.println("  3. Idle threads STEAL from BOTTOM of another thread's deque (FIFO)");
        System.out.println("  4. This keeps all CPU cores busy without central coordination");
        System.out.println("  5. Stealing is FIFO = steals LARGE tasks (most work), leaving small ones");

        pool.shutdown();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: Parallel Streams
    // ═══════════════════════════════════════════════════════════════
    static void partD_ParallelStreams() {
        System.out.println("\n=== Part D: Parallel Streams ===\n");

        // Large data set — parallel is FASTER
        List<Integer> largeList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) largeList.add(i);

        long start = System.currentTimeMillis();
        long seqResult = largeList.stream()
            .filter(n -> n % 2 == 0)
            .mapToLong(n -> (long) n * n)
            .sum();
        long seqTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        long parResult = largeList.parallelStream()
            .filter(n -> n % 2 == 0)
            .mapToLong(n -> (long) n * n)
            .sum();
        long parTime = System.currentTimeMillis() - start;

        System.out.println("Large data (1M elements):");
        System.out.println("  Sequential: " + seqTime + "ms (sum=" + seqResult + ")");
        System.out.println("  Parallel:   " + parTime + "ms (sum=" + parResult + ")");

        // Small data set — parallel is SLOWER (overhead!)
        List<Integer> smallList = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        start = System.nanoTime();
        smallList.stream().mapToInt(n -> n * n).sum();
        long seqNano = System.nanoTime() - start;

        start = System.nanoTime();
        smallList.parallelStream().mapToInt(n -> n * n).sum();
        long parNano = System.nanoTime() - start;

        System.out.println("\nSmall data (10 elements):");
        System.out.println("  Sequential: " + seqNano / 1000 + "µs");
        System.out.println("  Parallel:   " + parNano / 1000 + "µs (overhead > benefit!)");

        System.out.println("\n⚠️ When parallel streams HURT:");
        System.out.println("  - Small data sets (thread management overhead > computation)");
        System.out.println("  - I/O operations (threads block, pool starves)");
        System.out.println("  - LinkedList (poor spatial locality for splitting)");
        System.out.println("  - Order-dependent operations (ordering overhead)");
        System.out.println("  - Shared mutable state (race conditions!)");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Custom ForkJoinPool for parallel streams
    // ═══════════════════════════════════════════════════════════════
    static void partE_CustomPoolForStreams() throws Exception {
        System.out.println("\n=== Part E: Custom Pool for Parallel Streams ===\n");

        List<Integer> data = List.of(1, 2, 3, 4, 5);

        // Default: uses ForkJoinPool.commonPool (shared across JVM!)
        System.out.println("Default pool (common):");
        data.parallelStream().forEach(n ->
            System.out.println("  [" + Thread.currentThread().getName() + "] " + n)
        );

        // Custom pool: isolate your parallel stream
        System.out.println("\nCustom pool (isolated):");
        ForkJoinPool customPool = new ForkJoinPool(2);  // Only 2 threads
        customPool.submit(() ->
            data.parallelStream().forEach(n ->
                System.out.println("  [" + Thread.currentThread().getName() + "] " + n)
            )
        ).get();
        customPool.shutdown();

        System.out.println("\n⚠️ Why use a custom pool?");
        System.out.println("  Common pool is shared across the ENTIRE JVM");
        System.out.println("  One slow parallel stream blocks ALL other parallel streams!");
        System.out.println("  Custom pool isolates your workload");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ ForkJoinPool: divide-and-conquer parallelism");
        System.out.println("✓ RecursiveTask<V>: returns value, RecursiveAction: void");
        System.out.println("✓ fork() → compute() → join() pattern");
        System.out.println("✓ Work-stealing: idle threads steal from busy threads' deques");
        System.out.println("✓ parallelStream(): syntactic sugar over ForkJoinPool");
        System.out.println("✓ Only parallelize CPU-bound work on LARGE data sets");
        System.out.println("=".repeat(60));
    }
}
