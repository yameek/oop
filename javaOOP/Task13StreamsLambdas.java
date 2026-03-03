/**
 * TASK 13: Streams and Lambdas
 * ==============================
 * Difficulty: Advanced ⭐⭐⭐⭐
 *
 * Learn about: Lambda expressions, Functional interfaces, Stream API,
 *              Method references, Optional
 *
 * PROBLEM:
 * --------
 * You have a list of employees. Use Streams and Lambdas to query and
 * transform the data — without writing a single for loop.
 *
 * SETUP — Employee record
 * ------------------------
 * Create a class Employee with:
 * - name (String), department (String), salary (double), yearsOfExperience (int)
 * - Constructor and getters
 * - toString() → "Employee{name='Alice', dept='Engineering', salary=95000.0, exp=5}"
 *
 * PART A — Lambda Basics
 * -----------------------
 * 1. Create a List<Employee> with at least 8 employees across 3 departments
 *    (Engineering, Marketing, HR) with varying salaries (40k–120k) and experience (1–15 yrs)
 *
 * 2. Sort employees by salary (ascending) using a lambda with Collections.sort()
 * 3. Sort employees by name (alphabetically) using Comparator.comparing()
 * 4. Print all employees using forEach + lambda
 *
 * PART B — Stream filter / map / collect
 * ----------------------------------------
 * Using Stream API:
 * 1. Find all employees in "Engineering"
 * 2. Find all employees with salary > 80000
 * 3. Get a List<String> of just the names of all employees
 * 4. Get a List<String> of names of Engineering employees with salary > 80000, sorted
 *
 * PART C — Stream reduce / statistics
 * -------------------------------------
 * 1. Calculate the total salary bill (sum of all salaries)
 * 2. Find the highest salary
 * 3. Find the average salary
 * 4. Count employees with more than 5 years of experience
 *
 * PART D — Collectors.groupingBy / partitioningBy
 * --------------------------------------------------
 * 1. Group employees by department → Map<String, List<Employee>>
 *    Print each department and its employees
 * 2. Get average salary per department → Map<String, Double>
 * 3. Partition employees into two groups: salary >= 80000 and < 80000
 *    → Map<Boolean, List<Employee>>
 *
 * PART E — Optional
 * ------------------
 * 1. Find the employee with the highest salary using Stream.max() → returns Optional<Employee>
 *    - If present: print their name and salary
 *    - Use orElse() to provide a default if not found
 * 2. Find the first employee in HR with experience > 10 years
 *    - Use findFirst() which returns Optional<Employee>
 *    - Use ifPresent() to print result
 *    - Use orElseThrow() to throw RuntimeException if none found
 *
 * PART F — Method References
 * ---------------------------
 * Replace lambdas with method references where possible:
 * - System.out::println  instead of  e -> System.out.println(e)
 * - Employee::getName    instead of  e -> e.getName()
 * - String::toUpperCase  instead of  s -> s.toUpperCase()
 * Print all employee names in uppercase using method references only.
 *
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Lambda: (params) -> expression  OR  (params) -> { statements; }
 * - Functional interface: interface with exactly ONE abstract method (e.g., Predicate, Function)
 * - Stream: a pipeline of operations on a sequence; lazy until terminal op
 * - Intermediate ops (lazy): filter, map, sorted, distinct, limit
 * - Terminal ops (eager): collect, forEach, reduce, count, min, max, findFirst
 * - Optional: a container that may or may not hold a value — avoids NullPointerException
 * - Method reference types:
 *     ClassName::staticMethod       e.g. Integer::parseInt
 *     object::instanceMethod        e.g. System.out::println
 *     ClassName::instanceMethod     e.g. String::toUpperCase
 *     ClassName::new                e.g. Employee::new
 *
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Engineering employees: [Alice, Charlie, Eve]
 * Total salary bill: 720000.0
 * Highest salary: 120000.0
 * Avg salary: 72000.0
 * Department groups: {Engineering=[...], Marketing=[...], HR=[...]}
 * Highest paid: Alice - $120000.0
 */

// Write your solution below:
