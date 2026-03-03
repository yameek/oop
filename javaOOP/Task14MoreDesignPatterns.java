/**
 * TASK 14: Design Patterns — Observer and Strategy
 * ==================================================
 * Difficulty: Advanced ⭐⭐⭐⭐⭐
 *
 * Learn about: Observer Pattern, Strategy Pattern
 * (extends Task 10 which covered Singleton, Factory, Builder)
 *
 * PROBLEM:
 * --------
 * PART A — Observer Pattern: Event Notification System
 * -----------------------------------------------------
 * The Observer pattern defines a one-to-many dependency: when one object (Subject)
 * changes state, all its dependents (Observers) are notified automatically.
 *
 * Build a stock price notification system:
 *
 * 1. Create an interface StockObserver with method:
 *    - onPriceChange(String stockSymbol, double oldPrice, double newPrice)
 *
 * 2. Create an interface StockSubject with methods:
 *    - subscribe(StockObserver observer)
 *    - unsubscribe(StockObserver observer)
 *    - notifyObservers(String symbol, double oldPrice, double newPrice)
 *
 * 3. Create class StockMarket (implements StockSubject):
 *    - Stores a Map<String, Double> of stock prices
 *    - Stores a List<StockObserver> of subscribers
 *    - updatePrice(String symbol, double newPrice):
 *        saves old price, updates to new price, calls notifyObservers()
 *
 * 4. Create these observer implementations:
 *    a) MobileAlertObserver(String username) — prints a push notification
 *    b) EmailObserver(String email) — prints email notification
 *    c) PriceThresholdObserver(String symbol, double threshold)
 *       — only alerts if new price drops BELOW the threshold
 *
 * PART B — Strategy Pattern: Sorting and Discount
 * -------------------------------------------------
 * The Strategy pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable. The strategy changes independently from clients.
 *
 * Build a shopping cart with pluggable discount strategies:
 *
 * 1. Create a functional interface DiscountStrategy:
 *    - double applyDiscount(double originalPrice)
 *
 * 2. Create these strategy implementations (as classes AND as lambdas in main):
 *    a) NoDiscount                        — returns original price unchanged
 *    b) PercentageDiscount(double percent) — e.g., 10% off → price * 0.9
 *    c) FlatDiscount(double amount)        — e.g., $20 off → price - 20 (min 0)
 *    d) BuyOneGetOneFree                   — half the price (equivalent to 50% off)
 *
 * 3. Create class ShoppingCart:
 *    - List<CartItem> items (CartItem has name and price)
 *    - DiscountStrategy strategy (changeable at runtime!)
 *    - setStrategy(DiscountStrategy strategy)
 *    - addItem(String name, double price)
 *    - calculateTotal() → applies strategy to each item's price, sums them
 *    - printReceipt() → shows each item, discounted price, and total
 *
 * TEST YOUR CODE:
 * ---------------
 * PART A:
 * - Create StockMarket with AAPL=150, GOOG=2800
 * - Subscribe: 2 mobile users, 1 email user, 1 threshold observer (AAPL < 140)
 * - Update AAPL to 145 (threshold not triggered), then to 135 (threshold triggered)
 * - Update GOOG to 2750
 * - Unsubscribe one mobile user, update AAPL to 130 (verify they don't get notified)
 *
 * PART B:
 * - Create ShoppingCart with 3 items (laptop $1200, mouse $50, keyboard $80)
 * - Print receipts with 4 different strategies
 * - Create a custom lambda strategy: 15% off if total > 1000, else 5% off
 * - Change strategy at runtime (demonstrate Strategy's power)
 *
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * Observer:
 * - Subject holds list of observers; doesn't know their concrete types (loose coupling)
 * - Observers register/unregister dynamically
 * - Push model (subject sends data) vs Pull model (observer fetches data)
 * - Java built-in: java.util.Observer (deprecated) → use your own interface
 * - Real-world: GUI event listeners, pub/sub messaging, Model-View in MVC
 *
 * Strategy:
 * - Encapsulates an algorithm behind an interface → swap at runtime
 * - Eliminates conditional logic (if "gold member" ... else if "sale" ...)
 * - In Java 8+, DiscountStrategy as @FunctionalInterface → use lambdas directly
 * - Real-world: sorting algorithms, payment methods, compression algorithms
 *
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * [MOBILE] Alice: AAPL changed 150.00 → 145.00
 * [EMAIL] bob@mail.com: AAPL price update: $145.00 (was $150.00)
 * [MOBILE] Alice: AAPL changed 145.00 → 135.00
 * [ALERT] AAPL dropped below 140.0! Current: 135.00
 * --- Receipt (10% Discount) ---
 * Laptop:    $1200.00 → $1080.00
 * Mouse:     $  50.00 → $  45.00
 * Total: $1125.00
 */

// Write your solution below:
