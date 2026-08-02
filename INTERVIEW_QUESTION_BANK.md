# Comprehensive Interview Question Bank
*Based on Cross-Language OOP, Collections, and Framework Analysis*

This question bank takes you from basic proficiency to mastery (FAANG-level) with **detailed answers** for Java, Python, Go, and TypeScript.

---

## 🏗️ Part 1: Core OOP Concepts & Architecture

### 🟢 Basic
1.  **Define "Encapsulation". How is it strictly enforced in Java (modifiers) vs. how is it handled in Python (naming conventions)?**
    *   **Answer:** Encapsulation bundles data and methods while restricting direct access.
        *   **Java**: Strictly enforced by compiler using `private` (class only), `protected` (package + subclasses), `public`, and package-private (default).
        *   **Python**: Relies on convention. `_variable` is a hint for "internal use". `__variable` invokes *name mangling* (becoming `_ClassName__variable`) to prevent accidental overrides, but it is not true private access logic (can still be accessed if you know the mangled name).

2.  **What is Polymorphism? Explain the difference between compile-time (overloading) and runtime (overriding) polymorphism.**
    *   **Answer:** Polymorphism allows objects to be treated as instances of their parent class.
        *   **Compile-time (Overloading)**: Same method name, different signatures (params). Resolved by the compiler.
            *   *Note*: Python does **not** support overloading directly (last definition overwrites previous). You handle it via default arguments or `*args`.
        *   **Runtime (Overriding)**: Subclass provides specific implementation of a parent method. Resolved at runtime via dynamic dispatch (V-Table).

3.  **Inheritance vs. Composition:**
    *   **Why is "Composition over Inheritance" often recommended?**
        *   **Answer:** Inheritance creates tight coupling; changing the parent breaks children ("Fragile Base Class" problem). Composition allows flexible reuse by holding a reference to an object, enabling behavior changes at runtime.
    *   **How would you simulate inheritance in Go, which has no `extends` keyword?**
        *   **Answer:** **Embedding**. You include a struct inside another struct anonymously. The inner struct's methods are "promoted" to the outer struct, mimicking inheritance.
        ```go
        type Animal struct {}
        func (a *Animal) Move() {}
        type Dog struct { *Animal } // Dog now has Move()
        ```

4.  **Abstract Classes vs. Interfaces:**
    *   **In Java, when would you use an Abstract Class over an Interface?**
        *   **Answer:** Use Abstract Class when you need shared **state** (fields) or a common constructor implementation. Use Interfaces for defining **capabilities** (e.g., `Runnable`, `Comparable`) across unrelated classes.
    *   **How does TypeScript's `interface` differ from a Java `interface` in terms of runtime existence?**
        *   **Answer:** TS interfaces are **erased** at runtime; they exist only for compile-time checking. Java interfaces exist in the compiled bytecode (`.class`) and are checked at runtime.

### 🟡 Intermediate
5.  **The "Diamond Problem" (Multiple Inheritance):**
    *   **How does Python resolve this using the C3 Linearization Algorithm (MRO)?**
        *   **Answer:** Python allows multiple inheritance. It uses the C3 algorithm to create a consistent Method Resolution Order (MRO), ensuring a class appears after its parents and keeping the order of parent declaration. You can check `ClassName.mro()`.
    *   **Why does Java forbid multiple class inheritance but allow multiple interface inheritance?**
        *   **Answer:** To avoid state ambiguity (which `int x` to inherit?). Interfaces have no state (only constants and behaviors), so implementing multiple interfaces creates no memory layout conflicts.

6.  **Solid Principles (LSP):**
    *   **Give an example where a subclass violates LSP.**
        *   **Answer:** A `Square` class inheriting `Rectangle`. If you set `width`, `height` must change too. A function expecting a `Rectangle` might incorrectly assume changing width doesn't affect height.
    *   **How does duck-typing in Python interact with LSP?**
        *   **Answer:** Python doesn't check types, it checks *methods*. If an object "A" has the methods expected by function "F", it substitutes correctly, satisfying LSP implicitly even without a shared hierarchy.

7.  **Constructors:**
    *   **Go doesn't have constructors. What pattern does it use instead?**
        *   **Answer:** **Factory Functions**. Conventionally named `NewUser()` or `NewRequest()`.
    *   **How does the `super()` call work in Java vs Python?**
        *   **Answer:**
            *   **Java**: `super()` must be the **first** statement in the constructor. Static binding.
            *   **Python**: `super()` returns a proxy object that delegates method calls based on MRO. You can call it anywhere in the method.

### 🔴 Master (FAANG Level)
8.  **Memory Model & V-Tables:**
    *   **How do Java/C++ use V-Tables?**
        *   **Answer:** Each class with virtual methods has a hidden pointer (vptr) to a table of function pointers (vtable). At runtime, the program looks up the correct method address for the actual object type.
    *   **Go's interface implementation `(value, type)`:**
        *   **Answer:** An interface value in Go is a distinct `iface` struct containing two pointers: one to the type information (itab) and one to the concrete data. This structure allows dynamic dispatch without a traditional class hierarchy but incurs a small allocation/indirection cost.

9.  **Mixin Patterns:**
    *   **Compare Mixins in Python vs. TypeScript vs. Java.**
        *   **Python**: Uses Multiple Inheritance.
        *   **TypeScript**: Uses Function Composition (a function taking a class and returning a new class extending it) or Class Decorators.
        *   **Java**: Uses Interfaces with **Default Methods** (since Java 8) to provide mixin-like behavior (adding behavior without state).

10. **Design Patterns in Practice:**
    *   **Singleton in Go vs Java:**
        *   **Go**: Use `sync.Once`.
            ```go
            var once sync.Once
            once.Do(func() { instance = &MyType{} })
            ```
        *   **Java**: The `enum` Singleton is the safest (handles serialization/reflection attacks). Double-checked locking requires `volatile`.
    *   **Observer Pattern:**
        *   **Node.js**: The internal `EventEmitter` class is the core implementation.
        *   **Spring**: `ApplicationEventPublisher` and `@EventListener` annotations provide a synchronous (or async with `@Async`) observer model decoupled from the caller.

---

## 🐍☕🐹 Part 2: Language-Specific Deep Dives

### Java Enterprise
*   **Basic: Checked vs Unchecked Exceptions.**
    *   **Answer:** **Checked** (`IOException`) must be declared in `throws` or caught. Forces callers to handle errors. **Unchecked** (`RuntimeException`, `NullPointerException`) indicate programming errors. C#/Kotlin avoided checked exceptions because they often lead to verbose boilerplate (empty catch blocks) and versioning issues.
*   **Intermediate: HashMap Internals.**
    *   **Answer:** Array of "buckets". Key -> `hash(key)` -> index.
    *   **Collision**: Linked List in the bucket.
    *   **Java 8 Improvement**: If a bucket has > 8 elements, it converts the Linked List to a **Red-Black Tree** (O(n) -> O(log n) lookup).
*   **Master: Java Memory Model (JMM) & `happens-before`.**
    *   **Answer:** JMM defines how threads interact through memory. "Happens-before" guarantees that the result of an operation is visible to another. `volatile` ensures a variable is read/written directly to main memory (not CPU cache) and prevents the compiler/CPU from reordering instructions involving that variable.

### Python Internals
*   **Basic: `__str__` vs `__repr__`.**
    *   **Answer:** `__str__` is for end-users (readable). `__repr__` is for developers (ambiguity-free, ideally valid python code to recreate the object).
*   **Intermediate: Garbage Collection.**
    *   **Answer:** Primary mechanism is **Reference Counting** (immediate reclamation when count is 0). Secondary mechanism is **Cyclic GC** (generations 0, 1, 2) which periodically scans for reference cycles (A references B, B references A) that ref-counting misses.
*   **Master: Global Interpreter Lock (GIL).**
    *   **Answer:** A mutex that prevents multiple native threads from executing Python bytecodes at once.
    *   **Impact**: CPU-bound threads (math) won't run in parallel on multi-core. I/O-bound threads (network) are fine (GIL acts like a timeslice).
    *   **Bypass**: `multiprocessing` (separate processes, separate memory) or write critical code in C/C++.

### Go Systems
*   **Basic: Pointer vs Value Receiver.**
    *   **Answer:** Use **Pointer** (`func (s *Struct)`) if you need to mutate the struct or if the struct is large (copying is expensive). Use **Value** (`func (s Struct)`) for small immutable structs/logic safe for concurrency (copying avoids shared state).
*   **Intermediate: Implicit Interfaces.**
    *   **Pros**: Decoupling. You can define an interface for a 3rd party library without modifying their code (Dependency Inversion).
    *   **Cons**: Harder to find all implementers in an IDE. You might accidentally implement an interface.
*   **Master: Goroutine Scheduling (M:N).**
    *   **Answer:** Go runtime multiplexes **M** Goroutines onto **N** OS Threads.
    *   **P (Processor)**: A context for scheduling.
    *   **Work Stealing**: If a P runs out of goroutines, it steals half from another P.
    *   **Syscall blocking**: If a goroutine blocks (syscall), the OS thread blocks, but the Go runtime detaches the P and moves it to a new/idle OS thread to keep other goroutines running.

### TypeScript & JS Runtime
*   **Basic: `interface` vs `type`.**
    *   **Answer:** `interface` is better for objects/classes and supports **declaration merging** (adding properties to existing interface). `type` is more flexible for Unions, Intersections, and Primitives.
*   **Intermediate: Structural vs Nominal Typing.**
    *   **Answer:** **Structural** (TS/Go): If it fits the shape, it works. `{x:1}` fits `interface Point {x:number}`. **Nominal** (Java): Must explicitly be named (instance of) the type. Java rejects it even if fields match perfectly.
*   **Master: V8 Hidden Classes & Inline Caches.**
    *   **Hidden Classes**: V8 creates hidden C++ classes for JS objects behind the scenes.
    *   **Optimization**: If you add property `.z` to an object that previously only had `.x`, V8 creates a *transition* to a new hidden class.
    *   **Dynamic add**: `obj.newProp = 1` forces a transition, creating a slower lookup path. Defining all props in constructor creates a stable hidden class, allowing the engine to generate optimized machine code (Inline Caching).

---

## 🍃 vs 🦁 Part 3: Framework Architecture (Spring Boot vs NestJS)

### 🟢 Basic
1.  **Annotations vs Decorators:**
    *   **Answer:** Conceptually similar (metadata).
        *   **Spring Annotations**: Read via Reflection at runtime.
        *   **NestJS Decorators**: TS feature. Functions that wrap the class/method. Executed when the class is defined (load time).
2.  **Singleton Scope:**
    *   **Answer:** Singletons reduce memory overhead and instantiation cost (GC pressure). Critical for high-throughput servers.

### 🟡 Intermediate
3.  **Circular Dependencies:**
    *   **Spring**: Breaks the bean creation cycle. Uses `@Lazy` (inject a proxy, initialize real bean on first use).
    *   **NestJS**: Uses `forwardRef(() => Module)`. Wraps the injection in a function so the reference is resolved slightly later.
4.  **Middleware & Interceptors:**
    *   **Order (NestJS)**: Middleware (Express layer) -> Guards (Auth) -> Interceptors (Pre-logic) -> **Pipe** (Validation) -> Controller -> Service -> Interceptor (Post-logic) -> Filter (Exception).
    *   **Spring**: Filter (Servlet) -> Interceptor (Spring MVC) -> Controller -> AOP (Service).

