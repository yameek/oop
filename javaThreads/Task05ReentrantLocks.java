/**
 * TASK 5: ReentrantLock & ReadWriteLock
 * =======================================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Learn about: Lock interface, ReentrantLock, tryLock(), lockInterruptibly(),
 *              fairness policy, ReadWriteLock, StampedLock optimistic reads
 * 
 * PROBLEM:
 * --------
 * Build a "Thread-Safe Cache" system that demonstrates various lock types.
 * 
 * Part A — ReentrantLock basics:
 * 1. Create a class SafeCache<K, V> with:
 *    - private HashMap<K, V> map
 *    - private ReentrantLock lock
 *    - put(K key, V value): lock, insert, unlock (always in try-finally!)
 *    - get(K key): lock, retrieve, unlock
 *    - size(): returns map size
 * 2. Test with 5 threads adding 100 entries each
 * 3. Verify final size is exactly 500
 * 
 * Part B — tryLock (non-blocking):
 * 4. Create a method tryPut(K key, V value, long timeoutMs):
 *    - Uses tryLock(timeout, TimeUnit.MILLISECONDS)
 *    - Returns true if inserted, false if timeout
 * 5. Create contention: one thread holds the lock for 2 seconds
 * 6. Another thread tries tryPut with 500ms timeout → fails
 * 7. Then tries with 3000ms timeout → succeeds
 * 
 * Part C — lockInterruptibly:
 * 8. Create a method interruptiblePut(K key, V value):
 *    - Uses lock.lockInterruptibly()
 *    - Can be interrupted while waiting for the lock
 * 9. Hold the lock in one thread, try to acquire in another
 * 10. Interrupt the waiting thread → it throws InterruptedException
 * 
 * Part D — ReadWriteLock (multiple readers, single writer):
 * 11. Create a class RWCache<K, V> with:
 *     - ReadWriteLock rwLock
 *     - get(): uses readLock (multiple readers allowed simultaneously)
 *     - put(): uses writeLock (exclusive access)
 * 12. Start 8 reader threads and 2 writer threads
 * 13. Print "[Reader-N] reading..." / "[Writer-N] writing..."
 * 14. Observe: readers run in parallel, writers are exclusive
 * 
 * Part E — StampedLock (optimistic read):
 * 15. Create a Point class with x, y coordinates
 * 16. Use StampedLock with optimistic read:
 *     - long stamp = lock.tryOptimisticRead()
 *     - read x, y
 *     - if (!lock.validate(stamp)) → fall back to read lock
 * 17. Explain when optimistic reads are beneficial (mostly-read workloads)
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Lock interface: more flexible than synchronized (tryLock, interruptible, fairness)
 * - ReentrantLock: same thread can lock multiple times (must unlock same number)
 * - ALWAYS unlock in finally block — otherwise deadlock on exception!
 * - tryLock(): returns immediately or with timeout — no infinite waiting
 * - lockInterruptibly(): waiting thread can be interrupted (unlike synchronized)
 * - Fairness: fair=true → longest-waiting thread gets lock next (slower but fair)
 * - ReadWriteLock: many readers OR one writer (not both)
 * - StampedLock: optimistic reads for maximum read throughput (no locking at all!)
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: ReentrantLock ===
 * Final cache size: 500 ✓
 * 
 * === Part B: tryLock ===
 * [Thread-2] tryPut with 500ms timeout: false (timed out)
 * [Thread-2] tryPut with 3000ms timeout: true (success)
 * 
 * === Part C: lockInterruptibly ===
 * [Thread-2] Interrupted while waiting for lock!
 * 
 * === Part D: ReadWriteLock ===
 * [Reader-1] reading... [Reader-2] reading... (parallel!)
 * [Writer-1] writing... (exclusive)
 * 
 * === Part E: StampedLock ===
 * Optimistic read succeeded (no locking needed!)
 * Optimistic read failed, falling back to read lock.
 */

// Write your solution below:

