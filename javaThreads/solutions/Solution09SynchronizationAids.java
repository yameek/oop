/**
 * SOLUTION 9: Synchronization Aids
 * ===================================
 * 
 * KEY TAKEAWAYS:
 * - Semaphore: controls access to N permits (like a parking lot)
 * - CountDownLatch: one-shot "wait for N events" 
 * - CyclicBarrier: reusable "N threads wait for each other"
 * - Phaser: flexible barrier with dynamic registration
 * - Exchanger: two-thread data swap
 */

package solutions;

import java.util.concurrent.*;

public class Solution09SynchronizationAids {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 9: Synchronization Aids");
        System.out.println("=".repeat(60));

        partA_Semaphore();
        partB_CountDownLatch();
        partC_CyclicBarrier();
        partE_Phaser();
        partF_Exchanger();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: Semaphore (Connection Pool)
    // ═══════════════════════════════════════════════════════════════
    static void partA_Semaphore() throws InterruptedException {
        System.out.println("\n=== Part A: Semaphore (max 3 connections) ===\n");
        Semaphore semaphore = new Semaphore(3);  // 3 permits

        Thread[] threads = new Thread[6];
        for (int i = 0; i < 6; i++) {
            final int id = i + 1;
            threads[i] = new Thread(() -> {
                try {
                    System.out.println("[Thread-" + id + "] Requesting connection...");
                    semaphore.acquire();  // Block if no permits available
                    System.out.println("[Thread-" + id + "] Connected! (available: " +
                            semaphore.availablePermits() + ")");
                    Thread.sleep(1000);  // Use connection for 1 second
                    System.out.println("[Thread-" + id + "] Disconnecting.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();  // Return permit
                }
            }, "Thread-" + id);
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("✓ Only 3 threads connected at any time");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part B: CountDownLatch (Service Startup)
    // ═══════════════════════════════════════════════════════════════
    static void partB_CountDownLatch() throws InterruptedException {
        System.out.println("\n=== Part B: CountDownLatch (Service Startup) ===\n");
        CountDownLatch latch = new CountDownLatch(3);  // Wait for 3 services

        String[] services = {"Database", "Cache", "MessageQueue"};
        int[] delays = {1000, 500, 1500};

        for (int i = 0; i < 3; i++) {
            final int idx = i;
            new Thread(() -> {
                try {
                    Thread.sleep(delays[idx]);
                    System.out.println("[" + services[idx] + "] Started ✓ (count: " +
                            (latch.getCount() - 1) + ")");
                    latch.countDown();  // Decrement count
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }).start();
        }

        System.out.println("[main] Waiting for all services...");
        latch.await();  // Block until count reaches 0
        System.out.println("🚀 All services ready! Application starting...");
        System.out.println("Note: CountDownLatch is ONE-SHOT (cannot be reset)");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: CyclicBarrier (Parallel Computation)
    // ═══════════════════════════════════════════════════════════════
    static void partC_CyclicBarrier() throws InterruptedException {
        System.out.println("\n=== Part C: CyclicBarrier (3 phases, 3 workers) ===\n");

        // Barrier action runs when all parties arrive
        CyclicBarrier barrier = new CyclicBarrier(3, () ->
            System.out.println(">>> Phase complete! Merging results... <<<")
        );

        for (int w = 1; w <= 3; w++) {
            final int workerId = w;
            new Thread(() -> {
                try {
                    for (int phase = 1; phase <= 3; phase++) {
                        // Simulate computation
                        Thread.sleep(200 * workerId);
                        System.out.println("[Worker-" + workerId + "] Phase " + phase + " done, waiting at barrier");
                        barrier.await();  // Wait for all workers
                    }
                } catch (Exception e) { Thread.currentThread().interrupt(); }
            }).start();
        }

        Thread.sleep(5000);  // Let all phases complete
        System.out.println("✓ CyclicBarrier is REUSABLE (ran 3 phases with same barrier)");

        // Comparison
        System.out.println("\nCountDownLatch vs CyclicBarrier:");
        System.out.println("  | Feature       | CountDownLatch | CyclicBarrier    |");
        System.out.println("  |---------------|----------------|------------------|");
        System.out.println("  | Reusable?     | NO (one-shot)  | YES (cyclic)     |");
        System.out.println("  | Who waits?    | One thread     | All participants |");
        System.out.println("  | Barrier action| No             | Yes (Runnable)   |");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part E: Phaser (flexible barrier)
    // ═══════════════════════════════════════════════════════════════
    static void partE_Phaser() throws InterruptedException {
        System.out.println("\n=== Part E: Phaser (dynamic registration) ===\n");
        Phaser phaser = new Phaser(3);  // 3 initial participants

        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> {
                for (int phase = 0; phase < 3; phase++) {
                    try { Thread.sleep(100 * id); } catch (InterruptedException e) { return; }
                    System.out.println("[Worker-" + id + "] Phase " + phase + " done");

                    if (id == 3 && phase == 1) {
                        System.out.println("[Worker-3] Leaving after phase 1!");
                        phaser.arriveAndDeregister();  // Leave the phaser
                        return;
                    }
                    phaser.arriveAndAwaitAdvance();  // Wait for others
                }
            }).start();
        }

        Thread.sleep(3000);
        System.out.println("✓ Phaser supports dynamic add/remove of participants");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part F: Exchanger (two-thread data swap)
    // ═══════════════════════════════════════════════════════════════
    static void partF_Exchanger() throws InterruptedException {
        System.out.println("\n=== Part F: Exchanger ===\n");
        Exchanger<String> exchanger = new Exchanger<>();

        Thread threadA = new Thread(() -> {
            try {
                String myData = "Data from A";
                System.out.println("[Thread-A] Sending: " + myData);
                String received = exchanger.exchange(myData);  // Blocks until partner arrives
                System.out.println("[Thread-A] Received: " + received);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread threadB = new Thread(() -> {
            try {
                String myData = "Data from B";
                System.out.println("[Thread-B] Sending: " + myData);
                String received = exchanger.exchange(myData);
                System.out.println("[Thread-B] Received: " + received);
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        threadA.start(); threadB.start();
        threadA.join(); threadB.join();
        System.out.println("✓ Both threads swapped data at the meeting point");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ Semaphore: N permits — rate limiting, connection pools");
        System.out.println("✓ CountDownLatch: one-shot countdown to zero");
        System.out.println("✓ CyclicBarrier: reusable N-thread rendezvous");
        System.out.println("✓ Phaser: flexible barrier with dynamic participants");
        System.out.println("✓ Exchanger: two-thread data swap at meeting point");
        System.out.println("=".repeat(60));
    }
}
