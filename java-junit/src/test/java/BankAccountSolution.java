/**
 * SOLUTION 15: JUnit 5 — BankAccount Tests
 * ==========================================
 *
 * CONCEPTS DEMONSTRATED:
 * ----------------------
 * - @BeforeEach setup
 * - @Test with assertions (assertEquals, assertTrue, assertThrows, assertAll)
 * - assertThrows — captures exception and lets you inspect it
 * - @Nested — groups related tests; each group can have its own @BeforeEach
 * - @DisplayName — human-readable test descriptions
 * - @ParameterizedTest + @ValueSource — run same test with multiple inputs
 * - @ParameterizedTest + @MethodSource — complex parameterized test cases
 * - assertAll — reports ALL failures at once, not just the first
 *
 * Run with: mvn test  (from the java-junit/ directory)
 */

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BankAccount Tests")
public class BankAccountSolution {

    // Shared account recreated before each test — ensures test isolation
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("TEST", 500.0);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TASK A: Construction
    // ─────────────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Initial balance matches constructor argument")
    void initialBalanceIsCorrect() {
        assertEquals(500.0, account.getBalance());
    }

    @Test
    @DisplayName("Constructor with negative balance throws IllegalArgumentException")
    void negativeInitialBalanceThrows() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> new BankAccount("BAD", -100)
        );
        assertTrue(ex.getMessage().contains("-100"),
            "Exception message should mention the bad value");
    }

    @Test
    @DisplayName("New account is not frozen")
    void newAccountIsNotFrozen() {
        assertFalse(account.isFrozen());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TASK G: @Nested — DepositTests
    // ─────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Deposit Tests")
    class DepositTests {

        @Test
        @DisplayName("Deposit increases balance by exact amount")
        void depositIncreasesBalance() {
            account.deposit(200);
            assertEquals(700.0, account.getBalance());
        }

        @Test
        @DisplayName("Multiple deposits accumulate correctly")
        void multipleDepositsAccumulate() {
            account.deposit(100);
            account.deposit(150);
            account.deposit(50);
            assertEquals(800.0, account.getBalance());
        }

        @Test
        @DisplayName("Deposit throws for zero amount")
        void depositZeroThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        }

        @Test
        @DisplayName("Deposit throws for negative amount")
        void depositNegativeThrows() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(-50));
        }

        @Test
        @DisplayName("Deposit on frozen account throws IllegalStateException")
        void depositOnFrozenAccountThrows() {
            account.freeze();
            IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> account.deposit(100)
            );
            assertTrue(ex.getMessage().contains("frozen"));
        }

        // ── TASK F: Parameterized test ───────────────────────────────────────

        @ParameterizedTest(name = "deposit({0}) should throw")
        @DisplayName("Deposit throws for all invalid amounts")
        @ValueSource(doubles = {-100, -1, 0})
        void depositThrowsForInvalidAmounts(double invalidAmount) {
            assertThrows(IllegalArgumentException.class,
                () -> account.deposit(invalidAmount));
        }

        @ParameterizedTest(name = "deposit({0}) → balance should be {1}")
        @DisplayName("Deposit produces correct balance for valid amounts")
        @MethodSource("validDepositCases")
        void depositProducesCorrectBalance(double depositAmount, double expectedBalance) {
            account.deposit(depositAmount);
            assertEquals(expectedBalance, account.getBalance(), 0.001);
        }

        // Static method providing test cases for @MethodSource
        static Stream<Arguments> validDepositCases() {
            return Stream.of(
                Arguments.of(100.0,  600.0),
                Arguments.of(500.0, 1000.0),
                Arguments.of(0.01,  500.01),
                Arguments.of(9999.0, 10499.0)
            );
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TASK G: @Nested — WithdrawTests
    // ─────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Withdraw Tests")
    class WithdrawTests {

        @Test
        @DisplayName("Withdraw decreases balance correctly")
        void withdrawDecreasesBalance() {
            account.withdraw(200);
            assertEquals(300.0, account.getBalance());
        }

        @Test
        @DisplayName("Withdraw exact balance leaves zero")
        void withdrawExactBalance() {
            account.withdraw(500.0);
            assertEquals(0.0, account.getBalance());
        }

        @Test
        @DisplayName("Withdraw throws IllegalStateException when insufficient funds")
        void overdraftThrows() {
            IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> account.withdraw(600)
            );
            assertTrue(ex.getMessage().toLowerCase().contains("insufficient"),
                "Message should say insufficient");
        }

        @Test
        @DisplayName("Withdraw throws IllegalArgumentException for negative amount")
        void negativeWithdrawThrows() {
            assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-50));
        }

        @Test
        @DisplayName("Withdraw on frozen account throws IllegalStateException")
        void withdrawOnFrozenThrows() {
            account.freeze();
            assertThrows(IllegalStateException.class,
                () -> account.withdraw(100));
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TASK D: Freeze / Unfreeze
    // ─────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Freeze / Unfreeze Tests")
    class FreezeTests {

        @Test
        @DisplayName("freeze() makes isFrozen() return true")
        void freezeMakesAccountFrozen() {
            account.freeze();
            assertTrue(account.isFrozen());
        }

        @Test
        @DisplayName("unfreeze() makes isFrozen() return false")
        void unfreezeMakesAccountNotFrozen() {
            account.freeze();
            account.unfreeze();
            assertFalse(account.isFrozen());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TASK E: Transfer
    // ─────────────────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Transfer Tests")
    class TransferTests {

        private BankAccount receiver;

        @BeforeEach
        void setUpReceiver() {
            // @Nested classes get their own @BeforeEach in addition to the outer one
            receiver = new BankAccount("RECV", 0);
        }

        @Test
        @DisplayName("Transfer moves money correctly between accounts")
        void transferMovesMoneyCorrectly() {
            account.transfer(receiver, 200);
            // assertAll checks ALL assertions and reports all failures at once
            assertAll("transfer balances",
                () -> assertEquals(300.0, account.getBalance(),   "Sender balance"),
                () -> assertEquals(200.0, receiver.getBalance(), "Receiver balance")
            );
        }

        @Test
        @DisplayName("Transfer to frozen account rolls back sender balance")
        void transferToFrozenRollsBack() {
            receiver.freeze();
            double senderBefore = account.getBalance();
            assertThrows(IllegalStateException.class,
                () -> account.transfer(receiver, 200));
            // Sender's balance should be restored
            assertEquals(senderBefore, account.getBalance(),
                "Sender balance should be restored after failed transfer");
        }

        @Test
        @DisplayName("Transfer with insufficient funds throws and leaves balances unchanged")
        void transferInsufficientFunds() {
            assertThrows(IllegalStateException.class,
                () -> account.transfer(receiver, 9999));
            assertAll("balances unchanged",
                () -> assertEquals(500.0, account.getBalance()),
                () -> assertEquals(0.0, receiver.getBalance())
            );
        }
    }

    /*
     * KEY TAKEAWAYS:
     * ─────────────
     * ✅ @BeforeEach ensures each test gets a clean state — tests don't depend on each other
     * ✅ assertThrows returns the exception — always check the message too
     * ✅ assertAll reports ALL failures — great for checking multiple related values
     * ✅ @Nested organizes tests logically; each nest can have its own @BeforeEach
     * ✅ @ParameterizedTest eliminates copy-paste for same logic with different inputs
     * ✅ @MethodSource allows complex test data (multiple arguments, objects)
     *
     * TEST NAMING CONVENTION:
     * ───────────────────────
     * Option 1: methodName_StateUnderTest_ExpectedBehavior
     *           e.g. deposit_NegativeAmount_ThrowsException
     * Option 2: Plain English with @DisplayName (used here)
     *           e.g. "Deposit throws for negative amount"
     * Pick one and be consistent.
     *
     * WHAT MAKES A GOOD TEST:
     * ───────────────────────
     * F — Fast         (milliseconds)
     * I — Independent  (no shared mutable state between tests)
     * R — Repeatable   (same result every run)
     * S — Self-validating (pass or fail, no manual inspection)
     * T — Thorough     (happy path + edge cases + error cases)
     */
}
