import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * TASK 6: Off-Heap & Native Memory Engineering
 * =============================================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. On-Heap vs Off-Heap Memory:
 *    - On-Heap: Managed by JVM GC. Safe, but incurs GC pause overhead and memory copy during I/O.
 *    - Off-Heap: Allocated directly from OS C-heap (native memory). Bypasses GC pauses completely; ideal for huge caches & zero-copy I/O.
 * 2. ByteBuffer.allocateDirect():
 *    - Off-heap allocation in `java.nio`. Uses native OS memory, freed when DirectByteBuffer cleaner is GC'd.
 * 3. sun.misc.Unsafe Off-Heap Memory Management:
 *    - Native allocation via `Unsafe.allocateMemory(bytes)`.
 *    - Requires manual deallocation via `Unsafe.freeMemory(address)` to avoid catastrophic C-heap native memory leaks!
 * 4. Foreign Function & Memory API (Project Panama - Java 22+ LTS):
 *    - `Arena` & `MemorySegment` safe off-heap abstractions (available in Java 22+ or Java 21 with `--enable-preview`).
 * 5. Native Memory Tracking (NMT):
 *    - Enabled via `-XX:NativeMemoryTracking=summary` to monitor C-heap allocations (`jcmd <pid> VM.native_memory`).
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You will build an Off-Heap Memory Allocator comparing Direct ByteBuffers and Unsafe/Native Off-Heap Memory:
 * 
 * Requirements:
 * 1. Allocate a Direct ByteBuffer (10 MB), write integer data to it, read it back, and verify `isDirect() == true`.
 * 2. Obtain `sun.misc.Unsafe` via reflection (`theUnsafe` field).
 * 3. Allocate 1,000 bytes of native off-heap memory using `unsafe.allocateMemory(bytes)`.
 * 4. Write long/int data to native memory addresses, read it back, and ALWAYS call `unsafe.freeMemory(address)` in a `finally` block!
 * 
 * TO RUN:
 * javac Task06OffHeapAndNativeMemory.java && java Task06OffHeapAndNativeMemory
 */
public class Task06OffHeapAndNativeMemory {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Direct ByteBuffer (NIO Off-Heap Allocation) ===");
        testDirectByteBuffer();

        System.out.println("\n=== 2. Native Memory Allocation via sun.misc.Unsafe ===");
        testUnsafeOffHeapMemory();
    }

    /**
     * TODO: Implement testDirectByteBuffer()
     * 1. Allocate 10 MB direct buffer: `ByteBuffer directBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);`
     * 2. Put int values: `directBuffer.putInt(42);`
     * 3. Flip buffer and read back values: `directBuffer.flip(); int val = directBuffer.getInt();`
     * 4. Check `directBuffer.isDirect()` boolean.
     */
    private static void testDirectByteBuffer() {
        // TODO: Implement direct buffer test
    }

    /**
     * TODO: Implement testUnsafeOffHeapMemory()
     * 1. Fetch Unsafe instance via reflection from Unsafe.class.getDeclaredField("theUnsafe")
     * 2. long address = unsafe.allocateMemory(1024);
     * 3. Write data using unsafe.putInt(address, 9999);
     * 4. Read data using unsafe.getInt(address);
     * 5. Always free memory in finally block: `unsafe.freeMemory(address);`
     */
    private static void testUnsafeOffHeapMemory() throws Exception {
        // TODO: Implement native memory allocation test
    }
}
