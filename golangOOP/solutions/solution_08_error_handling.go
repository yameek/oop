package main

import (
	"errors"
	"fmt"
	"strings"
)

// Solution for Task 8: CUSTOM ERRORS AND ERROR HANDLING
// =====================================================

// Sentinel errors
var (
	ErrUserExists     = errors.New("user already exists")
	ErrInvalidEmail   = errors.New("invalid email address")
	ErrWeakPassword   = errors.New("password too weak")
	ErrUserNotFound   = errors.New("user not found")
)

// ValidationError is a custom error type
type ValidationError struct {
	Field   string
	Message string
}

func (e *ValidationError) Error() string {
	return fmt.Sprintf("validation error on %s: %s", e.Field, e.Message)
}

// MultiError holds multiple errors
type MultiError struct {
	Errors []error
}

func (m *MultiError) Error() string {
	if len(m.Errors) == 0 {
		return "no errors"
	}
	var msgs []string
	for _, e := range m.Errors {
		msgs = append(msgs, e.Error())
	}
	return strings.Join(msgs, "; ")
}

func (m *MultiError) Add(err error) {
	if err != nil {
		m.Errors = append(m.Errors, err)
	}
}

func (m *MultiError) HasErrors() bool {
	return len(m.Errors) > 0
}

// User struct
type User struct {
	ID       int
	Username string
	Email    string
	Password string
}

// UserRegistry manages users
type UserRegistry struct {
	users  map[int]*User
	nextID int
}

func NewUserRegistry() *UserRegistry {
	return &UserRegistry{users: make(map[int]*User), nextID: 1}
}

func (r *UserRegistry) Register(username, email, password string) (*User, error) {
	// Validate username
	if len(username) < 3 {
		return nil, &ValidationError{Field: "username", Message: "must be at least 3 characters"}
	}

	// Validate email
	if !strings.Contains(email, "@") {
		return nil, ErrInvalidEmail
	}

	// Validate password
	if len(password) < 8 {
		return nil, ErrWeakPassword
	}

	// Check for existing user
	for _, u := range r.users {
		if u.Username == username || u.Email == email {
			return nil, ErrUserExists
		}
	}

	// Create user
	user := &User{
		ID:       r.nextID,
		Username: username,
		Email:    email,
		Password: password,
	}
	r.users[r.nextID] = user
	r.nextID++

	return user, nil
}

func (r *UserRegistry) GetUser(id int) (*User, error) {
	user, ok := r.users[id]
	if !ok {
		return nil, ErrUserNotFound
	}
	return user, nil
}

func (r *UserRegistry) UpdateEmail(id int, newEmail string) error {
	user, err := r.GetUser(id)
	if err != nil {
		return err
	}
	if !strings.Contains(newEmail, "@") {
		return ErrInvalidEmail
	}
	user.Email = newEmail
	return nil
}

// ValidateUser returns all validation errors at once
func ValidateUser(user *User) error {
	multi := &MultiError{}

	if len(user.Username) < 3 {
		multi.Add(&ValidationError{Field: "username", Message: "too short"})
	}
	if !strings.Contains(user.Email, "@") {
		multi.Add(ErrInvalidEmail)
	}
	if len(user.Password) < 8 {
		multi.Add(ErrWeakPassword)
	}

	if multi.HasErrors() {
		return multi
	}
	return nil
}

// HandleRegistration demonstrates error handling patterns
func HandleRegistration(registry *UserRegistry, username, email, password string) {
	user, err := registry.Register(username, email, password)
	if err != nil {
		// Check for sentinel errors
		if errors.Is(err, ErrUserExists) {
			fmt.Println("  ✗ User already exists. Try a different username.")
			return
		}
		if errors.Is(err, ErrInvalidEmail) {
			fmt.Println("  ✗ Invalid email. Please include @.")
			return
		}
		if errors.Is(err, ErrWeakPassword) {
			fmt.Println("  ✗ Password too weak. Use at least 8 characters.")
			return
		}

		// Check for ValidationError type
		var valErr *ValidationError
		if errors.As(err, &valErr) {
			fmt.Printf("  ✗ Validation failed on '%s': %s\n", valErr.Field, valErr.Message)
			return
		}

		fmt.Println("  ✗ Unknown error:", err)
		return
	}

	fmt.Printf("  ✓ User '%s' registered with ID %d\n", user.Username, user.ID)
}

func main() {
	fmt.Println("SOLUTION 8: CUSTOM ERRORS AND ERROR HANDLING")
	fmt.Println("=============================================")

	registry := NewUserRegistry()

	// Test successful registration
	fmt.Println("\n1. Successful Registration:")
	HandleRegistration(registry, "alice", "alice@example.com", "securepass123")

	// Test duplicate user
	fmt.Println("\n2. Duplicate User:")
	HandleRegistration(registry, "alice", "alice2@example.com", "anotherpass123")

	// Test invalid email
	fmt.Println("\n3. Invalid Email:")
	HandleRegistration(registry, "bob", "invalidemail", "securepass123")

	// Test weak password
	fmt.Println("\n4. Weak Password:")
	HandleRegistration(registry, "charlie", "charlie@example.com", "short")

	// Test validation error (short username)
	fmt.Println("\n5. Short Username:")
	HandleRegistration(registry, "ab", "ab@example.com", "securepass123")

	// Test MultiError
	fmt.Println("\n6. MultiError Validation:")
	badUser := &User{ID: 99, Username: "x", Email: "bad", Password: "123"}
	if err := ValidateUser(badUser); err != nil {
		fmt.Printf("  Multiple errors: %v\n", err)
	}

	// Test GetUser
	fmt.Println("\n7. GetUser:")
	if user, err := registry.GetUser(1); err == nil {
		fmt.Printf("  Found user: %s (%s)\n", user.Username, user.Email)
	}
	if _, err := registry.GetUser(999); err != nil {
		fmt.Printf("  Error: %v\n", err)
	}
}
