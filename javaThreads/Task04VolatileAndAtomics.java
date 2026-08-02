/**
 * TASK 4: Volatile & Atomics
 * ============================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Learn about: volatile keyword, memory visibility, AtomicInteger, AtomicReference,
 *              AtomicBoolean, CAS (Compare-And-Swap), Java Memory Model, happens-before
 * 
 * PROBLEM:
 * --------
 * 
 * Part A — Visibility Problem (without volatile):
 * 1. Create a class StopFlag with a boolean field 'running = true'
 * 2. Start a worker thread that loops while 'running' is true
 * 3. Main thread sleeps 1 second, then sets running = false
 * 4. Observe: worker thread MAY NEVER STOP (JIT caches the value in CPU register)
 *    - Add -server JVM flag to increase likelihood of this happening
 * 5. Fix by marking 'running' as volatile
 * 
 * Part B — Volatile is NOT enough for compound operations:
 * 6. Create a VolatileCounter with: volatile int count = 0
 * 7. Start 10 threads that each increment count 1000 times (count++)
 * 8. Expected total: 10,000. Actual: less than 10,000!
 * 9. Explain WHY: count++ is NOT atomic (read → increment → write = 3 steps)
 * 
 * Part C — Fix with AtomicInteger:
 * 10. Replace volatile int with AtomicInteger
 * 11. Use incrementAndGet() or getAndIncrement()
 * 12. Observe: result is always exactly 10,000
 * 13. Also demonstrate:
 *     - compareAndSet(expected, update): CAS operation
 *     - addAndGet(delta)
 *     - getAndUpdate(lambda)
 * 
 * Part D — AtomicReference (thread-safe object swap):
 * 14. Create an AtomicReference<String> holding a username
 * 15. Two threads try to update it: only the first CAS succeeds
 * 16. Print who won and who lost the race
 * 
 * Part E — Explain happens-before:
 * 17. In comments or print statements, explain:
 *     - What is the Java Memory Model (JMM)?
 *     - What does "happens-before" guarantee?
 *     - volatile write → volatile read (happens-before)
 *     - synchronized unlock → synchronized lock (happens-before)
 *     - thread.start() → first action in run() (happens-before)
 *     - last action in run() → thread.join() returns (happens-before)
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - CPU Cache: each thread may have its own cached copy of variables
 * - volatile: forces read/write from main memory (visibility), prevents reordering
 * - volatile does NOT guarantee atomicity for compound operations (i++, check-then-act)
 * - AtomicInteger uses CAS (Compare-And-Swap) — a CPU-level atomic instruction
 * - CAS: "if value is X, set it to Y" — no locks needed, lock-free programming
 * - JMM: defines rules for when one thread's writes become visible to another
 * - happens-before: if A happens-before B, then A's effects are visible to B
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Visibility Problem ===
 * [Worker] Running without volatile... (may hang!)
 * [Worker] Fixed with volatile — stopped correctly.
 * 
 * === Part B: Volatile Counter (broken) ===
 * Expected: 10000, Actual: 9847 ← NOT ATOMIC!
 * 
 * === Part C: AtomicInteger (fixed) ===
 * Expected: 10000, Actual: 10000 ✓
 * CAS demo: compareAndSet(10000, 0) → true
 * 
 * === Part D: AtomicReference ===
 * [Thread-1] CAS succeeded: alice → bob
 * [Thread-2] CAS failed: expected alice but was bob
 * 
 * === Part E: Happens-Before ===
 * (See printed explanations)
 */

// Write your solution below:

