/*
TASK 9: Generics (Go 1.18+)
============================
Difficulty: Advanced

Learn about: Type Parameters, Generic Functions, Generic Types, Constraints

PROBLEM:
--------
Create a generic data structure library.

Requirements:
1. Create a generic Stack[T any] with:
   - items []T
   - Methods:
     - Push(item T)
     - Pop() (T, error)
     - Peek() (T, error)
     - IsEmpty() bool
     - Size() int

2. Create a generic Queue[T any] with:
   - items []T
   - Methods:
     - Enqueue(item T)
     - Dequeue() (T, error)
     - Front() (T, error)
     - IsEmpty() bool
     - Size() int

3. Create a generic function 'Map[T, U any](slice []T, fn func(T) U) []U' that:
   - Transforms each element using fn
   - Returns new slice of transformed elements

4. Create a generic function 'Filter[T any](slice []T, predicate func(T) bool) []T' that:
   - Keeps only elements where predicate returns true
   - Returns filtered slice

5. Create a generic function 'Reduce[T, U any](slice []T, initial U, fn func(U, T) U) U' that:
   - Reduces slice to single value
   - Uses accumulator pattern

6. Create a constraint interface 'Numeric' that allows:
   - int, int8, int16, int32, int64
   - float32, float64

7. Create a generic function 'Sum[T Numeric](numbers []T) T' that:
   - Uses Numeric constraint
   - Returns sum of all numbers

8. Create a generic function 'Max[T Numeric](numbers []T) (T, error)' that:
   - Returns maximum value
   - Returns error if slice is empty

9. Create a generic struct 'Pair[T, U any]' with:
   - First T
   - Second U
   - Methods:
     - Swap() Pair[U, T]
     - String() string

10. Create a generic function 'Contains[T comparable](slice []T, target T) bool' that:
    - Uses comparable constraint
    - Returns true if target is in slice

TEST YOUR CODE:
---------------
- Test Stack with different types (int, string, custom structs)
- Test Queue with different types
- Test Map, Filter, Reduce with various transformations
- Test Sum and Max with int and float64
- Test Pair with different type combinations
- Test Contains with comparable types

NOTES:
------
- Generics added in Go 1.18
- [T any] means T can be any type
- [T comparable] means T can be compared with == and !=
- Can create custom constraints with interfaces
- Type parameters are in square brackets
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
