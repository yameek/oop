/**
 * SOLUTION 8: Special Object Methods and Comparable
 * ================================================
 * Demonstrates overriding toString/equals/hashCode, implementing Comparable,
 * and using Comparator for alternative ordering strategies.
 *
 * Implementation notes:
 * - `Product` is immutable so overrides of `equals`/`hashCode` stay consistent once created.
 * - Equality is based on the unique product id, matching the requirement to deduplicate by id.
 * - Natural ordering (`compareTo`) sorts by ascending price and, when prices tie, by descending rating
 *   to show a multi-field comparison. A separate comparator sorts alphabetically by name to contrast
 *   custom orderings.
 * - The `main` method exercises each behavior (string output, equality, hash-based deduplication,
 *   natural ordering, and comparator ordering) so the output showcases every special method in action.
 */
package solutions;

import java.util.*;

class Product implements Comparable<Product> {
    private final String id;
    private final String name;
    private final double price;
    private final double rating;

    public Product(String id, String name, double price, double rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format("Product{id='%s', name='%s', price=$%.2f, rating=%.1f}", id, name, price, rating);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Product other) {
        int priceComparison = Double.compare(this.price, other.price);
        if (priceComparison != 0) {
            return priceComparison;
        }
        // Higher rating should come first if prices are equal
        return -Double.compare(this.rating, other.rating);
    }

    public static final Comparator<Product> BY_NAME = Comparator.comparing(Product::getName);
}

public class Solution08SpecialMethods {
    public static void main(String[] args) {
        Product p1 = new Product("P001", "Laptop", 999.99, 4.5);
        Product p2 = new Product("P002", "Keyboard", 79.99, 4.7);
        Product p3 = new Product("P003", "Mouse", 29.99, 4.3);
        Product duplicateId = new Product("P001", "Gaming Laptop", 1099.99, 4.8);

        System.out.println("=== toString Demo ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);

        System.out.println("\n=== Equality Demo ===");
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p1.equals(duplicateId): " + p1.equals(duplicateId));
        System.out.println("Hash codes: p1=" + p1.hashCode() + ", duplicateId=" + duplicateId.hashCode());

        System.out.println("\n=== HashSet Demo (duplicates removed by ID) ===");
        Set<Product> productSet = new HashSet<>();
        Collections.addAll(productSet, p1, p2, p3, duplicateId);
        productSet.forEach(System.out::println);

        System.out.println("\n=== Natural Ordering (price asc, rating desc) ===");
        Product[] products = {p1, p2, p3, duplicateId};
        Arrays.sort(products);
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            System.out.printf("%d. %s - $%.2f (rating %.1f)%n", i + 1, product.getName(), product.getPrice(), product.getRating());
        }

        System.out.println("\n=== Comparator Ordering (name asc) ===");
        Arrays.sort(products, Product.BY_NAME);
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            System.out.printf("%d. %s - $%.2f%n", i + 1, product.getName(), product.getPrice());
        }
    }
}