### 🔴 Master
5.  **Performance: Spring Reflection vs NestJS Imports.**
    *   **Answer:** Spring's classpath scanning can be slow at startup (scanning thousands of classes). NestJS requires explicit `imports: [ModuleA]`. This makes NestJS startup instant/static but requires more manual wiring code.
6.  **Concurrency: Thread-per-request vs Event Loop.**
    *   **Scenario (Image Processing)**:
        *   **Spring**: It's fine. The thread blocks, but you have a pool of 200 threads. Other requests run on other threads.
        *   **NestJS**: **Disaster**. Image processing blocks the *Main Event Loop*. No other requests can be handled (server freezes).
        *   **Solution**: Offload to `Worker Threads` or a separate Microservice/Queue.

---

## 📚 Part 4: Data Structures & Collections

### 🟢 Basic
1.  **List vs Set:**
    *   **Answer:** `Set` uses Hashing (O(1)). `List` uses linear scan (O(n)).
2.  **Map Ordering:**
    *   **Java**: `HashMap` (Random), `LinkedHashMap` (Insertion Order), `TreeMap` (Sorted by Key).
    *   **JS/TS**: `Map` inherently preserves insertion order. `Object` keys generally do, but strictly only for non-integer keys in some older specs (modern engines preserve order).

### 🟡 Intermediate
3.  **Array Growth:**
    *   **Java ArrayList**: New array = Old size * 1.5. Copies elements.
    *   **JS V8 Array**: Starts as **Fixed Array** (C++). If you `push`, it allocates a larger chunk (appx 1.5x - 2x).
    *   **Dense vs Sparse**: `[1, 2, 3]` is Dense (packed memory). `a=[]; a[100]=1` is Sparse (implemented as a Hash Table/Dictionary, much slower access).
4.  **Priority Queues in TS:**
    *   **Answer:** TS has no built-in Binary Heap. You implement a `MinHeap` class using an Array, using formulas `left = 2*i + 1`, `parent = (i-1)/2` for bubbling up/down.

### 🔴 Master
5.  **Thread Safety:**
    *   **ConcurrentHashMap**: Uses **Lock Stripping** (segments) or CAS (Compare-And-Swap) on the specific bucket node. Multiple threads can write to *different* buckets simultaneously without locking the whole map.
    *   **Node.js**: No concurrent collections needed because user code runs on one thread. If using `Worker Threads`, memory is isolated (no shared objects) except for `SharedArrayButter`, which requires `Atomics` for safe access.

---

## ⚡ Part 5: Advanced Concurrency & Async Models

### 🟢 Basic
1.  **Sync vs Async (Blocking vs Non-Blocking):**
    *   **Answer:**
        *   **Blocking (Java/Spring MVC)**: `Thread` waits for DB query. Scale limited by RAM (thread stack size).
        *   **Non-Blocking (Node/Vert.x/WebFlux)**: `Thread` fires DB query and handles *other* requests. When DB replies, a callback/promise resumes logic. High concurrency with few threads.
2.  **Goroutine vs OS Thread:**
    *   **OS Thread**: Fixed stack (e.g., 1MB). Managed by Kernel. High context switch cost.
    *   **Goroutine**: Dynamic stack (starts at 2KB). Managed by Go Runtime (User space). Cheap context switch.

### 🟡 Intermediate
3.  **Future vs Promise:**
    *   **Answer:** Conceptually same (placeholder for result).
    *   **Python Async**: Python requires an explicitly started Event Loop (`asyncio.run()`). JS starts one by default. Python functions are just "generators" until `awaited`.
4.  **Go Channels:**
    *   **Unbuffered**: Blocking send/receive. Synchronizes sender/receiver (rendezvous).
    *   **Buffered**: Sender only blocks if buffer full.
    *   **Worker Pool**: Create a channel `jobs`. Spawn 5 goroutines that range loop over `jobs`.

### 🔴 Master
5.  **Concurrency Safety:**
    *   **Go Race Detector**: Compiler instuments memory access with "happens-before" checks. Runs at runtime. overhead ~10x.
    *   **Visibility**: Java threads have local memory/cache. `volatile` forces write-through to main memory. Without it, Thread A might loop forever checking `flag` while Thread B has already set `flag = true` in its own cache.
6.  **Event Loop Blocking:**
    *   **Node JSON.parse(50MB)**: It is CPU bound. It blocks the **Single Main Thread**. No health checks, no new connections, nothing works until strict parsing finishes.
    *   **Go Blocking Syscall**: Go runtime parks the *Thread* but moves context (P) to another thread. Other Goroutines keep running.

### ⚡ Java 25 LTS & Modern Concurrency Deep Dive
7.  **Virtual Threads vs. Platform Threads:**
    *   **What are Virtual Threads and how do they differ from OS platform threads?**
        *   **Answer**: Platform threads map 1-to-1 with kernel OS threads (high memory ~1MB, costly context switches). Virtual threads are lightweight user-mode threads managed by the JVM (~2KB stack, millions can run concurrently). When a virtual thread blocks on I/O, the JVM unmounts it from its underlying "carrier" platform thread, freeing the carrier thread to run other virtual threads.
    *   **Do Virtual Threads make CPU-bound computations faster?**
        *   **Answer**: **No**. Virtual threads provide high *throughput* for I/O-bound tasks (HTTP requests, DB queries), not raw computation speed. For CPU-bound tasks (encryption, video encoding), platform threads using the ForkJoinPool or traditional pools remain appropriate.

8.  **Virtual Thread Pinning:**
    *   **What is Virtual Thread "Pinning" and why does it happen?**
        *   **Answer**: Pinning occurs when a virtual thread cannot be unmounted from its carrier thread during a blocking operation. This happens when blocking inside a `synchronized` block/method or inside a native method call. When pinned, the carrier thread remains blocked, defeating the purpose of virtual threads.
    *   **How do you prevent Pinning in Java 25?**
        *   **Answer**: Replace `synchronized` blocks with `java.util.concurrent.locks.ReentrantLock`. `ReentrantLock` allows the JVM to unmount virtual threads while waiting for lock acquisition. Use `-Djdk.tracePinnedThreads=short` to detect pinned threads during runtime.

9.  **Structured Concurrency & Scoped Values (Java 25 Finalized):**
    *   **What is `StructuredTaskScope` and why is it superior to unstructured `CompletableFuture`?**
        *   **Answer**: Structured Concurrency treats sub-tasks running in separate threads as a single unit of work. `StructuredTaskScope` (`ShutdownOnFailure`, `ShutdownOnSuccess`) enforces that child threads are scoped to a `try-with-resources` block. If one sub-task fails, all sibling sub-tasks are automatically cancelled, preventing thread leaks. Thread dumps preserve parent-child hierarchy.
    *   **Why are `ScopedValue`s preferred over `ThreadLocal` in virtual thread applications?**
        *   **Answer**: `ThreadLocal` is mutable, un-scoped, and wasteful when millions of virtual threads exist (each keeping its own copy, risking memory leaks in thread pools). `ScopedValue` is immutable, bound to a specific lexical scope (automatic cleanup), and efficiently shared across structured sub-threads.

10. **Stream Gatherers (`Gatherers.mapConcurrent`):**
    *   **How does `Gatherers.mapConcurrent` improve concurrent stream processing in Java 25?**
        *   **Answer**: Traditional `parallelStream()` uses `ForkJoinPool.commonPool` without built-in concurrency caps or backpressure. `Gatherers.mapConcurrent(maxConcurrency, mapper)` processes stream items concurrently using virtual threads while enforcing an upper limit on active concurrency and managing backpressure natively within the Stream pipeline.

---

## 🏗️ Part 6: Applied Design Patterns & Architecture

### 🟢 Basic
1.  **Factory Pattern (Java vs Go):**
    *   **Answer:** Java forces everything into classes, so `Factory` class is needed. Go/Python support standalone functions, so `NewUser()` is cleaner than `UserFactory.create()`.
