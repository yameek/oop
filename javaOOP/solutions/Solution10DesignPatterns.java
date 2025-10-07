/**
 * SOLUTION 10: Design Patterns (Singleton, Factory, Builder)
 * =========================================================
 * Demonstrates three foundational design patterns with concise examples
 * tailored to the project requirements.
 *
 * Implementation notes:
 * - Singleton: double-checked locking on a volatile field keeps lazy initialization thread-safe
 *   without synchronizing every call. Guard methods surface state transitions and prevent misuse.
 * - Factory: a single entry point normalizes the type string and returns the appropriate
 *   `Notification` implementation, encapsulating construction logic and making it easy to extend.
 * - Builder: optional fields stay readable via fluent setters, while `build()` validates required
 *   data (`username`, `email`) before constructing an immutable `User`.
 * - The demonstration obtains two singleton references to prove they match, exercises each
 *   notification channel, and shows both successful and failing builder usages, covering the
 *   behaviors the prompt calls out.
 */
package solutions;

import java.util.Locale;

// --- Singleton Pattern ----------------------------------------------------
class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private boolean connected;

    private DatabaseConnection() {
        // Prevent external instantiation
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void connect() {
        if (!connected) {
            connected = true;
            System.out.println("Connected to database");
        } else {
            System.out.println("Already connected");
        }
    }

    public void disconnect() {
        if (connected) {
            connected = false;
            System.out.println("Disconnected from database");
        } else {
            System.out.println("Already disconnected");
        }
    }

    public void query(String sql) {
        if (!connected) {
            throw new IllegalStateException("Cannot execute query when not connected");
        }
        System.out.println("Executing query: " + sql);
    }
}

// --- Factory Pattern ------------------------------------------------------
interface Notification {
    void send(String message);
}

class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Push: " + message);
    }
}

class NotificationFactory {
    public static Notification createNotification(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Notification type cannot be null");
        }
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "email" -> new EmailNotification();
            case "sms" -> new SMSNotification();
            case "push" -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}

// --- Builder Pattern ------------------------------------------------------
class User {
    private final String username;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Integer age;
    private final String phone;

    private User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                (firstName != null ? ", firstName='" + firstName + '\'' : "") +
                (lastName != null ? ", lastName='" + lastName + '\'' : "") +
                (age != null ? ", age=" + age : "") +
                (phone != null ? ", phone='" + phone + '\'' : "") +
                '}';
    }

    public static class Builder {
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private Integer age;
        private String phone;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder age(int age) {
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
            this.age = age;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            if (username == null || username.isBlank()) {
                throw new IllegalStateException("Username is required");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalStateException("Email is required");
            }
            return new User(this);
        }
    }
}

public class Solution10DesignPatterns {
    public static void main(String[] args) {
        System.out.println("[SINGLETON]");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println("Database instances are same: " + (db1 == db2));
        db1.connect();
        db1.query("SELECT * FROM users");
        db2.disconnect();

        System.out.println("\n[FACTORY]");
        Notification email = NotificationFactory.createNotification("email");
        Notification sms = NotificationFactory.createNotification("sms");
        Notification push = NotificationFactory.createNotification("push");
        email.send("Hello via Email");
        sms.send("Hello via SMS");
        push.send("Hello via Push Notification");

        System.out.println("\n[BUILDER]");
        User user1 = new User.Builder()
                .username("john_doe")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();
        User user2 = new User.Builder()
                .username("jane")
                .email("jane@example.com")
                .age(25)
                .build();
        System.out.println(user1);
        System.out.println(user2);

        System.out.println("\nAttempting to build user without required fields...");
        try {
            new User.Builder().email("missing@example.com").build();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            System.out.println("Failed to build user: " + ex.getMessage());
        }
    }
}
