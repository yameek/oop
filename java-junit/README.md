# JUnit 5 — Java Testing Module

Learn to write professional unit tests with JUnit 5.

## Setup (one-time)

You need **Java 17+** and **Maven**:
```bash
java -version
mvn -version
```

Install Maven if needed:
```bash
sudo apt install maven   # Ubuntu/Debian
brew install maven       # macOS
```

## Running Tests

```bash
cd java-junit

# Run all tests
mvn test

# Run with verbose output
mvn test -Dsurefire.useFile=false

# Run a specific test class
mvn test -Dtest=BankAccountSolution
```

## Structure

```
java-junit/
├── pom.xml                         ← Maven config with JUnit 5 dependency
└── src/
    ├── main/java/
    │   └── BankAccount.java        ← Class being tested
    └── test/java/
        ├── BankAccountTest.java    ← YOUR task: write tests here
        └── BankAccountSolution.java ← Full solution with all patterns
```

## Learning Path

1. Read `BankAccountTest.java` — it contains detailed instructions for all 20 tasks
2. Write your tests in `BankAccountTest.java`
3. Run `mvn test` to see which tests pass
4. Compare with `BankAccountSolution.java` when done

## JUnit 5 Quick Reference

| Annotation | Purpose |
|------------|---------|
| `@Test` | Marks a test method |
| `@BeforeEach` | Runs before each test |
| `@AfterEach` | Runs after each test |
| `@BeforeAll` | Runs once before all tests (static) |
| `@DisplayName` | Human-readable test name |
| `@Nested` | Groups related tests |
| `@Disabled` | Skips a test |
| `@ParameterizedTest` | Runs test with multiple inputs |
| `@ValueSource` | Provides primitive values |
| `@MethodSource` | Provides complex values via static method |

| Assertion | Checks |
|-----------|--------|
| `assertEquals(expected, actual)` | Values are equal |
| `assertNotEquals(a, b)` | Values differ |
| `assertTrue(condition)` | Condition is true |
| `assertFalse(condition)` | Condition is false |
| `assertNull(obj)` | Object is null |
| `assertNotNull(obj)` | Object is not null |
| `assertThrows(Ex.class, () -> ...)` | Lambda throws given exception |
| `assertDoesNotThrow(() -> ...)` | Lambda throws nothing |
| `assertAll(exec1, exec2, ...)` | All assertions run (no short-circuit) |