2.  **Decorator Pattern:**
    *   **Java**: Wrapper object implementing same interface. `new BufferedInputStream(new FileInputStream())`.
    *   **Python**: `@decorator` is Higher-Order Function syntax. It wraps the *function definition* at load time, changing the function itself permanently.

### 🟡 Intermediate
3.  **Dependency Inversion (Go vs Java):**
    *   **Answer:** Go uses **Consumer-Defined Interfaces**. You (the consumer) define what you need (`Reader`), not the library. Java libraries define interfaces (`UserRepo`) which you implement. Go's approach decouples dependencies completely.
4.  **Strategy Pattern (TS Types):**
    *   **Answer:** Instead of `Interface Strategy` + `Class A`, `Class B`, TS can just use a Union of strings (`'local' | 's3'`) or an object map of functions.
        ```ts
        const strategies = { local: uploadLocal, s3: uploadS3 };
        strategies[type](file);
        ```
        Zero OOP boilerplate.

### 🔴 Master
5.  **Proxy Pattern & Self-Invocation (Spring/NestJS):**
    *   **The Problem**: `@Transactional` or `@Cacheable` works via a Proxy wrapper around the class.
    *   **Trace**: `External -> Proxy -> RealClass.methodA()`.
    *   **Issue**: If `methodA()` calls `this.methodB()` (which is also transactional), it calls the *internal* reference, skipping the Proxy. Transaction logic for B is ignored.
    *   **Fix**: Self-inject the bean or use AspectJ weaving (rare).
6.  **Microservices Consistency (Saga):**
    *   **Distributed Transaction**: DB1 commits, DB2 fails. DB1 is already committed.
    *   **Saga**: A sequence of local transactions.
        *   Step 1: Order Service -> Create Order (Pending).
        *   Step 2: Inventory Service -> Reserve Item. Fails?
        *   Step 3 (**Compensation**): Order Service -> Cancel/Reject Order.
    *   **NestJS/Spring**: Both support this via Event Bus (RabbitMQ/Kafka) consumers triggering success or failure/compensation events.

---

## 🧠 Part 4: JVM Architecture, GC & Memory Tuning (🔥 Top FAANG Topic)

### 🟢 Basic
1.  **Explain the JVM Memory Layout (Heap, Stack, Metaspace, Native Memory).**
    *   **Answer:**
        *   **Heap**: Shared memory storing object instances. Divided into Young Gen (Eden, S0, S1) and Old Gen (Tenured).
        *   **Stack**: Per-thread execution stack storing Stack Frames (local variables, operand stack, frame data).
        *   **Metaspace**: Stores class metadata, bytecode, and constant pools in native C-heap memory (replaced PermGen in Java 8).
        *   **Native Memory**: Used by Direct ByteBuffers, C-heap allocations, and JIT Compiled Code Cache.

2.  **What is the difference between `System.gc()` and automatic Garbage Collection?**
    *   **Answer:** `System.gc()` sends a non-binding request to the JVM to run garbage collection. The JVM is not guaranteed to honor it immediately, or at all (and it can be disabled completely using `-XX:+DisableExplicitGC`).

### 🟡 Intermediate
3.  **Explain the Weak Generational Hypothesis and how G1GC leverages it.**
    *   **Answer:** The Generational Hypothesis states that most objects die shortly after creation. G1GC divides the heap into equal-sized regions (1MB to 32MB). It collects regions with the most garbage first ("Garbage First") during Young GCs, keeping pause times predictable while promoting long-lived objects to Old Gen regions.

4.  **What is the difference between `Class.forName("Foo")` and `ClassLoader.loadClass("Foo")`?**
    *   **Answer:** `Class.forName()` loads, links, AND initializes the class, triggering `<clinit>` static initializer blocks. `ClassLoader.loadClass()` only loads and links the class lazily, deferring static initialization until the class is instantiated or its static members are accessed.

