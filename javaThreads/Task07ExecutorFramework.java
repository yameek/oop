/**
 * TASK 7: Executor Framework
 * ============================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Learn about: ExecutorService, FixedThreadPool, CachedThreadPool,
 *              SingleThreadExecutor, ScheduledExecutor, Callable vs Runnable,
 *              Future, proper shutdown patterns, ThreadFactory
 * 
 * PROBLEM:
 * --------
 * Build a "Task Processing Engine" using different executor types.
 * 
 * Part A — FixedThreadPool:
 * 1. Create a FixedThreadPool with 3 threads
 * 2. Submit 10 Runnable tasks (each prints "[pool-thread-N] Task #X running")
 * 3. Observe: only 3 tasks run at a time, others wait in queue
 * 4. Properly shut down the executor
 * 
 * Part B — Callable & Future:
 * 5. Create a Callable<Integer> that simulates a price lookup:
 *    - Takes a product name, sleeps 1 second, returns a random price
 * 6. Submit 5 Callables to a FixedThreadPool(3)
 * 7. Collect results using Future.get() — print each result
 * 8. Demonstrate Future.get(timeout) — what happens on timeout?
 * 9. Demonstrate Future.cancel() and Future.isCancelled()
 * 
 * Part C — CachedThreadPool vs FixedThreadPool:
 * 10. Submit 20 tasks to CachedThreadPool — observe many threads created
 * 11. Submit 20 tasks to FixedThreadPool(3) — observe only 3 threads
 * 12. Print the thread names to show the difference
 * 13. Explain when to use each:
 *     - Fixed: bounded, predictable resource usage
 *     - Cached: short-lived tasks, many but brief
 *     - Single: sequential task execution, ordering guarantee
 * 
 * Part D — ScheduledExecutorService:
 * 14. Schedule a task to run after 2 seconds (one-shot delay)
 * 15. Schedule a task to repeat every 1 second (fixed rate)
 * 16. Run for 5 seconds, then shut down
 * 17. Explain scheduleAtFixedRate vs scheduleWithFixedDelay
 * 
 * Part E — Proper Shutdown Pattern:
 * 18. Implement the recommended shutdown pattern:
 *     executor.shutdown();
 *     if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
 *         executor.shutdownNow();
 *     }
 * 19. Explain shutdown() vs shutdownNow()
 * 
 * Part F — Custom ThreadFactory:
 * 20. Create a custom ThreadFactory that:
 *     - Names threads "Worker-1", "Worker-2", etc.
 *     - Sets threads as daemon
 *     - Sets a custom UncaughtExceptionHandler
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Don't create raw threads in production — use Executors!
 * - ExecutorService decouples task submission from execution
 * - Runnable: no return value, no checked exceptions
 * - Callable<V>: returns a value, can throw checked exceptions
 * - Future<V>: handle to a pending result; get() blocks until done
 * - Thread pool types:
 *   | Type          | Threads      | Queue      | Use Case                |
 *   |---------------|-------------|------------|-------------------------|
 *   | Fixed         | N (fixed)    | Unbounded  | Predictable load        |
 *   | Cached        | 0 → ∞       | SynchronousQueue | Many short tasks  |
 *   | Single        | 1            | Unbounded  | Sequential execution    |
 *   | Scheduled     | N (core)     | DelayQueue | Timed/periodic tasks    |
 *   | Virtual (21+) | Unlimited    | —          | I/O-heavy, massive scale|
 * - ALWAYS shut down executors — otherwise JVM won't exit!
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: FixedThreadPool ===
 * [pool-1-thread-1] Task #1 running
 * [pool-1-thread-2] Task #2 running
 * [pool-1-thread-3] Task #3 running
 * [pool-1-thread-1] Task #4 running (thread-1 reused!)
 * ...
 * 
 * === Part B: Callable & Future ===
 * Product: Laptop → Price: $1234
 * Product: Phone → Price: $899
 * ...
 * 
 * === Part D: ScheduledExecutor ===
 * [Scheduled] One-shot task after 2s
 * [Scheduled] Repeating: tick #1
 * [Scheduled] Repeating: tick #2
 * ...
 */

// Write your solution below:

