import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;

/**
 * SOLUTION 5: JIT Compiler & Runtime Optimization
 * ================================================
 * Demonstrates JIT compiler inspection, escape analysis, scalar replacement, and warmup performance curves.
 */
public class Solution05JITCompilerAndOptimizations {

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. JIT Compiler Information ===");
        printJITCompilerInfo();

        System.out.println("\n=== 2. Observing JIT Warmup & Escape Analysis Optimization ===");
        runJITWarmupBenchmark();
    }

    private static void printJITCompilerInfo() {
        CompilationMXBean compilationBean = ManagementFactory.getCompilationMXBean();
        if (compilationBean != null) {
            System.out.println("JIT Compiler Name        : " + compilationBean.getName());
            if (compilationBean.isCompilationTimeMonitoringSupported()) {
                System.out.println("Total JIT Compile Time   : " + compilationBean.getTotalCompilationTime() + " ms");
            }
        } else {
            System.out.println("JIT Compiler Info        : Not Available / Interpreter Only (-Xint)");
        }
    }

    private static void runJITWarmupBenchmark() {
        int iterationsPerRound = 30_000_000;
        int totalRounds = 5;

        System.out.printf("Executing %d rounds of %,d iterations per round...%n%n", totalRounds, iterationsPerRound);

        for (int round = 1; round <= totalRounds; round++) {
            long startTime = System.nanoTime();
            long sum = computeSum(iterationsPerRound);
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            System.out.printf("Round %d | Execution Time: %4d ms | Checksum: %d | (Tiered Comp Status)%n",
                    round, durationMs, sum);
        }

        System.out.println("\n--> Explanation: Round 1 & 2 execute in Interpreter / C1 mode.");
        System.out.println("--> Subsequent rounds execute fully compiled C2 native machine code with Escape Analysis!");
    }

    private static long computeSum(int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            // Point object DOES NOT ESCAPE this method.
            // C2 Escape Analysis applies Scalar Replacement: no heap allocation occurs!
            Point p = new Point(i, i + 1);
            sum += p.getX() + p.getY();
        }
        return sum;
    }
}
