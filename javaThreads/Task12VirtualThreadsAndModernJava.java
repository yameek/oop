/**
 * TASK 12: Virtual Threads & Modern Java (Java 25 LTS)
 * ======================================================
 * Difficulty: Expert ⭐⭐⭐⭐⭐
 * 
 * Learn about: Virtual threads, platform vs virtual, pinning, 
 *              StructuredTaskScope (finalized), ScopedValue (finalized),
 *              Stream Gatherers (Gatherers.mapConcurrent)
 * 
 * PREREQUISITES: Java 25+ (LTS)
 * 
 * PROBLEM:
 * --------
 * Build a "High-Concurrency API Gateway" using modern Java 25 features.
 * 
 * Part A — Virtual Thread Basics:
 * 1. Create 10,000 virtual threads (impossible with platform threads!)
 * 2. Each virtual thread sleeps 1 second (simulating I/O)
 * 3. Measure total time — should be ~1 second, not 10,000 seconds
 * 4. Three ways to create virtual threads:
 *    a. Thread.startVirtualThread(() -> ...)
 *    b. Thread.ofVirtual().name("vt-", 0).start(() -> ...)
 *    c. Executors.newVirtualThreadPerTaskExecutor()
 * 5. Print thread.isVirtual() to confirm
 * 
 * Part B — Platform vs Virtual Thread Comparison:
 * 1. Task: make 1000 simulated HTTP calls (each sleeps 100ms)
 * 2. Run with FixedThreadPool(100) — platform threads
 * 3. Run with newVirtualThreadPerTaskExecutor() — virtual threads
 * 4. Compare:
 *    - Execution time (virtual should be faster at high concurrency)
 *    - Memory usage (virtual threads use ~1KB vs ~1MB stack)
 * 5. When to use which:
 *    - Virtual: I/O-bound work (HTTP, DB, file I/O)
 *    - Platform: CPU-bound work (math, compression, encryption)
 *    - Virtual threads DON'T make CPU-bound code faster!
 * 
 * Part C — Pinning (the gotcha!):
 * 1. Demonstrate virtual thread pinning:
 *    - synchronized blocks PIN the virtual thread to its carrier thread
 *    - This defeats the purpose of virtual threads!
 * 2. Create a synchronized block that sleeps (simulates I/O):
 *    synchronized (lock) { Thread.sleep(1000); } // BAD — pins!
 * 3. Replace with ReentrantLock:
 *    lock.lock();
 *    try { Thread.sleep(1000); } // GOOD — no pinning!
 *    finally { lock.unlock(); }
 * 4. Run with -Djdk.tracePinnedThreads=short to detect pinning
 * 5. Rule: NEVER use synchronized for I/O in virtual threads → use ReentrantLock
 * 
 * Part D — Structured Concurrency (StructuredTaskScope, finalized in Java 25):
 * 1. Build an API that fetches user + orders + recommendations in parallel
 * 2. Use ShutdownOnFailure:
 *    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
 *        Subtask<User> user = scope.fork(() -> fetchUser(id));
 *        Subtask<List<Order>> orders = scope.fork(() -> fetchOrders(id));
 *        Subtask<List<String>> recs = scope.fork(() -> fetchRecommendations(id));
 *        scope.join();
 *        scope.throwIfFailed();
 *        return new Response(user.get(), orders.get(), recs.get());
 *    }
 * 3. If ANY subtask fails → all others are cancelled automatically
 * 4. Demonstrate: make fetchOrders throw → user and recs get cancelled
 * 
 * 5. Use ShutdownOnSuccess (first result wins):
 *    try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
 *        scope.fork(() -> fetchFromPrimary());
 *        scope.fork(() -> fetchFromFallback());
 *        scope.join();
 *        return scope.result();  // First successful result
 *    }
 * 
 * 6. Benefits over CompletableFuture:
 *    - Thread lifecycle is tied to the scope (no leaked threads)
 *    - Cancellation is automatic (no manual Future.cancel())
 *    - Thread dumps show parent-child relationships
 *    - Structured = easier to reason about
 * 
 * Part E — Scoped Values (ScopedValue, finalized in Java 25):
 * 1. Create a ScopedValue for the current user context:
 *    private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();
 * 2. Bind it and run code:
 *    ScopedValue.where(CURRENT_USER, "alice").run(() -> {
 *        handleRequest();  // All code here sees "alice"
 *    });
 * 3. Access it deep in the call chain:
 *    String user = CURRENT_USER.get();  // "alice"
 * 4. Show it's inherited by child threads in StructuredTaskScope
 * 5. Compare with ThreadLocal:
 *    | Feature                | ThreadLocal      | ScopedValue          |
 *    |------------------------|------------------|----------------------|
 *    | Mutability             | Mutable          | Immutable in scope   |
 *    | Cleanup                | Manual remove()  | Automatic            |
 *    | Memory with VT         | Wasteful         | Efficient            |
 *    | Thread pool safe?      | No (leaks!)      | Yes                  |
 *    | Child thread inherit?  | InheritableOnly  | Yes (structured)     |
 * 6. Use ScopedValue.where().call() for return values
 * 
 * Part F — Stream Gatherers (Gatherers.mapConcurrent, finalized in Java 25):
 * 1. Process a list of URLs concurrently using stream gatherers:
 *    List<String> results = urls.stream()
 *        .gather(Gatherers.mapConcurrent(10, url -> fetchContent(url)))
 *        .toList();
 * 2. The second argument (10) limits concurrency to 10 virtual threads
 * 3. Compare with:
 *    - Sequential stream: urls.stream().map(url -> fetch(url))
 *    - Manual parallelism: CompletableFuture + allOf
 * 4. Advantages:
 *    - Concise syntax
 *    - Built-in backpressure (maxConcurrency parameter)
 *    - Uses virtual threads under the hood
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Virtual threads: lightweight threads managed by JVM (not OS)
 *   - 1 million virtual threads = ~1GB RAM (vs 1TB for platform threads)
 *   - JVM multiplexes virtual threads onto a small pool of platform (carrier) threads
 *   - When a virtual thread blocks (I/O), it's unmounted from carrier → carrier is free
 * - Pinning: virtual thread stuck on carrier (synchronized blocks, native methods)
 * - StructuredTaskScope: parent-child thread relationship with automatic cleanup
 * - ScopedValue: immutable, scope-bound context — replaces ThreadLocal for virtual threads
 * - Gatherers.mapConcurrent: concurrent stream processing with backpressure
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part A: Virtual Thread Basics ===
 * Created 10,000 virtual threads in 1.1 seconds ✓
 * Thread.isVirtual(): true
 * 
 * === Part B: Platform vs Virtual ===
 * Platform (100 threads): 1000 calls in 1050ms
 * Virtual (unlimited):    1000 calls in 110ms  (9.5x faster!)
 * 
 * === Part C: Pinning ===
 * With synchronized: pinned! (slow)
 * With ReentrantLock: no pinning (fast)
 * 
 * === Part D: Structured Concurrency ===
 * User: alice, Orders: [order1, order2], Recs: [rec1, rec2]
 * Failure test: fetchOrders failed → user+recs cancelled automatically
 * 
 * === Part E: Scoped Values ===
 * [main] CURRENT_USER = alice
 * [child-task] CURRENT_USER = alice (inherited!)
 * 
 * === Part F: Stream Gatherers ===
 * Sequential: 10 URLs in 10.0s
 * mapConcurrent(10): 10 URLs in 1.0s ✓
 */

// Write your solution below:

