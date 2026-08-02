/**
 * SOLUTION 6: Thread Communication
 * ===================================
 * 
 * KEY TAKEAWAYS:
 * - wait() releases the lock; sleep() does NOT
 * - ALWAYS use while-loop with wait() (spurious wakeups)
 * - Condition variables: like wait/notify but for ReentrantLock
 * - Producer-Consumer: the classic thread communication pattern
 */

package solutions;

import java.util.*;
import java.util.concurrent.locks.*;

// ─── Part A: Order Queue with wait/notify ────────────────────────────────────
class OrderQueue {
    private final Queue<String> orders = new LinkedList<>();
    private final int maxSize;

    public OrderQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void placeOrder(String order) throws InterruptedException {
        while (orders.size() >= maxSize) {  // WHILE, not IF (spurious wakeups!)
            System.out.println("[" + Thread.currentThread().getName() + "] Queue full, waiting...");
            wait();  // Releases the lock! Other threads can enter.
        }
        orders.add(order);
        System.out.println("[" + Thread.currentThread().getName() + "] placed: " + order +
                " (queue: " + orders.size() + "/" + maxSize + ")");
        notifyAll();  // Wake ALL waiting threads (consumers might be waiting)
    }

    public synchronized String takeOrder() throws InterruptedException {
        while (orders.isEmpty()) {  // WHILE, not IF!
            wait();  // Releases lock, waits for notification
        }
        String order = orders.poll();
        System.out.println("[" + Thread.currentThread().getName() + "] serving: " + order +
                " (queue: " + orders.size() + "/" + maxSize + ")");
        notifyAll();  // Wake producers that might be waiting on full queue
        return order;
    }

    public synchronized int size() { return orders.size(); }
}

// ─── Part D: Order Queue with Condition variables ────────────────────────────
class ConditionOrderQueue {
    private final Queue<String> orders = new LinkedList<>();
    private final int maxSize;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();   // Producers wait here
    private final Condition notEmpty = lock.newCondition();  // Consumers wait here

    public ConditionOrderQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void placeOrder(String order) throws InterruptedException {
        lock.lock();
        try {
            while (orders.size() >= maxSize) {
                notFull.await();  // Only producers wait here
            }
            orders.add(order);
            System.out.println("[" + Thread.currentThread().getName() + "] placed: " + order);
            notEmpty.signal();  // Only wake ONE consumer (more efficient than notifyAll!)
        } finally {
            lock.unlock();
        }
    }

    public String takeOrder() throws InterruptedException {
        lock.lock();
        try {
            while (orders.isEmpty()) {
                notEmpty.await();  // Only consumers wait here
            }
            String order = orders.poll();
            System.out.println("[" + Thread.currentThread().getName() + "] serving: " + order);
            notFull.signal();  // Only wake ONE producer
            return order;
        } finally {
            lock.unlock();
        }
    }
}

public class Solution06ThreadCommunication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 6: Thread Communication");
        System.out.println("=".repeat(60));

        partA_ProducerConsumer();
        partC_SleepVsWait();
        partD_ConditionVariables();
    }

    // ═══════════════════════════════════════════════════════════════
    // Part A: Producer-Consumer with wait/notify
    // ═══════════════════════════════════════════════════════════════
    static void partA_ProducerConsumer() throws InterruptedException {
        System.out.println("\n=== Part A: Producer-Consumer (wait/notify) ===\n");
        OrderQueue queue = new OrderQueue(3);  // Max 3 orders in queue
        int ordersPerChef = 3;

        // 2 Chefs (producers)
        Thread chef1 = new Thread(() -> {
            try {
                for (int i = 1; i <= ordersPerChef; i++) {
                    queue.placeOrder("Pizza #" + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Chef-1");

        Thread chef2 = new Thread(() -> {
            try {
                for (int i = 1; i <= ordersPerChef; i++) {
                    queue.placeOrder("Burger #" + i);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Chef-2");

        // 2 Waiters (consumers)
        int totalOrders = ordersPerChef * 2;
        Thread waiter1 = new Thread(() -> {
            try {
                for (int i = 0; i < totalOrders / 2; i++) {
                    queue.takeOrder();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Waiter-1");

        Thread waiter2 = new Thread(() -> {
            try {
                for (int i = 0; i < totalOrders / 2; i++) {
                    queue.takeOrder();
                    Thread.sleep(350);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Waiter-2");

        chef1.start(); chef2.start();
        waiter1.start(); waiter2.start();
        chef1.join(); chef2.join();
        waiter1.join(); waiter2.join();
        System.out.println("All orders served! ✓");

        // Spurious wakeup explanation
        System.out.println("\n--- Why WHILE loop, not IF? (Spurious Wakeups) ---");
        System.out.println("  // WRONG:                    // CORRECT:");
        System.out.println("  if (queue.isEmpty())         while (queue.isEmpty())");
        System.out.println("      wait();                      wait();");
        System.out.println("  // If spuriously woken,      // Re-checks after waking");
        System.out.println("  // proceeds with empty queue! // → safe!");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part C: sleep() vs wait() Demo
    // ═══════════════════════════════════════════════════════════════
    static void partC_SleepVsWait() throws InterruptedException {
        System.out.println("\n=== Part C: sleep() vs wait() ===\n");

        Object lock = new Object();

        // --- sleep() does NOT release the lock ---
        System.out.println("--- sleep() holds the lock ---");
        Thread sleeper = new Thread(() -> {
            synchronized (lock) {
                System.out.println("[Sleeper] Got lock, sleeping 1s (lock NOT released)...");
                try { Thread.sleep(1000); } catch (InterruptedException e) { return; }
                System.out.println("[Sleeper] Woke up, releasing lock");
            }
        }, "Sleeper");

        Thread blockedBySleep = new Thread(() -> {
            long start = System.currentTimeMillis();
            synchronized (lock) {
                long waited = System.currentTimeMillis() - start;
                System.out.println("[Blocked] Got lock after " + waited + "ms " +
                        "(blocked because sleep() holds the lock!)");
            }
        }, "Blocked");

        sleeper.start();
        Thread.sleep(100);  // Let sleeper acquire lock first
        blockedBySleep.start();
        sleeper.join(); blockedBySleep.join();

        // --- wait() DOES release the lock ---
        System.out.println("\n--- wait() releases the lock ---");
        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                System.out.println("[Waiter] Got lock, calling wait() (lock RELEASED)...");
                try { lock.wait(1000); } catch (InterruptedException e) { return; }
                System.out.println("[Waiter] Woke up from wait()");
            }
        }, "Waiter");

        Thread canEnter = new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException e) { return; }
            synchronized (lock) {
                System.out.println("[CanEnter] Got lock! (wait() released it) ✓");
                lock.notify();  // Wake the waiter
            }
        }, "CanEnter");

        waiter.start();
        canEnter.start();
        waiter.join(); canEnter.join();

        System.out.println("\n  Summary:");
        System.out.println("  sleep(): pauses thread, KEEPS lock, woken by timer");
        System.out.println("  wait():  pauses thread, RELEASES lock, woken by notify()");
    }

    // ═══════════════════════════════════════════════════════════════
    // Part D: Condition Variables
    // ═══════════════════════════════════════════════════════════════
    static void partD_ConditionVariables() throws InterruptedException {
        System.out.println("\n=== Part D: Condition Variables (Lock + Condition) ===\n");
        ConditionOrderQueue cQueue = new ConditionOrderQueue(2);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    cQueue.placeOrder("Item-" + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 3; i++) {
                    cQueue.takeOrder();
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }, "Consumer");

        producer.start(); consumer.start();
        producer.join(); consumer.join();

        System.out.println("\nAdvantage of Condition over wait/notify:");
        System.out.println("  - Separate conditions: notFull for producers, notEmpty for consumers");
        System.out.println("  - signal() wakes only the RIGHT type of thread");
        System.out.println("  - notifyAll() wakes ALL threads — wasteful!");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ wait() releases lock; sleep() does NOT");
        System.out.println("✓ Always use while-loop with wait() (spurious wakeups)");
        System.out.println("✓ notifyAll() > notify() (safer, avoids lost wakeups)");
        System.out.println("✓ Condition variables: targeted signaling (better than notifyAll)");
        System.out.println("✓ Producer-Consumer: the classic thread communication pattern");
        System.out.println("=".repeat(60));
    }
}
