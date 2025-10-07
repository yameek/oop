/**
 * SOLUTION 7: Interfaces & Structural Typing
 * ==========================================
 *
 * Concepts covered:
 * - Implementing interfaces in classes
 * - Structural typing with object literals
 * - Service class consuming polymorphic channels
 */

interface NotificationChannel {
  readonly type: string;
  readonly defaultRecipient: string;
  send(recipient: string, message: string): void;
}

abstract class BaseNotification implements NotificationChannel {
  constructor(public readonly type: string, public readonly defaultRecipient: string) {}

  abstract send(recipient: string, message: string): void;

  protected timestamp(): string {
    return new Date().toISOString();
  }
}

class EmailNotification extends BaseNotification {
  constructor(private readonly from: string, defaultRecipient: string) {
    super("email", defaultRecipient);
  }

  override send(recipient: string, message: string): void {
    console.log(`📧 [${this.timestamp()}] Email to ${recipient} (from ${this.from}): ${message}`);
  }
}

class SmsNotification extends BaseNotification {
  constructor(defaultRecipient: string) {
    super("sms", defaultRecipient);
  }

  override send(recipient: string, message: string): void {
    console.log(`📱 [${this.timestamp()}] SMS to ${recipient}: ${message}`);
  }
}

class PushNotification extends BaseNotification {
  constructor(private readonly appName: string, defaultRecipient: string) {
    super("push", defaultRecipient);
  }

  override send(recipient: string, message: string): void {
    console.log(`📲 [${this.timestamp()}] Push via ${this.appName} to ${recipient}: ${message}`);
  }
}

class NotificationService {
  private readonly channels: Map<string, NotificationChannel> = new Map();

  constructor(channels: NotificationChannel[]) {
    channels.forEach((channel) => this.register(channel));
  }

  register(channel: NotificationChannel): void {
    this.channels.set(channel.type.toLowerCase(), channel);
  }

  broadcast(message: string): void {
    console.log(`\nBroadcasting: ${message}`);
    for (const channel of this.channels.values()) {
      channel.send(channel.defaultRecipient, message);
    }
  }

  send(type: string, recipient: string, message: string): void {
    const key = type.toLowerCase();
    const channel = this.channels.get(key);
    if (!channel) {
      throw new Error(`Unknown channel type '${type}'`);
    }
    channel.send(recipient, message);
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const email = new EmailNotification("noreply@example.com", "user@example.com");
const sms = new SmsNotification("+12065551234");
const push = new PushNotification("AcmeApp", "user-device-token");

const service = new NotificationService([email, sms, push]);

// Structural typing: object literal that conforms to NotificationChannel
service.register({
  type: "webhook",
  defaultRecipient: "https://hooks.example.com",
  send: (recipient, message) => {
    console.log(`🌐 [${new Date().toISOString()}] POST ${recipient} :: ${message}`);
  },
});

service.broadcast("System maintenance tonight at 11 PM UTC.");
service.send("email", "support@example.com", "Ticket #42 has been resolved.");
service.send("webhook", "https://hooks.example.com/alerts", "API latency recovered.");

export {};
