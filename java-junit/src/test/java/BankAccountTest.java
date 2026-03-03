/**
 * TASK 15: JUnit 5 — Writing Tests
 * ==================================
 * Difficulty: Intermediate ⭐⭐⭐
 *
 * SETUP:
 * ------
 * This module uses Maven. Run tests with:
 *   cd java-junit && mvn test
 *
 * Or if you have IntelliJ / VSCode with Java Extension Pack: just right-click and "Run Tests"
 *
 * WHAT IS UNIT TESTING?
 * ----------------------
 * Unit testing = testing individual units of code (a method, a class) in isolation.
 * JUnit 5 is the standard Java testing framework.
 *
 * A test method:
 *   @Test
 *   void depositShouldIncreaseBalance() {
 *       BankAccount acc = new BankAccount("A1", 100);
 *       acc.deposit(50);
 *       assertEquals(150, acc.getBalance());
 *   }
 *
 * KEY ANNOTATIONS:
 * ----------------
 * @Test              — marks a method as a test case
 * @BeforeEach        — runs before EACH test (setup)
 * @AfterEach         — runs after EACH test (teardown)
 * @BeforeAll         — runs once before ALL tests (static method)
 * @AfterAll          — runs once after ALL tests (static method)
 * @DisplayName       — gives a readable name to the test
 * @Disabled          — skips the test
 * @ParameterizedTest — runs same test with multiple inputs
 * @ValueSource        — provides values to parameterized test
 * @MethodSource       — provides complex values via a static method
 * @Nested            — groups related tests inside a test class
 *
 * COMMON ASSERTIONS (from org.junit.jupiter.api.Assertions):
 * -----------------------------------------------------------
 * assertEquals(expected, actual)
 * assertNotEquals(unexpected, actual)
 * assertTrue(condition)
 * assertFalse(condition)
 * assertNull(object)
 * assertNotNull(object)
 * assertThrows(ExceptionClass.class, () -> { ... })
 * assertDoesNotThrow(() -> { ... })
 * assertAll(executable1, executable2, ...)  ← checks ALL assertions, not just first failure
 *
 * YOUR TASKS:
 * -----------
 * The class being tested: BankAccount (in src/main/java/BankAccount.java)
 *
 * Write tests in this file that cover:
 *
 * TASK A — Basic test structure
 * 1. @BeforeEach: Create a fresh BankAccount("TEST", 500) before each test
 * 2. Test: initial balance is correct after construction
 * 3. Test: constructor throws IllegalArgumentException for negative initial balance
 *
 * TASK B — Deposit tests
 * 4. Test: deposit increases balance correctly
 * 5. Test: multiple deposits accumulate correctly
 * 6. Test: deposit throws IllegalArgumentException for amount <= 0
 * 7. Test: deposit throws IllegalStateException when account is frozen
 *
 * TASK C — Withdraw tests
 * 8. Test: withdraw decreases balance correctly
 * 9. Test: withdraw throws IllegalStateException (insufficient funds)
 * 10. Test: withdraw throws IllegalArgumentException for negative amount
 * 11. Test: withdraw exact balance → balance becomes 0
 *
 * TASK D — Freeze/unfreeze tests
 * 12. Test: isFrozen() returns false initially
 * 13. Test: freeze() makes isFrozen() true
 * 14. Test: unfreeze() makes isFrozen() false
 *
 * TASK E — Transfer tests
 * 15. Test: transfer moves money from sender to receiver correctly
 * 16. Test: transfer to frozen account rolls back the sender's balance
 * 17. Test: transfer with insufficient funds throws exception
 *
 * TASK F — Parameterized tests
 * 18. Use @ParameterizedTest + @ValueSource to test that deposit
 *     throws for all of: -100, -1, 0 (three invalid amounts)
 * 19. Use @ParameterizedTest + @MethodSource to test that deposit
 *     works for multiple valid amounts and produces correct balances
 *
 * TASK G — @Nested + @DisplayName
 * 20. Organize your deposit tests inside a @Nested class called "DepositTests"
 *     and your withdraw tests inside "WithdrawTests"
 *     Give each test a @DisplayName with plain English description
 *
 * HOW TO THINK ABOUT TESTS (AAA pattern):
 * ----------------------------------------
 * Arrange — set up the objects and preconditions
 * Act     — call the method you're testing
 * Assert  — verify the result is what you expected
 *
 * TIPS:
 * -----
 * - One test = one concern. Don't test 5 things in one method.
 * - Test method names should read like sentences: depositShouldIncreaseBalance()
 * - Test BOTH the happy path AND edge cases / error cases
 * - assertThrows returns the exception — you can then check its message too
 */

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

// Write your tests below:
public class BankAccountTest {

    // TODO: Add @BeforeEach setup and all test methods here

}
