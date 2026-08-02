import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * TASK 2: Garbage Collection & Reference Types
 * ============================================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. Generational Hypothesis:
 *    - Weak Generational Hypothesis states that most objects die shortly after creation.
 *    - Young Gen (Eden) has frequent Minor GCs; surviving objects promote to Old Gen.
 * 2. Collector Architectures:
 *    - G1GC: Region-based, target pause times (-XX:MaxGCPauseMillis), concurrent marking.
 *    - ZGC / Shenandoah: Ultra-low latency (<1ms pause) using colored pointers and load barriers.
 * 3. Java Reference Types:
 *    - Strong Reference: Default (`Object obj = new Object()`). Never GC'd while reachable.
 *    - SoftReference: GC'd only when JVM is in high memory pressure / near OOM (useful for caches).
 *    - WeakReference: GC'd immediately during next GC cycle if no strong references exist (useful for WeakHashMap).
 *    - PhantomReference: Enqueued into ReferenceQueue after object is finalized; used for native cleanup.
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You will build a reference type demonstrator and GC behavior simulator:
 * 
 * Requirements:
 * 1. Create a `WeakReference` to an object, invoke `System.gc()`, and verify if the reference is cleared (`get() == null`).
 * 2. Create a `SoftReference` to an object, invoke `System.gc()`, and verify that the soft reference survives normal GC.
 * 3. Demonstrate `PhantomReference` registration with a `ReferenceQueue` and observe when the phantom reference gets enqueued.
 * 4. Print active GC Collectors and their accumulated collection count and pause times using `GarbageCollectorMXBean`.
 * 
 * TO RUN:
 * javac Task02GarbageCollection.java && java Task02GarbageCollection
 */
public class Task02GarbageCollection {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 1. Demonstrating WeakReference Behavior ===");
        testWeakReference();

        System.out.println("\n=== 2. Demonstrating SoftReference Behavior ===");
        testSoftReference();

        System.out.println("\n=== 3. Demonstrating PhantomReference & ReferenceQueue ===");
        testPhantomReference();
    }

    /**
     * TODO: Implement testWeakReference()
     * 1. Create a byte array payload inside a WeakReference: `WeakReference<byte[]> weakRef = new WeakReference<>(new byte[1024*1024]);`
     * 2. Verify weakRef.get() is not null.
     * 3. Invoke System.gc() and Thread.sleep(100).
     * 4. Assert/print whether weakRef.get() has become null.
     */
    private static void testWeakReference() throws InterruptedException {
        // TODO: Implement WeakReference behavior test
    }

    /**
     * TODO: Implement testSoftReference()
     * 1. Create a SoftReference: `SoftReference<byte[]> softRef = new SoftReference<>(new byte[1024*1024]);`
     * 2. Invoke System.gc() and Thread.sleep(100).
     * 3. Assert/print that softRef.get() is STILL non-null (because JVM is not under memory pressure).
     */
    private static void testSoftReference() throws InterruptedException {
        // TODO: Implement SoftReference behavior test
    }

    /**
     * TODO: Implement testPhantomReference()
     * 1. Create a ReferenceQueue<Object> queue = new ReferenceQueue<>();
     * 2. Create Object target = new Object();
     * 3. Create PhantomReference<Object> phantomRef = new PhantomReference<>(target, queue);
     * 4. Nullify strong ref: `target = null;`
     * 5. Trigger System.gc() and inspect queue.poll().
     */
    private static void testPhantomReference() throws InterruptedException {
        // TODO: Implement PhantomReference behavior test
    }
}
