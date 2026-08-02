# 🧵 Java Threads & Concurrency — Learning Track
*Master multithreading from basics to Java 25 LTS modern concurrency*

**Target: Java 25 LTS**

---

## 📚 Prerequisites

Before starting this track, you should be comfortable with:
- Java OOP basics (classes, interfaces, inheritance) — see [javaOOP/](../javaOOP/)
- Lambda expressions and functional interfaces
- Basic exception handling (`try-catch-finally`)

---

## 🚀 How to Run

```bash
cd javaThreads

# Run a task (after writing your solution)
javac Task01ThreadCreation.java && java Task01ThreadCreation

# Run a solution
javac solutions/Solution01ThreadCreation.java && java -cp solutions Solution01ThreadCreation

# For Task 12 (Java 25 features — virtual threads, ScopedValue, etc.)
javac solutions/Solution12VirtualThreadsAndModernJava.java && java -cp solutions Solution12VirtualThreadsAndModernJava
```

---

## 📖 Learning Path

### 🟢 Fundamentals (Start Here)
| # | Task | What You'll Learn |
|---|------|-------------------|
| 1 | **Thread Creation & Lifecycle** | `Thread`, `Runnable`, lambda threads, lifecycle states, `join()`, `sleep()`, daemon threads, interruption |
| 2 | **Synchronization Basics** | `synchronized`, race conditions, critical sections, intrinsic locks, reentrancy |

### 🟡 Intermediate
| # | Task | What You'll Learn |
|---|------|-------------------|
| 3 | **Deadlock, Livelock & Starvation** | The 4 deadlock conditions, prevention strategies, livelock, starvation |
| 4 | **Volatile & Atomics** | `volatile`, memory visibility, `AtomicInteger`, CAS, Java Memory Model |
| 5 | **ReentrantLock & ReadWriteLock** | `Lock`, `tryLock()`, fairness, `ReadWriteLock`, `StampedLock` |
| 6 | **Thread Communication** | `wait()`/`notify()`, Producer-Consumer, `Condition` variables |

### 🔴 Advanced
| # | Task | What You'll Learn |
|---|------|-------------------|
| 7 | **Executor Framework** | `ExecutorService`, thread pools, `Callable`/`Future`, shutdown patterns |
| 8 | **CompletableFuture** | Async chaining, `thenApply`, `thenCombine`, exception handling |
| 9 | **Synchronization Aids** | `Semaphore`, `CountDownLatch`, `CyclicBarrier`, `Phaser` |
| 10 | **Concurrent Collections** | `ConcurrentHashMap`, `BlockingQueue`, `CopyOnWriteArrayList`, `ThreadLocal` |

### ⚡ Modern Java (Expert)
| # | Task | What You'll Learn |
|---|------|-------------------|
| 11 | **ForkJoinPool & Parallel Streams** | `ForkJoinPool`, `RecursiveTask`, work-stealing, parallel streams |
| 12 | **Virtual Threads & Modern Java 25** | Virtual threads, `StructuredTaskScope`, `ScopedValue`, `Gatherers.mapConcurrent()` |

---

## 💡 Tips for Concurrency Learning

1. **Run solutions multiple times** — threading bugs are non-deterministic; output may vary between runs
2. **Add `Thread.sleep()` calls** — slowing things down makes race conditions easier to observe
3. **Read the console carefully** — thread interleaving produces different output each time
4. **Don't skip Task 3 (Deadlock)** — it's the #1 interview topic for concurrency
5. **Use `jconsole` or `jstack`** — for visualizing thread states and detecting deadlocks

---

## 📞 Resources

| Resource | Link |
|----------|------|
| Java Concurrency in Practice (book) | Brian Goetz — the bible of Java threading |
| JEP 444: Virtual Threads | https://openjdk.org/jeps/444 |
| JEP 453: Structured Concurrency | https://openjdk.org/jeps/462 |
| JEP 446: Scoped Values | https://openjdk.org/jeps/464 |
| Oracle Concurrency Tutorial | https://docs.oracle.com/javase/tutorial/essential/concurrency/ |
