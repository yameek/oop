/**
 * SOLUTION 1: Thread Creation & Lifecycle
 * =========================================
 * 
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Thread class: extend it and override run()
 * 2. Runnable interface: implement run(), pass to Thread constructor
 * 3. Lambda: shortest way — Thread t = new Thread(() -> { ... });
 * 4. start() vs run(): start() creates new OS thread; run() is just a method call
 * 5. Thread states: NEW → RUNNABLE → BLOCKED/WAITING/TIMED_WAITING → TERMINATED
 * 6. join(): calling thread blocks until target thread finishes
 * 7. sleep(): pauses current thread (does NOT release locks)
 * 8. Daemon threads: background threads that don't prevent JVM shutdown
 * 9. interrupt(): cooperative cancellation — sets a flag, doesn't force-stop
 * 
 * KEY TAKEAWAYS:
 * --------------
 * - Prefer Runnable/Lambda over extending Thread (composition over inheritance)
 * - Always use start(), never call run() directly (common mistake!)
 * - Daemon threads are killed when all non-daemon threads finish
 * - interrupt() is cooperative — the thread must CHECK for it
 */

package solutions;

// ─── Method 1: Extend Thread ─────────────────────────────────────────────────
class FileDownloader extends Thread {
    private String filename;
    private int fileSize;

    public FileDownloader(String filename, int fileSize) {
        super("Downloader-1");  // Set thread name
        this.filename = filename;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        // Simulate downloading with progress updates
        for (int progress = 25; progress <= 100; progress += 25) {
            System.out.println("[" + getName() + "] Downloading " + filename + "... " + progress + "%");
            try {
                Thread.sleep(500);  // Simulate network I/O (500ms per chunk)
            } catch (InterruptedException e) {
                // InterruptedException is thrown if thread is interrupted during sleep
                System.out.println("[" + getName() + "] Download interrupted!");
                return;  // Exit gracefully
            }
        }
        System.out.println("[" + getName() + "] Download of " + filename + " complete!");
    }
}

// ─── Method 2: Implement Runnable ────────────────────────────────────────────
class VirusScanner implements Runnable {
    private String filename;

    public VirusScanner(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Scanning " + filename + "...");
        try {
            Thread.sleep(2000);  // Simulate 2-second scan
        } catch (InterruptedException e) {
            System.out.println("[" + threadName + "] Scan interrupted!");
            return;
        }
        System.out.println("[" + threadName + "] Scan of " + filename + " complete. No threats found.");
    }
}

