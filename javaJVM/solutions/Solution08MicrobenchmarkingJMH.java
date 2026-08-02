/**
 * SOLUTION 8: Microbenchmarking with JMH Principles
 * =================================================
 * Demonstrates Dead-Code Elimination (DCE), Constant Folding prevention, Blackhole consumers, and warmup loops.
 */
public class Solution08MicrobenchmarkingJMH {

    public static void main(String[] args) {
        System.out.println("=== 1. Demonstrating Dead-Code Elimination (DCE) Pitfall ===");
        testDeadCodeElimination();

        System.out.println("\n=== 2. Demonstrating Constant Folding Pitfall ===");
        testConstantFolding();

        System.out.println("\n=== 3. Simulated Microbenchmark Runner with Warmup ===");
        runBenchmarkWithWarmup();
    }

    public static class Blackhole {
        public static volatile long dummySink;

        public static void consume(long value) {
            dummySink = value;
        }

        public static void consume(double value) {
            dummySink = Double.doubleToRawLongBits(value);
        }
    }

    private static void testDeadCodeElimination() {
        int iterations = 50_000_000;

        // Naive Loop: Unused return value (C2 compiler DCE target)
        long start1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            long temp = (long) i * i;
        }
        long duration1 = (System.nanoTime() - start1) / 1_000_000;
        System.out.printf("Naive Loop (Result Unused / DCE Risk)  : %4d ms%n", duration1);

        // Blackhole Loop: Result consumed to prevent DCE
        long start2 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            long temp = (long) i * i;
            Blackhole.consume(temp);
        }
        long duration2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.printf("Blackhole Loop (DCE Prevented)         : %4d ms%n", duration2);
    }

    private static void testConstantFolding() {
        int iterations = 30_000_000;

        // Constant Folding scenario: Compiler evaluates fixed input at compile time
        long start1 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            double res = Math.sin(3.1415926535);
            Blackhole.consume(res);
        }
        long duration1 = (System.nanoTime() - start1) / 1_000_000;
        System.out.printf("Constant Inputs (Constant Folding Risk): %4d ms%n", duration1);

        // Dynamic State scenario: Non-constant input prevents compile-time constant evaluation
        long start2 = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            double dynamicInput = i * 0.001;
            double res = Math.sin(dynamicInput);
            Blackhole.consume(res);
        }
        long duration2 = (System.nanoTime() - start2) / 1_000_000;
        System.out.printf("Dynamic State Inputs (Folding Prevented): %4d ms%n", duration2);
    }

    private static void runBenchmarkWithWarmup() {
        int warmupRounds = 3;
        int measurementRounds = 3;
        int opsPerRound = 20_000_000;

        System.out.println("Starting Benchmark Execution Harness...");

        // Warmup Phase
        for (int w = 1; w <= warmupRounds; w++) {
            long t0 = System.nanoTime();
            executeWorkload(opsPerRound);
            long elapsedMs = (System.nanoTime() - t0) / 1_000_000;
            System.out.printf("  [Warmup #%d] Execution Time: %d ms (JIT Compilation in Progress)%n", w, elapsedMs);
        }

        // Measurement Phase
        long totalMeasurementMs = 0;
        for (int m = 1; m <= measurementRounds; m++) {
            long t0 = System.nanoTime();
            executeWorkload(opsPerRound);
            long elapsedMs = (System.nanoTime() - t0) / 1_000_000;
            totalMeasurementMs += elapsedMs;
            System.out.printf("  [Measurement #%d] Execution Time: %d ms (Steady State)%n", m, elapsedMs);
        }

        double avgMs = (double) totalMeasurementMs / measurementRounds;
        double opsPerSec = (opsPerRound / (avgMs / 1000.0)) / 1_000_000.0;
        System.out.printf("%nAverage Steady-State Time : %.2f ms%n", avgMs);
        System.out.printf("Computed Throughput        : %.2f Million Ops/sec%n", opsPerSec);
    }

    private static void executeWorkload(int iterations) {
        for (int i = 0; i < iterations; i++) {
            double val = Math.hypot(i, i + 1);
            Blackhole.consume(val);
        }
    }
}
