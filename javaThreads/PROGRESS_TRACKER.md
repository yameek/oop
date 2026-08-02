# 🧵 Java Threads — Progress Tracker

Track your progress through the Java Threads & Concurrency learning track.

---

## Tasks

| # | Task | Status | Date Started | Date Completed | Difficulty | Notes |
|---|------|--------|-------------|----------------|------------|-------|
| 1 | Thread Creation & Lifecycle | ⬜ Not Started | | | ⭐ | |
| 2 | Synchronization Basics | ⬜ Not Started | | | ⭐⭐ | |
| 3 | Deadlock, Livelock & Starvation | ⬜ Not Started | | | ⭐⭐⭐ | |
| 4 | Volatile & Atomics | ⬜ Not Started | | | ⭐⭐⭐ | |
| 5 | ReentrantLock & ReadWriteLock | ⬜ Not Started | | | ⭐⭐⭐ | |
| 6 | Thread Communication | ⬜ Not Started | | | ⭐⭐⭐⭐ | |
| 7 | Executor Framework | ⬜ Not Started | | | ⭐⭐⭐ | |
| 8 | CompletableFuture | ⬜ Not Started | | | ⭐⭐⭐⭐ | |
| 9 | Synchronization Aids | ⬜ Not Started | | | ⭐⭐⭐⭐ | |
| 10 | Concurrent Collections | ⬜ Not Started | | | ⭐⭐⭐⭐ | |
| 11 | ForkJoinPool & Parallel Streams | ⬜ Not Started | | | ⭐⭐⭐⭐ | |
| 12 | Virtual Threads & Modern Java 25 | ⬜ Not Started | | | ⭐⭐⭐⭐⭐ | |

---

## Concept Mastery Checklist

### Fundamentals
- [ ] I can create threads using Thread, Runnable, and lambdas
- [ ] I understand the 6 thread lifecycle states
- [ ] I know the difference between `start()` and `run()`
- [ ] I understand daemon threads vs user threads
- [ ] I can use `join()`, `sleep()`, and `interrupt()`

### Synchronization
- [ ] I can identify race conditions in code
- [ ] I understand `synchronized` methods vs blocks
- [ ] I can explain intrinsic locks and monitors
- [ ] I can identify and prevent deadlocks
- [ ] I understand the 4 Coffman conditions for deadlock

### Memory Model & Atomics
- [ ] I understand Java Memory Model (JMM) basics
- [ ] I know when to use `volatile` vs `synchronized`
- [ ] I can use `AtomicInteger` and CAS operations
- [ ] I understand happens-before relationships

### Locks
- [ ] I can use `ReentrantLock` with try-finally
- [ ] I understand `tryLock()` and `lockInterruptibly()`
- [ ] I know when to use `ReadWriteLock` vs `ReentrantLock`
- [ ] I understand `StampedLock` optimistic reads

### Thread Communication
- [ ] I can implement Producer-Consumer with wait/notify
- [ ] I know why `wait()` must be in a while-loop
- [ ] I understand `sleep()` vs `wait()`
- [ ] I can use `Condition` variables with locks

### Executor Framework
- [ ] I can choose the right thread pool type
- [ ] I understand `Callable` vs `Runnable`
- [ ] I can properly shut down an ExecutorService
- [ ] I can chain async operations with CompletableFuture

### Synchronization Aids
- [ ] I can use Semaphore for rate limiting
- [ ] I understand CountDownLatch vs CyclicBarrier
- [ ] I can implement a worker barrier pattern

### Concurrent Collections
- [ ] I know when to use ConcurrentHashMap vs synchronized map
- [ ] I understand BlockingQueue for producer-consumer
- [ ] I know when ThreadLocal is appropriate

### Modern Java (25 LTS)
- [ ] I can create and use virtual threads
- [ ] I understand platform vs virtual thread tradeoffs
- [ ] I can use StructuredTaskScope for structured concurrency
- [ ] I understand ScopedValue as ThreadLocal replacement
- [ ] I can use Gatherers.mapConcurrent() for concurrent streams

---

## Self-Assessment

After completing all tasks, rate yourself (1-5):

| Area | Rating | Notes |
|------|--------|-------|
| Thread basics | /5 | |
| Synchronization | /5 | |
| Deadlock understanding | /5 | |
| Memory model | /5 | |
| Executor framework | /5 | |
| CompletableFuture | /5 | |
| Concurrent collections | /5 | |
| Modern Java concurrency | /5 | |
| Overall confidence | /5 | |
