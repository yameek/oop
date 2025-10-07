/**
 * TASK 8: Special Methods (toString, equals, hashCode, Comparable)
 * =================================================================
 * Difficulty: Advanced ⭐⭐⭐⭐
 * 
 * Learn about: Object methods, Comparable interface, Proper equality
 * 
 * PROBLEM:
 * --------
 * Create a Product class for an e-commerce system.
 * 
 * Requirements:
 * 
 * 1. Create class 'Product' implementing Comparable<Product>:
 *    - Instance variables: id (String), name (String), price (double), rating (double)
 *    - Constructor taking all parameters
 *    - Getters for all fields
 * 
 * 2. Override toString():
 *    - Return formatted string: "Product{id='P001', name='Laptop', price=$999.99, rating=4.5}"
 * 
 * 3. Override equals(Object obj):
 *    - Two products are equal if they have the same id
 *    - Follow proper equals contract (reflexive, symmetric, transitive)
 * 
 * 4. Override hashCode():
 *    - Must be consistent with equals
 *    - Use id for hash code calculation
 * 
 * 5. Implement Comparable<Product>:
 *    - compareTo() method comparing by price (ascending)
 *    - If prices equal, compare by rating (descending)
 * 
 * 6. Create a separate Comparator for sorting by name
 * 
 * 7. Demonstrate usage:
 *    - Create multiple products
 *    - Test equality with ==, equals(), and hashCode()
 *    - Sort using natural ordering (Comparable)
 *    - Sort using Comparator by name
 *    - Store in HashSet to demonstrate hash code importance
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create products and test toString()
 * - Compare products with equals()
 * - Add to HashSet and verify uniqueness
 * - Sort array using Arrays.sort()
 * - Sort with custom Comparator
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - toString() for string representation
 * - equals() contract and proper implementation
 * - hashCode() contract and relationship with equals()
 * - Comparable for natural ordering
 * - Comparator for custom ordering
 * - Why equals and hashCode must be consistent
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Product{id='P001', name='Laptop', price=$999.99, rating=4.5}
 * 
 * Equality test:
 * product1.equals(product2): false
 * product1.equals(product3): true
 * 
 * Sorted by price:
 * 1. Mouse - $29.99
 * 2. Keyboard - $79.99
 * 3. Laptop - $999.99
 * 
 * Sorted by name:
 * 1. Keyboard - $79.99
 * 2. Laptop - $999.99
 * 3. Mouse - $29.99
 */

// Write your solution below:

