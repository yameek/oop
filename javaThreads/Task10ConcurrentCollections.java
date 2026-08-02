/**
 * TASK 10: Concurrent Collections
 * =================================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Learn about: ConcurrentHashMap (lock striping, CAS), CopyOnWriteArrayList,
 *              BlockingQueue variants, ConcurrentLinkedQueue, ThreadLocal
 * 
 * PROBLEM:
 * --------
 * Build a "Real-Time Analytics System" using concurrent data structures.
 * 
 * Part A — ConcurrentHashMap (Word Counter):
 * 1. Create a word frequency counter using ConcurrentHashMap<String, Integer>
 * 2. Feed it text from 4 threads simultaneously
 * 3. Use atomic methods:
 *    - merge(key, 1, Integer::sum)     — atomic increment
 *    - compute(key, (k, v) -> ...)     — atomic compute
 *    - putIfAbsent(key, value)         — atomic insert-if-missing
 * 4. Compare with synchronized HashMap — show ConcurrentHashMap is faster
 * 5. In comments, explain HOW it works:
 *    - Java 7: Segment locking (16 segments, each with own lock)
 *    - Java 8+: CAS on bucket nodes + synchronized on bucket head
 *    - Multiple threads can write to DIFFERENT buckets simultaneously
 * 
 * Part B — CopyOnWriteArrayList (Event Listeners):
 * 1. Create an EventBus class with CopyOnWriteArrayList<Listener>
 * 2. Multiple threads add/remove listeners while events fire
 * 3. No ConcurrentModificationException! (unlike ArrayList)
 * 4. Explain trade-off: fast reads, SLOW writes (copies entire array)
 * 5. Best for: few writes, many reads (listener lists, config)
 * 
 * Part C — BlockingQueue (Producer-Consumer):
 * 1. Implement a log processing pipeline:
 *    - Producer threads generate log entries
 *    - Consumer threads process them
 * 2. Use ArrayBlockingQueue(capacity) — bounded, blocks when full
 * 3. Demonstrate the key methods:
 *    - put(): blocks if full
 *    - take(): blocks if empty
 *    - offer(e, timeout): returns false if timeout
 *    - poll(timeout): returns null if timeout
 * 4. Compare BlockingQueue types:
 *    | Type                   | Bounded? | Ordering   |
 *    |------------------------|----------|------------|
 *    | ArrayBlockingQueue     | Yes      | FIFO       |
 *    | LinkedBlockingQueue    | Optional | FIFO       |
 *    | PriorityBlockingQueue  | No       | Priority   |
 *    | SynchronousQueue       | 0 cap    | Hand-off   |
 * 
 * Part D — ConcurrentLinkedQueue (non-blocking):
 * 1. Create a task queue using ConcurrentLinkedQueue
 * 2. Multiple producers add tasks, multiple consumers poll
 * 3. Compare: BlockingQueue blocks, ConcurrentLinkedQueue returns null
 * 4. Use in a busy-wait loop (or prefer BlockingQueue for most cases)
 * 
 * Part E — ThreadLocal (per-thread state):
 * 1. Create a ThreadLocal<SimpleDateFormat> (DateFormat is NOT thread-safe!)
 * 2. Multiple threads format dates using their own copy
 * 3. Print thread name + formatted date
 * 4. IMPORTANT: call threadLocal.remove() to prevent memory leaks!
 * 5. Explain the problem with ThreadLocal + thread pools:
 *    - Thread pool reuses threads → ThreadLocal values persist
 *    - Must remove() in finally block to avoid stale data
 * 6. Mention: Java 25's ScopedValue fixes this (see Task 12)
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - ConcurrentHashMap: thread-safe without locking the entire map
 * - CopyOnWriteArrayList: thread-safe list, copies on every write
 * - BlockingQueue: the backbone of producer-consumer patterns
 * - ThreadLocal: each thread gets its own isolated copy (no sharing)
 * - When to use what:
 *   - Need fast map? → ConcurrentHashMap
 *   - Need thread-safe list, rarely write? → CopyOnWriteArrayList
 *   - Need producer-consumer? → BlockingQueue
 *   - Need per-thread state? → ThreadLocal (or ScopedValue in Java 25)
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: ConcurrentHashMap ===
 * Word frequencies: {java=15, thread=12, concurrent=8, ...}
 * ConcurrentHashMap time: 45ms
 * Synchronized HashMap time: 120ms
 * 
 * === Part B: CopyOnWriteArrayList ===
 * No ConcurrentModificationException! ✓
 * Listeners notified: 100, Modifications: 10
 * 
 * === Part C: BlockingQueue ===
 * [Producer-1] put: LogEntry{...}
 * [Consumer-1] took: LogEntry{...}
 * 
 * === Part E: ThreadLocal ===
 * [Thread-1] 2025-07-30
 * [Thread-2] 2025-07-30
 * (Each thread used its OWN SimpleDateFormat instance)
 */

// Write your solution below:

