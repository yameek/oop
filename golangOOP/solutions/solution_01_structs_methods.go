package main

import (
"fmt"
"strings"
)

type Book struct {
Title       string
Author      string
Pages       int
IsAvailable bool
}

func NewBook(title, author string, pages int) *Book {
return &Book{
Title:       title,
Author:      author,
Pages:       pages,
IsAvailable: true,
}
}

func (b Book) DisplayInfo() {
status := "Available"
if !b.IsAvailable {
status = "Borrowed"
}
fmt.Println("\n--- Book Information ---")
fmt.Printf("Title: %s\n", b.Title)
fmt.Printf("Author: %s\n", b.Author)
fmt.Printf("Pages: %d\n", b.Pages)
fmt.Printf("Status: %s\n", status)
fmt.Println("------------------------")
}

func (b *Book) Borrow() {
if b.IsAvailable {
b.IsAvailable = false
fmt.Printf("✓ '%s' borrowed successfully!\n", b.Title)
} else {
fmt.Printf("✗ Sorry, '%s' is already borrowed.\n", b.Title)
}
}

func (b *Book) Return() {
b.IsAvailable = true
fmt.Printf("✓ '%s' returned successfully!\n", b.Title)
}

func main() {
fmt.Println(strings.Repeat("=", 60))
fmt.Println("TESTING BOOK STRUCT AND METHODS")
fmt.Println(strings.Repeat("=", 60))

book1 := NewBook("The Go Programming Language", "Donovan & Kernighan", 400)
book2 := NewBook("Learning Go", "Jon Bodner", 350)

fmt.Println("\n1. Displaying book information:")
book1.DisplayInfo()
book2.DisplayInfo()

fmt.Println("\n2. Testing borrow:")
book1.Borrow()
book1.Borrow()
book2.Borrow()

fmt.Println("\n3. Status after borrowing:")
book1.DisplayInfo()

fmt.Println("\n4. Testing return:")
book1.Return()
book1.DisplayInfo()

fmt.Println("\n" + strings.Repeat("=", 60))
fmt.Println("KEY TAKEAWAYS:")
fmt.Println(strings.Repeat("=", 60))
fmt.Println("1. Structs are Go's way of creating custom types")
fmt.Println("2. Methods have receivers: func (r ReceiverType) MethodName()")
fmt.Println("3. Pointer receivers (*Type) modify the original struct")
fmt.Println("4. Value receivers (Type) work with copies")
fmt.Println("5. Constructor convention: NewTypeName()")
}