public class Solution01ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=".repeat(60));
        System.out.println("TASK 1: Thread Creation & Lifecycle");
        System.out.println("=".repeat(60));

        // ─── Method 1: Extend Thread ─────────────────────────────────
        System.out.println("\n--- Method 1: Extending Thread ---");
        FileDownloader downloader = new FileDownloader("report.pdf", 100);

        // Show initial state: NEW (created but not started)
        System.out.println("[main] FileDownloader state: " + downloader.getState());  // NEW

        // ─── CRITICAL: start() vs run() ──────────────────────────────
        System.out.println("\n--- start() vs run() Demo ---");
        // Calling run() directly — runs on MAIN thread (NOT a new thread!)
        System.out.println("[main] Calling run() directly...");
        System.out.println("[main] Current thread: " + Thread.currentThread().getName());
        // downloader.run();  // Uncomment to see: runs on "main" thread, NOT "Downloader-1"
        System.out.println("[main] State after run(): " + downloader.getState());  // Still NEW!
        System.out.println("[main] ⚠️ run() does NOT create a new thread — it's just a method call!\n");

        // ─── Start all threads ───────────────────────────────────────
        System.out.println("--- Starting all threads ---");

        // Start the downloader (Method 1: extends Thread)
        downloader.start();
        System.out.println("[main] FileDownloader state: " + downloader.getState());  // RUNNABLE

        // Method 2: Implement Runnable
        Thread scanner = new Thread(new VirusScanner("report.pdf"), "Scanner-1");
        scanner.start();

        // Method 3: Lambda (preferred for simple tasks)
        Thread logger = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("[Logger] Heartbeat at " + java.time.LocalTime.now());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "Logger");

        // ─── Daemon thread ───────────────────────────────────────────
        // Daemon threads are background threads — JVM won't wait for them to finish
        // If all non-daemon threads finish, daemon threads are killed immediately
        logger.setDaemon(true);  // MUST be called before start()!
        logger.start();
        System.out.println("[main] Logger is daemon: " + logger.isDaemon());

        // ─── join() — wait for downloader to finish ──────────────────
        System.out.println("[main] Waiting for download to finish (join)...\n");
        downloader.join();  // Main thread blocks here until downloader finishes
        System.out.println("\n[main] Download complete! (join() returned)");
        System.out.println("[main] FileDownloader state: " + downloader.getState());  // TERMINATED

        // Wait for scanner too
        scanner.join();

        // ─── Thread interruption demo ────────────────────────────────
        System.out.println("\n--- Interruption Demo ---");
        Thread longTask = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Do work... check for interruption periodically
                System.out.println("[LongTask] Working...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // sleep() throws InterruptedException when interrupted
                    System.out.println("[LongTask] Interrupted during sleep! Exiting gracefully.");
                    return;  // Cooperative shutdown
                }
            }
            System.out.println("[LongTask] Interrupted flag detected! Exiting.");
        }, "LongTask");
        
        longTask.start();
        Thread.sleep(1200);  // Let it run for a bit
        longTask.interrupt();  // Request interruption
        longTask.join();
        System.out.println("[main] LongTask state: " + longTask.getState());  // TERMINATED

        // ─── Summary ────────────────────────────────────────────────
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("✓ Thread creation: extend Thread, implement Runnable, Lambda");
        System.out.println("✓ start() creates a new OS thread; run() is just a method call");
        System.out.println("✓ Thread states: NEW → RUNNABLE → TERMINATED");
        System.out.println("✓ join(): wait for a thread to finish");
        System.out.println("✓ sleep(): pause current thread (doesn't release locks)");
        System.out.println("✓ Daemon threads: background, killed when JVM exits");
        System.out.println("✓ interrupt(): cooperative cancellation mechanism");
        System.out.println("=".repeat(60));
    }
}

/*
 * LEARNING NOTES:
 * ===============
 * 
 * 1. THREE WAYS TO CREATE THREADS:
 *    a) Extend Thread:     class MyThread extends Thread { void run() {...} }
 *    b) Implement Runnable: class MyTask implements Runnable { void run() {...} }
 *    c) Lambda:            new Thread(() -> { ... }).start();
 *    → Prefer Runnable/Lambda: allows extending other classes (single inheritance)
 * 
 * 2. THREAD LIFECYCLE STATES (Thread.State enum):
 *    - NEW:            Created but not started
 *    - RUNNABLE:       Running or ready to run
 *    - BLOCKED:        Waiting to enter a synchronized block
 *    - WAITING:        Waiting indefinitely (wait(), join() without timeout)
 *    - TIMED_WAITING:  Waiting with timeout (sleep(), join(ms), wait(ms))
 *    - TERMINATED:     run() has completed
 * 
 * 3. start() vs run():
 *    - start(): Asks the JVM to create a NEW OS thread → calls run() on it
 *    - run():   Just a regular method call on the CURRENT thread
 *    - Common mistake: calling run() and wondering why it's not concurrent!
 * 
 * 4. DAEMON THREADS:
 *    - User threads: JVM waits for ALL user threads to finish before exiting
 *    - Daemon threads: JVM kills them when all user threads are done
 *    - Use for: logging, heartbeats, cache cleanup (things that can stop anytime)
 *    - MUST call setDaemon(true) BEFORE start()!
 * 
 * 5. INTERRUPT() — COOPERATIVE CANCELLATION:
 *    - Does NOT force-stop the thread (unlike the deprecated stop())
 *    - Sets a boolean flag (Thread.interrupted / isInterrupted())
 *    - If thread is in sleep/wait/join → throws InterruptedException
 *    - Thread must CHECK for interruption and exit gracefully
 * 
 * COMMON MISTAKES TO AVOID:
 * =========================
 * 1. Calling run() instead of start()
 * 2. Forgetting to handle InterruptedException
 * 3. Setting daemon after start() (throws IllegalThreadStateException)
 * 4. Using Thread.stop() (deprecated — use interrupt() instead)
 * 5. Ignoring InterruptedException (swallowing it without action)
 */
