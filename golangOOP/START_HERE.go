/*
╔═══════════════════════════════════════════════════════════════════════════════╗
║                                                                               ║
║                   GO OOP LEARNING TASKS - GETTING STARTED                     ║
║                                                                               ║
╚═══════════════════════════════════════════════════════════════════════════════╝
*/

package main

import (
	"fmt"
	"strings"
)

func main() {
	printWelcome()
}

func printWelcome() {
	border := strings.Repeat("=", 79)
	
	fmt.Println(border)
	fmt.Println("                  GO OOP LEARNING TASKS - GETTING STARTED")
	fmt.Println(border)
	
	fmt.Println("\nWelcome! You now have everything you need to master Object-Oriented Programming")
	fmt.Println("in Go. Here's how to get started:")
	
	fmt.Println("\n📁 WHAT YOU HAVE")
	fmt.Println(border)
	
	fmt.Println("✅ 10 Task Files (task_01 through task_10)")
	fmt.Println("   - Each contains a detailed problem to solve")
	fmt.Println("   - Progressively increasing difficulty")
	fmt.Println("   - Real-world scenarios to learn from")
	
	fmt.Println("\n✅ 10 Solution Files (in solutions/ folder)")
	fmt.Println("   - Fully explained solutions")
	fmt.Println("   - Detailed comments and documentation")
	fmt.Println("   - Runnable examples with test cases")
	
	fmt.Println("\n✅ README.md")
	fmt.Println("   - Complete guide to using this repository")
	fmt.Println("   - Learning path and recommended approach")
	fmt.Println("   - Go-specific OOP concepts")
	
	fmt.Println("\n✅ QUICK_REFERENCE.md")
	fmt.Println("   - Handy Go syntax reference")
	fmt.Println("   - Common patterns and idioms")
	fmt.Println("   - Quick lookup for concepts")
	
	fmt.Println("\n🚀 QUICK START GUIDE")
	fmt.Println(border)
	
	steps := []string{
		"Read the README:           cat README.md",
		"Check Quick Reference:     cat QUICK_REFERENCE.md",
		"Start with Task 1:         cat task_01_structs_methods.go",
		"Write your solution in the task file",
		"Test your solution:        go run task_01_structs_methods.go",
		"If stuck, check solution:  go run solutions/solution_01_structs_methods.go",
		"Move to Task 2 and repeat!",
	}
	
	for i, step := range steps {
		fmt.Printf("Step %d: %s\n", i+1, step)
	}
	
	fmt.Println("\n📚 LEARNING PATH")
	fmt.Println(border)
	
	printSection("BEGINNER (Start Here!)", []string{
		"Task 1: Structs and Methods              [⭐ Easy]",
		"Task 2: Interfaces                       [⭐⭐ Easy-Medium]",
	})
	
	printSection("INTERMEDIATE", []string{
		"Task 3: Struct Embedding                 [⭐⭐ Medium]",
		"Task 4: Encapsulation                    [⭐⭐⭐ Medium]",
		"Task 5: Interface Composition            [⭐⭐⭐ Medium-Hard]",
	})
	
	printSection("ADVANCED", []string{
		"Task 6: Type Assertions                  [⭐⭐⭐⭐ Hard]",
		"Task 7: Custom Types                     [⭐⭐⭐⭐ Hard]",
		"Task 8: Error Handling                   [⭐⭐⭐⭐ Hard]",
		"Task 9: Generics                         [⭐⭐⭐⭐⭐ Very Hard]",
		"Task 10: Design Patterns                 [⭐⭐⭐⭐⭐ Very Hard]",
	})
	
	fmt.Println("\n💡 TIPS FOR SUCCESS")
	fmt.Println(border)
	
	tips := []string{
		"DON'T RUSH - Take your time, understanding > speed",
		"TRY FIRST - Attempt before checking solutions",
		"TYPE, DON'T COPY - Build muscle memory",
		"USE GO FMT - Format your code: go fmt filename.go",
		"READ ERRORS - Go errors are helpful and clear",
		"THINK IN GO - Embrace composition, not inheritance",
	}
	
	for i, tip := range tips {
		fmt.Printf("%d. %s\n", i+1, tip)
	}
	
	fmt.Println("\n📖 WHAT YOU'LL LEARN")
	fmt.Println(border)
	
	topics := []string{
		"Structs and Methods",
		"Interfaces and Polymorphism",
		"Composition via Embedding",
		"Encapsulation (Exported/Unexported)",
		"Interface Composition",
		"Type Assertions and Type Switches",
		"Custom Types and Methods",
		"Custom Error Types",
		"Generics (Go 1.18+)",
		"Design Patterns in Go",
	}
	
	for _, topic := range topics {
		fmt.Printf("✓ %s\n", topic)
	}
	
	fmt.Println("\n🎯 KEY DIFFERENCES FROM OTHER OOP LANGUAGES")
	fmt.Println(border)
	
	differences := []struct{ from, to string }{
		{"Classes", "Structs"},
		{"Inheritance", "Composition (Embedding)"},
		{"'implements' keyword", "Implicit interface implementation"},
		{"Constructors", "NewTypeName() functions"},
		{"public/private keywords", "Capitalization (Name vs name)"},
		{"Try-Catch exceptions", "Explicit error values"},
	}
	
	for _, diff := range differences {
		fmt.Printf("   %s → %s\n", diff.from, diff.to)
	}
	
	fmt.Println("\n⚡ RUNNING THE CODE")
	fmt.Println(border)
	
	fmt.Println("Run your solution:")
	fmt.Println("   $ go run task_01_structs_methods.go")
	
	fmt.Println("\nRun a solution to see expected output:")
	fmt.Println("   $ go run solutions/solution_01_structs_methods.go")
	
	fmt.Println("\nFormat your code (Go standard):")
	fmt.Println("   $ go fmt task_01_structs_methods.go")
	
	fmt.Println("\nCheck for issues:")
	fmt.Println("   $ go vet task_01_structs_methods.go")
	
	fmt.Println("\n🌟 GO PROVERBS TO REMEMBER")
	fmt.Println(border)
	
	proverbs := []string{
		"Don't communicate by sharing memory, share memory by communicating.",
		"Concurrency is not parallelism.",
		"The bigger the interface, the weaker the abstraction.",
		"Make the zero value useful.",
		"interface{} says nothing.",
		"Gofmt's style is no one's favorite, yet gofmt is everyone's favorite.",
		"A little copying is better than a little dependency.",
		"Clear is better than clever.",
		"Errors are values.",
		"Don't just check errors, handle them gracefully.",
	}
	
	for i, proverb := range proverbs {
		fmt.Printf("%2d. %s\n", i+1, proverb)
	}
	
	fmt.Println("\n" + border)
	fmt.Println("Ready to start? Open task_01_structs_methods.go and begin your journey!")
	fmt.Println()
	fmt.Println("                            HAPPY CODING! 🚀")
	fmt.Println(border)
}

func printSection(title string, items []string) {
	fmt.Printf("\n%s\n", title)
	for _, item := range items {
		fmt.Printf("├── %s\n", item)
	}
}
