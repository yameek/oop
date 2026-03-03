/**
 * SOLUTION 14: Design Patterns — Observer and Strategy
 * ======================================================
 *
 * CONCEPTS EXPLAINED:
 * -------------------
 * Observer:
 *   Subject (StockMarket) maintains a list of Observers.
 *   When state changes, it calls notifyObservers() → each observer reacts.
 *   Observers are DECOUPLED from the subject — they just implement an interface.
 *
 * Strategy:
 *   The algorithm (discount) is encapsulated behind an interface.
 *   The context (ShoppingCart) delegates to the strategy at runtime.
 *   Since DiscountStrategy is a @FunctionalInterface, lambdas work directly.
 *
 * KEY TAKEAWAYS:
 * --------------
 * - Observer: loose coupling between subject and observers; open/closed principle
 * - Strategy: replaces if-else chains with polymorphism; open for extension
 * - Both patterns use interfaces/composition over inheritance
 * - Java 8+ allows strategies as lambdas — no need for concrete class per strategy
 */

package solutions;

import java.util.*;

// ═════════════════════════════════════════════════════════════════════════════
// PART A: OBSERVER PATTERN
// ═════════════════════════════════════════════════════════════════════════════

// ── Observer interface ───────────────────────────────────────────────────────
interface StockObserver {
    void onPriceChange(String stockSymbol, double oldPrice, double newPrice);
}

// ── Subject interface ────────────────────────────────────────────────────────
interface StockSubject {
    void subscribe(StockObserver observer);
    void unsubscribe(StockObserver observer);
    void notifyObservers(String symbol, double oldPrice, double newPrice);
}

// ── Concrete Subject ─────────────────────────────────────────────────────────
class StockMarket implements StockSubject {
    private final Map<String, Double> prices    = new HashMap<>();
    private final List<StockObserver> observers = new ArrayList<>();

    public void updatePrice(String symbol, double newPrice) {
        double oldPrice = prices.getOrDefault(symbol, newPrice);
        prices.put(symbol, newPrice);
        notifyObservers(symbol, oldPrice, newPrice);
    }

    @Override
    public void subscribe(StockObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(StockObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String symbol, double oldPrice, double newPrice) {
        // Each observer gets the same data — they decide what to do with it
        for (StockObserver observer : observers) {
            observer.onPriceChange(symbol, oldPrice, newPrice);
        }
    }
}

// ── Concrete Observers ───────────────────────────────────────────────────────
class MobileAlertObserver implements StockObserver {
    private final String username;

    public MobileAlertObserver(String username) { this.username = username; }

    @Override
    public void onPriceChange(String symbol, double oldPrice, double newPrice) {
        System.out.printf("[MOBILE] %s: %s changed %.2f → %.2f%n",
            username, symbol, oldPrice, newPrice);
    }
}

class EmailObserver implements StockObserver {
    private final String email;

    public EmailObserver(String email) { this.email = email; }

    @Override
    public void onPriceChange(String symbol, double oldPrice, double newPrice) {
        System.out.printf("[EMAIL]  %s: %s price update: $%.2f (was $%.2f)%n",
            email, symbol, newPrice, oldPrice);
    }
}

class PriceThresholdObserver implements StockObserver {
    private final String watchSymbol;
    private final double threshold;

    public PriceThresholdObserver(String watchSymbol, double threshold) {
        this.watchSymbol = watchSymbol;
        this.threshold   = threshold;
    }

    @Override
    public void onPriceChange(String symbol, double oldPrice, double newPrice) {
        // Only care about a specific symbol dropping below threshold
        if (symbol.equals(watchSymbol) && newPrice < threshold) {
            System.out.printf("[ALERT]  %s dropped below %.1f! Current: $%.2f%n",
                symbol, threshold, newPrice);
        }
    }
}

// ═════════════════════════════════════════════════════════════════════════════
// PART B: STRATEGY PATTERN
// ═════════════════════════════════════════════════════════════════════════════

// ── Strategy interface (FunctionalInterface → lambdas work!) ─────────────────
@FunctionalInterface
interface DiscountStrategy {
    double applyDiscount(double originalPrice);

    // Default method with a description — optional but helpful
    default String description() { return "Custom strategy"; }
}

// ── Concrete Strategies ──────────────────────────────────────────────────────
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) { return price; }

    @Override
    public String description() { return "No Discount"; }
}

class PercentageDiscount implements DiscountStrategy {
    private final double percent;

    public PercentageDiscount(double percent) { this.percent = percent; }

    @Override
    public double applyDiscount(double price) { return price * (1 - percent / 100); }

    @Override
    public String description() { return (int) percent + "% Discount"; }
}

class FlatDiscount implements DiscountStrategy {
    private final double amount;

    public FlatDiscount(double amount) { this.amount = amount; }

    @Override
    public double applyDiscount(double price) { return Math.max(0, price - amount); }

    @Override
    public String description() { return "$" + (int) amount + " Flat Discount"; }
}

class BuyOneGetOneFree implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) { return price * 0.5; }

    @Override
    public String description() { return "Buy One Get One Free (50% off)"; }
}

// ── CartItem helper ──────────────────────────────────────────────────────────
class CartItem {
    final String name;
    final double price;

    CartItem(String name, double price) {
        this.name  = name;
        this.price = price;
    }
}

// ── Context: ShoppingCart ────────────────────────────────────────────────────
class ShoppingCart {
    private final List<CartItem> items = new ArrayList<>();
    private DiscountStrategy strategy;

    public ShoppingCart(DiscountStrategy strategy) { this.strategy = strategy; }

    // Strategy is changeable at runtime — this is the key power of the pattern
    public void setStrategy(DiscountStrategy strategy) { this.strategy = strategy; }

    public void addItem(String name, double price) { items.add(new CartItem(name, price)); }

    public double calculateTotal() {
        return items.stream()
            .mapToDouble(item -> strategy.applyDiscount(item.price))
            .sum();
    }

    public void printReceipt() {
        System.out.println("\n--- Receipt: " + strategy.description() + " ---");
        for (CartItem item : items) {
            double discounted = strategy.applyDiscount(item.price);
            if (discounted == item.price) {
                System.out.printf("  %-12s $%8.2f%n", item.name + ":", item.price);
            } else {
                System.out.printf("  %-12s $%8.2f → $%.2f%n",
                    item.name + ":", item.price, discounted);
            }
        }
        System.out.printf("  %-12s $%8.2f%n", "TOTAL:", calculateTotal());
    }
}

// ═════════════════════════════════════════════════════════════════════════════
// Main
// ═════════════════════════════════════════════════════════════════════════════

public class Solution14MoreDesignPatterns {

    public static void main(String[] args) {

        // ── PART A: Observer ────────────────────────────────────────────────
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║  OBSERVER PATTERN             ║");
        System.out.println("╚══════════════════════════════╝");

        StockMarket market = new StockMarket();

        MobileAlertObserver alice = new MobileAlertObserver("Alice");
        MobileAlertObserver bob   = new MobileAlertObserver("Bob");
        EmailObserver       carol = new EmailObserver("carol@email.com");
        StockObserver       alert = new PriceThresholdObserver("AAPL", 140.0);

        market.subscribe(alice);
        market.subscribe(bob);
        market.subscribe(carol);
        market.subscribe(alert);

        // Initial prices (no old price stored yet → both same, no change notification)
        market.updatePrice("AAPL", 150.0);
        market.updatePrice("GOOG", 2800.0);

        System.out.println("\n--- AAPL drops to 145 (threshold not triggered) ---");
        market.updatePrice("AAPL", 145.0);

        System.out.println("\n--- AAPL drops to 135 (threshold triggered!) ---");
        market.updatePrice("AAPL", 135.0);

        System.out.println("\n--- GOOG update ---");
        market.updatePrice("GOOG", 2750.0);

        // Unsubscribe Bob — he should no longer receive notifications
        System.out.println("\n--- Bob unsubscribed ---");
        market.unsubscribe(bob);
        market.updatePrice("AAPL", 130.0);

        // ── PART B: Strategy ────────────────────────────────────────────────
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║  STRATEGY PATTERN             ║");
        System.out.println("╚══════════════════════════════╝");

        ShoppingCart cart = new ShoppingCart(new NoDiscount());
        cart.addItem("Laptop",   1200.0);
        cart.addItem("Mouse",      50.0);
        cart.addItem("Keyboard",   80.0);

        cart.printReceipt();  // No discount

        // Swap strategy at runtime — no cart modification needed
        cart.setStrategy(new PercentageDiscount(10));
        cart.printReceipt();

        cart.setStrategy(new FlatDiscount(100));
        cart.printReceipt();

        cart.setStrategy(new BuyOneGetOneFree());
        cart.printReceipt();

        // Lambda strategy — no concrete class needed!
        // "15% off if item price > 500, else 5% off"
        DiscountStrategy tiered = price -> price > 500 ? price * 0.85 : price * 0.95;
        cart.setStrategy(tiered);
        cart.printReceipt();

        System.out.println("\n--- Changing strategy mid-session (clearance sale) ---");
        cart.setStrategy(new PercentageDiscount(50));
        cart.printReceipt();

        /*
         * KEY TAKEAWAYS:
         * ─────────────
         *
         * OBSERVER:
         * ✅ Subject knows observers only through the StockObserver interface
         * ✅ Adding a new observer type (e.g., SlackObserver) requires ZERO changes to StockMarket
         * ✅ Observers can be added/removed at runtime
         * ✅ PriceThresholdObserver shows observers can have their own filtering logic
         *
         * STRATEGY:
         * ✅ ShoppingCart doesn't care HOW the discount works — only calls applyDiscount()
         * ✅ New discount types require ZERO changes to ShoppingCart (open/closed principle)
         * ✅ @FunctionalInterface lets you use lambdas directly — no boilerplate class needed
         * ✅ setStrategy() demonstrates runtime flexibility — same cart, different behavior
         *
         * OBSERVER vs STRATEGY:
         * ─────────────────────
         * Observer   — "notify me when something HAPPENS" (event-driven)
         * Strategy   — "do THIS operation using THAT algorithm" (behavior injection)
         */
    }
}
