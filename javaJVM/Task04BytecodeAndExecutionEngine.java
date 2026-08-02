import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * TASK 4: Bytecode & Execution Engine (MethodHandles, VarHandles, Indy)
 * ======================================================================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. Java Stack Machine Architecture:
 *    - Bytecode instructions execute on an Operand Stack within each Stack Frame.
 *    - Opcodes push operands onto stack (`iload`, `aload`, `bipush`) and compute results (`iadd`, `imul`).
 * 2. Invoke Bytecode Instructions:
 *    - `invokevirtual`: Standard instance method call (polymorphic lookup).
 *    - `invokestatic`: Static method call.
 *    - `invokespecial`: Constructor `<init>`, `private` method, or `super` call (direct non-polymorphic lookup).
 *    - `invokeinterface`: Interface method call.
 *    - `invokedynamic` (Indy): Dynamic callsite linking determined at runtime via Bootstrap Method (BSM).
 * 3. MethodHandles API (`java.lang.invoke`):
 *    - Strongly-typed, directly executable reference to a underlying method/field.
 *    - Faster and safer than Reflection (`java.lang.reflect`) because security checks are done ONCE during lookup.
 * 4. VarHandles API (`java.lang.invoke.VarHandle`):
 *    - High-performance, type-safe replacement for `sun.misc.Unsafe` for atomic operations (`compareAndSet`, `getAndSet`).
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You will build a JVM Execution Engine Inspector demonstrating MethodHandles and VarHandles:
 * 
 * Requirements:
 * 1. Use `MethodHandles.lookup()` to find and invoke a private or public method dynamically without standard reflection.
 * 2. Use `MethodHandles.Lookup.findStatic()` to invoke a static utility method `square(int x)`.
 * 3. Create a `VarHandle` targeting an `int counter` field and perform atomic Compare-And-Swap (`compareAndSet`).
 * 4. Understand how Java Lambdas compile to `invokedynamic` call sites with `LambdaMetafactory`.
 * 
 * TO RUN:
 * javac Task04BytecodeAndExecutionEngine.java && java Task04BytecodeAndExecutionEngine
 */
public class Task04BytecodeAndExecutionEngine {

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

    /**
     * TODO: Implement testStaticMethodHandle()
     * 1. Obtain MethodHandles.Lookup lookup = MethodHandles.lookup();
     * 2. Define MethodType mt = MethodType.methodType(int.class, int.class);
     * 3. MethodHandle mh = lookup.findStatic(Calculator.class, "square", mt);
     * 4. Invoke mh.invoke(5) and print result (25).
     */
    private static void testStaticMethodHandle() throws Throwable {
        // TODO: Implement static method handle call
    }

    /**
     * TODO: Implement testInstanceMethodHandle()
     * 1. MethodType mt = MethodType.methodType(String.class, int.class, int.class);
     * 2. MethodHandle mh = MethodHandles.lookup().findVirtual(Calculator.class, "multiply", mt);
     * 3. Invoke mh.invoke(new Calculator(), 6, 7) and print result.
     */
    private static void testInstanceMethodHandle() throws Throwable {
        // TODO: Implement instance method handle call
    }

    /**
     * TODO: Implement testVarHandle()
     * 1. VarHandle vh = MethodHandles.lookup().findVarHandle(Calculator.class, "counter", int.class);
     * 2. Calculator calc = new Calculator();
     * 3. Perform vh.compareAndSet(calc, 100, 250);
     * 4. Print updated counter value.
     */
    private static void testVarHandle() throws Throwable {
        // TODO: Implement VarHandle atomic CAS operation
    }
}
