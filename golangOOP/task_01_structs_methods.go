/*
TASK 1: Structs and Methods
============================
Difficulty: Beginner

Learn about: Structs, Methods, Receiver Functions, Constructors

PROBLEM:
--------
Create a Book management system using Go structs and methods.

Requirements:
1. Create a struct called 'Book' with the following fields:
   - Title (string)
   - Author (string)
   - Pages (int)
   - IsAvailable (bool)

2. Create a constructor function 'NewBook(title, author string, pages int) *Book'
   that returns a pointer to a new Book with IsAvailable set to true

3. Create a method 'DisplayInfo()' that prints all book information

4. Create a method 'Borrow()' that:
   - Sets IsAvailable to false if the book is available
   - Prints "Book borrowed successfully"
   - If not available, prints "Book is already borrowed"

5. Create a method 'Return()' that:
   - Sets IsAvailable to true
   - Prints "Book returned successfully"

TEST YOUR CODE:
---------------
Create at least 2 book objects and test all methods.

NOTES:
------
- In Go, we use structs instead of classes
- Methods are functions with receiver parameters
- Use pointer receivers (*Book) to modify the struct
- Use value receivers (Book) when you don't need to modify
*/

package main

import "fmt"

// Write your solution below:

func main() {
	// Test your code here
	
}
