/**
 * TASK 8: Microbenchmarking with JMH Principles
 * =============================================
 * Difficulty: Medium ⭐⭐⭐
 * 
 * Key Concepts:
 * ------------
 * 1. Pitfalls of Microbenchmarking in Java:
 *    - Dead-Code Elimination (DCE): C2 compiler removes loops or method calls if computed values are never read.
 *    - Constant Folding: C2 compiler evaluates constant inputs at compile-time (e.g. `Math.sin(3.14)` replaced by constant).
 *    - JIT Warmup: Measuring during Interpreter / C1 phase produces misleading results.
 *    - Loop Unrolling & Branch Prediction: Microbenchmarks may not mirror production branch prediction dynamics.
 * 2. JMH (Java Microbenchmark Harness) Core Mechanisms:
 *    - `Blackhole`: Consumes calculated values to guarantee JIT compiler cannot eliminate code as unused (DCE prevention).
 *    - `@State`: Encloses state variables so inputs are non-constant, preventing compile-time evaluation.
 *    - `@Warmup` & `@Measurement`: Controls warmup iterations vs measurement iterations.
 *    - `@BenchmarkMode`: Configures metrics (`Throughput` ops/sec, `AverageTime` ns/op, `SampleTime`).
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You will build a JMH principle simulator demonstrating how to avoid benchmarking flaws:
 * 
 * Requirements:
 * 1. Simulate Dead-Code Elimination (DCE): Demonstrate how a naive loop calculation gets eliminated by C2 compiler
 *    versus a Blackhole-protected loop that forces execution.
 * 2. Simulate Constant Folding: Show how fixed input calculations get pre-computed versus non-constant state inputs.
 * 3. Implement a mini microbenchmark harness runner executing warmup iterations before measurement.
 * 
 * TO RUN:
 * javac Task08MicrobenchmarkingJMH.java && java Task08MicrobenchmarkingJMH
 */
public class Task08MicrobenchmarkingJMH {

    public static void main(String[] args) {
        System.out.println("=== 1. Demonstrating Dead-Code Elimination (DCE) Pitfall ===");
        testDeadCodeElimination();

        System.out.println("\n=== 2. Demonstrating Constant Folding Pitfall ===");
        testConstantFolding();

        System.out.println("\n=== 3. Simulated Microbenchmark Runner with Warmup ===");
        runBenchmarkWithWarmup();
    }

    /**
     * Simulated Blackhole consumer class mirroring org.openjdk.jmh.infra.Blackhole
     */
    public static class Blackhole {
        public static volatile long dummySink;

        public static void consume(long value) {
            dummySink = value; // Volatile store prevents compiler from eliminating calculation
        }

        public static void consume(double value) {
            dummySink = Double.doubleToRawLongBits(value);
        }
    }

    /**
     * TODO: Implement testDeadCodeElimination()
     * 1. Run naiveLoop() where result is unused (C2 eliminates loop).
     * 2. Run blackholeLoop() where result is passed to Blackhole.consume().
     * 3. Print execution times and explain difference.
     */
    private static void testDeadCodeElimination() {
        // TODO: Implement DCE test
    }

    /**
     * TODO: Implement testConstantFolding()
     * 1. Run constantCalculation() computing Math.sqrt(256.0) repeatedly.
     * 2. Run dynamicCalculation(stateInput) computing Math.sqrt(nonConstantInput).
     * 3. Print execution times.
     */
    private static void testConstantFolding() {
        // TODO: Implement constant folding test
    }

    /**
     * TODO: Implement runBenchmarkWithWarmup()
     * Execute 3 warmup rounds followed by 3 measurement rounds of target workload.
     */
    private static void runBenchmarkWithWarmup() {
        // TODO: Implement benchmark harness runner
    }
}
