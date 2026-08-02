/**
 * TASK 3: Deadlock, Livelock & Starvation
 * =========================================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Learn about: Deadlock (4 Coffman conditions, detection, prevention),
 *              Livelock, Starvation, lock ordering, timeout strategies
 * 
 * ⚠️ THIS IS THE #1 CONCURRENCY INTERVIEW TOPIC — DO NOT SKIP!
 * 
 * PROBLEM:
 * --------
 * 
 * Part A — Create a Deadlock:
 * 1. Create two resources: resourceA (Object) and resourceB (Object)
 * 2. Thread-1 locks resourceA, sleeps 100ms, then tries to lock resourceB
 * 3. Thread-2 locks resourceB, sleeps 100ms, then tries to lock resourceA
 * 4. Observe: both threads freeze forever (deadlock!)
 * 5. Print thread states using Thread.getState() after 2 seconds
 * 
 * Part B — Fix with Lock Ordering:
 * 6. Both threads lock in the SAME order (resourceA first, then resourceB)
 * 7. Demonstrate this eliminates the deadlock
 * 
 * Part C — Fix with tryLock (timeout strategy):
 * 8. Use ReentrantLock instead of synchronized
 * 9. Use tryLock(500, TimeUnit.MILLISECONDS)
 * 10. If lock not acquired, release all locks and retry
 * 11. Print "[ThreadName] Could not acquire lock, backing off..."
 * 
 * Part D — Demonstrate Livelock:
 * 12. Create two "polite" threads that keep yielding to each other:
 *     - Thread A: "After you!" → releases lock, retries
 *     - Thread B: "No, after you!" → releases lock, retries
 *     - Both keep "moving" but neither makes progress
 * 13. Add a maximum retry count to break the livelock
 * 
 * Part E — Demonstrate Starvation:
 * 14. Create a ReentrantLock with fairness=false (default)
 * 15. Create 1 "greedy" thread that repeatedly acquires and releases the lock
 * 16. Create 1 "starving" thread that tries to acquire the same lock
 * 17. Show the starving thread rarely gets access
 * 18. Fix with fairness=true and show difference
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Deadlock: Two+ threads each holding a lock the other needs
 * - 4 Coffman Conditions (ALL must be true for deadlock):
 *   1. Mutual Exclusion: resource held exclusively
 *   2. Hold and Wait: hold one resource, wait for another
 *   3. No Preemption: can't force a thread to release its lock
 *   4. Circular Wait: A waits for B, B waits for A
 * - Prevention: break any ONE condition (lock ordering breaks circular wait)
 * - Livelock: threads actively respond to each other but make no progress
 * - Starvation: a thread can never acquire the resource it needs
 * - jstack: JVM tool to dump thread states (shows BLOCKED threads)
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Deadlock Demo ===
 * [Thread-1] Locked resourceA, trying resourceB...
 * [Thread-2] Locked resourceB, trying resourceA...
 * [main] After 2s → Thread-1 state: BLOCKED
 * [main] After 2s → Thread-2 state: BLOCKED
 * ⚠️ DEADLOCK DETECTED!
 * 
 * === Part B: Lock Ordering Fix ===
 * [Thread-1] Acquired both locks successfully!
 * [Thread-2] Acquired both locks successfully!
 * ✓ No deadlock!
 * 
 * === Part C: tryLock Fix ===
 * [Thread-1] Could not acquire lock, backing off...
 * [Thread-2] Acquired both locks!
 * ✓ No deadlock — timeout strategy worked!
 * 
 * === Part D: Livelock Demo ===
 * [Polite-1] After you!
 * [Polite-2] No, after you!
 * ... (repeats until max retries)
 * 
 * === Part E: Starvation Demo ===
 * [Greedy] acquired lock 95 times
 * [Starving] acquired lock 5 times ← STARVED!
 * With fairness=true:
 * [Greedy] acquired lock 50 times
 * [Starving] acquired lock 50 times ← FAIR!
 */

// Write your solution below:

