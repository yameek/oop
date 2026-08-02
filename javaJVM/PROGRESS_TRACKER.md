# 📊 Java JVM Track — Progress Tracker

Track your progress as you work through the tasks. Mark `[x]` when completed!

---

## 🟢 Core Architecture & Memory

- [ ] **Task 01: JVM Memory Layout**
  - **Topics:** Heap (Eden, Survivor S0/S1, Old Gen), Stack (Stack Frames, Local Variable Table), Metaspace, Native Memory, TLABs.
  - **File:** `Task01JVMMemoryLayout.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

- [ ] **Task 02: Garbage Collection Algorithms**
  - **Topics:** Mark-Sweep-Compact, Generational Hypothesis, G1GC, ZGC, Reference Types (`Weak`, `Soft`, `Phantom`), GC tuning.
  - **File:** `Task02GarbageCollection.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

- [ ] **Task 03: Class Loading Mechanism**
  - **Topics:** Bootstrap, Platform, App ClassLoaders, Parent Delegation Model, Custom ClassLoader, dynamic bytecode loading.
  - **File:** `Task03ClassLoadingMechanism.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

---

## 🟡 Execution Engine & Bytecode

- [ ] **Task 04: Bytecode & Execution Engine**
  - **Topics:** Java Stack Machine, `javap` inspection, MethodHandles, VarHandles, `invokedynamic` (Indy) & Bootstrap Methods.
  - **File:** `Task04BytecodeAndExecutionEngine.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

- [ ] **Task 05: JIT Compiler & Optimizations**
  - **Topics:** Tiered Compilation (C1/C2), On-Stack Replacement (OSR), Escape Analysis, Scalar Replacement, Method Inlining.
  - **File:** `Task05JITCompilerAndOptimizations.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

---

## 🔴 Memory Engineering & Troubleshooting

- [ ] **Task 06: Off-Heap & Native Memory**
  - **Topics:** Direct ByteBuffers, `sun.misc.Unsafe` vs Java 22+ Foreign Memory API (`Arena`, `MemorySegment`), NMT tracking.
  - **File:** `Task06OffHeapAndNativeMemory.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

- [ ] **Task 07: JVM Diagnostics & OOM Troubleshooting**
  - **Topics:** 5 OOM types, Heap Dump analysis (`jmap`), Thread Dumps (`jstack`/deadlocks), JFR/JMC telemetry.
  - **File:** `Task07JVMDiagnosticsAndOOMTroubleshooting.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete

- [ ] **Task 08: Microbenchmarking with JMH**
  - **Topics:** JMH principles, avoiding Dead-Code Elimination (`Blackhole`), preventing Constant Folding, `@State`, `@Benchmark`.
  - **File:** `Task08MicrobenchmarkingJMH.java`
  - **Status:** ⬜ Not Started | 🟨 In Progress | ✅ Complete
