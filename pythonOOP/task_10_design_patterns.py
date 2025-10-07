"""
TASK 10: Design Patterns (Singleton, Factory)
==============================================
Difficulty: Advanced

Learn about: Singleton Pattern, Factory Pattern

PROBLEM:
--------
Implement Singleton and Factory design patterns.

PART A - Singleton Pattern:
----------------------------
Create a 'DatabaseConnection' class that implements the Singleton pattern:
1. Only one instance of DatabaseConnection should ever exist
2. Attributes: host, port, database_name, connection_status
3. Method: connect() - simulates connecting to database
4. Method: disconnect() - simulates disconnecting
5. Method: execute_query(query) - simulates query execution
6. Ensure that multiple calls to create the instance return the same object

PART B - Factory Pattern:
--------------------------
Create a notification system using Factory pattern:

1. Create a base class 'Notification' with:
   - Attributes: recipient, message
   - Abstract method: send()

2. Create concrete classes:
   - EmailNotification: send() simulates sending email
   - SMSNotification: send() simulates sending SMS
   - PushNotification: send() simulates sending push notification

3. Create a 'NotificationFactory' class with:
   - Static method: create_notification(notification_type, recipient, message)
   - Should return appropriate notification object based on type
   - Should raise ValueError for invalid notification types

4. Create a 'NotificationManager' that:
   - Uses the factory to create notifications
   - Keeps track of all sent notifications
   - Method: send_notification(type, recipient, message)
   - Method: get_notification_history() - returns all sent notifications

TEST YOUR CODE:
---------------
SINGLETON:
- Try to create multiple DatabaseConnection instances
- Verify they're the same object (use 'is' operator)
- Test connection methods

FACTORY:
- Create different types of notifications using the factory
- Send multiple notifications
- Check notification history
- Try to create an invalid notification type
"""

# Write your solution below:

