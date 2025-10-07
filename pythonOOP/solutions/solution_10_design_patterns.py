"""
SOLUTION 10: Design Patterns (Singleton, Factory)
==================================================

CONCEPTS EXPLAINED:
-------------------
SINGLETON PATTERN:
1. Ensures only ONE instance of a class exists
2. Provides global access point to that instance
3. Useful for: database connections, logging, caching
4. Implementation: Override __new__ method

FACTORY PATTERN:
5. Creates objects without specifying exact class
6. Centralizes object creation logic
7. Makes code more flexible and maintainable
8. Useful for: creating similar objects, plugin systems

"""

# ============================================
# PART A: SINGLETON PATTERN
# ============================================

class DatabaseConnection:
    """
    Singleton class for database connection
    
    NOTE: Only one instance can exist at any time
    All attempts to create new instances return the same object
    """
    
    # Class variable to store the single instance
    _instance = None
    _initialized = False
    
    def __new__(cls, *args, **kwargs):
        """
        Override __new__ to control instance creation
        
        NOTE: __new__ is called BEFORE __init__
        It's responsible for creating the object
        This is where we implement the singleton pattern
        """
        if cls._instance is None:
            print("🔨 Creating new DatabaseConnection instance...")
            cls._instance = super().__new__(cls)
        else:
            print("♻️  Returning existing DatabaseConnection instance...")
        
        return cls._instance
    
    def __init__(self, host="localhost", port=5432, database_name="mydb"):
        """
        Initialize database connection
        
        NOTE: __init__ is called every time, even for existing instance
        We use _initialized flag to prevent re-initialization
        """
        # Only initialize once
        if not DatabaseConnection._initialized:
            print(f"🔧 Initializing connection to {database_name}...")
            self.host = host
            self.port = port
            self.database_name = database_name
            self.connection_status = "disconnected"
            DatabaseConnection._initialized = True
        else:
            print("⚠️  Already initialized, skipping re-initialization")
    
    def connect(self):
        """Connect to the database"""
        if self.connection_status == "connected":
            print(f"ℹ️  Already connected to {self.database_name}")
            return
        
        print(f"🔌 Connecting to {self.database_name} at {self.host}:{self.port}...")
        self.connection_status = "connected"
        print(f"✓ Connected successfully!")
    
    def disconnect(self):
        """Disconnect from the database"""
        if self.connection_status == "disconnected":
            print("ℹ️  Already disconnected")
            return
        
        print(f"🔌 Disconnecting from {self.database_name}...")
        self.connection_status = "disconnected"
        print("✓ Disconnected successfully!")
    
    def execute_query(self, query):
        """
        Execute a database query
        
        Parameters:
        -----------
        query : str
            SQL query to execute
        """
        if self.connection_status != "connected":
            print("❌ Error: Not connected to database")
            return
        
        print(f"🔍 Executing query: {query}")
        print(f"✓ Query executed successfully!")
    
    def get_status(self):
        """Display connection status"""
        print(f"\n{'=' * 50}")
        print(f"Database: {self.database_name}")
        print(f"Host: {self.host}:{self.port}")
        print(f"Status: {self.connection_status}")
        print(f"{'=' * 50}")


# ============================================
# PART B: FACTORY PATTERN
# ============================================

class Notification:
    """Base class for all notifications"""
    
    def __init__(self, recipient, message):
        """
        Initialize a Notification
        
        Parameters:
        -----------
        recipient : str
            Notification recipient
        message : str
            Message to send
        """
        self.recipient = recipient
        self.message = message
        self.sent = False
    
    def send(self):
        """
        Send the notification - must be overridden
        
        NOTE: This is similar to abstract method
        """
        raise NotImplementedError("Subclasses must implement send()")


class EmailNotification(Notification):
    """Email notification implementation"""
    
    def send(self):
        """Send email notification"""
        print(f"\n📧 Sending EMAIL to {self.recipient}")
        print(f"   Subject: Notification")
        print(f"   Message: {self.message}")
        print(f"   ✓ Email sent successfully!")
        self.sent = True
        return True


class SMSNotification(Notification):
    """SMS notification implementation"""
    
    def send(self):
        """Send SMS notification"""
        print(f"\n📱 Sending SMS to {self.recipient}")
        print(f"   Message: {self.message}")
        print(f"   ✓ SMS sent successfully!")
        self.sent = True
        return True


class PushNotification(Notification):
    """Push notification implementation"""
    
    def send(self):
        """Send push notification"""
        print(f"\n🔔 Sending PUSH notification to {self.recipient}")
        print(f"   Message: {self.message}")
        print(f"   ✓ Push notification sent successfully!")
        self.sent = True
        return True


class NotificationFactory:
    """
    Factory class for creating notifications
    
    NOTE: This is the FACTORY PATTERN
    Clients don't create notification objects directly
    They ask the factory to create them
    """
    
    @staticmethod
    def create_notification(notification_type, recipient, message):
        """
        Create a notification of the specified type
        
        Parameters:
        -----------
        notification_type : str
            Type of notification (email, sms, push)
        recipient : str
            Notification recipient
        message : str
            Message to send
        
        Returns:
        --------
        Notification : Appropriate notification object
        
        Raises:
        -------
        ValueError : If notification type is invalid
        """
        # Normalize type
        notification_type = notification_type.lower()
        
        # Factory logic - decide which class to instantiate
        if notification_type == "email":
            return EmailNotification(recipient, message)
        elif notification_type == "sms":
            return SMSNotification(recipient, message)
        elif notification_type == "push":
            return PushNotification(recipient, message)
        else:
            raise ValueError(f"Invalid notification type: {notification_type}")


class NotificationManager:
    """
    Manages notifications using the factory
    
    NOTE: This shows how to use the factory pattern
    """
    
    def __init__(self):
        """Initialize notification manager"""
        self.notification_history = []
    
    def send_notification(self, notification_type, recipient, message):
        """
        Send a notification using the factory
        
        Parameters:
        -----------
        notification_type : str
            Type of notification
        recipient : str
            Recipient
        message : str
            Message to send
        
        Returns:
        --------
        bool : True if sent successfully
        """
        try:
            # Use factory to create notification
            notification = NotificationFactory.create_notification(
                notification_type,
                recipient,
                message
            )
            
            # Send the notification
            success = notification.send()
            
            # Store in history
            if success:
                self.notification_history.append({
                    'type': notification_type,
                    'recipient': recipient,
                    'message': message,
                    'notification': notification
                })
            
            return success
            
        except ValueError as e:
            print(f"\n❌ Error: {e}")
            return False
    
    def get_notification_history(self):
        """
        Get history of all sent notifications
        
        Returns:
        --------
        list : List of sent notifications
        """
        return self.notification_history
    
    def display_history(self):
        """Display notification history"""
        print(f"\n{'=' * 60}")
        print("NOTIFICATION HISTORY")
        print(f"{'=' * 60}")
        
        if not self.notification_history:
            print("No notifications sent yet")
        else:
            for i, notif in enumerate(self.notification_history, 1):
                print(f"\n{i}. {notif['type'].upper()}")
                print(f"   To: {notif['recipient']}")
                print(f"   Message: {notif['message']}")
                print(f"   Sent: {notif['notification'].sent}")
        
        print(f"\nTotal notifications sent: {len(self.notification_history)}")
        print(f"{'=' * 60}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING DESIGN PATTERNS")
    print("=" * 60)
    
    # ========================================
    # PART 1: TESTING SINGLETON PATTERN
    # ========================================
    print("\n" + "=" * 60)
    print("PART 1: SINGLETON PATTERN")
    print("=" * 60)
    
    print("\n1. Creating first DatabaseConnection instance:")
    db1 = DatabaseConnection("localhost", 5432, "users_db")
    
    print("\n2. Attempting to create second instance:")
    db2 = DatabaseConnection("different_host", 3306, "different_db")
    
    print("\n3. Verifying both are the same object:")
    print(f"db1 is db2: {db1 is db2}")  # Should be True
    print(f"db1 == db2: {db1 == db2}")  # Should be True
    print(f"id(db1): {id(db1)}")
    print(f"id(db2): {id(db2)}")
    
    print("\n4. Checking configuration (should use first instance's config):")
    db1.get_status()
    db2.get_status()
    
    print("\n5. Testing database operations:")
    db1.connect()
    db1.execute_query("SELECT * FROM users")
    db1.execute_query("INSERT INTO users VALUES (1, 'Alice')")
    
    print("\n6. Using db2 (same connection):")
    db2.execute_query("SELECT * FROM products")  # Works because same connection
    
    db1.disconnect()
    
    print("\n7. Creating third instance:")
    db3 = DatabaseConnection()
    print(f"db3 is db1: {db3 is db1}")  # Should be True
    
    # ========================================
    # PART 2: TESTING FACTORY PATTERN
    # ========================================
    print("\n" + "=" * 60)
    print("PART 2: FACTORY PATTERN")
    print("=" * 60)
    
    print("\n1. Creating notifications using factory:")
    
    # Use factory to create different notification types
    email = NotificationFactory.create_notification(
        "email",
        "alice@example.com",
        "Your order has been shipped!"
    )
    
    sms = NotificationFactory.create_notification(
        "sms",
        "+1234567890",
        "Your verification code is 123456"
    )
    
    push = NotificationFactory.create_notification(
        "push",
        "user_12345",
        "You have a new message!"
    )
    
    print(f"✓ Created {type(email).__name__}")
    print(f"✓ Created {type(sms).__name__}")
    print(f"✓ Created {type(push).__name__}")
    
    print("\n2. Sending notifications:")
    email.send()
    sms.send()
    push.send()
    
    print("\n3. Testing invalid notification type:")
    try:
        invalid = NotificationFactory.create_notification(
            "carrier_pigeon",
            "somewhere",
            "Hello!"
        )
    except ValueError as e:
        print(f"✓ Caught expected error: {e}")
    
    print("\n4. Using NotificationManager:")
    manager = NotificationManager()
    
    manager.send_notification("email", "bob@example.com", "Welcome to our service!")
    manager.send_notification("sms", "+9876543210", "Your package has arrived!")
    manager.send_notification("push", "user_67890", "New friend request!")
    manager.send_notification("email", "carol@example.com", "Password reset link")
    
    print("\n5. Testing invalid notification with manager:")
    manager.send_notification("fax", "555-1234", "This won't work")
    
    print("\n6. Displaying notification history:")
    manager.display_history()
    
    print("\n7. Getting notification history:")
    history = manager.get_notification_history()
    print(f"Total notifications in history: {len(history)}")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("\nSINGLETON PATTERN:")
    print("1. Only one instance exists throughout the program")
    print("2. Implemented by overriding __new__ method")
    print("3. Useful for shared resources (DB, config, logging)")
    print("4. All references point to the same object")
    print("5. Use _instance class variable to store singleton")
    
    print("\nFACTORY PATTERN:")
    print("6. Centralizes object creation logic")
    print("7. Client doesn't need to know specific classes")
    print("8. Easy to add new types without changing client code")
    print("9. Returns objects based on parameters")
    print("10. Makes code more maintainable and flexible")
