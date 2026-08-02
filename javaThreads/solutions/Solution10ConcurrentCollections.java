/**
 * SOLUTION 10: Concurrent Collections
 * ======================================
 * 
 * KEY TAKEAWAYS:
 * - ConcurrentHashMap: thread-safe map, CAS on buckets (not whole map)
 * - CopyOnWriteArrayList: snapshot-on-write, great for read-heavy workloads
 * - BlockingQueue: the backbone of producer-consumer
 * - ThreadLocal: per-thread isolated state (but beware of thread pool leaks!)
 */

package solutions;

import java.util.*;
import java.util.concurrent.*;
import java.text.SimpleDateFormat;

public class Solution10ConcurrentCollections {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 10: Concurrent Collections");
        System.out.println("=".repeat(60));

        partA_ConcurrentHashMap();
        partB_CopyOnWriteArrayList();
        partC_BlockingQueue();
        partE_ThreadLocal();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: ConcurrentHashMap
    // ═══════════════════════════════════════════════════════════════
    static void partA_ConcurrentHashMap() throws InterruptedException {
        System.out.println("\n=== Part A: ConcurrentHashMap (Word Counter) ===\n");

        ConcurrentHashMap<String, Integer> wordCount = new ConcurrentHashMap<>();
        String[] words = {"java", "thread", "concurrent", "java", "thread", "java",
                          "lock", "atomic", "java", "concurrent"};

        // 4 threads counting words simultaneously
        Thread[] threads = new Thread[4];
        for (int i = 0; i < 4; i++) {
            threads[i] = new Thread(() -> {
                for (String word : words) {
                    // merge() is ATOMIC — no race conditions!
                    wordCount.merge(word, 1, Integer::sum);
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        System.out.println("Word frequencies: " + wordCount);

        // Other atomic operations
        ConcurrentHashMap<String, Integer> demo = new ConcurrentHashMap<>();
        demo.putIfAbsent("key", 10);         // Only insert if missing
        demo.compute("key", (k, v) -> v + 5); // Atomic compute
        System.out.println("putIfAbsent + compute: key=" + demo.get("key") + " (expected 15)");

        // How it works internally
        System.out.println("\nHow ConcurrentHashMap works:");
        System.out.println("  Java 7: Segment locking (16 segments, each with own lock)");
        System.out.println("  Java 8+: CAS on bucket head nodes + synchronized per bucket");
        System.out.println("  → Multiple threads can write to DIFFERENT buckets simultaneously!");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: CopyOnWriteArrayList
    // ═══════════════════════════════════════════════════════════════
    static void partB_CopyOnWriteArrayList() throws InterruptedException {
        System.out.println("\n=== Part B: CopyOnWriteArrayList ===\n");

        CopyOnWriteArrayList<String> listeners = new CopyOnWriteArrayList<>();
        listeners.add("Listener-1");
        listeners.add("Listener-2");

        // Reading while modifying — NO ConcurrentModificationException!
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                // Iterator uses a SNAPSHOT — safe even during modification
                for (String listener : listeners) {
                    System.out.println("[Reader] Notifying: " + listener);
                }
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
            }
        });

        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(150);
                listeners.add("Listener-3");
                System.out.println("[Writer] Added Listener-3");
                Thread.sleep(200);
                listeners.remove("Listener-1");
                System.out.println("[Writer] Removed Listener-1");
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        reader.start(); writer.start();
        reader.join(); writer.join();

        System.out.println("✓ No ConcurrentModificationException!");
        System.out.println("Trade-off: fast reads, SLOW writes (copies entire array on each write)");
        System.out.println("Best for: event listeners, config lists (few writes, many reads)");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: BlockingQueue (Log Processing Pipeline)
    // ═══════════════════════════════════════════════════════════════
    static void partC_BlockingQueue() throws InterruptedException {
        System.out.println("\n=== Part C: BlockingQueue (Producer-Consumer) ===\n");

        // ArrayBlockingQueue: bounded, FIFO
        BlockingQueue<String> logQueue = new ArrayBlockingQueue<>(5);

        // Producer — generates log entries
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 8; i++) {
                    String log = "LogEntry-" + i;
                    logQueue.put(log);  // BLOCKS if queue is full
                    System.out.println("[Producer] put: " + log + " (size: " + logQueue.size() + ")");
                    Thread.sleep(100);
                }
                logQueue.put("POISON_PILL");  // Signal to stop
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Producer");

        // Consumer — processes log entries
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String log = logQueue.take();  // BLOCKS if queue is empty
                    if (log.equals("POISON_PILL")) break;  // Shutdown signal
                    System.out.println("[Consumer] took: " + log);
                    Thread.sleep(200);  // Processing takes longer than producing
                }
                System.out.println("[Consumer] Received POISON_PILL — shutting down");
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Consumer");

        producer.start(); consumer.start();
        producer.join(); consumer.join();

        System.out.println("\nBlockingQueue types:");
        System.out.println("  ArrayBlockingQueue:    bounded, FIFO, fair option");
        System.out.println("  LinkedBlockingQueue:   optionally bounded, FIFO");
        System.out.println("  PriorityBlockingQueue: unbounded, priority ordering");
        System.out.println("  SynchronousQueue:      0 capacity, direct hand-off");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: ThreadLocal
    // ═══════════════════════════════════════════════════════════════
    static void partE_ThreadLocal() throws InterruptedException {
        System.out.println("\n=== Part E: ThreadLocal ===\n");

        // SimpleDateFormat is NOT thread-safe!
        // ThreadLocal gives each thread its OWN instance
        ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        );

        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new Thread(() -> {
                try {
                    // Each thread gets its OWN SimpleDateFormat
                    String formatted = dateFormat.get().format(new java.util.Date());
                    System.out.println("[" + Thread.currentThread().getName() + "] " + formatted);
                } finally {
                    dateFormat.remove();  // CRITICAL: prevent memory leaks!
                    // Without remove(): if using thread pool, thread persists → 
                    // ThreadLocal value sticks around → memory leak!
                }
            }, "Thread-" + i);
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        System.out.println("\n⚠️ ThreadLocal + Thread Pools = DANGER:");
        System.out.println("  Thread pools REUSE threads → ThreadLocal values persist");
        System.out.println("  Must call remove() in finally to avoid stale data & memory leaks");
        System.out.println("  → Java 25: Use ScopedValue instead (automatic cleanup, see Task 12)");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ ConcurrentHashMap: lock-free reads, CAS writes per bucket");
        System.out.println("✓ CopyOnWriteArrayList: snapshot iteration, slow writes");
        System.out.println("✓ BlockingQueue: put() blocks if full, take() blocks if empty");
        System.out.println("✓ ThreadLocal: per-thread isolated state (ALWAYS call remove()!)");
        System.out.println("✓ ScopedValue (Java 25): better ThreadLocal for virtual threads");
        System.out.println("=".repeat(60));
    }
}
