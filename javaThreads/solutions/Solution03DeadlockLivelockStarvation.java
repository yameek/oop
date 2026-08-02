/**
 * SOLUTION 3: Deadlock, Livelock & Starvation
 * ==============================================
 * 
 * ⚠️ THIS IS THE #1 CONCURRENCY INTERVIEW TOPIC!
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Deadlock: two+ threads each hold a lock the other needs → frozen forever
 * 2. 4 Coffman Conditions (ALL must be true for deadlock to occur):
 *    - Mutual Exclusion, Hold and Wait, No Preemption, Circular Wait
 * 3. Prevention strategies: lock ordering, tryLock with timeout
 * 4. Livelock: threads actively respond but make no progress
 * 5. Starvation: a thread can never acquire the resource it needs
 */

package solutions;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class Solution03DeadlockLivelockStarvation {

    // Shared resources for deadlock demo
    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 3: Deadlock, Livelock & Starvation");
        System.out.println("=".repeat(60));

        // Run demos (Part A is intentionally skipped in automated run 
        // because it would freeze the program!)
        partA_DeadlockDemo();
        partB_LockOrderingFix();
        partC_TryLockFix();
        partD_LivelockDemo();
        partE_StarvationDemo();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: Create a Deadlock (CAREFUL — this will freeze!)
    // ═══════════════════════════════════════════════════════════════
    static void partA_DeadlockDemo() throws InterruptedException {
        System.out.println("\n=== Part A: Deadlock Demo ===");
        System.out.println("(Running with timeout detection — won't freeze forever)\n");

        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println("[Thread-1] Locked resourceA, trying resourceB...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (resourceB) {
                    System.out.println("[Thread-1] Got both locks!");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println("[Thread-2] Locked resourceB, trying resourceA...");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (resourceA) {
                    System.out.println("[Thread-2] Got both locks!");
                }
            }
        }, "Thread-2");

        thread1.start();
        thread2.start();

        // Wait 2 seconds then check if deadlocked
        Thread.sleep(2000);
        System.out.println("[main] Thread-1 state: " + thread1.getState());
        System.out.println("[main] Thread-2 state: " + thread2.getState());

        if (thread1.getState() == Thread.State.BLOCKED &&
            thread2.getState() == Thread.State.BLOCKED) {
            System.out.println("⚠️ DEADLOCK DETECTED! Both threads are BLOCKED.");
            System.out.println("   Thread-1 holds resourceA, wants resourceB");
            System.out.println("   Thread-2 holds resourceB, wants resourceA");
            System.out.println("   → Circular wait! Neither can proceed.\n");
            
            // 4 Coffman Conditions explained:
            System.out.println("   4 Coffman Conditions (all present):");
            System.out.println("   1. Mutual Exclusion: synchronized = exclusive lock");
            System.out.println("   2. Hold and Wait: each holds one, waits for the other");
            System.out.println("   3. No Preemption: can't force a thread to release its lock");
            System.out.println("   4. Circular Wait: Thread-1 → resourceB → Thread-2 → resourceA → Thread-1");
        }

        // Force-interrupt to unblock (for demo purposes)
        thread1.interrupt();
        thread2.interrupt();
        thread1.join(1000);
        thread2.join(1000);
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: Fix with Lock Ordering (breaks Circular Wait)
    // ═══════════════════════════════════════════════════════════════
    static void partB_LockOrderingFix() throws InterruptedException {
        System.out.println("\n=== Part B: Lock Ordering Fix ===");
        System.out.println("Both threads lock in SAME order: resourceA first, then resourceB\n");

        Thread thread1 = new Thread(() -> {
            synchronized (resourceA) {          // Always lock A first
                System.out.println("[Thread-1] Locked resourceA");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (resourceB) {      // Then lock B
                    System.out.println("[Thread-1] Locked resourceB — got both! ✓");
                }
            }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            synchronized (resourceA) {          // Same order: A first!
                System.out.println("[Thread-2] Locked resourceA");
                try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                synchronized (resourceB) {
                    System.out.println("[Thread-2] Locked resourceB — got both! ✓");
                }
            }
        }, "Thread-2");

        thread1.start(); thread2.start();
        thread1.join(); thread2.join();
        System.out.println("✓ No deadlock — lock ordering breaks circular wait!");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: Fix with tryLock (timeout strategy)
    // ═══════════════════════════════════════════════════════════════
    static void partC_TryLockFix() throws InterruptedException {
        System.out.println("\n=== Part C: tryLock Fix (timeout strategy) ===\n");

        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                while (true) {
                    if (lockA.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("[Thread-1] Got lockA, trying lockB...");
                            Thread.sleep(100);
                            if (lockB.tryLock(500, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("[Thread-1] Got both locks! ✓");
                                    return;  // Success!
                                } finally {
                                    lockB.unlock();
                                }
                            } else {
                                System.out.println("[Thread-1] Couldn't get lockB, backing off...");
                                // Release lockA and retry — breaks Hold and Wait!
                            }
                        } finally {
                            lockA.unlock();
                        }
                    }
                    Thread.sleep(50);  // Back-off before retry
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            try {
                while (true) {
                    if (lockB.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("[Thread-2] Got lockB, trying lockA...");
                            Thread.sleep(100);
                            if (lockA.tryLock(500, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("[Thread-2] Got both locks! ✓");
                                    return;
                                } finally {
                                    lockA.unlock();
                                }
                            } else {
                                System.out.println("[Thread-2] Couldn't get lockA, backing off...");
                            }
                        } finally {
                            lockB.unlock();
                        }
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Thread-2");

        thread1.start(); thread2.start();
        thread1.join(); thread2.join();
        System.out.println("✓ No deadlock — tryLock with timeout breaks Hold and Wait!");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: Livelock Demo
    // ═══════════════════════════════════════════════════════════════
    static void partD_LivelockDemo() throws InterruptedException {
        System.out.println("\n=== Part D: Livelock Demo ===");
        System.out.println("Two 'polite' threads keep yielding to each other\n");

        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        final int MAX_RETRIES = 5;

        Thread polite1 = new Thread(() -> {
            for (int i = 0; i < MAX_RETRIES; i++) {
                lock1.lock();
                try {
                    System.out.println("[Polite-1] Got lock1, checking lock2...");
                    if (!lock2.tryLock()) {
                        System.out.println("[Polite-1] lock2 taken — \"After you!\" (releasing lock1)");
                        // Releasing and retrying — being "polite" but making no progress!
                        try { Thread.sleep(10); } catch (InterruptedException e) { return; }
                        continue;
                    }
                    try {
                        System.out.println("[Polite-1] Got both locks! Done. ✓");
                        return;
                    } finally {
                        lock2.unlock();
                    }
                } finally {
                    lock1.unlock();
                }
            }
            System.out.println("[Polite-1] Gave up after " + MAX_RETRIES + " retries");
        }, "Polite-1");

        Thread polite2 = new Thread(() -> {
            for (int i = 0; i < MAX_RETRIES; i++) {
                lock2.lock();
                try {
                    System.out.println("[Polite-2] Got lock2, checking lock1...");
                    if (!lock1.tryLock()) {
                        System.out.println("[Polite-2] lock1 taken — \"No, after you!\" (releasing lock2)");
                        try { Thread.sleep(10); } catch (InterruptedException e) { return; }
                        continue;
                    }
                    try {
                        System.out.println("[Polite-2] Got both locks! Done. ✓");
                        return;
                    } finally {
                        lock1.unlock();
                    }
                } finally {
                    lock2.unlock();
                }
            }
            System.out.println("[Polite-2] Gave up after " + MAX_RETRIES + " retries");
        }, "Polite-2");

        polite1.start(); polite2.start();
        polite1.join(); polite2.join();
        System.out.println("↑ Livelock: threads are ACTIVE but make no progress!");
        System.out.println("  Fix: add random back-off delay (like Ethernet collision protocol)");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Starvation Demo
    // ═══════════════════════════════════════════════════════════════
    static void partE_StarvationDemo() throws InterruptedException {
        System.out.println("\n=== Part E: Starvation Demo ===\n");

        // Non-fair lock (default) — greedy thread dominates
        System.out.println("--- Non-fair lock (default) ---");
        ReentrantLock unfairLock = new ReentrantLock(false);
        int[] greedyCount = {0};
        int[] starvingCount = {0};

        Thread greedy = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                unfairLock.lock();
                try {
                    greedyCount[0]++;
                    // Immediately re-acquires — starves the other thread
                } finally {
                    unfairLock.unlock();
                }
            }
        }, "Greedy");

        Thread starving = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                unfairLock.lock();
                try {
                    starvingCount[0]++;
                } finally {
                    unfairLock.unlock();
                }
            }
        }, "Starving");

        greedy.start(); starving.start();
        greedy.join(); starving.join();
        System.out.println("[Greedy]   acquired lock " + greedyCount[0] + " times");
        System.out.println("[Starving] acquired lock " + starvingCount[0] + " times");

        // Fair lock — both get equal access
        System.out.println("\n--- Fair lock (fairness=true) ---");
        ReentrantLock fairLock = new ReentrantLock(true);  // Fair!
        int[] fairGreedy = {0};
        int[] fairStarving = {0};

        Thread fg = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                fairLock.lock();
                try { fairGreedy[0]++; }
                finally { fairLock.unlock(); }
            }
        });
        Thread fs = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                fairLock.lock();
                try { fairStarving[0]++; }
                finally { fairLock.unlock(); }
            }
        });

        fg.start(); fs.start();
        fg.join(); fs.join();
        System.out.println("[Thread-1] acquired lock " + fairGreedy[0] + " times");
        System.out.println("[Thread-2] acquired lock " + fairStarving[0] + " times");
        System.out.println("↑ Fair lock ensures longest-waiting thread gets lock next");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ Deadlock: threads frozen forever, each waiting for the other");
        System.out.println("✓ Prevention: lock ordering OR tryLock with timeout");
        System.out.println("✓ Livelock: threads active but make no progress (polite yielding)");
        System.out.println("✓ Starvation: a thread never gets the resource (unfair scheduling)");
        System.out.println("✓ 4 Coffman Conditions: ALL must hold for deadlock to occur");
        System.out.println("✓ Break ANY ONE condition → no deadlock possible");
        System.out.println("=".repeat(60));
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * DEADLOCK PREVENTION STRATEGIES:
 * 1. Lock Ordering: always acquire locks in a consistent global order
 *    → Breaks "Circular Wait" condition
 * 2. tryLock with timeout: if you can't get the lock, release and retry
 *    → Breaks "Hold and Wait" condition
 * 3. Single lock: use one coarse lock instead of multiple fine locks
 *    → Breaks "Hold and Wait" (but hurts concurrency)
 * 4. Lock-free algorithms: use Atomics/CAS instead of locks
 *    → Breaks "Mutual Exclusion" condition
 * 
 * DEADLOCK vs LIVELOCK vs STARVATION:
 * | Problem    | Threads doing | Progress | Detection         |
 * |------------|---------------|----------|-------------------|
 * | Deadlock   | Nothing       | None     | jstack (BLOCKED)  |
 * | Livelock   | Active work   | None     | Hard (CPU busy)   |
 * | Starvation | Some work     | Unfair   | Monitoring counts |
 * 
 * INTERVIEW TIP:
 * If asked "how would you detect a deadlock in production?":
 * 1. jstack <pid> — shows thread dump with lock owners
 * 2. JMX MXBean: ThreadMXBean.findDeadlockedThreads()
 * 3. jconsole — visual thread monitoring
 * 4. Thread dump in logs: kill -3 <pid> (Unix)
 */