5.  **Explain Escape Analysis and how the JIT compiler uses Scalar Replacement.**
    *   **Answer:** Escape Analysis determines if an object allocated inside a method escapes the thread or method scope. If it does NOT escape ("No Escape"), the C2 compiler replaces the object allocation with primitive scalar local variables directly on the stack (Scalar Replacement), avoiding heap allocation and GC pressure completely.

### 🔴 Master
6.  **How do you diagnose and resolve a `java.lang.OutOfMemoryError: Direct buffer memory`?**
    *   **Answer:**
        *   **Root Cause**: Native off-heap memory limit specified by `-XX:MaxDirectMemorySize` was hit by `ByteBuffer.allocateDirect()`.
        *   **Diagnosis**: Use `jcmd <pid> VM.native_memory` to monitor off-heap native allocations.
        *   **Resolution**: Check for unreleased `DirectByteBuffer` references, increase `-XX:MaxDirectMemorySize`, or migrate to Java 22+ Foreign Memory API (`Arena.ofConfined()`) for deterministic lifecycle management.

7.  **Why do standard timing loops (`System.nanoTime()`) fail for microbenchmarking, and how does JMH solve it?**
    *   **Answer:**
        *   **Pitfalls**: C2 compiler applies Dead-Code Elimination (DCE) to remove unused computation loops and Constant Folding to pre-compute fixed calculations.
        *   **JMH Solution**: Uses `Blackhole.consume()` to create volatile consumption side-effects (preventing DCE) and non-constant `@State` fields (preventing Constant Folding) with dedicated warmup iterations.

---

## 📌 Code Implementation References (Self-Study)
*Practical code examples in this workspace that demonstrate the concepts above.*

| Concept | Language | File Path |
|---------|----------|-----------|
| **Generics (Stack/Queue)** | Go | [golangOOP/solutions/solution_09_generics.go](golangOOP/solutions/solution_09_generics.go) |
| **Context Managers** | Python | [pythonOOP/solutions/solution_08_magic_methods.py](pythonOOP/solutions/solution_08_magic_methods.py) |
| **Iterators & Symbols** | TypeScript | [typescriptOOP/solutions/solution_08_symbols_iterators.ts](typescriptOOP/solutions/solution_08_symbols_iterators.ts) |
| **Design Patterns** | All | `*/solutions/solution_10_design_patterns.*` |
| **Java Concurrency Track (Tasks 01-12)** | Java (25 LTS) | [javaThreads/README.md](javaThreads/README.md) |
| **Virtual Threads & Java 25 Features** | Java | [javaThreads/solutions/Solution12VirtualThreadsAndModernJava.java](javaThreads/solutions/Solution12VirtualThreadsAndModernJava.java) |
| **Deadlocks & Lock Ordering** | Java | [javaThreads/solutions/Solution03DeadlockLivelockStarvation.java](javaThreads/solutions/Solution03DeadlockLivelockStarvation.java) |
| **CompletableFuture & Async Pipelines** | Java | [javaThreads/solutions/Solution08CompletableFuture.java](javaThreads/solutions/Solution08CompletableFuture.java) |
| **Java JVM Architecture Track (Tasks 01-08)** | Java | [javaJVM/README.md](javaJVM/README.md) |
| **JIT Escape Analysis & Scalar Replacement** | Java | [javaJVM/solutions/Solution05JITCompilerAndOptimizations.java](javaJVM/solutions/Solution05JITCompilerAndOptimizations.java) |
| **Off-Heap Memory & Native Allocation** | Java | [javaJVM/solutions/Solution06OffHeapAndNativeMemory.java](javaJVM/solutions/Solution06OffHeapAndNativeMemory.java) |
| **Collections Intro** | TS | [typescript-collections/COMPARISON.md](typescript-collections/COMPARISON.md) |
| **Dependency Injection** | MD | [spring-vs-nestjs/DI_DEPENDENCY_DEEP_DIVE.md](spring-vs-nestjs/DI_DEPENDENCY_DEEP_DIVE.md) |



