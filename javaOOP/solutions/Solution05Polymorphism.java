/**
 * SOLUTION 5: Polymorphism Through Payment Processing
 * ====================================================
 * Shows runtime polymorphism via overridden methods and compile-time
 * polymorphism via method overloading.
 */
package solutions;

abstract class Payment {
    private final double amount;
    private final String description;

    protected Payment(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void displayInfo() {
        System.out.printf("Payment of $%.2f for %s%n", amount, description);
    }

    public abstract void processPayment();
}

class CreditCardPayment extends Payment {
    private final String cardNumber;
    private final String cardHolder;

    public CreditCardPayment(double amount, String description, String cardNumber, String cardHolder) {
        super(amount, description);
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void processPayment() {
        System.out.printf("Processing Credit Card payment of $%.2f for %s%n", getAmount(), getDescription());
        System.out.printf("Card Holder: %s, Card: **** **** **** %s%n", cardHolder, cardNumber.substring(cardNumber.length() - 4));
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Payment Method: Credit Card (%s)%n", cardHolder);
    }
}

class PayPalPayment extends Payment {
    private final String email;

    public PayPalPayment(double amount, String description, String email) {
        super(amount, description);
        this.email = email;
    }

    @Override
    public void processPayment() {
        System.out.printf("Processing PayPal payment of $%.2f for %s%n", getAmount(), getDescription());
        System.out.printf("PayPal Account: %s%n", email);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Payment Method: PayPal (%s)%n", email);
    }
}

class CashPayment extends Payment {
    private final double amountTendered;

    public CashPayment(double amount, String description, double amountTendered) {
        super(amount, description);
        this.amountTendered = amountTendered;
    }

    @Override
    public void processPayment() {
        System.out.printf("Processing Cash payment of $%.2f for %s%n", getAmount(), getDescription());
        System.out.printf("Tendered: $%.2f, Change: $%.2f%n", amountTendered, calculateChange());
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Payment Method: Cash");
    }

    public double calculateChange() {
        return amountTendered - getAmount();
    }
}

class PaymentProcessor {
    public void process(Payment payment) {
        System.out.println("\n--- Processing single payment ---");
        payment.displayInfo();
        payment.processPayment();
    }

    public void process(Payment payment, boolean sendReceipt) {
        process(payment);
        if (sendReceipt) {
            System.out.println("Receipt has been emailed to the customer.");
        } else {
            System.out.println("Receipt suppressed as requested.");
        }
    }

    public void process(Payment[] payments) {
        System.out.println("\n=== Processing batch of payments ===");
        for (Payment payment : payments) {
            process(payment, true);
        }
        System.out.println("=== Batch complete ===");
    }
}

public class Solution05Polymorphism {
    public static void main(String[] args) {
        Payment creditCard = new CreditCardPayment(99.99, "Online Course", "1234567812345678", "Alex Johnson");
        Payment paypal = new PayPalPayment(49.99, "E-book", "user@example.com");
        Payment cash = new CashPayment(25.00, "Coffee Shop", 30.00);

        PaymentProcessor processor = new PaymentProcessor();

        processor.process(creditCard);
        processor.process(paypal, false);
        processor.process(new Payment[]{creditCard, paypal, cash});
    }
}
