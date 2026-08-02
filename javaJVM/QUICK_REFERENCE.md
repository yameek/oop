# ⚡ JVM Architecture & GC Tuning — Quick Reference & Cheat Sheet

---

## 📌 1. JVM Memory Structure Overview

```
+-------------------------------------------------------------------------------+
|                                JVM MEMORY LAYOUT                               |
+-------------------------------------------------------------------------------+
|                                 HEAP MEMORY                                   |
| +-----------------------------------------------+ +-------------------------+ |
| |              Young Generation                 | |    Old Generation       | |
| | +----------------+ +---------+ +------------+ | |     (Tenured Gen)     | |
| | | Eden Space     | | Survivor| | Survivor   | | |                         | |
| | |                | | S0      | | S1         | | |                         | |
| | +----------------+ +---------+ +------------+ | |                         | |
| +-----------------------------------------------+ +-------------------------+ |
+-------------------------------------------------------------------------------+
|                               NON-HEAP MEMORY                                 |
| +-----------------------------------+ +-------------------------------------+ |
| |             Metaspace             | |             Code Cache              | |
| | (Class Metadata, Constant Pool)   | | (JIT Compiled Native Code C1/C2)    | |
| +-----------------------------------+ +-------------------------------------+ |
+-------------------------------------------------------------------------------+
|                              NATIVE / OFF-HEAP                                |
| +-----------------------------------+ +-------------------------------------+ |
| |  Direct ByteBuffers / FFM Arena   | |  Thread Stacks (Xss * NumThreads)   | |
| +-----------------------------------+ +-------------------------------------+ |
+-------------------------------------------------------------------------------+
```

---

## ⚙️ 2. Essential JVM Tuning Flags

### Heap Allocation Flags
```bash
-Xms4g                            # Initial Heap Size (Set equal to Xmx in production)
-Xmx4g                            # Maximum Heap Size
-Xmn1536m                         # Young Generation Size
-XX:NewRatio=2                    # Old Gen / Young Gen ratio (2 means Young is 1/3 of Heap)
-XX:SurvivorRatio=8               # Eden / Survivor ratio (8 means Eden is 8/10 of Young)
-XX:MaxTenuringThreshold=15       # Max minor GCs before promoting object to Old Gen
-XX:MetaspaceSize=256m            # Initial Metaspace Size
-XX:MaxMetaspaceSize=512m         # Maximum Metaspace Limit
-Xss1m                            # Thread Stack Size (Default 1MB per thread)
```

### Garbage Collector Selection
```bash
-XX:+UseG1GC                      # G1GC (Default since Java 9 - General balance of throughput & latency)
-XX:+UseZGC -XX:+ZGenerational    # ZGC (Ultra-low latency <1ms pause times, scalable to TBs)
-XX:+UseShenandoahGC              # Shenandoah GC (Ultra-low latency concurrent GC)
-XX:+UseParallelGC                # Parallel / Throughput Collector (Max throughput, longer pauses)
-XX:+UseSerialGC                  # Serial GC (Single thread, suitable for small containers)
```

### Collector-Specific Tuning Flags
```bash
# G1GC Tuning
-XX:MaxGCPauseMillis=200          # Target max GC pause time target (default 200ms)
-XX:InitiatingHeapOccupancyPercent=45 # IHOP: Trigger concurrent marking stage when Old Gen is 45% full
-XX:G1HeapRegionSize=16m          # G1 Region Size (1MB to 32MB, power of 2)

# GC Logging (Unified JVM Logging - Java 9+)
-Xlog:gc*,gc+phases=debug:file=/var/log/gc.log:time,uptime,pid:filecount=5,filesize=100M
```

---

## 🛠️ 3. Command Line Diagnostic Tools

| Command | Purpose | Essential Example |
|---------|---------|-------------------|
| `jcmd` | All-in-one JVM control & diagnostic tool | `jcmd <pid> VM.flags` |
| `jstack` | Capture Java Thread Dumps (Deadlocks, High CPU) | `jstack -l <pid> > thread_dump.txt` |
| `jmap` | Capture Memory Heap Dumps & Histogram | `jmap -histo:live <pid>` or `jmap -dump:format=b,file=heap.hprof <pid>` |
| `jstat` | Real-time GC stats monitoring | `jstat -gcutil <pid> 1000 10` (every 1s for 10 times) |
| `jinfo` | View & dynamically update JVM flags | `jinfo -flag +PrintGCDetails <pid>` |
| `javap` | Java Class File Disassembler (Bytecode) | `javap -c -v -p MyClass.class` |

### Key `jcmd` Shortcuts
```bash
jcmd <pid> GC.heap_info                  # Inspect current heap generation sizes
jcmd <pid> GC.class_histogram            # Instant memory footprint by class
jcmd <pid> Thread.print                  # Dump thread stack traces
jcmd <pid> VM.native_memory summary      # Check NMT (Native Memory Tracking) stats
jcmd <pid> JFR.start name=MyRec duration=60s filename=profile.jfr # Record 60s JFR trace
```

---

## 💥 4. Troubleshooting OutOfMemoryErrors (OOM)

| Exception | Root Cause | Fix Strategy |
|-----------|------------|--------------|
| `java.lang.OutOfMemoryError: Java heap space` | Heap full due to leak or under-sized heap | Increase `-Xmx`, analyze heap dump with MAT/JMC for leaking collections |
| `java.lang.OutOfMemoryError: Metaspace` | Too many dynamic classes loaded (Proxy/Reflection leaks) | Increase `-XX:MaxMetaspaceSize`, check for ClassLoader leaks |
| `java.lang.OutOfMemoryError: Direct buffer memory` | Off-heap native memory exhausted (`ByteBuffer.allocateDirect`) | Increase `-XX:MaxDirectMemorySize`, check un-freed direct buffers |
| `java.lang.OutOfMemoryError: Unable to create new native thread` | OS process thread limit hit or native memory full | Reduce `-Xss` (stack size) or increase OS `ulimit -u` |
| `java.lang.OutOfMemoryError: GC overhead limit exceeded` | 98%+ time spent in GC recovering <2% heap | Optimize code, increase heap, eliminate memory allocations |
