import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.List;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 *                                                                               
 *              JAVA JVM ARCHITECTURE & GC TUNING - GETTING STARTED              
 *                          Target: Java 21+ / 25 LTS                            
 *                                                                               
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * Welcome! This track will take you deep into JVM execution, garbage collection,
 * class loading, JIT compilation, off-heap memory, and production profiling.
 * 
 * 📁 TRACK CONTENTS
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * 🟢 Task 01: JVM Memory Layout (Heap, Stack, Metaspace, TLAB)
 * 🟢 Task 02: Garbage Collection Algorithms (G1, ZGC, References)
 * 🟡 Task 03: Class Loading Mechanism (Delegation Model, Custom ClassLoader)
 * 🟡 Task 04: Bytecode & Execution Engine (javap, MethodHandles, VarHandles, Indy)
 * 🔴 Task 05: JIT Compiler & Optimizations (Tiered Comp, Escape Analysis, Inlining)
 * 🔴 Task 06: Off-Heap & Native Memory (Direct Buffers, Foreign Memory API / Panama)
 * 🔴 Task 07: JVM Diagnostics & OOM Troubleshooting (Thread/Heap Dumps, JFR)
 * 🟡 Task 08: Microbenchmarking with JMH (Dead Code Elimination, Blackhole)
 * 
 * 🚀 QUICK COMMANDS
 * ═══════════════════════════════════════════════════════════════════════════════
 * 
 * Compile and test a task:
 *   $ javac Task01JVMMemoryLayout.java && java Task01JVMMemoryLayout
 * 
 * Compile and run reference solution:
 *   $ javac solutions/Solution01JVMMemoryLayout.java && java -cp solutions Solution01JVMMemoryLayout
 * 
 * ═══════════════════════════════════════════════════════════════════════════════
 */
public class START_HERE {

    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("  🧠 JAVA JVM TRACK: CURRENT RUNTIME TELEMETRY");
        System.out.println("===================================================================");

        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        System.out.printf("  Java VM Name      : %s (%s)%n", runtimeBean.getVmName(), runtimeBean.getVmVersion());
        System.out.printf("  Java Spec Version : %s%n", System.getProperty("java.version"));
        System.out.printf("  VM Vendor         : %s%n", runtimeBean.getVmVendor());
        System.out.printf("  Uptime            : %d ms%n", runtimeBean.getUptime());
        
        System.out.println("\n  📊 Memory Metrics:");
        long heapUsedMB = memoryBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
        long heapMaxMB = memoryBean.getHeapMemoryUsage().getMax() / (1024 * 1024);
        long nonHeapUsedMB = memoryBean.getNonHeapMemoryUsage().getUsed() / (1024 * 1024);
        
        System.out.printf("  Heap Memory Used  : %d MB / Max %d MB%n", heapUsedMB, heapMaxMB);
        System.out.printf("  Non-Heap (Meta)   : %d MB%n", nonHeapUsedMB);

        System.out.println("\n  ♻️ Active Garbage Collectors:");
        for (GarbageCollectorMXBean gc : gcBeans) {
            System.out.printf("  - %-20s | Collections: %-5d | Total Time: %d ms%n", 
                              gc.getName(), gc.getCollectionCount(), gc.getCollectionTime());
        }

        System.out.println("\n  💡 Input Arguments Passed to JVM:");
        List<String> inputArgs = runtimeBean.getInputArguments();
        if (inputArgs.isEmpty()) {
            System.out.println("  (None - running with default JVM flags)");
        } else {
            for (String arg : inputArgs) {
                System.out.println("  - " + arg);
            }
        }

        System.out.println("\n===================================================================");
        System.out.println("  🎯 READY TO START? Open Task01JVMMemoryLayout.java to begin!");
        System.out.println("===================================================================");
    }
}
