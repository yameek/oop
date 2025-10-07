/*
TASK 5: Interface Composition
==============================
Difficulty: Intermediate to Advanced

Learn about: Interface Embedding, Multiple Interface Implementation

PROBLEM:
--------
Create a media player system with composed interfaces.

Requirements:
1. Create interface 'Reader' with:
   - Read() string

2. Create interface 'Writer' with:
   - Write(data string) error

3. Create interface 'Closer' with:
   - Close() error

4. Create interface 'ReadWriter' that embeds Reader and Writer

5. Create interface 'ReadWriteCloser' that embeds Reader, Writer, and Closer

6. Create struct 'File' with:
   - name string
   - content string
   - isOpen bool

7. Implement all required methods for File to satisfy ReadWriteCloser:
   - Read() returns content
   - Write(data) appends to content
   - Close() sets isOpen to false

8. Create struct 'NetworkConnection' with:
   - address string
   - buffer string
   - connected bool

9. Implement ReadWriteCloser for NetworkConnection

10. Create a function 'ProcessReadWriter(rw ReadWriter)' that:
    - Demonstrates working with the composed interface
    - Writes data and reads it back

11. Create a function 'ProcessReadWriteCloser(rwc ReadWriteCloser)' that:
    - Writes data, reads it, then closes
    - Handles errors properly

TEST YOUR CODE:
---------------
- Create File and NetworkConnection objects
- Pass them to ProcessReadWriter
- Pass them to ProcessReadWriteCloser
- Demonstrate interface satisfaction

NOTES:
------
- Interfaces can embed other interfaces
- Types must implement all embedded interface methods
- This is composition at the interface level
- Very powerful for creating flexible APIs
*/

package main

import (
	"errors"
	"fmt"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
