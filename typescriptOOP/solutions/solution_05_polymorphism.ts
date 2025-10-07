/**
 * SOLUTION 5: Polymorphism & Union Types
 * ======================================
 *
 * Concepts covered:
 * - Shared interface implemented by multiple classes
 * - Discriminated unions for exhaustive branching
 * - Type guards via `switch` on `type`
 */

interface PaymentMethod {
  readonly type: "card" | "cash" | "crypto";
  charge(amount: number): string;
}

class CardPayment implements PaymentMethod {
  readonly type = "card" as const;

  constructor(private readonly cardNumber: string, private readonly expiry: string) {}

  charge(amount: number): string {
    const lastFour = this.cardNumber.slice(-4);
    return `Charged ${this.format(amount)} to card ending ${lastFour} (exp ${this.expiry}).`;
  }

  private format(amount: number): string {
    return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
  }
}

class CashPayment implements PaymentMethod {
  readonly type = "cash" as const;

  charge(amount: number): string {
    return `Collected ${this.format(amount)} in cash.`;
  }

  private format(amount: number): string {
    return `$${amount.toFixed(2)}`;
  }
}

class CryptoPayment implements PaymentMethod {
  readonly type = "crypto" as const;

  constructor(private readonly walletAddress: string, private readonly token: string) {}

  charge(amount: number): string {
    return `Initiated crypto transfer of ${amount.toFixed(4)} ${this.token} to ${this.walletAddress}.`;
  }
}

type SupportedPayment = CardPayment | CashPayment | CryptoPayment;

type PaymentResult =
  | { ok: true; receipt: string }
  | { ok: false; reason: string };

function processPayment(payment: SupportedPayment, amount: number): PaymentResult {
  if (amount <= 0) {
    return { ok: false, reason: "Amount must be greater than zero." };
  }

  switch (payment.type) {
    case "card":
    case "cash":
    case "crypto":
      return { ok: true, receipt: payment.charge(amount) };
    default:
      const neverType: never = payment;
      throw new Error(`Unsupported payment method ${(neverType as PaymentMethod).type}`);
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const samplePayments: Array<{ method: SupportedPayment; amount: number }> = [
  { method: new CardPayment("4242424242424242", "12/26"), amount: 129.99 },
  { method: new CashPayment(), amount: 45.5 },
  { method: new CryptoPayment("0xDEADBEEF", "ETH"), amount: 0.0153 },
  { method: new CardPayment("1111222233334444", "01/27"), amount: -5 },
];

for (const { method, amount } of samplePayments) {
  const result = processPayment(method, amount);

  if (result.ok) {
    console.log(result.receipt);
  } else {
    console.warn(`Payment failed: ${result.reason}`);
  }
}

export {};
