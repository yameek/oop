package main

import (
	"errors"
	"fmt"
)

// Solution for Task 5: INTERFACE COMPOSITION
// ==========================================

// Basic interfaces
type Reader interface {
	Read() string
}

type Writer interface {
	Write(data string) error
}

type Closer interface {
	Close() error
}

// Composed interfaces (embedding)
type ReadWriter interface {
	Reader
	Writer
}

type ReadWriteCloser interface {
	Reader
	Writer
	Closer
}

// File implements ReadWriteCloser
type File struct {
	name    string
	content string
	isOpen  bool
}

func NewFile(name string) *File {
	return &File{name: name, isOpen: true}
}

func (f *File) Read() string {
	if !f.isOpen {
		return ""
	}
	return f.content
}

func (f *File) Write(data string) error {
	if !f.isOpen {
		return errors.New("file is closed")
	}
	f.content += data
	return nil
}

func (f *File) Close() error {
	if !f.isOpen {
		return errors.New("file already closed")
	}
	f.isOpen = false
	fmt.Printf("  [File] Closed: %s\n", f.name)
	return nil
}

// NetworkConnection implements ReadWriteCloser
type NetworkConnection struct {
	address   string
	buffer    string
	connected bool
}

func NewNetworkConnection(address string) *NetworkConnection {
	return &NetworkConnection{address: address, connected: true}
}

func (n *NetworkConnection) Read() string {
	if !n.connected {
		return ""
	}
	return n.buffer
}

func (n *NetworkConnection) Write(data string) error {
	if !n.connected {
		return errors.New("connection closed")
	}
	n.buffer += data
	return nil
}

func (n *NetworkConnection) Close() error {
	if !n.connected {
		return errors.New("already disconnected")
	}
	n.connected = false
	fmt.Printf("  [Network] Disconnected from: %s\n", n.address)
	return nil
}

// ProcessReadWriter demonstrates working with composed interface
func ProcessReadWriter(rw ReadWriter) {
	fmt.Println("  Writing data...")
	rw.Write("Hello, World!")
	fmt.Printf("  Reading back: %q\n", rw.Read())
}

// ProcessReadWriteCloser writes, reads, then closes
func ProcessReadWriteCloser(rwc ReadWriteCloser) {
	fmt.Println("  Writing data...")
	rwc.Write("Important data")
	fmt.Printf("  Reading: %q\n", rwc.Read())
	rwc.Close()
}

func main() {
	fmt.Println("SOLUTION 5: INTERFACE COMPOSITION")
	fmt.Println("==================================")

	// Test with File
	fmt.Println("\n1. Testing File with ReadWriter:")
	file := NewFile("document.txt")
	ProcessReadWriter(file)

	fmt.Println("\n2. Testing File with ReadWriteCloser:")
	file2 := NewFile("data.txt")
	ProcessReadWriteCloser(file2)

	// Test with NetworkConnection
	fmt.Println("\n3. Testing NetworkConnection with ReadWriter:")
	conn := NewNetworkConnection("api.example.com:443")
	ProcessReadWriter(conn)

	fmt.Println("\n4. Testing NetworkConnection with ReadWriteCloser:")
	conn2 := NewNetworkConnection("db.example.com:5432")
	ProcessReadWriteCloser(conn2)

	// Demonstrate interface satisfaction
	fmt.Println("\n5. Interface Satisfaction Demo:")
	var rwc ReadWriteCloser = NewFile("test.txt")
	var rw ReadWriter = rwc   // ReadWriteCloser satisfies ReadWriter
	var r Reader = rw         // ReadWriter satisfies Reader
	fmt.Printf("All interfaces satisfied. Reader type: %T\n", r)
}
