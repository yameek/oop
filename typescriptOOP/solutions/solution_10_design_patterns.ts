/**
 * SOLUTION 10: Design Patterns (Singleton + Factory)
 * =================================================
 *
 * Concepts covered:
 * - Singleton pattern with lazy initialization
 * - Factory method returning concrete implementations
 * - Shared logger dependency across instances
 */

class Logger {
  private static instance: Logger | null = null;

  private constructor() {}

  static getInstance(): Logger {
    if (!Logger.instance) {
      Logger.instance = new Logger();
    }
    return Logger.instance;
  }

  log(message: string): void {
    const timestamp = new Date().toISOString();
    console.log(`[${timestamp}] ${message}`);
  }
}

interface Notifier {
  send(message: string): void;
}

class EmailNotification implements Notifier {
  private readonly logger = Logger.getInstance();

  constructor(private readonly from: string, private readonly to: string) {}

  send(message: string): void {
    this.logger.log(`Email -> To: ${this.to} | From: ${this.from} | Msg: ${message}`);
  }
}

class SmsNotification implements Notifier {
  private readonly logger = Logger.getInstance();

  constructor(private readonly phoneNumber: string) {}

  send(message: string): void {
    this.logger.log(`SMS -> Number: ${this.phoneNumber} | Msg: ${message}`);
  }
}

class PushNotification implements Notifier {
  private readonly logger = Logger.getInstance();

  constructor(private readonly deviceToken: string, private readonly appName: string) {}

  send(message: string): void {
    this.logger.log(`Push -> App: ${this.appName} | Token: ${this.deviceToken} | Msg: ${message}`);
  }
}

type NotificationConfig = Record<string, unknown>;

class NotificationFactory {
  static create(type: string, config: NotificationConfig): Notifier {
    switch (type.toLowerCase()) {
      case "email":
        return new EmailNotification(
          String(config.from ?? "noreply@example.com"),
          String(config.to ?? "user@example.com")
        );
      case "sms":
        if (!config.number) {
          throw new Error("SMS config requires 'number'");
        }
        return new SmsNotification(String(config.number));
      case "push":
        if (!config.token || !config.app) {
          throw new Error("Push config requires 'token' and 'app'");
        }
        return new PushNotification(String(config.token), String(config.app));
      default:
        throw new Error(`Unknown notification type '${type}'`);
    }
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const notifications: Notifier[] = [
  NotificationFactory.create("email", { from: "alerts@example.com", to: "dev@example.com" }),
  NotificationFactory.create("sms", { number: "+12065551234" }),
  NotificationFactory.create("push", { token: "device-123", app: "AcmeApp" }),
];

notifications.forEach((channel, index) => {
  channel.send(`Notification #${index + 1}`);
});

// Prove singleton behavior
const loggerA = Logger.getInstance();
const loggerB = Logger.getInstance();
console.log("Logger instances identical:", loggerA === loggerB);

export {};
