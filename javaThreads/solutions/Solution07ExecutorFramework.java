/**
 * SOLUTION 7: Executor Framework
 * =================================
 * 
 * KEY TAKEAWAYS:
 * - Don't create raw threads in production — use Executors!
 * - Choose the right pool type for your workload
 * - ALWAYS shut down executors (otherwise JVM won't exit)
 * - Callable returns a value; Runnable does not
 */

package solutions;

import java.util.*;
import java.util.concurrent.*;

public class Solution07ExecutorFramework {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 7: Executor Framework");
        System.out.println("=".repeat(60));

        partA_FixedThreadPool();
        partB_CallableAndFuture();
        partC_PoolComparison();
        partD_ScheduledExecutor();
        partE_ProperShutdown();
        partF_CustomThreadFactory();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: FixedThreadPool
    // ═══════════════════════════════════════════════════════════════
    static void partA_FixedThreadPool() throws InterruptedException {
        System.out.println("\n=== Part A: FixedThreadPool ===\n");
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            executor.execute(() -> {  // Runnable — no return value
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] Task #" + taskId + " running");
                try { Thread.sleep(300); } catch (InterruptedException e) { return; }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("✓ All 10 tasks completed with only 3 threads");
        System.out.println("  → Threads are REUSED (notice repeated thread names)");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: Callable & Future
    // ═══════════════════════════════════════════════════════════════
    static void partB_CallableAndFuture() throws Exception {
        System.out.println("\n=== Part B: Callable & Future ===\n");
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Callable<V> — returns a value, can throw checked exceptions
        String[] products = {"Laptop", "Phone", "Tablet", "Watch", "Headphones"};
        List<Future<String>> futures = new ArrayList<>();

        for (String product : products) {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(500);  // Simulate price lookup
                int price = new Random().nextInt(500) + 100;
                return product + " → $" + price;
            });
            futures.add(future);
        }

        // Collect results (get() blocks until result is ready)
        System.out.println("Price lookup results:");
        for (Future<String> future : futures) {
            System.out.println("  " + future.get());  // Blocks if not done yet
        }

        // Demonstrate timeout
        Future<String> slowTask = executor.submit(() -> {
            Thread.sleep(5000);  // Very slow task
            return "done";
        });

        try {
            slowTask.get(1, TimeUnit.SECONDS);  // Timeout after 1s
        } catch (TimeoutException e) {
            System.out.println("\n⏰ TimeoutException: task didn't complete in 1 second");
            slowTask.cancel(true);  // Cancel the slow task
            System.out.println("   Task cancelled: " + slowTask.isCancelled());
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: Pool Comparison
    // ═══════════════════════════════════════════════════════════════
    static void partC_PoolComparison() throws InterruptedException {
        System.out.println("\n=== Part C: Pool Type Comparison ===\n");

        // CachedThreadPool — creates new threads as needed
        System.out.println("--- CachedThreadPool ---");
        ExecutorService cached = Executors.newCachedThreadPool();
        Set<String> cachedThreads = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 20; i++) {
            cached.execute(() -> {
                cachedThreads.add(Thread.currentThread().getName());
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            });
        }
        cached.shutdown();
        cached.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Threads created: " + cachedThreads.size() + " (up to 20!)");

        // FixedThreadPool — fixed number of threads
        System.out.println("\n--- FixedThreadPool(3) ---");
        ExecutorService fixed = Executors.newFixedThreadPool(3);
        Set<String> fixedThreads = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 20; i++) {
            fixed.execute(() -> {
                fixedThreads.add(Thread.currentThread().getName());
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            });
        }
        fixed.shutdown();
        fixed.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Threads created: " + fixedThreads.size() + " (always 3!)");

        System.out.println("\nWhen to use each:");
        System.out.println("  Fixed:   bounded, predictable — web server request handling");
        System.out.println("  Cached:  short-lived tasks, many but brief — event processing");
        System.out.println("  Single:  sequential execution, ordering guarantee — log writing");
        System.out.println("  Virtual: I/O-heavy, massive scale (Java 21+) — API gateways");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: ScheduledExecutorService
    // ═══════════════════════════════════════════════════════════════
    static void partD_ScheduledExecutor() throws InterruptedException {
        System.out.println("\n=== Part D: ScheduledExecutorService ===\n");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // One-shot delayed task
        scheduler.schedule(() -> {
            System.out.println("[Scheduled] One-shot task after 1s delay");
        }, 1, TimeUnit.SECONDS);

        // Fixed-rate repeating task
        ScheduledFuture<?> repeating = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("[Scheduled] Tick at " + System.currentTimeMillis() % 10000 + "ms");
        }, 0, 500, TimeUnit.MILLISECONDS);  // Every 500ms

        Thread.sleep(2500);  // Let it tick a few times
        repeating.cancel(false);  // Stop the repeating task

        System.out.println("\nscheduleAtFixedRate: next run = start + period (ignores duration)");
        System.out.println("scheduleWithFixedDelay: next run = end + delay (waits after completion)");

        scheduler.shutdown();
        scheduler.awaitTermination(3, TimeUnit.SECONDS);
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Proper Shutdown Pattern
    // ═══════════════════════════════════════════════════════════════
    static void partE_ProperShutdown() throws InterruptedException {
        System.out.println("\n=== Part E: Proper Shutdown Pattern ===\n");
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { return; }
            System.out.println("Task 1 done");
        });
        executor.execute(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { return; }
            System.out.println("Task 2 done");
        });

        // The recommended shutdown pattern:
        System.out.println("Calling shutdown()...");
        executor.shutdown();  // No new tasks accepted; existing tasks keep running

        System.out.println("Waiting for termination...");
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("Timed out! Calling shutdownNow()...");
            executor.shutdownNow();  // Interrupt running tasks
        }
        System.out.println("Executor terminated ✓");

        System.out.println("\nshutdown():     rejects new tasks, runs existing tasks to completion");
        System.out.println("shutdownNow():  rejects new + interrupts running tasks");
        System.out.println("awaitTermination(): blocks until all tasks finish or timeout");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part F: Custom ThreadFactory
    // ═══════════════════════════════════════════════════════════════
    static void partF_CustomThreadFactory() throws InterruptedException {
        System.out.println("\n=== Part F: Custom ThreadFactory ===\n");

        ThreadFactory factory = new ThreadFactory() {
            private int count = 0;

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "Worker-" + (++count));
                t.setDaemon(true);
                t.setUncaughtExceptionHandler((thread, ex) ->
                    System.out.println("[" + thread.getName() + "] UNCAUGHT: " + ex.getMessage())
                );
                return t;
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(3, factory);
        for (int i = 0; i < 5; i++) {
            executor.execute(() ->
                System.out.println("[" + Thread.currentThread().getName() + "] Running (daemon=" +
                        Thread.currentThread().isDaemon() + ")")
            );
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("✓ Custom names, daemon threads, exception handlers");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ ExecutorService: decouple task submission from execution");
        System.out.println("✓ Callable<V>: like Runnable but returns a value");
        System.out.println("✓ Future.get(): blocks until result is ready (or timeout)");
        System.out.println("✓ Choose pool type: Fixed, Cached, Single, Scheduled, Virtual");
        System.out.println("✓ ALWAYS shut down executors (shutdown + awaitTermination)");
        System.out.println("=".repeat(60));
    }
}
