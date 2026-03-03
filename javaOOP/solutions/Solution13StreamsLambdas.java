/**
 * SOLUTION 13: Streams and Lambdas
 * ==================================
 *
 * CONCEPTS EXPLAINED:
 * -------------------
 * 1. Lambda Expression    — anonymous function: (params) -> body
 * 2. Functional Interface — @FunctionalInterface with one abstract method
 * 3. Stream Pipeline      — source → intermediate ops → terminal op
 * 4. Optional             — null-safe container; prefer over null returns
 * 5. Method Reference     — shorthand for simple lambdas
 * 6. Collectors           — groupingBy, partitioningBy, toList, toMap, averagingDouble
 *
 * KEY TAKEAWAYS:
 * --------------
 * - Streams don't modify the original list; they produce new results
 * - Intermediate ops are LAZY — nothing runs until a terminal op is called
 * - Use Optional.ifPresent/orElse/orElseThrow instead of null checks
 * - Method references make code more readable when the lambda just delegates
 * - Collectors.groupingBy is the stream equivalent of SQL GROUP BY
 */

package solutions;

import java.util.*;
import java.util.stream.*;

// ─────────────────────────────────────────────────────────────────────────────
// Employee class
// ─────────────────────────────────────────────────────────────────────────────

class Employee {
    private final String name;
    private final String department;
    private final double salary;
    private final int yearsOfExperience;

    public Employee(String name, String department, double salary, int yearsOfExperience) {
        this.name              = name;
        this.department        = department;
        this.salary            = salary;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getName()           { return name; }
    public String getDepartment()     { return department; }
    public double getSalary()         { return salary; }
    public int    getYearsOfExperience() { return yearsOfExperience; }

    @Override
    public String toString() {
        return String.format("Employee{name='%s', dept='%s', salary=%.1f, exp=%d}",
            name, department, salary, yearsOfExperience);
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Main
// ─────────────────────────────────────────────────────────────────────────────

public class Solution13StreamsLambdas {

    public static void main(String[] args) {

        // ── SETUP: Employee list ────────────────────────────────────────────
        List<Employee> employees = List.of(
            new Employee("Alice",   "Engineering", 120000, 10),
            new Employee("Bob",     "Marketing",    55000,  3),
            new Employee("Charlie", "Engineering",  95000,  7),
            new Employee("Diana",   "HR",           48000,  2),
            new Employee("Eve",     "Engineering",  87000,  5),
            new Employee("Frank",   "Marketing",    72000,  8),
            new Employee("Grace",   "HR",           51000, 12),
            new Employee("Hank",    "Engineering",  40000,  1),
            new Employee("Iris",    "Marketing",    65000,  4),
            new Employee("Jack",    "HR",           43000,  6)
        );

        // ── PART A: Lambda basics ───────────────────────────────────────────
        System.out.println("=== PART A: Sorting with Lambdas ===");

        // Sort by salary (ascending) — lambda
        List<Employee> bySalary = new ArrayList<>(employees);
        Collections.sort(bySalary, (a, b) -> Double.compare(a.getSalary(), b.getSalary()));
        System.out.println("By salary (asc): ");
        bySalary.forEach(e -> System.out.printf("  %-10s $%.0f%n", e.getName(), e.getSalary()));

        // Sort by name — method reference
        List<Employee> byName = employees.stream()
            .sorted(Comparator.comparing(Employee::getName))
            .collect(Collectors.toList());
        System.out.print("\nBy name: ");
        byName.stream().map(Employee::getName).forEach(n -> System.out.print(n + " "));
        System.out.println();

        // ── PART B: filter / map / collect ─────────────────────────────────
        System.out.println("\n=== PART B: filter / map / collect ===");

        List<Employee> engineers = employees.stream()
            .filter(e -> e.getDepartment().equals("Engineering"))
            .collect(Collectors.toList());
        System.out.println("Engineering employees: " +
            engineers.stream().map(Employee::getName).collect(Collectors.toList()));

        List<Employee> highEarners = employees.stream()
            .filter(e -> e.getSalary() > 80000)
            .collect(Collectors.toList());
        System.out.println("Salary > 80k: " +
            highEarners.stream().map(Employee::getName).collect(Collectors.toList()));

        List<String> allNames = employees.stream()
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println("All names: " + allNames);

        // Chain: Engineering + salary > 80k + sorted by name
        List<String> seniorEngineers = employees.stream()
            .filter(e -> e.getDepartment().equals("Engineering"))
            .filter(e -> e.getSalary() > 80000)
            .sorted(Comparator.comparing(Employee::getName))
            .map(Employee::getName)
            .collect(Collectors.toList());
        System.out.println("Senior Engineers (>80k, sorted): " + seniorEngineers);

        // ── PART C: reduce / statistics ────────────────────────────────────
        System.out.println("\n=== PART C: reduce / statistics ===");

        double totalSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .sum();
        System.out.printf("Total salary bill: $%.0f%n", totalSalary);

        double maxSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .max()
            .orElse(0);
        System.out.printf("Highest salary: $%.0f%n", maxSalary);

        OptionalDouble avgSalary = employees.stream()
            .mapToDouble(Employee::getSalary)
            .average();
        System.out.printf("Average salary: $%.2f%n", avgSalary.orElse(0));

        long experiencedCount = employees.stream()
            .filter(e -> e.getYearsOfExperience() > 5)
            .count();
        System.out.println("Employees with >5 years experience: " + experiencedCount);

        // reduce() example — manual sum
        double manualSum = employees.stream()
            .mapToDouble(Employee::getSalary)
            .reduce(0, Double::sum);
        System.out.printf("Manual reduce sum: $%.0f%n", manualSum);

        // ── PART D: groupingBy / partitioningBy ────────────────────────────
        System.out.println("\n=== PART D: groupingBy / partitioningBy ===");

        Map<String, List<Employee>> byDept = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));
        byDept.forEach((dept, emps) -> {
            List<String> names = emps.stream().map(Employee::getName).collect(Collectors.toList());
            System.out.println("  " + dept + ": " + names);
        });

        Map<String, Double> avgByDept = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
        System.out.println("\nAvg salary by department:");
        avgByDept.forEach((dept, avg) ->
            System.out.printf("  %-15s $%.2f%n", dept, avg));

        // partitioningBy → two groups: true (>=80k) and false (<80k)
        Map<Boolean, List<Employee>> partitioned = employees.stream()
            .collect(Collectors.partitioningBy(e -> e.getSalary() >= 80000));
        System.out.println("\nHigh earners (>=80k): " +
            partitioned.get(true).stream().map(Employee::getName).collect(Collectors.toList()));
        System.out.println("Lower earners (<80k): " +
            partitioned.get(false).stream().map(Employee::getName).collect(Collectors.toList()));

        // ── PART E: Optional ────────────────────────────────────────────────
        System.out.println("\n=== PART E: Optional ===");

        // max() returns Optional<Employee>
        Optional<Employee> topEarner = employees.stream()
            .max(Comparator.comparingDouble(Employee::getSalary));
        topEarner.ifPresent(e ->
            System.out.printf("Highest paid: %s - $%.0f%n", e.getName(), e.getSalary()));
        // orElse — safe default
        Employee fallback = topEarner.orElse(new Employee("Nobody", "None", 0, 0));
        System.out.println("orElse result: " + fallback.getName());

        // findFirst + orElseThrow
        Optional<Employee> hrSenior = employees.stream()
            .filter(e -> e.getDepartment().equals("HR"))
            .filter(e -> e.getYearsOfExperience() > 10)
            .findFirst();
        hrSenior.ifPresent(e ->
            System.out.println("HR senior (>10 yrs): " + e.getName()));

        try {
            Employee mustExist = employees.stream()
                .filter(e -> e.getDepartment().equals("Legal")) // no Legal dept
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Legal department employees found"));
        } catch (RuntimeException e) {
            System.out.println("orElseThrow caught: " + e.getMessage());
        }

        // ── PART F: Method References ───────────────────────────────────────
        System.out.println("\n=== PART F: Method References ===");
        System.out.println("Names in uppercase (method references only):");
        employees.stream()
            .map(Employee::getName)         // ClassName::instanceMethod
            .map(String::toUpperCase)       // ClassName::instanceMethod
            .forEach(System.out::println);  // object::instanceMethod

        /*
         * KEY TAKEAWAYS:
         * ─────────────
         * ✅ stream().filter().map().collect()  — the core pipeline pattern
         * ✅ mapToDouble/mapToInt               — primitive specializations (avoid boxing)
         * ✅ Collectors.groupingBy              — SQL GROUP BY equivalent
         * ✅ Collectors.partitioningBy          — splits into true/false groups
         * ✅ Optional.ifPresent/orElse/orElseThrow — null-safe alternatives
         * ✅ Method references                  — cleaner lambdas when just delegating
         *
         * STREAM vs FOR-LOOP:
         * ───────────────────
         * for-loop  → imperative: you say HOW to iterate
         * stream    → declarative: you say WHAT you want
         * Use streams for readability; use for-loops when you need index/mutable state
         */
    }
}
