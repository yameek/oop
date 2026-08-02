# 🧵 Java Threads — Quick Reference (Java 25 LTS)

A concise cheat sheet for Java concurrency syntax and patterns.

---

## 1. Thread Creation

```java
// Method 1: Extend Thread
class MyThread extends Thread {
    @Override
    public void run() { System.out.println("Running"); }
}
new MyThread().start();

// Method 2: Implement Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() { System.out.println("Running"); }
}
new Thread(new MyRunnable()).start();

// Method 3: Lambda (preferred)
Thread t = new Thread(() -> System.out.println("Running"));
t.start();

// Method 4: Virtual thread (Java 21+)
Thread.startVirtualThread(() -> System.out.println("Virtual!"));
Thread vt = Thread.ofVirtual().name("vt-1").start(() -> {});
```

## 2. Thread Lifecycle States

```
NEW ──start()──▶ RUNNABLE ──run() ends──▶ TERMINATED
                    │ ▲
        wait()      │ │  notify()
        sleep()     ▼ │  time expires
              WAITING / TIMED_WAITING
                    │ ▲
    synchronized    │ │  lock acquired
                    ▼ │
                  BLOCKED
```

## 3. Key Thread Methods

```java
t.start();                    // Start thread (calls run() in new thread)
t.join();                     // Wait for thread to finish
t.join(1000);                 // Wait max 1 second
Thread.sleep(500);            // Pause current thread 500ms
t.interrupt();                // Request thread interruption
t.isInterrupted();            // Check (non-clearing)
Thread.interrupted();         // Check AND clear flag
t.setDaemon(true);            // Must call before start()
Thread.currentThread();       // Get current thread reference
t.isAlive();                  // Is thread still running?
Thread.yield();               // Hint to scheduler (rarely used)
```

## 4. Synchronized

```java
// Synchronized method (locks 'this')
public synchronized void method() { /* critical section */ }

// Synchronized static method (locks Class object)
public static synchronized void staticMethod() { /* ... */ }

// Synchronized block (preferred — finer control)
synchronized (lockObject) {
    // critical section
}
```

## 5. Volatile & Atomics

```java
// Volatile — visibility guarantee, NO atomicity for compound ops
private volatile boolean flag = false;

// AtomicInteger — atomic compound operations
AtomicInteger counter = new AtomicInteger(0);
counter.incrementAndGet();        // ++counter (atomic)
counter.getAndIncrement();        // counter++ (atomic)
counter.compareAndSet(5, 10);     // CAS: if 5 → set 10
counter.addAndGet(3);             // counter += 3

// AtomicReference
AtomicReference<String> ref = new AtomicReference<>("hello");
ref.compareAndSet("hello", "world");
```

## 6. ReentrantLock

```java
ReentrantLock lock = new ReentrantLock();     // Non-fair (default)
ReentrantLock fairLock = new ReentrantLock(true); // Fair

// Always use try-finally!
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}

// tryLock — non-blocking
if (lock.tryLock(1, TimeUnit.SECONDS)) {
    try { /* ... */ }
    finally { lock.unlock(); }
} else {
    // lock not acquired
}

// ReadWriteLock
ReadWriteLock rwLock = new ReentrantReadWriteLock();
rwLock.readLock().lock();    // Multiple readers allowed
rwLock.writeLock().lock();   // Exclusive write access
```

## 7. Wait / Notify

```java
synchronized (lock) {
    while (!condition) {       // ALWAYS use while, not if (spurious wakeups)
        lock.wait();           // Releases lock, thread goes WAITING
    }
    // condition is true, proceed
}

synchronized (lock) {
    condition = true;
    lock.notifyAll();          // Wake all waiting threads (prefer over notify())
}
```

### sleep() vs wait()

| | `sleep()` | `wait()` |
|---|-----------|----------|
| Releases lock? | ❌ No | ✅ Yes |
| Requires synchronized? | ❌ No | ✅ Yes |
| Called on | `Thread.sleep()` | `object.wait()` |
| Woken by | Timer | `notify()`/`notifyAll()` |

## 8. Condition Variables (with Lock)

```java
ReentrantLock lock = new ReentrantLock();
Condition notEmpty = lock.newCondition();
Condition notFull  = lock.newCondition();

lock.lock();
try {
    while (isEmpty) notEmpty.await();      // Like wait()
    notFull.signal();                      // Like notify()
    notFull.signalAll();                   // Like notifyAll()
} finally {
    lock.unlock();
}
```

## 9. Executor Framework

```java
// Thread Pool Types
ExecutorService fixed   = Executors.newFixedThreadPool(4);
ExecutorService cached  = Executors.newCachedThreadPool();
ExecutorService single  = Executors.newSingleThreadExecutor();
ScheduledExecutorService sched = Executors.newScheduledThreadPool(2);
ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor(); // Java 21+

// Submit tasks
executor.execute(runnable);            // Fire-and-forget
Future<String> f = executor.submit(callable);  // Returns Future
String result = f.get();               // Blocking wait
String result = f.get(5, TimeUnit.SECONDS);    // Timeout

// Proper shutdown
executor.shutdown();                   // No new tasks, finish existing
executor.shutdownNow();                // Interrupt running tasks
executor.awaitTermination(10, TimeUnit.SECONDS);

// Scheduled tasks
sched.schedule(task, 5, TimeUnit.SECONDS);         // One-shot delay
sched.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);  // Repeat
```

## 10. CompletableFuture

