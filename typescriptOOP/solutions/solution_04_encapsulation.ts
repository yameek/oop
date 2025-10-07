/**
 * SOLUTION 4: Encapsulation & Accessors
 * =====================================
 *
 * Concepts covered:
 * - ECMAScript #private fields
 * - Input validation with errors
 * - Protected members for subclasses
 */

class BankAccount {
  #balance: number;
  protected readonly transactionLog: Array<{ type: string; amount: number; time: Date }> = [];

  constructor(public readonly accountNumber: string, protected ownerName: string, initialBalance = 0) {
    if (initialBalance < 0) {
      throw new RangeError("Initial balance cannot be negative");
    }

    this.#balance = initialBalance;
    this.recordTransaction("INIT", initialBalance);
  }

  get balance(): number {
    return this.#balance;
  }

  set owner(newName: string) {
    const sanitized = newName.trim();
    if (!sanitized) {
      throw new Error("Owner name cannot be empty");
    }
    this.ownerName = sanitized;
  }

  get owner(): string {
    return this.ownerName;
  }

  deposit(amount: number): void {
    this.ensurePositiveAmount(amount);
    this.#balance += amount;
    this.recordTransaction("DEPOSIT", amount);
    console.log(`Deposited ${this.format(amount)} into ${this.accountNumber}.`);
  }

  withdraw(amount: number): void {
    this.ensurePositiveAmount(amount);
    if (amount > this.#balance) {
      throw new Error("Insufficient funds");
    }
    this.#balance -= amount;
    this.recordTransaction("WITHDRAW", amount);
    console.log(`Withdrew ${this.format(amount)} from ${this.accountNumber}.`);
  }

  printStatement(): void {
    console.log("\n=== Account Statement ===");
    console.log(`Account: ${this.accountNumber}`);
    console.log(`Owner  : ${this.ownerName}`);
    console.log(`Balance: ${this.format(this.balance)}`);
    console.log("Recent activity:");
    this.transactionLog.slice(-5).forEach((entry) => {
      console.log(` - ${entry.time.toISOString()} :: ${entry.type} ${this.format(entry.amount)}`);
    });
    console.log("=========================\n");
  }

  protected recordTransaction(type: string, amount: number): void {
    this.transactionLog.push({ type, amount, time: new Date() });
  }

  private ensurePositiveAmount(amount: number): void {
    if (amount <= 0) {
      throw new RangeError("Amount must be positive");
    }
  }

  private format(amount: number): string {
    return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
  }
}

class SavingsAccount extends BankAccount {
  constructor(accountNumber: string, ownerName: string, initialBalance = 0, private readonly interestRate = 0.02) {
    super(accountNumber, ownerName, initialBalance);
  }

  applyMonthlyInterest(): void {
    const interest = this.balance * this.interestRate;
    this.deposit(interest);
    this.recordTransaction("INTEREST", interest);
    console.log(`Applied interest of ${interest.toFixed(2)} at rate ${(this.interestRate * 100).toFixed(2)}%.`);
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const primary = new SavingsAccount("AC-123456", "Jordan Smith", 500);

primary.deposit(250);
primary.withdraw(125);
try {
  primary.withdraw(1000);
} catch (error) {
  if (error instanceof Error) {
    console.log(`⚠️  Withdrawal failed: ${error.message}`);
  }
}

primary.applyMonthlyInterest();
primary.printStatement();

primary.owner = "Jordan A. Smith";
primary.printStatement();
export {};
