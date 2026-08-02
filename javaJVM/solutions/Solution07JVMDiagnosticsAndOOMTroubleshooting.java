import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * SOLUTION 7: JVM Diagnostics & OOM Troubleshooting
 * =================================================
 * Demonstrates ThreadMXBean telemetry, deadlock detection routines, and OOM exception classification.
 */
public class Solution07JVMDiagnosticsAndOOMTroubleshooting {

    public static void main(String[] args) {
        System.out.println("=== 1. Inspecting Live Thread Telemetry ===");
        printThreadSummary();

        System.out.println("\n=== 2. Deadlock Detection Routine ===");
        detectDeadlocks();

        System.out.println("\n=== 3. OOM Error Classification & Remediation Advisor ===");
        diagnoseOOM(new OutOfMemoryError("Java heap space"));
        diagnoseOOM(new OutOfMemoryError("Metaspace"));
        diagnoseOOM(new OutOfMemoryError("Direct buffer memory"));
        diagnoseOOM(new OutOfMemoryError("Unable to create new native thread"));
    }

    private static void printThreadSummary() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        System.out.println("Live Thread Count        : " + threadBean.getThreadCount());
        System.out.println("Peak Thread Count        : " + threadBean.getPeakThreadCount());
        System.out.println("Daemon Thread Count      : " + threadBean.getDaemonThreadCount());
        System.out.println("Total Started Threads    : " + threadBean.getTotalStartedThreadCount());
        System.out.println("CPU Time Supported?      : " + threadBean.isThreadCpuTimeSupported());
    }

    private static void detectDeadlocks() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedIds = threadBean.findDeadlockedThreads();

        if (deadlockedIds == null || deadlockedIds.length == 0) {
            System.out.println("--> Deadlock Check: Clean! No deadlocked threads detected.");
        } else {
            System.out.println("--> DEADLOCK DETECTED! Affected Thread IDs: " + deadlockedIds.length);
            ThreadInfo[] threadInfos = threadBean.getThreadInfo(deadlockedIds);
            for (ThreadInfo info : threadInfos) {
                System.out.println("  Thread: " + info.getThreadName() + " blocked by " + info.getLockOwnerName());
            }
        }
    }

    private static void diagnoseOOM(OutOfMemoryError error) {
        String msg = error.getMessage();
        System.out.println("\n[ERROR DETECTED] " + error.getClass().getName() + ": " + msg);

        if (msg.contains("Java heap space")) {
            System.out.println("  🔍 Diagnosis   : Heap Memory Exhausted.");
            System.out.println("  💡 Remediation : 1. Check for memory leaks in static maps/caches.");
            System.out.println("                   2. Increase heap size with `-Xmx<size>`.");
            System.out.println("                   3. Pass `-XX:+HeapDumpOnOutOfMemoryError` to inspect with Eclipse MAT/JMC.");
        } else if (msg.contains("Metaspace")) {
            System.out.println("  🔍 Diagnosis   : Native Metaspace Limit Exhausted (Class Metadata).");
            System.out.println("  💡 Remediation : 1. Increase Metaspace limit with `-XX:MaxMetaspaceSize=<size>`.");
            System.out.println("                   2. Investigate ClassLoader leaks in dynamic proxy frameworks (CGLIB/ByteBuddy).");
        } else if (msg.contains("Direct buffer memory")) {
            System.out.println("  🔍 Diagnosis   : Native Direct Off-Heap Memory Exhausted (`ByteBuffer.allocateDirect`).");
            System.out.println("  💡 Remediation : 1. Increase direct memory limit with `-XX:MaxDirectMemorySize=<size>`.");
            System.out.println("                   2. Verify off-heap buffers are released/cleaned up properly.");
        } else if (msg.contains("Unable to create new native thread")) {
            System.out.println("  🔍 Diagnosis   : OS Native Thread Limit Hit or Process Out of Memory.");
            System.out.println("  💡 Remediation : 1. Reduce thread stack size with `-Xss<size>` (e.g. `-Xss512k`).");
            System.out.println("                   2. Use Virtual Threads (Java 21+) or Executor service thread pools.");
        } else {
            System.out.println("  🔍 Diagnosis   : Generic OOM Condition.");
            System.out.println("  💡 Remediation : Inspect GC logs and system native memory limits.");
        }
    }
}
