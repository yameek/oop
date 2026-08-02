/**
 * SOLUTION 8: CompletableFuture
 * ===============================
 * 
 * KEY TAKEAWAYS:
 * - CompletableFuture = Future + reactive-style chaining (non-blocking)
 * - thenApply: T → U (synchronous transform, like map)
 * - thenCompose: T → CompletableFuture<U> (async chain, like flatMap)
 * - thenCombine: combine two independent futures
 * - exceptionally/handle: exception recovery in async flows
 */

package solutions;

import java.util.*;
import java.util.concurrent.*;

public class Solution08CompletableFuture {

    // Simulated async operations
    static String fetchUser(int id) {
        sleep(500);
        return "User-" + id;
    }

    static String fetchOrder(int id) {
        sleep(500);
        return "Order-" + id;
    }

    static List<String> fetchOrdersForUser(String user) {
        sleep(300);
        return List.of(user + "-OrderA", user + "-OrderB");
    }

    static String fetchUnreliable() {
        sleep(200);
        if (new Random().nextBoolean()) throw new RuntimeException("Service unavailable!");
        return "Success!";
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("TASK 8: CompletableFuture");
        System.out.println("=".repeat(60));

        partA_BasicAsync();
        partB_Chaining();
        partC_ThenCompose();
        partD_ThenCombine();
        partE_AllOfAnyOf();
        partF_ExceptionHandling();
        partG_CustomExecutor();
    }

    static void partA_BasicAsync() throws Exception {
        System.out.println("\n=== Part A: Basic Async ===\n");
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Fetching user...");
            return fetchUser(1);
        });
        System.out.println("Result: " + cf.get());
        System.out.println("Default pool: ForkJoinPool.commonPool");
    }

    static void partB_Chaining() throws Exception {
        System.out.println("\n=== Part B: Chaining (thenApply/thenAccept/thenRun) ===\n");

        CompletableFuture.supplyAsync(() -> fetchUser(1))
            .thenApply(user -> user.toUpperCase())           // Transform
            .thenApply(user -> "Welcome, " + user + "!")     // Transform again
            .thenAccept(msg -> System.out.println(msg))      // Consume (no return)
            .thenRun(() -> System.out.println("Pipeline done!"))  // Side effect
            .join();  // Wait for completion (like get() but unchecked exceptions)
    }

    static void partC_ThenCompose() throws Exception {
        System.out.println("\n=== Part C: thenCompose (flatMap) ===\n");

        // thenCompose: chains dependent async operations
        // Without thenCompose: CompletableFuture<CompletableFuture<List<String>>> 😱
        // With thenCompose:    CompletableFuture<List<String>> ✓
        CompletableFuture<List<String>> result = CompletableFuture
            .supplyAsync(() -> fetchUser(1))
            .thenCompose(user -> CompletableFuture.supplyAsync(() -> fetchOrdersForUser(user)));

        System.out.println("Orders: " + result.get());
        System.out.println("\nthenApply:   T → U         (sync transform, like Stream.map)");
        System.out.println("thenCompose: T → CF<U>      (async chain,   like Stream.flatMap)");
    }

    static void partD_ThenCombine() throws Exception {
        System.out.println("\n=== Part D: thenCombine (parallel + merge) ===\n");

        long start = System.currentTimeMillis();

        CompletableFuture<String> userCf = CompletableFuture.supplyAsync(() -> fetchUser(1));
        CompletableFuture<String> orderCf = CompletableFuture.supplyAsync(() -> fetchOrder(101));

        // Both run in PARALLEL, then combine when both complete
        String combined = userCf.thenCombine(orderCf, (user, order) ->
            user + " placed " + order
        ).get();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println(combined);
        System.out.println("Completed in " + elapsed + "ms (≈500ms, not 1000ms — parallel!) ✓");
    }

    static void partE_AllOfAnyOf() throws Exception {
        System.out.println("\n=== Part E: allOf / anyOf ===\n");

        // allOf — wait for ALL futures to complete
        long start = System.currentTimeMillis();
        CompletableFuture<String>[] cfs = new CompletableFuture[5];
        for (int i = 0; i < 5; i++) {
            final int id = i;
            cfs[i] = CompletableFuture.supplyAsync(() -> {
                sleep(500);
                return "Product-" + id;
            });
        }

        CompletableFuture.allOf(cfs).join();  // Wait for all 5
        long elapsed = System.currentTimeMillis() - start;

        List<String> results = new ArrayList<>();
        for (CompletableFuture<String> cf : cfs) results.add(cf.get());
        System.out.println("allOf results: " + results);
        System.out.println("All 5 fetched in " + elapsed + "ms (≈500ms — parallel!) ✓");

        // anyOf — first result wins
        System.out.println();
        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> { sleep(100); return "Fast!"; });
        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> { sleep(1000); return "Slow!"; });
        Object first = CompletableFuture.anyOf(fast, slow).get();
        System.out.println("anyOf result: " + first + " (fastest wins)");
    }

    static void partF_ExceptionHandling() throws Exception {
        System.out.println("\n=== Part F: Exception Handling ===\n");

        // exceptionally — catch and recover
        String result1 = CompletableFuture.<String>supplyAsync(() -> {
            throw new RuntimeException("DB connection failed!");
        }).exceptionally(ex -> {
            System.out.println("exceptionally caught: " + ex.getMessage());
            return "Fallback value";
        }).get();
        System.out.println("Result: " + result1);

        // handle — access both result AND exception
        System.out.println();
        String result2 = CompletableFuture.<String>supplyAsync(() -> {
            throw new RuntimeException("API timeout!");
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println("handle caught: " + ex.getMessage());
                return "Handled fallback";
            }
            return res;
        }).get();
        System.out.println("Result: " + result2);

        // whenComplete — for logging (doesn't change result)
        System.out.println();
        CompletableFuture.supplyAsync(() -> "Success data")
            .whenComplete((res, ex) -> {
                if (ex != null) System.out.println("whenComplete error: " + ex);
                else System.out.println("whenComplete success: " + res);
            }).get();
    }

    static void partG_CustomExecutor() throws Exception {
        System.out.println("\n=== Part G: Custom Executor ===\n");
        ExecutorService myPool = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r, "MyPool-thread");
            return t;
        });

        CompletableFuture.supplyAsync(() -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Running on custom pool");
            return "Custom pool result";
        }, myPool).thenAcceptAsync(result -> {
            System.out.println("[" + Thread.currentThread().getName() + "] Consuming on custom pool");
        }, myPool).join();

        myPool.shutdown();
        System.out.println("✓ Custom executor used instead of ForkJoinPool.commonPool");

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("KEY CONCEPTS");
        System.out.println("=".repeat(60));
        System.out.println("✓ supplyAsync: start async computation");
        System.out.println("✓ thenApply: transform (map), thenCompose: chain async (flatMap)");
        System.out.println("✓ thenCombine: merge two independent futures");
        System.out.println("✓ allOf/anyOf: wait for all or first result");
        System.out.println("✓ exceptionally/handle: exception recovery in async flows");
        System.out.println("✓ *Async variants: run callback on different thread");
        System.out.println("=".repeat(60));
    }
}
