/**
 * TASK 9: Synchronization Aids
 * ==============================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Learn about: Semaphore, CountDownLatch, CyclicBarrier, Phaser, Exchanger
 * 
 * PROBLEM:
 * --------
 * Build several mini-systems that demonstrate each synchronization aid.
 * 
 * Part A — Semaphore (Connection Pool):
 * 1. Create a class ConnectionPool with:
 *    - A Semaphore with 3 permits (max 3 simultaneous connections)
 *    - getConnection(): semaphore.acquire(), return connection
 *    - releaseConnection(): semaphore.release()
 * 2. Start 8 threads that each try to get a connection
 * 3. Each thread holds the connection for 1 second
 * 4. Observe: only 3 threads connect at a time, others wait
 * 5. Print "[Thread-N] Connected (available: X)" / "[Thread-N] Waiting..."
 * 
 * Part B — CountDownLatch (Service Startup):
 * 1. Simulate a microservice system that requires 3 services to start:
 *    - Database, Cache, MessageQueue (each takes 1-3 seconds)
 * 2. Create CountDownLatch(3)
 * 3. Each service thread calls latch.countDown() when ready
 * 4. Main thread calls latch.await() — blocks until count reaches 0
 * 5. Print "All services ready! Application starting..."
 * 6. Note: CountDownLatch is ONE-SHOT (cannot be reset)
 * 
 * Part C — CyclicBarrier (Parallel Computation):
 * 1. Simulate a parallel matrix computation:
 *    - 4 worker threads each compute a section
 *    - All must finish before merging results
 * 2. Create CyclicBarrier(4, () -> System.out.println("Phase complete!"))
 * 3. Each worker calls barrier.await() when done with its section
 * 4. Run 3 phases — barrier is REUSABLE (unlike CountDownLatch)
 * 5. Print "[Worker-N] Phase X complete, waiting at barrier..."
 * 
 * Part D — CountDownLatch vs CyclicBarrier comparison:
 * 1. In comments, explain the differences:
 *    | Feature       | CountDownLatch | CyclicBarrier    |
 *    |---------------|----------------|------------------|
 *    | Reusable?     | NO (one-shot)  | YES (cyclic)     |
 *    | Who waits?    | One thread     | All participants |
 *    | Who counts?   | Any thread     | Participants     |
 *    | Barrier action| No             | Yes (Runnable)   |
 * 
 * Part E — Phaser (flexible replacement):
 * 1. Create a Phaser for 3 participants
 * 2. Run 3 phases, but in phase 2, one participant leaves (deregister)
 * 3. Use arriveAndAwaitAdvance() for waiting
 * 4. Use arriveAndDeregister() for leaving
 * 5. Show phase numbers advancing: 0, 1, 2...
 * 
 * Part F — Exchanger (two-thread data swap):
 * 1. Create two threads that exchange data using Exchanger<String>
 * 2. Thread-A sends "Data from A", Thread-B sends "Data from B"
 * 3. After exchange: A has B's data, B has A's data
 * 4. Use exchanger.exchange(myData) — blocks until partner arrives
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Semaphore: controls access to N permits (like a parking lot with N spots)
 *   - acquire() decrements permit, blocks if 0
 *   - release() increments permit, wakes a waiting thread
 * - CountDownLatch: "wait for N events" (one-shot countdown to zero)
 * - CyclicBarrier: "N threads wait for each other" (reusable rendezvous)
 * - Phaser: flexible barrier — dynamic registration/deregistration, phases
 * - Exchanger: two threads swap data at a meeting point
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Semaphore ===
 * [Thread-1] Connected (permits: 2)
 * [Thread-2] Connected (permits: 1)
 * [Thread-3] Connected (permits: 0)
 * [Thread-4] Waiting for connection...
 * [Thread-1] Released connection
 * [Thread-4] Connected!
 * 
 * === Part B: CountDownLatch ===
 * [Database] Started in 2s ✓ (count: 2)
 * [Cache] Started in 1s ✓ (count: 1)
 * [MessageQueue] Started in 3s ✓ (count: 0)
 * 🚀 All services ready! Application starting...
 * 
 * === Part C: CyclicBarrier ===
 * Phase 1: all workers done → merged!
 * Phase 2: all workers done → merged!
 * Phase 3: all workers done → merged!
 */

// Write your solution below:

