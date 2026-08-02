/**
 * TASK 8: CompletableFuture
 * ===========================
 * Difficulty: Hard ⭐⭐⭐⭐
 * 
 * Learn about: supplyAsync, thenApply, thenCompose, thenCombine,
 *              allOf, anyOf, exception handling, custom executors
 * 
 * PROBLEM:
 * --------
 * Build an "E-Commerce Order Pipeline" using CompletableFuture for
 * non-blocking async operations.
 * 
 * Part A — Basic async operations:
 * 1. Create helper methods that simulate async operations (each sleeps 1s):
 *    - fetchUser(int userId) → returns "User-" + userId
 *    - fetchOrder(int orderId) → returns "Order-" + orderId
 *    - fetchProduct(int productId) → returns "Product-" + productId
 * 2. Use supplyAsync(() -> fetchUser(1)) to run asynchronously
 * 3. Print which thread executes it (ForkJoinPool.commonPool by default)
 * 
 * Part B — Chaining with thenApply / thenAccept / thenRun:
 * 4. Build a pipeline:
 *    fetchUser(1)
 *      .thenApply(user -> user.toUpperCase())         // Transform
 *      .thenApply(user -> "Welcome, " + user + "!")   // Transform again
 *      .thenAccept(msg -> System.out.println(msg))    // Consume
 *      .thenRun(() -> System.out.println("Done!"))    // Side effect
 * 
 * Part C — thenCompose (flatMap — chaining dependent futures):
 * 5. Fetch user, THEN use the user to fetch their orders:
 *    fetchUser(1)
 *      .thenCompose(user -> fetchOrdersForUser(user))
 *    This is like flatMap — avoids CompletableFuture<CompletableFuture<T>>
 * 6. Explain thenApply vs thenCompose:
 *    - thenApply: T → U (synchronous transformation)
 *    - thenCompose: T → CompletableFuture<U> (async chaining)
 * 
 * Part D — thenCombine (combining two independent futures):
 * 7. Fetch user AND order in parallel, then combine:
 *    fetchUser(1).thenCombine(fetchOrder(101), (user, order) ->
 *        user + " placed " + order)
 * 8. Print the combined result and how long it took (should be ~1s, not ~2s)
 * 
 * Part E — allOf / anyOf:
 * 9. Fetch 5 products in parallel using allOf:
 *    CompletableFuture.allOf(cf1, cf2, cf3, cf4, cf5).join();
 * 10. Use anyOf to get the FIRST result (fastest wins):
 *    CompletableFuture.anyOf(cf1, cf2, cf3).thenAccept(first -> ...)
 * 
 * Part F — Exception handling:
 * 11. Create a method that randomly fails: fetchUnreliable()
 * 12. Handle with exceptionally():
 *     future.exceptionally(ex -> "Fallback value")
 * 13. Handle with handle() (access both result and exception):
 *     future.handle((result, ex) -> ex != null ? "Error: " + ex : result)
 * 14. Chain with whenComplete() for logging:
 *     future.whenComplete((result, ex) -> log(result, ex))
 * 
 * Part G — Custom Executor:
 * 15. Instead of ForkJoinPool.commonPool, use a custom FixedThreadPool:
 *     CompletableFuture.supplyAsync(() -> ..., myExecutor)
 * 16. Print thread names to verify custom pool is used
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - CompletableFuture = Future + reactive-style chaining
 * - Non-blocking: no thread is blocked waiting; callbacks fire when ready
 * - thenApply vs thenCompose: map vs flatMap
 * - allOf: wait for ALL futures (returns CompletableFuture<Void>)
 * - anyOf: wait for FIRST future (returns CompletableFuture<Object>)
 * - Exception propagation: exceptions flow down the chain
 * - exceptionally: catch + recover (like catch block)
 * - handle: inspect both result and exception (like try-catch-finally)
 * - *Async variants (thenApplyAsync, etc.): run callback on different thread
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * === Part B: Chaining ===
 * [ForkJoinPool.commonPool-worker-1] Fetching user...
 * Welcome, USER-1!
 * Done!
 * 
 * === Part D: thenCombine ===
 * User-1 placed Order-101 (completed in 1.05s — parallel!)
 * 
 * === Part E: allOf ===
 * All 5 products fetched in 1.02s
 * 
 * === Part F: Exception Handling ===
 * exceptionally: Fallback value
 * handle: Error: java.lang.RuntimeException: Service unavailable
 */

// Write your solution below:

