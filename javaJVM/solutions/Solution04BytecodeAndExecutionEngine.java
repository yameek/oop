import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * SOLUTION 4: Bytecode & Execution Engine (MethodHandles, VarHandles, Indy)
 * ======================================================================
 * Demonstrates type-safe MethodHandle invocation, VarHandle atomic CAS, and invokedynamic mechanics.
 */
public class Solution04BytecodeAndExecutionEngine {

    public static class Calculator {
        private int counter = 100;

        public static int square(int input) {
            return input * input;
        }

        public String multiply(int a, int b) {
            return "Result: " + (a * b);
        }

        public int getCounter() {
            return counter;
        }
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("=== 1. MethodHandle Invocation (Static Method) ===");
        testStaticMethodHandle();

        System.out.println("\n=== 2. MethodHandle Invocation (Instance Method) ===");
        testInstanceMethodHandle();

        System.out.println("\n=== 3. VarHandle Atomic Memory Operation (CAS) ===");
        testVarHandle();
    }

    private static void testStaticMethodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class);
        MethodHandle mh = lookup.findStatic(Calculator.class, "square", mt);

        int result = (int) mh.invoke(5);
        System.out.println("Invoked Calculator.square(5) via MethodHandle -> Result: " + result);
    }

    private static void testInstanceMethodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        MethodHandle mh = lookup.findVirtual(Calculator.class, "multiply", mt);

        Calculator calc = new Calculator();
        String result = (String) mh.invoke(calc, 6, 7);
        System.out.println("Invoked calc.multiply(6, 7) via MethodHandle -> Result: " + result);
    }

    private static void testVarHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Calculator.class, MethodHandles.lookup());
        VarHandle vh = lookup.findVarHandle(Calculator.class, "counter", int.class);

        Calculator calc = new Calculator();
        System.out.println("Initial counter value                       : " + calc.getCounter());

        boolean casSuccess = vh.compareAndSet(calc, 100, 250);
        System.out.println("VarHandle.compareAndSet(calc, 100, 250)    : " + casSuccess);
        System.out.println("Updated counter value                       : " + calc.getCounter());

        int prevVal = (int) vh.getAndAdd(calc, 50);
        System.out.println("VarHandle.getAndAdd(calc, 50) Returned Old  : " + prevVal);
        System.out.println("Final counter value after getAndAdd         : " + calc.getCounter());
    }
}
