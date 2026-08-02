/**
 * TASK 11: ForkJoinPool & Parallel Streams
 * ==========================================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Learn about: ForkJoinPool, RecursiveTask, RecursiveAction,
 *              work-stealing algorithm, parallel streams, common pool
 * 
 * PROBLEM:
 * --------
 * Build divide-and-conquer solutions and compare with parallel streams.
 * 
 * Part A — RecursiveTask (Parallel Sum):
 * 1. Create a class ParallelSum extends RecursiveTask<Long>
 * 2. Given an int[] array, compute the sum:
 *    - If array segment <= THRESHOLD (e.g., 1000): compute directly
 *    - Otherwise: split in half, fork left, compute right, join left
 * 3. Test with an array of 10_000_000 elements
 * 4. Compare execution time:
 *    - Sequential: Arrays.stream(arr).sum()
 *    - ForkJoin: pool.invoke(new ParallelSum(arr, 0, arr.length))
 *    - Parallel stream: Arrays.stream(arr).parallel().sum()
 * 5. Print times for all three approaches
 * 
 * Part B — RecursiveAction (Parallel Sort):
 * 1. Create a class ParallelMergeSort extends RecursiveAction
 * 2. Sort an array using fork/join:
 *    - If size <= THRESHOLD: Arrays.sort() (use built-in for small arrays)
 *    - Otherwise: split, fork left, compute right, join, merge
 * 3. Compare with Arrays.sort() and Arrays.parallelSort()
 * 
 * Part C — Work-Stealing Algorithm:
 * 1. In comments, explain how work-stealing works:
 *    - Each thread has its own deque (double-ended queue)
 *    - Thread pushes/pops tasks from its OWN deque (LIFO — cache-friendly)
 *    - Idle threads STEAL from the BOTTOM of another thread's deque (FIFO)
 *    - This keeps all threads busy without central coordination
 * 2. Print ForkJoinPool stats:
 *    - pool.getPoolSize()
 *    - pool.getActiveThreadCount()
 *    - pool.getStealCount()
 * 
 * Part D — Parallel Streams:
 * 1. Process a list of 1_000_000 strings (simulating file paths)
 * 2. Sequential: list.stream().filter(...).map(...).collect(...)
 * 3. Parallel: list.parallelStream().filter(...).map(...).collect(...)
 * 4. Compare times
 * 5. IMPORTANT — when parallel streams HURT:
 *    - Small data sets (overhead > benefit)
 *    - I/O operations (threads block, pool starves)
 *    - Linked structures (poor spatial locality)
 *    - Order-dependent operations (ordering overhead)
 * 6. Demonstrate: parallel stream with a small list is SLOWER
 * 
 * Part E — Custom ForkJoinPool for parallel streams:
 * 1. By default, parallelStream() uses the common ForkJoinPool
 * 2. This is shared across the entire JVM — one slow stream blocks others!
 * 3. Create a custom pool to isolate:
 *    ForkJoinPool custom = new ForkJoinPool(4);
 *    custom.submit(() -> list.parallelStream().forEach(...)).get();
 * 4. Print thread names to prove custom pool is used
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Fork/Join: divide-and-conquer parallelism
 * - RecursiveTask<V>: returns a value (fork + join)
 * - RecursiveAction: no return value (fork + join)
 * - Work-stealing: idle threads steal tasks from busy threads' queues
 * - Common pool: shared ForkJoinPool, parallelism = Runtime.availableProcessors() - 1
 * - Parallel streams: syntactic sugar over ForkJoinPool
 * - Rule of thumb: only parallelize CPU-bound work on large data sets
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Parallel Sum ===
 * Sequential sum:  500000000000 in 35ms
 * ForkJoin sum:    500000000000 in 12ms (2.9x faster)
 * Parallel stream: 500000000000 in 11ms (3.2x faster)
 * 
 * === Part C: Work-Stealing Stats ===
 * Pool size: 8, Active: 4, Steals: 127
 * 
 * === Part D: Parallel Streams ===
 * Sequential: 450ms | Parallel: 120ms (large data ✓)
 * Sequential: 0.5ms | Parallel: 2.1ms (small data ✗ — overhead!)
 */

// Write your solution below:

