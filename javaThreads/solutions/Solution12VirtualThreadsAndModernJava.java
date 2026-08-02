/**
 * SOLUTION 12: Virtual Threads & Modern Java (Java 25 LTS)
 * ==========================================================
 * 
 * REQUIRES: Java 25+ (LTS)
 * 
 * KEY TAKEAWAYS:
 * - Virtual threads: lightweight threads managed by JVM, not OS
 * - 1M virtual threads ≈ 1GB RAM (vs 1TB for platform threads!)
 * - StructuredTaskScope: parent-child thread relationship with auto-cleanup
 * - ScopedValue: immutable, scope-bound context (replaces ThreadLocal)
 * - Gatherers.mapConcurrent: concurrent stream processing with backpressure
 * 
 * ⚠️ NOTE: Some features may need --enable-preview depending on your exact
 *    Java 25 build. Virtual threads are fully finalized since Java 21.
 */

package solutions;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.time.Duration;

public class Solution12VirtualThreadsAndModernJava {

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 12: Virtual Threads & Modern Java (Java 25 LTS)");
        System.out.println("=".repeat(60));
        System.out.println("Java version: " + System.getProperty("java.version"));

        partA_VirtualThreadBasics();
        partB_PlatformVsVirtual();
        partC_PinningDemo();
        partD_StructuredConcurrency();
        partE_ScopedValues();
        partF_StreamGatherers();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: Virtual Thread Basics
    // ═══════════════════════════════════════════════════════════════
    static void partA_VirtualThreadBasics() throws Exception {
        System.out.println("\n=== Part A: Virtual Thread Basics ===\n");

        // Method 1: Thread.startVirtualThread()
        Thread vt1 = Thread.startVirtualThread(() -> {
            System.out.println("[Method-1] isVirtual: " + Thread.currentThread().isVirtual());
        });
        vt1.join();

        // Method 2: Thread.ofVirtual().name().start()
        Thread vt2 = Thread.ofVirtual()
            .name("my-virtual-", 0)  // Named virtual thread (with counter)
            .start(() -> {
                System.out.println("[Method-2] name: " + Thread.currentThread().getName() +
                        ", isVirtual: " + Thread.currentThread().isVirtual());
            });
        vt2.join();

        // Method 3: newVirtualThreadPerTaskExecutor (PREFERRED for many tasks)
        System.out.println("\n--- Creating 10,000 virtual threads ---");
        long start = System.currentTimeMillis();
        
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));  // Simulate I/O
                    return null;
                });
            }
        }  // Auto-shutdown: waits for all tasks to complete

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("10,000 virtual threads (each sleeping 1s) completed in " + elapsed + "ms ✓");
        System.out.println("  → If platform threads: would need 10,000 OS threads (~10GB RAM)!");
        System.out.println("  → Virtual threads: ~10MB total, multiplexed onto few carrier threads");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: Platform vs Virtual Thread Comparison
    // ═══════════════════════════════════════════════════════════════
    static void partB_PlatformVsVirtual() throws Exception {
        System.out.println("\n=== Part B: Platform vs Virtual Thread Comparison ===\n");

        int numTasks = 1000;
        Runnable ioTask = () -> {
            try { Thread.sleep(100); } catch (InterruptedException e) { }
        };

        // Platform threads (FixedThreadPool of 100)
        long start = System.currentTimeMillis();
        try (var executor = Executors.newFixedThreadPool(100)) {
            for (int i = 0; i < numTasks; i++) executor.submit(ioTask);
        }
        long platformTime = System.currentTimeMillis() - start;

        // Virtual threads (unlimited)
        start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < numTasks; i++) executor.submit(ioTask);
        }
        long virtualTime = System.currentTimeMillis() - start;

        System.out.println(numTasks + " I/O tasks (each 100ms sleep):");
        System.out.println("  Platform (100 threads): " + platformTime + "ms");
        System.out.println("  Virtual (unlimited):    " + virtualTime + "ms");
        
        System.out.println("\n📌 When to use which:");
        System.out.println("  Virtual:  I/O-bound (HTTP, DB, file) — use for massive concurrency");
        System.out.println("  Platform: CPU-bound (math, compression) — virtual threads don't help here");
        System.out.println("  ⚠️ Virtual threads do NOT make CPU-bound code faster!");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: Pinning Demo
    // ═══════════════════════════════════════════════════════════════
    static void partC_PinningDemo() throws Exception {
        System.out.println("\n=== Part C: Virtual Thread Pinning ===\n");

        System.out.println("PINNING = virtual thread stuck on its carrier (platform) thread");
        System.out.println("This happens when:");
        System.out.println("  1. Inside a synchronized block/method (intrinsic lock)");
        System.out.println("  2. During native method calls");
        System.out.println();

        // BAD: synchronized pins the virtual thread to carrier
        Object syncLock = new Object();
        System.out.println("❌ BAD (synchronized — pins virtual thread):");
        System.out.println("   synchronized (lock) { Thread.sleep(1000); }");
        System.out.println("   → Virtual thread is PINNED to carrier — carrier can't run other VTs!");
        System.out.println();

        // GOOD: ReentrantLock does NOT pin
        ReentrantLock reentrantLock = new ReentrantLock();
        System.out.println("✅ GOOD (ReentrantLock — no pinning):");
        System.out.println("   lock.lock();");
        System.out.println("   try { Thread.sleep(1000); }");
        System.out.println("   finally { lock.unlock(); }");
        System.out.println("   → Virtual thread unmounts from carrier during sleep — carrier is FREE!");
        System.out.println();

        // Demo: ReentrantLock with virtual threads
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    reentrantLock.lock();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) { }
                    finally {
                        reentrantLock.unlock();
                    }
                });
            }
        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("100 VTs with ReentrantLock: " + elapsed + "ms");
        System.out.println("\n💡 Rule: NEVER use synchronized for I/O in virtual threads → use ReentrantLock");
        System.out.println("   Detect pinning: run with -Djdk.tracePinnedThreads=short");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: Structured Concurrency
    // ═══════════════════════════════════════════════════════════════
    static void partD_StructuredConcurrency() throws Exception {
        System.out.println("\n=== Part D: Structured Concurrency (StructuredTaskScope) ===\n");

        System.out.println("StructuredTaskScope: finalized in Java 25 LTS");
        System.out.println("  → Parent-child thread relationship with automatic cleanup");
        System.out.println("  → If parent scope closes, all child tasks are cancelled");
        System.out.println("  → Thread dumps show the parent-child hierarchy!\n");

        // ShutdownOnFailure: cancel ALL if ANY subtask fails
        System.out.println("--- ShutdownOnFailure ---");
        System.out.println("Pattern:");
        System.out.println("  try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {");
        System.out.println("      Subtask<String> user  = scope.fork(() -> fetchUser(id));");
        System.out.println("      Subtask<String> order = scope.fork(() -> fetchOrder(id));");
        System.out.println("      scope.join();           // Wait for both");
        System.out.println("      scope.throwIfFailed();  // Propagate any exception");
        System.out.println("      return user.get() + order.get();");
        System.out.println("  }");
        System.out.println("  → If fetchOrder() fails, fetchUser() is automatically CANCELLED\n");

        // ShutdownOnSuccess: return first successful result
        System.out.println("--- ShutdownOnSuccess ---");
        System.out.println("Pattern:");
        System.out.println("  try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {");
        System.out.println("      scope.fork(() -> fetchFromPrimary());   // Try source 1");
        System.out.println("      scope.fork(() -> fetchFromFallback());  // Try source 2");
        System.out.println("      scope.join();");
        System.out.println("      return scope.result();  // First success wins!");
        System.out.println("  }\n");

        System.out.println("Benefits over CompletableFuture:");
        System.out.println("  ✓ No leaked threads (scope auto-cancels on close)");
        System.out.println("  ✓ Cancellation is automatic (no manual Future.cancel())");
        System.out.println("  ✓ Thread dumps show parent → child relationships");
        System.out.println("  ✓ Easier to reason about (structured = scoped)");

        // Demonstrate with a simple simulation
        System.out.println("\n--- Simulated Demo ---");
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> user = executor.submit(() -> { Thread.sleep(200); return "User-Alice"; });
            Future<String> order = executor.submit(() -> { Thread.sleep(300); return "Order-101"; });
            System.out.println("Fetched: " + user.get() + " + " + order.get());
            System.out.println("(In real code, use StructuredTaskScope for automatic cancellation)");
        }
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Scoped Values
    // ═══════════════════════════════════════════════════════════════
    static void partE_ScopedValues() {
        System.out.println("\n=== Part E: Scoped Values (ScopedValue) ===\n");

        System.out.println("ScopedValue: finalized in Java 25 LTS");
        System.out.println("  → Modern replacement for ThreadLocal (especially with virtual threads)\n");

        System.out.println("Usage:");
        System.out.println("  private static final ScopedValue<String> USER = ScopedValue.newInstance();");
        System.out.println();
        System.out.println("  // Bind and run (immutable within scope):");
        System.out.println("  ScopedValue.where(USER, \"alice\").run(() -> {");
        System.out.println("      System.out.println(USER.get());  // \"alice\"");
        System.out.println("      handleRequest();  // All nested calls see \"alice\"");
        System.out.println("  });");
        System.out.println();
        System.out.println("  // With return value:");
        System.out.println("  String result = ScopedValue.where(USER, \"bob\").call(() -> {");
        System.out.println("      return processFor(USER.get());");
        System.out.println("  });\n");

        System.out.println("ScopedValue vs ThreadLocal:");
        System.out.println("  | Feature                | ThreadLocal      | ScopedValue          |");
        System.out.println("  |------------------------|------------------|----------------------|");
        System.out.println("  | Mutability             | Mutable anywhere | Immutable in scope   |");
        System.out.println("  | Cleanup                | Manual remove()  | Automatic (scoped)   |");
        System.out.println("  | Memory with VTs        | Wasteful (per VT)| Efficient (shared)   |");
        System.out.println("  | Thread pool safe?      | No (leaks!)      | Yes (always scoped)  |");
        System.out.println("  | Child thread inherit?  | InheritableOnly  | Yes (structured)     |");
        System.out.println();
        System.out.println("⚠️ Why ThreadLocal is BAD with virtual threads:");
        System.out.println("  1. You might create 1M virtual threads → 1M ThreadLocal copies");
        System.out.println("  2. ThreadLocal is mutable → hard to reason about in async code");
        System.out.println("  3. No automatic cleanup → memory leaks in thread pools");
        System.out.println("  → ScopedValue fixes ALL of these issues");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part F: Stream Gatherers
    // ═══════════════════════════════════════════════════════════════
    static void partF_StreamGatherers() {
        System.out.println("\n=== Part F: Stream Gatherers (Gatherers.mapConcurrent) ===\n");

        System.out.println("Gatherers.mapConcurrent: finalized in Java 25 LTS");
        System.out.println("  → Process stream elements concurrently with backpressure\n");

        System.out.println("Usage:");
        System.out.println("  import java.util.stream.Gatherers;");
        System.out.println();
        System.out.println("  List<String> results = urls.stream()");
        System.out.println("      .gather(Gatherers.mapConcurrent(10, url -> fetch(url)))");
        System.out.println("      .toList();");
        System.out.println();
        System.out.println("  → 10 = max concurrent virtual threads");
        System.out.println("  → Built-in backpressure (won't spawn more than 10 at a time)");
        System.out.println("  → Uses virtual threads under the hood\n");

        // Simulated demo (without actual URLs)
        System.out.println("--- Simulated comparison ---");
        List<String> urls = List.of("url1", "url2", "url3", "url4", "url5",
                                     "url6", "url7", "url8", "url9", "url10");

        // Sequential simulation
        long start = System.currentTimeMillis();
        List<String> seqResults = urls.stream()
            .map(url -> { sleep(100); return url + "-fetched"; })
            .toList();
        long seqTime = System.currentTimeMillis() - start;

        System.out.println("Sequential: " + urls.size() + " URLs in " + seqTime + "ms");

        // With Gatherers.mapConcurrent (simulated):
        System.out.println("mapConcurrent(10): ~" + (seqTime / 10) + "ms (10x faster!)");
        System.out.println();

        System.out.println("Advantages over CompletableFuture.allOf():");
        System.out.println("  ✓ Concise — one line instead of Future[] + allOf + join");
        System.out.println("  ✓ Built-in backpressure (maxConcurrency parameter)");
        System.out.println("  ✓ Integrates with Stream API (filter, map, collect)");
        System.out.println("  ✓ Uses virtual threads automatically");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS — Java 25 LTS Concurrency");
        System.out.println("=".repeat(60));
        System.out.println("✓ Virtual threads: lightweight (2KB stack), JVM-managed, 1M+ possible");
        System.out.println("✓ Use for I/O-bound work; platform threads for CPU-bound work");
        System.out.println("✓ Avoid synchronized in VTs → use ReentrantLock (prevents pinning)");
        System.out.println("✓ StructuredTaskScope: auto-cancel child tasks on failure/success");
        System.out.println("✓ ScopedValue: immutable, scoped, replaces ThreadLocal for VTs");
        System.out.println("✓ Gatherers.mapConcurrent: concurrent streams with backpressure");
        System.out.println("=".repeat(60));
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * VIRTUAL THREADS — HOW THEY WORK:
 * 1. Virtual thread is a Java object (~2KB), NOT an OS thread (~1MB)
 * 2. JVM maintains a pool of "carrier" threads (= platform threads)
 * 3. Virtual thread is MOUNTED onto a carrier to run
 * 4. When VT blocks (I/O, sleep, lock), it's UNMOUNTED → carrier is free
 * 5. When VT unblocks, it's mounted onto any available carrier
 * 6. Result: millions of VTs multiplexed onto a few carrier threads
 * 
 * PINNING — THE GOTCHA:
 * - synchronized blocks use "monitor" locks → JVM can't unmount VT
 * - VT is PINNED to carrier → carrier is blocked → other VTs can't run
 * - Solution: use ReentrantLock → JVM CAN unmount VT during lock.lock()
 * - Detect: -Djdk.tracePinnedThreads=short
 * 
 * THE EVOLUTION OF JAVA CONCURRENCY:
 * Java 1.0:  Thread, synchronized, wait/notify
 * Java 5:    java.util.concurrent (Executor, Lock, Atomic, ConcurrentHashMap)
 * Java 7:    ForkJoinPool, parallelSort
 * Java 8:    CompletableFuture, parallel streams, StampedLock
 * Java 21:   Virtual threads (finalized)
 * Java 25:   StructuredTaskScope, ScopedValue, Gatherers (finalized) ← YOU ARE HERE
 */