```java
// Create
CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "Hello");

// Chain (non-blocking)
cf.thenApply(s -> s + " World")          // Transform result
  .thenAccept(System.out::println)       // Consume result
  .thenRun(() -> System.out.println("Done")); // Side effect

// Compose (flatMap)
cf.thenCompose(s -> fetchAsync(s));      // Chain async operations

// Combine two futures
cf1.thenCombine(cf2, (a, b) -> a + b);

// Wait for all / any
CompletableFuture.allOf(cf1, cf2, cf3).join();
CompletableFuture.anyOf(cf1, cf2, cf3).join();

// Exception handling
cf.exceptionally(ex -> "fallback")
  .handle((result, ex) -> ex != null ? "error" : result);
```

## 11. Synchronization Aids

```java
// Semaphore — limit concurrent access
Semaphore sem = new Semaphore(3);        // 3 permits
sem.acquire();    // Block until permit available
sem.release();    // Return permit

// CountDownLatch — one-shot barrier (count down to zero)
CountDownLatch latch = new CountDownLatch(3);
latch.countDown();                       // Decrement count
latch.await();                           // Block until count == 0

// CyclicBarrier — reusable barrier (wait for N threads)
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All arrived"));
barrier.await();                         // Block until N threads call await()

// Phaser — flexible phasing
Phaser phaser = new Phaser(3);
phaser.arriveAndAwaitAdvance();          // Arrive and wait for others
phaser.arriveAndDeregister();            // Leave the phaser
```

## 12. Concurrent Collections

```java
// Thread-safe Map (lock striping, CAS)
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.putIfAbsent("key", 1);
map.compute("key", (k, v) -> v + 1);    // Atomic update
map.merge("key", 1, Integer::sum);      // Atomic merge

// Thread-safe List (snapshot on write)
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

// Blocking Queues (producer-consumer)
BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
queue.put("item");                       // Block if full
String item = queue.take();              // Block if empty

// ThreadLocal
ThreadLocal<SimpleDateFormat> tl = ThreadLocal.withInitial(
    () -> new SimpleDateFormat("yyyy-MM-dd")
);
tl.get();                                // Get thread-local value
tl.remove();                             // Clean up (important!)
```

## 13. ForkJoinPool

```java
// RecursiveTask<V> — returns a value
class SumTask extends RecursiveTask<Long> {
    @Override
    protected Long compute() {
        if (size <= THRESHOLD) return directSum();
        SumTask left  = new SumTask(/* left half */);
        SumTask right = new SumTask(/* right half */);
        left.fork();                      // Submit to pool
        return right.compute() + left.join(); // Compute right, wait for left
    }
}
ForkJoinPool pool = new ForkJoinPool();
long result = pool.invoke(new SumTask(data));

// Parallel Streams (uses common ForkJoinPool)
list.parallelStream().filter(...).map(...).collect(...);
```

## 14. Virtual Threads (Java 21+, finalized)

```java
// Create virtual threads
Thread.startVirtualThread(() -> doWork());
Thread vt = Thread.ofVirtual().name("vt").start(() -> doWork());

// Virtual thread executor
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> handleRequest());
}

// Check if virtual
Thread.currentThread().isVirtual();

// ⚠️ PINNING: avoid synchronized in virtual threads — use ReentrantLock!
// synchronized blocks pin the virtual thread to the carrier (platform) thread
```

## 15. Structured Concurrency (Java 25 LTS, finalized)

```java
// ShutdownOnFailure — cancel all if any subtask fails
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Subtask<String> user  = scope.fork(() -> fetchUser());
    Subtask<String> order = scope.fork(() -> fetchOrder());
    scope.join();              // Wait for both
    scope.throwIfFailed();     // Propagate exceptions
    return user.get() + order.get();
}

// ShutdownOnSuccess — return first successful result
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> fetchFromSource1());
    scope.fork(() -> fetchFromSource2());
    scope.join();
    return scope.result();     // First successful result
}
```

## 16. Scoped Values (Java 25 LTS, finalized)

```java
// ScopedValue — modern replacement for ThreadLocal (especially with virtual threads)
private static final ScopedValue<String> USER = ScopedValue.newInstance();

// Bind and run
ScopedValue.where(USER, "alice").run(() -> {
    System.out.println(USER.get());  // "alice"
    handleRequest();                  // All code in this scope sees "alice"
});

// With return value
String result = ScopedValue.where(USER, "bob").call(() -> {
    return processFor(USER.get());
});

// Why prefer over ThreadLocal?
// - Immutable within scope (no accidental mutation)
// - Automatically cleaned up (no memory leaks)
// - Efficient with virtual threads (no per-thread storage overhead)
// - Inherited by child threads in StructuredTaskScope
```

## 17. Stream Gatherers (Java 25 LTS, finalized)

```java
// mapConcurrent — process stream elements concurrently
import java.util.stream.Gatherers;

List<String> results = urls.stream()
    .gather(Gatherers.mapConcurrent(10, url -> fetch(url)))  // 10 concurrent tasks
    .toList();
```

---

## 🔥 Common Interview Patterns

### Deadlock — 4 Coffman Conditions
1. **Mutual Exclusion** — resource held exclusively
2. **Hold and Wait** — hold one, wait for another
3. **No Preemption** — can't force release
4. **Circular Wait** — A→B→C→A

**Prevention**: Break any one condition (usually: lock ordering or tryLock with timeout)

### Producer-Consumer Template
```java
BlockingQueue<Item> queue = new LinkedBlockingQueue<>(capacity);
// Producer: queue.put(item);
// Consumer: Item item = queue.take();
```

### Thread-Safe Singleton (Bill Pugh)
```java
public class Singleton {
    private Singleton() {}
    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }
    public static Singleton getInstance() { return Holder.INSTANCE; }
}
```
