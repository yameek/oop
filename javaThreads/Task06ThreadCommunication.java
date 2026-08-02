/**
 * TASK 6: Thread Communication
 * ==============================
 * Difficulty: Medium-Hard ⭐⭐⭐⭐
 * 
 * Learn about: wait(), notify(), notifyAll(), sleep() vs wait(),
 *              spurious wakeups, Condition variables, Producer-Consumer pattern
 * 
 * PROBLEM:
 * --------
 * Build a "Restaurant Order System" — producers (chefs) and consumers (waiters).
 * 
 * Part A — Basic wait/notify:
 * 1. Create a class OrderQueue with:
 *    - private Queue<String> orders (LinkedList)
 *    - private int maxSize (capacity)
 *    - synchronized placeOrder(String order):
 *      while queue is full → wait()
 *      add order, notifyAll()
 *    - synchronized String takeOrder():
 *      while queue is empty → wait()
 *      remove and return order, notifyAll()
 * 2. Create 2 Chef threads that each place 5 orders
 * 3. Create 3 Waiter threads that take orders until all are consumed
 * 4. Print "[Chef-N] placed: Burger #3" / "[Waiter-N] serving: Burger #3"
 * 
 * Part B — Why while-loop, not if:
 * 5. In comments, explain spurious wakeups:
 *    - JVM can wake a thread from wait() without notify() being called
 *    - If you use 'if', the thread proceeds with stale condition
 *    - 'while' re-checks the condition after waking up
 * 6. Show the difference: replace while with if, observe potential issues
 * 
 * Part C — sleep() vs wait() demo:
 * 7. Create a demo showing the key differences:
 *    - sleep(): does NOT release the lock (other threads blocked!)
 *    - wait(): RELEASES the lock (other threads can enter synchronized)
 * 8. Thread A holds lock and calls sleep(2000) → Thread B can't enter
 * 9. Thread A holds lock and calls wait() → Thread B CAN enter
 * 
 * Part D — Condition variables (with ReentrantLock):
 * 10. Rebuild the OrderQueue using ReentrantLock + Condition:
 *     - Condition notFull = lock.newCondition()
 *     - Condition notEmpty = lock.newCondition()
 *     - placeOrder: while full → notFull.await(); then notEmpty.signal()
 *     - takeOrder: while empty → notEmpty.await(); then notFull.signal()
 * 11. Advantage: separate conditions vs. notifyAll() waking ALL threads
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - wait(): releases lock, thread goes WAITING; must be inside synchronized
 * - notify(): wakes ONE waiting thread (unpredictable which one)
 * - notifyAll(): wakes ALL waiting threads (preferred — safer)
 * - Spurious wakeup: OS can wake thread without notify (always use while-loop!)
 * - sleep() vs wait():
 *   | Feature          | sleep()     | wait()          |
 *   |------------------|-------------|-----------------|
 *   | Releases lock?   | NO          | YES             |
 *   | Requires sync?   | NO          | YES             |
 *   | Woken by         | Timer       | notify/notifyAll|
 *   | Called on         | Thread      | Object          |
 * - Condition: like wait/notify but for ReentrantLock (more flexible)
 * - signal() = notify(), signalAll() = notifyAll(), await() = wait()
 * 
 * EXPECTED OUTPUT EXAMPLE (order varies!):
 * ----------------------------------------
 * === Part A: Producer-Consumer ===
 * [Chef-1] placed: Pizza #1
 * [Chef-2] placed: Burger #1
 * [Waiter-1] serving: Pizza #1
 * [Chef-1] placed: Pizza #2
 * [Waiter-2] serving: Burger #1
 * ... (all 10 orders placed and served)
 * All orders served!
 * 
 * === Part C: sleep() vs wait() ===
 * [Thread-A] Holding lock, calling sleep(2000)...
 * [Thread-B] Trying to enter synchronized block... BLOCKED!
 * [Thread-A] Holding lock, calling wait()...
 * [Thread-B] Entered synchronized block! (wait released the lock)
 * 
 * === Part D: Condition Variables ===
 * (Same output as Part A, but using Lock + Condition internally)
 */

// Write your solution below:

