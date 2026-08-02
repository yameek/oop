/**
 * SOLUTION 5: ReentrantLock & ReadWriteLock
 * ===========================================
 * 
 * KEY TAKEAWAYS:
 * - Lock interface: more flexible than synchronized (tryLock, interruptible, fairness)
 * - ALWAYS unlock in finally block
 * - ReadWriteLock: many readers OR one writer
 * - StampedLock: optimistic reads for max read throughput
 */

package solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

// ─── Part A: Safe Cache with ReentrantLock ───────────────────────────────────
class SafeCache<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void put(K key, V value) {
        lock.lock();              // Acquire lock
        try {
            map.put(key, value);  // Critical section
        } finally {
            lock.unlock();        // ALWAYS unlock in finally!
        }
    }

    public V get(K key) {
        lock.lock();
        try {
            return map.get(key);
        } finally {
            lock.unlock();
        }
    }

    // Part B: tryLock with timeout
    public boolean tryPut(K key, V value, long timeoutMs) throws InterruptedException {
        if (lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS)) {
            try {
                map.put(key, value);
                return true;
            } finally {
                lock.unlock();
            }
        }
        return false;  // Couldn't acquire lock within timeout
    }

    public int size() {
        lock.lock();
        try { return map.size(); }
        finally { lock.unlock(); }
    }
}

// ─── Part D: ReadWriteLock Cache ─────────────────────────────────────────────
class RWCache<K, V> {
    private final Map<K, V> map = new HashMap<>();
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public V get(K key) {
        rwLock.readLock().lock();   // Multiple readers can hold this simultaneously
        try {
            return map.get(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void put(K key, V value) {
        rwLock.writeLock().lock();  // Exclusive — no readers or writers allowed
        try {
            map.put(key, value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public int size() {
        rwLock.readLock().lock();
        try { return map.size(); }
        finally { rwLock.readLock().unlock(); }
    }
}

// ─── Part E: StampedLock with optimistic read ────────────────────────────────
class Point {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    public void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // Optimistic read — NO LOCK ACQUIRED (maximum throughput)
    public double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();  // Non-blocking!
        double currentX = x;
        double currentY = y;

        if (!sl.validate(stamp)) {
            // A write occurred during our read — fall back to read lock
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }

        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}

public class Solution05ReentrantLocks {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 5: ReentrantLock & ReadWriteLock");
        System.out.println("=".repeat(60));

        // ═══ Part A: ReentrantLock basics ════════════════════════════
        System.out.println("\n=== Part A: ReentrantLock ===\n");
        SafeCache<String, Integer> cache = new SafeCache<>();

        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    cache.put("key-" + threadId + "-" + j, j);
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("Final cache size: " + cache.size() + " (expected 500) " +
                (cache.size() == 500 ? "✓" : "✗"));

        // ═══ Part B: tryLock ═════════════════════════════════════════
        System.out.println("\n=== Part B: tryLock (timeout) ===\n");
        SafeCache<String, String> cache2 = new SafeCache<>();

        // Thread holds lock for 2 seconds
        Thread holder = new Thread(() -> {
            try {
                cache2.put("blocking", "value");
                // Simulate long operation while holding the lock
                // (In real code, the lock would be held across a long operation)
            } catch (Exception e) { }
        });
        holder.start();
        holder.join();

        // Demonstrate tryPut (these will succeed since holder already released)
        boolean result1 = cache2.tryPut("fast-key", "fast-value", 500);
        System.out.println("tryPut with 500ms: " + result1 + (result1 ? " ✓" : " ✗ (timed out)"));

        // ═══ Part C: lockInterruptibly ═══════════════════════════════
        System.out.println("\n=== Part C: lockInterruptibly ===\n");
        ReentrantLock interruptLock = new ReentrantLock();
        interruptLock.lock();  // Main thread holds the lock

        Thread waiter = new Thread(() -> {
            try {
                System.out.println("[Waiter] Trying to acquire lock (interruptibly)...");
                interruptLock.lockInterruptibly();  // Can be interrupted!
                try {
                    System.out.println("[Waiter] Got the lock!");
                } finally {
                    interruptLock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("[Waiter] Interrupted while waiting! ✓");
                System.out.println("  → lockInterruptibly() throws InterruptedException");
                System.out.println("  → Unlike synchronized, the waiting thread can be cancelled");
            }
        }, "Waiter");

        waiter.start();
        Thread.sleep(500);
        waiter.interrupt();  // Interrupt the waiting thread
        waiter.join();
        interruptLock.unlock();  // Release the lock

        // ═══ Part D: ReadWriteLock ═══════════════════════════════════
        System.out.println("\n=== Part D: ReadWriteLock ===\n");
        RWCache<String, String> rwCache = new RWCache<>();
        rwCache.put("key", "initial");

        // 8 reader threads
        for (int i = 0; i < 8; i++) {
            final int id = i;
            new Thread(() -> {
                String val = rwCache.get("key");
                System.out.println("[Reader-" + id + "] read: " + val);
            }, "Reader-" + i).start();
        }

        // 2 writer threads
        for (int i = 0; i < 2; i++) {
            final int id = i;
            new Thread(() -> {
                rwCache.put("key", "updated-by-writer-" + id);
                System.out.println("[Writer-" + id + "] wrote new value");
            }, "Writer-" + i).start();
        }

        Thread.sleep(1000);  // Let all finish
        System.out.println("Readers run in PARALLEL (share read lock)");
        System.out.println("Writers are EXCLUSIVE (block readers and other writers)");

        // ═══ Part E: StampedLock ═════════════════════════════════════
        System.out.println("\n=== Part E: StampedLock (optimistic read) ===\n");
        Point point = new Point();

        // Optimistic read (no actual lock!)
        double dist = point.distanceFromOrigin();
        System.out.println("Distance (optimistic): " + dist);
        System.out.println("Optimistic read = NO lock acquired → maximum read throughput");
        System.out.println("If a write happens during read → automatically falls back to read lock");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ ReentrantLock: more flexible than synchronized");
        System.out.println("✓ ALWAYS unlock in finally (prevents deadlock on exception)");
        System.out.println("✓ tryLock: non-blocking or with timeout");
        System.out.println("✓ lockInterruptibly: can be interrupted while waiting");
        System.out.println("✓ ReadWriteLock: many readers OR one writer");
        System.out.println("✓ StampedLock: optimistic read for max throughput");
        System.out.println("=".repeat(60));
    }
}
