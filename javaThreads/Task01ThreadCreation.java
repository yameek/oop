/**
 * TASK 1: Thread Creation & Lifecycle
 * =====================================
 * Difficulty: Beginner ⭐
 * 
 * Learn about: Thread class, Runnable interface, Lambda threads, Thread lifecycle,
 *              start() vs run(), join(), sleep(), daemon threads, interruption
 * 
 * PROBLEM:
 * --------
 * Create a simple "Download Simulator" that demonstrates the three ways to create
 * threads in Java and showcases thread lifecycle management.
 * 
 * Requirements:
 * 1. Create a thread by EXTENDING the Thread class:
 *    - Class: FileDownloader extends Thread
 *    - Constructor takes a filename (String) and fileSize (int, in MB)
 *    - run() simulates downloading by printing progress every 500ms
 *    - Print: "[ThreadName] Downloading <filename>... <progress>%"
 * 
 * 2. Create a thread by IMPLEMENTING Runnable:
 *    - Class: VirusScanner implements Runnable
 *    - Constructor takes a filename (String)
 *    - run() simulates scanning for 2 seconds
 *    - Print: "[ThreadName] Scanning <filename>..."
 * 
 * 3. Create a thread using a LAMBDA:
 *    - A simple logger that prints timestamps every second for 3 seconds
 *    - Print: "[Logger] Heartbeat at <timestamp>"
 * 
 * 4. In main():
 *    a. Create and start all three threads
 *    b. Make the logger thread a DAEMON thread (setDaemon(true) before start)
 *    c. Use join() to wait for the FileDownloader to finish before printing "Download complete"
 *    d. Print the thread STATE at various points (NEW, RUNNABLE, TERMINATED)
 *    e. Demonstrate start() vs run(): call run() directly and show it runs on main thread
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Thread vs Runnable vs Lambda: three ways to create threads
 * - start() creates a new OS thread; run() is just a method call on current thread
 * - Thread lifecycle: NEW → RUNNABLE → (BLOCKED/WAITING/TIMED_WAITING) → TERMINATED
 * - join(): calling thread waits for target thread to finish
 * - sleep(): pauses current thread (doesn't release locks!)
 * - Daemon threads: background threads that don't prevent JVM shutdown
 * - Thread.currentThread().getName(): get name of current thread
 * - interrupt(): cooperative cancellation mechanism
 * 
 * EXPECTED OUTPUT (order may vary due to threading!):
 * ---------------------------------------------------
 * [main] FileDownloader state: NEW
 * [main] Calling run() directly — runs on MAIN thread: main
 * [main] FileDownloader state: NEW (still — run() didn't change state)
 * [main] Starting all threads...
 * [main] FileDownloader state: RUNNABLE
 * [Downloader-1] Downloading report.pdf... 25%
 * [Scanner-1] Scanning report.pdf...
 * [Logger] Heartbeat at 2025-07-30T10:00:01
 * [Downloader-1] Downloading report.pdf... 50%
 * [Logger] Heartbeat at 2025-07-30T10:00:02
 * [Downloader-1] Downloading report.pdf... 75%
 * [Scanner-1] Scan of report.pdf complete. No threats found.
 * [Downloader-1] Downloading report.pdf... 100%
 * [main] Download complete! (join() returned)
 * [main] FileDownloader state: TERMINATED
 * [main] Logger is daemon: true — JVM won't wait for it
 */

// Write your solution below:

