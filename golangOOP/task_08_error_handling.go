/*
TASK 8: Custom Errors and Error Handling
=========================================
Difficulty: Advanced

Learn about: Error Interface, Custom Errors, Error Wrapping, Sentinel Errors

PROBLEM:
--------
Create a user registration system with custom errors.

Requirements:
1. Create custom error types:
   - ValidationError struct with Field and Message
   - Implement Error() string method
   
2. Create sentinel errors (package-level error variables):
   - ErrUserExists
   - ErrInvalidEmail
   - ErrWeakPassword
   - ErrUserNotFound

3. Create struct 'User' with:
   - ID int
   - Username string
   - Email string
   - Password string (in real app, would be hashed)

4. Create struct 'UserRegistry' with:
   - users map[int]*User
   - nextID int

5. Create method 'Register(username, email, password string) (*User, error)' that:
   - Validates username (min 3 chars)
   - Validates email (contains @)
   - Validates password (min 8 chars)
   - Checks if user already exists
   - Returns ValidationError for validation failures
   - Returns sentinel errors for specific cases
   - Creates and stores user if valid

6. Create method 'GetUser(id int) (*User, error)' that:
   - Returns user if exists
   - Returns ErrUserNotFound if not

7. Create method 'UpdateEmail(id int, newEmail string) error' that:
   - Validates email
   - Updates user email
   - Returns appropriate errors

8. Create function 'HandleRegistration(registry *UserRegistry, username, email, password string)' that:
   - Attempts to register user
   - Uses errors.Is() to check for sentinel errors
   - Uses type assertion to check for ValidationError
   - Handles each error type appropriately
   - Prints user-friendly messages

9. Create custom error type 'MultiError' that:
   - Holds multiple errors
   - Implements Error() to show all errors

10. Create method 'ValidateUser(user *User) error' that:
    - Validates all fields
    - Returns MultiError with all validation errors
    - Returns nil if all valid

TEST YOUR CODE:
---------------
- Test successful registration
- Test various validation failures
- Test duplicate user registration
- Test error checking with errors.Is()
- Test type assertions on errors
- Test MultiError with multiple validation failures

NOTES:
------
- error is an interface: type error interface { Error() string }
- Sentinel errors are package-level error variables
- Use errors.Is() for sentinel error comparison
- Use errors.As() for type assertion on errors
- Custom errors provide more context
*/

package main

import (
	"errors"
	"fmt"
	"strings"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
