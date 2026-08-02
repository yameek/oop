import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * SOLUTION 2: Garbage Collection & Reference Types
 * ================================================
 * Demonstrates WeakReference, SoftReference, PhantomReference with ReferenceQueue, and GC beans telemetry.
 */
public class Solution02GarbageCollection {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. Demonstrating WeakReference Behavior ===");
        testWeakReference();

        System.out.println("\n=== 2. Demonstrating SoftReference Behavior ===");
        testSoftReference();

        System.out.println("\n=== 3. Demonstrating PhantomReference & ReferenceQueue ===");
        testPhantomReference();

        System.out.println("\n=== 4. Active Garbage Collector Telemetry ===");
        printGCTelemetry();
    }

    private static void testWeakReference() throws InterruptedException {
        Object payload = new Object();
        WeakReference<Object> weakRef = new WeakReference<>(payload);

        System.out.println("Before clearing strong ref -> weakRef.get(): " + weakRef.get());
        payload = null; // Clear strong reference

        System.out.println("Triggering explicit Garbage Collection (System.gc())...");
        System.gc();
        Thread.sleep(100);

        System.out.println("After GC -> weakRef.get(): " + weakRef.get() + " (Expected: null)");
    }

    private static void testSoftReference() throws InterruptedException {
        Object payload = new Object();
        SoftReference<Object> softRef = new SoftReference<>(payload);

        System.out.println("Before clearing strong ref -> softRef.get(): " + softRef.get());
        payload = null; // Clear strong reference

        System.out.println("Triggering explicit Garbage Collection (System.gc())...");
        System.gc();
        Thread.sleep(100);

        System.out.println("After GC under normal memory conditions -> softRef.get(): " + softRef.get() 
                           + " (Expected: Non-null, retained until memory pressure)");
    }

    private static void testPhantomReference() throws InterruptedException {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object target = new Object();
        PhantomReference<Object> phantomRef = new PhantomReference<>(target, queue);

        System.out.println("PhantomReference.get() is ALWAYS null: " + phantomRef.get());
        System.out.println("Initial queue.poll(): " + queue.poll());

        target = null; // Remove strong reference
        System.gc();
        Thread.sleep(150);

        Object enqueuedRef = queue.poll();
        System.out.println("After GC -> Enqueued in ReferenceQueue? " + (enqueuedRef != null));
        System.out.println("Polled object matches phantomRef? " + (enqueuedRef == phantomRef));
    }

    private static void printGCTelemetry() {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : gcBeans) {
            System.out.printf("GC Name: %-20s | Collections: %-4d | Total Time: %d ms%n",
                    gc.getName(), gc.getCollectionCount(), gc.getCollectionTime());
        }
    }
}
