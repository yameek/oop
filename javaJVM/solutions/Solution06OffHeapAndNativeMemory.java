import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;

/**
 * SOLUTION 6: Off-Heap & Native Memory Engineering
 * =================================================
 * Demonstrates NIO Direct ByteBuffers and Unsafe native C-heap memory allocation and manual deallocation.
 */
public class Solution06OffHeapAndNativeMemory {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Direct ByteBuffer (NIO Off-Heap Allocation) ===");
        testDirectByteBuffer();

        System.out.println("\n=== 2. Native Memory Allocation via sun.misc.Unsafe ===");
        testUnsafeOffHeapMemory();
    }

    private static void testDirectByteBuffer() {
        int capacity = 10 * 1024 * 1024; // 10 MB
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(capacity);

        System.out.println("Allocated Direct ByteBuffer Size : " + (directBuffer.capacity() / (1024 * 1024)) + " MB");
        System.out.println("Is Buffer Direct Off-Heap?       : " + directBuffer.isDirect());

        // Write integer values into direct buffer
        directBuffer.putInt(100);
        directBuffer.putInt(200);
        directBuffer.putInt(300);

        // Prepare for reading
        directBuffer.flip();

        System.out.println("Reading int 1 from Direct Buffer : " + directBuffer.getInt());
        System.out.println("Reading int 2 from Direct Buffer : " + directBuffer.getInt());
        System.out.println("Reading int 3 from Direct Buffer : " + directBuffer.getInt());
    }

    private static void testUnsafeOffHeapMemory() throws Exception {
        // Fetch sun.misc.Unsafe singleton via Reflection
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        long bytesToAllocate = 1024; // 1 KB native memory
        long memoryAddress = unsafe.allocateMemory(bytesToAllocate);

        System.out.println("Allocated 1 KB Native Memory Address : 0x" + Long.toHexString(memoryAddress));

        try {
            // Write data directly into OS C-heap memory location
            unsafe.putInt(memoryAddress, 8888);
            unsafe.putLong(memoryAddress + 4, 9999999999L);

            // Read data back from raw native memory address
            int intVal = unsafe.getInt(memoryAddress);
            long longVal = unsafe.getLong(memoryAddress + 4);

            System.out.println("Read Int Value from Native Address   : " + intVal);
            System.out.println("Read Long Value from Native Address  : " + longVal);

        } finally {
            // Explicitly free C-heap native memory to prevent memory leak!
            unsafe.freeMemory(memoryAddress);
            System.out.println("Native Memory at 0x" + Long.toHexString(memoryAddress) + " FREED successfully!");
        }
    }
}
