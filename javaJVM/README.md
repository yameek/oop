# 🧠 Java JVM Architecture, GC & Memory Tuning — Learning Track
*Master JVM execution, garbage collection, JIT compilation, diagnostics, and performance engineering*

**Target: Java 21+ LTS / Java 25 LTS**

---

## 📚 Track Overview

This track is designed to give you deep, production-grade knowledge of the Java Virtual Machine (JVM). Understanding how the JVM executes bytecode, allocates and reclaims memory, compiles code at runtime, and exposes diagnostic telemetry is what distinguishes Senior and Staff Java Engineers.

---

## 🚀 How to Run

```bash
cd javaJVM

# Compile and run track entry point
javac START_HERE.java && java START_HERE

# Run a task template (implement your solution here)
javac Task01JVMMemoryLayout.java && java Task01JVMMemoryLayout

# Run a reference solution
javac solutions/Solution01JVMMemoryLayout.java && java -cp solutions Solution01JVMMemoryLayout
```

---

## 📖 Learning Path

### 🟢 Core Architecture & Memory
| # | Task | Key Concepts Covered | Difficulty | Interview Relevance |
|---|------|----------------------|------------|---------------------|
| 1 | **JVM Memory Layout** | Heap (Eden, Survivor S0/S1, Old Gen), Stack (Stack Frames, Local Variable Table), Metaspace, Native Memory, TLABs, `MemoryMXBean` | ⭐⭐ Easy-Medium | 🔴 High — Performance tuning |
| 2 | **Garbage Collection Algorithms** | Mark-Sweep-Compact, Generational Hypothesis, G1GC (Regions, IHOP), ZGC (Colored Pointers, Load Barriers), Reference Types (`Weak`, `Soft`, `Phantom`) | ⭐⭐⭐ Medium | 🔴 High — FAANG favorite |
| 3 | **Class Loading Mechanism** | Bootstrap, Platform, App ClassLoaders, Parent Delegation Model, Loading/Linking/Initialization phases, Custom ClassLoader | ⭐⭐⭐ Medium | 🟡 Medium — Framework internals |

### 🟡 Execution Engine & Bytecode
| # | Task | Key Concepts Covered | Difficulty | Interview Relevance |
|---|------|----------------------|------------|---------------------|
| 4 | **Bytecode & Execution Engine** | Java Stack Machine, `javap` disassembly, `invokevirtual` vs `invokestatic`, MethodHandles, VarHandles, `invokedynamic` (Indy) | ⭐⭐⭐⭐ Hard | 🔴 High — Deep JVM mechanics |
| 5 | **JIT Compiler & Optimizations** | Tiered Compilation (C1/C2), On-Stack Replacement (OSR), Escape Analysis, Scalar Replacement, Method Inlining, Lock Elision | ⭐⭐⭐⭐ Hard | 🔴 High — Micro-optimizations |

### 🔴 Memory Engineering & Troubleshooting
| # | Task | Key Concepts Covered | Difficulty | Interview Relevance |
|---|------|----------------------|------------|---------------------|
| 6 | **Off-Heap & Native Memory** | Direct ByteBuffers, `sun.misc.Unsafe` vs Java 22+ Foreign Function & Memory API (`Arena`, `MemorySegment`), NMT (`-XX:NativeMemoryTracking`) | ⭐⭐⭐⭐ Hard | 🔴 High — High-throughput systems |
| 7 | **JVM Diagnostics & OOM Troubleshooting** | Diagnosing the 5 OOM types, Heap Dump analysis (`jmap`), Thread Dumps (`jstack`/deadlock detection), JDK Flight Recorder (JFR) | ⭐⭐⭐⭐ Hard | 🔴 High — Production debugging |
| 8 | **Microbenchmarking with JMH** | Writing reliable microbenchmarks, preventing Dead-Code Elimination (`Blackhole`), avoiding Constant Folding, `@State`, `@Benchmark` | ⭐⭐⭐ Medium | 🟡 Medium — Performance testing |

---

## 🛠️ Essential JVM Tuning Flags Quick Sheet

```bash
# Heap Sizing
-Xms4g -Xmx4g -XX:NewRatio=2 -XX:SurvivingRatio=8

# Garbage Collectors
-XX:+UseG1GC -XX:MaxGCPauseMillis=200
-XX:+UseZGC -XX:+ZGenerational
-XX:+UseParallelGC

# GC Diagnostics & Logging
-Xlog:gc*,gc+phases=debug:file=gc.log:time,uptime,pid:filecount=5,filesize=100M

# Crash & OOM Analysis
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/dumps/oom.hprof
-XX:+CrashOnOutOfMemoryError

# JIT Compiler Diagnostics
-XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
```

---

## 📞 Recommended Resources

| Resource | Author / Link | Description |
|----------|---------------|-------------|
| **JVM Anatomy Quarks** | Aleksey Shipilëv | Bite-sized deep dives into JVM internals |
| **Java Performance: In-Depth Advice** | Scott Oaks (O'Reilly) | Definitive guide to JVM tuning and GC |
| **OpenJDK Source Code** | https://github.com/openjdk/jdk | Read actual C++ hotspot VM source code |
| **JMH Documentation** | https://github.com/openjdk/jmh | Official Java Microbenchmark Harness |
