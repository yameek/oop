/**
 * SOLUTION 4: Volatile & Atomics
 * ================================
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. volatile: visibility guarantee — forces read/write from main memory
 * 2. volatile does NOT guarantee atomicity for compound ops (i++, check-then-act)
 * 3. AtomicInteger: uses CAS (Compare-And-Swap) for lock-free atomic operations
 * 4. Java Memory Model (JMM): defines when writes become visible to other threads
 * 5. Happens-before: if A happens-before B, A's effects are guaranteed visible to B
 */

package solutions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Solution04VolatileAndAtomics {

    // ─── Part A: Visibility problem ──────────────────────────────────────────

    // WITHOUT volatile: JIT compiler may cache 'running' in CPU register
    // Worker thread never sees main thread's update → infinite loop!
    static boolean runningUnsafe = true;

    // WITH volatile: forces read from main memory every time
    static volatile boolean runningVolatile = true;

    // ─── Part B: Volatile counter (broken) ────────────────────────────────────
    static volatile int volatileCount = 0;  // volatile does NOT make ++ atomic!

    // ─── Part C: AtomicInteger (correct) ──────────────────────────────────────
    static AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 4: Volatile & Atomics");
        System.out.println("=".repeat(60));

        partA_VisibilityProblem();
        partB_VolatileCounterBroken();
        partC_AtomicIntegerFixed();
        partD_AtomicReference();
        partE_HappensBeforeExplanation();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: Visibility Problem
    // ═══════════════════════════════════════════════════════════════
    static void partA_VisibilityProblem() throws InterruptedException {
        System.out.println("\n=== Part A: Visibility Problem ===\n");

        // Demo with volatile (safe version — will terminate)
        runningVolatile = true;
        Thread worker = new Thread(() -> {
            int iterations = 0;
            while (runningVolatile) {  // volatile → reads from main memory each time
                iterations++;
            }
            System.out.println("[Worker] Stopped after " + iterations + " iterations");
        });

        worker.start();
        Thread.sleep(100);       // Let worker run for 100ms
        runningVolatile = false;  // volatile write → visible to worker immediately
        worker.join(2000);       // Should stop within 2 seconds

        if (worker.isAlive()) {
            System.out.println("⚠️ Worker is still running! (visibility issue)");
            worker.interrupt();
        } else {
            System.out.println("✓ Worker stopped correctly (volatile ensured visibility)");
        }

        System.out.println("\nWHY this works:");
        System.out.println("  Without volatile: JIT may hoist the read of 'running' out of the loop");
        System.out.println("  → Thread reads a cached copy, never sees the update");
        System.out.println("  With volatile: every read goes to main memory, every write flushes to main memory");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: Volatile Counter (BROKEN — volatile ≠ atomic)
    // ═══════════════════════════════════════════════════════════════
    static void partB_VolatileCounterBroken() throws InterruptedException {
        System.out.println("\n=== Part B: Volatile Counter (BROKEN) ===\n");

        volatileCount = 0;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    volatileCount++;  // NOT ATOMIC! Three steps:
                    // 1. READ volatileCount from memory      → e.g., 42
                    // 2. INCREMENT in CPU register            → 43
                    // 3. WRITE back to memory                 → 43
                    // If two threads read 42 simultaneously, both write 43!
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        System.out.println("Expected: 10000");
        System.out.println("Actual:   " + volatileCount + (volatileCount < 10000 ? " ← LOST UPDATES!" : ""));
        System.out.println("\n⚠️ volatile guarantees VISIBILITY, not ATOMICITY!");
        System.out.println("   count++ = read + increment + write (3 separate steps)");
        System.out.println("   Two threads can interleave between these steps");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: AtomicInteger (CORRECT — CAS-based)
    // ═══════════════════════════════════════════════════════════════
    static void partC_AtomicIntegerFixed() throws InterruptedException {
        System.out.println("\n=== Part C: AtomicInteger (CORRECT) ===\n");

        atomicCount.set(0);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicCount.incrementAndGet();
                    // CAS loop internally:
                    // 1. Read current value (e.g., 42)
                    // 2. Compute new value (43)
                    // 3. CAS: "if still 42, set to 43" (CPU atomic instruction)
                    // 4. If another thread changed it → retry from step 1
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();

        System.out.println("Expected: 10000");
        System.out.println("Actual:   " + atomicCount.get() + " ✓ (always correct!)");

        // Demonstrate other AtomicInteger operations
        System.out.println("\n--- Other AtomicInteger operations ---");
        AtomicInteger demo = new AtomicInteger(10);
        System.out.println("Initial: " + demo.get());

        // CAS: Compare-And-Set
        boolean casResult = demo.compareAndSet(10, 20);
        System.out.println("CAS(10 → 20): " + casResult + ", value: " + demo.get());

        casResult = demo.compareAndSet(10, 30);  // Fails — current is 20, not 10
        System.out.println("CAS(10 → 30): " + casResult + ", value: " + demo.get() + " (unchanged)");

        // addAndGet
        System.out.println("addAndGet(5): " + demo.addAndGet(5));

        // getAndUpdate with lambda
        int old = demo.getAndUpdate(v -> v * 2);
        System.out.println("getAndUpdate(v*2): old=" + old + ", new=" + demo.get());
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: AtomicReference
    // ═══════════════════════════════════════════════════════════════
    static void partD_AtomicReference() throws InterruptedException {
        System.out.println("\n=== Part D: AtomicReference ===\n");

        AtomicReference<String> currentUser = new AtomicReference<>("alice");

        Thread t1 = new Thread(() -> {
            boolean success = currentUser.compareAndSet("alice", "bob");
            System.out.println("[Thread-1] CAS alice→bob: " + (success ? "succeeded ✓" : "failed ✗"));
        });

        Thread t2 = new Thread(() -> {
            // Small delay to increase chance of conflict
            try { Thread.sleep(1); } catch (InterruptedException e) { return; }
            boolean success = currentUser.compareAndSet("alice", "charlie");
            System.out.println("[Thread-2] CAS alice→charlie: " + (success ? "succeeded ✓" : "failed ✗"));
        });

        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("Final user: " + currentUser.get());
        System.out.println("Only ONE CAS succeeded — the other saw a different expected value");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Happens-Before Explanation
    // ═══════════════════════════════════════════════════════════════
    static void partE_HappensBeforeExplanation() {
        System.out.println("\n=== Part E: Java Memory Model & Happens-Before ===\n");

        System.out.println("JAVA MEMORY MODEL (JMM):");
        System.out.println("  Defines rules for when one thread's writes become visible to another.");
        System.out.println("  Without these rules, compilers and CPUs can reorder instructions!");
        System.out.println();

        System.out.println("HAPPENS-BEFORE RELATIONSHIPS:");
        System.out.println("  If action A 'happens-before' action B, then A's effects");
        System.out.println("  are GUARANTEED visible to B.\n");

        System.out.println("  Key happens-before rules:");
        System.out.println("  1. Program Order:      Within one thread, earlier code hb later code");
        System.out.println("  2. Monitor Lock:       synchronized unlock hb next synchronized lock");
        System.out.println("  3. Volatile:           volatile write hb next volatile read (of same var)");
        System.out.println("  4. Thread Start:       thread.start() hb first action in run()");
        System.out.println("  5. Thread Join:        Last action in run() hb thread.join() return");
        System.out.println("  6. Thread Interrupt:   interrupt() hb interrupted thread detects it");
        System.out.println("  7. Transitivity:       If A hb B and B hb C, then A hb C");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ volatile = visibility (main memory) + ordering (no reordering)");
        System.out.println("✓ volatile ≠ atomicity (i++ is still broken with volatile!)");
        System.out.println("✓ AtomicInteger uses CAS — lock-free, hardware-level atomic ops");
        System.out.println("✓ CAS: 'if value is X, set to Y' — retries on conflict");
        System.out.println("✓ JMM + happens-before: the rules of thread memory visibility");
        System.out.println("=".repeat(60));
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * WHEN TO USE WHAT:
 * | Scenario                        | Use                    |
 * |---------------------------------|------------------------|
 * | Simple flag (stop/start)        | volatile boolean       |
 * | Counter (increment/decrement)   | AtomicInteger          |
 * | Complex state update            | synchronized or Lock   |
 * | Reference swap                  | AtomicReference        |
 * | Multiple related fields         | synchronized (or Lock) |
 * 
 * CAS (COMPARE-AND-SWAP) DEEP DIVE:
 * - CPU instruction: CMPXCHG (x86)
 * - Atomically: "if memory[addr] == expected, set memory[addr] = new_value"
 * - If another thread changed it → CAS fails → retry (spin loop)
 * - Lock-free: no OS-level blocking, just spinning
 * - Works great under LOW contention; degrades under HIGH contention (many retries)
 * 
 * VOLATILE vs SYNCHRONIZED:
 * | Feature      | volatile           | synchronized         |
 * |--------------|--------------------|----------------------|
 * | Visibility   | Yes                | Yes                  |
 * | Atomicity    | NO (single r/w)    | YES (entire block)   |
 * | Blocking     | Never              | Can block            |
 * | Use case     | Flags, status      | Critical sections    |
 */
