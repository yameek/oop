package main

import (
	"errors"
	"fmt"
)

// Solution for Task 9: GENERICS
// =============================

// 1. Stack[T any]
type Stack[T any] struct {
	items []T
}

func (s *Stack[T]) Push(item T) {
	s.items = append(s.items, item)
}

func (s *Stack[T]) Pop() (T, error) {
	var zero T
	if len(s.items) == 0 {
		return zero, errors.New("stack is empty")
	}
	lastIndex := len(s.items) - 1
	item := s.items[lastIndex]
	s.items = s.items[:lastIndex]
	return item, nil
}

func (s *Stack[T]) Peek() (T, error) {
	var zero T
	if len(s.items) == 0 {
		return zero, errors.New("stack is empty")
	}
	return s.items[len(s.items)-1], nil
}

func (s *Stack[T]) IsEmpty() bool {
	return len(s.items) == 0
}

func (s *Stack[T]) Size() int {
	return len(s.items)
}

// 2. Queue[T any]
type Queue[T any] struct {
	items []T
}

func (q *Queue[T]) Enqueue(item T) {
	q.items = append(q.items, item)
}

func (q *Queue[T]) Dequeue() (T, error) {
	var zero T
	if len(q.items) == 0 {
		return zero, errors.New("queue is empty")
	}
	item := q.items[0]
	q.items = q.items[1:]
	return item, nil
}

func (q *Queue[T]) IsEmpty() bool {
	return len(q.items) == 0
}

// 3. Map[T, U any]
func Map[T, U any](slice []T, fn func(T) U) []U {
	result := make([]U, len(slice))
	for i, v := range slice {
		result[i] = fn(v)
	}
	return result
}

// 4. Filter[T any]
func Filter[T any](slice []T, predicate func(T) bool) []T {
	result := make([]T, 0)
	for _, v := range slice {
		if predicate(v) {
			result = append(result, v)
		}
	}
	return result
}

// 5. Reduce[T, U any]
func Reduce[T, U any](slice []T, initial U, fn func(U, T) U) U {
	result := initial
	for _, v := range slice {
		result = fn(result, v)
	}
	return result
}

// 6. Constraint Interface 'Numeric'
type Numeric interface {
	int | int8 | int16 | int32 | int64 | float32 | float64
}

// 7. Sum[T Numeric]
func Sum[T Numeric](numbers []T) T {
	var sum T
	for _, n := range numbers {
		sum += n
	}
	return sum
}

// 8. Max[T Numeric]
func Max[T Numeric](numbers []T) (T, error) {
	var zero T
	if len(numbers) == 0 {
		return zero, errors.New("empty slice")
	}
	maxVal := numbers[0]
	for _, n := range numbers {
		if n > maxVal {
			maxVal = n
		}
	}
	return maxVal, nil
}

// 9. Pair[T, U any]
type Pair[T, U any] struct {
	First  T
	Second U
}

func (p Pair[T, U]) Swap() Pair[U, T] {
	return Pair[U, T]{First: p.Second, Second: p.First}
}

// 10. Contains[T comparable]
func Contains[T comparable](slice []T, target T) bool {
	for _, v := range slice {
		if v == target {
			return true
		}
	}
	return false
}

func main() {
	fmt.Println("SOLUTION 9: GENERICS")
	fmt.Println("====================")

	// Test 1: Stack
	fmt.Println("\n1. Testing Stack[int]:")
	intStack := Stack[int]{}
	intStack.Push(10)
	intStack.Push(20)
	fmt.Printf("Pushed 10, 20. Peek: %v\n", try(intStack.Peek()))
	val, _ := intStack.Pop()
	fmt.Printf("Popped: %d\n", val)

	// Test 2: Queue
	fmt.Println("\n2. Testing Queue[string]:")
	strQueue := Queue[string]{}
	strQueue.Enqueue("First")
	strQueue.Enqueue("Second")
	val2, _ := strQueue.Dequeue()
	fmt.Printf("Dequeued: %s\n", val2)

	// Test 3: Map
	fmt.Println("\n3. Testing Map (int -> int*2):")
	nums := []int{1, 2, 3, 4, 5}
	doubled := Map(nums, func(n int) int { return n * 2 }) // Implicit typing
	fmt.Printf("Original: %v, Doubled: %v\n", nums, doubled)

	// Test 4: Filter
	fmt.Println("\n4. Testing Filter (even numbers):")
	evens := Filter(nums, func(n int) bool { return n%2 == 0 })
	fmt.Printf("Evens: %v\n", evens)

	// Test 5: Reduce
	fmt.Println("\n5. Testing Reduce (Sum of ints):")
	total := Reduce(nums, 0, func(acc, curr int) int { return acc + curr })
	fmt.Printf("Total: %d\n", total)

	// Test 6/7: Numeric Sum
	fmt.Println("\n6. Testing Numeric Sum (floats):")
	floats := []float64{1.1, 2.2, 3.3}
	floatSum := Sum(floats)
	fmt.Printf("Sum: %.2f\n", floatSum)

	// Test 8: Max
	fmt.Println("\n7. Testing Max:")
	maxInt, _ := Max(nums)
	fmt.Printf("Max of %v is %d\n", nums, maxInt)

	// Test 9: Pair
	fmt.Println("\n8. Testing Pair:")
	p := Pair[string, int]{"Age", 30}
	fmt.Printf("Original: %+v\n", p)
	swapped := p.Swap()
	fmt.Printf("Swapped: %+v\n", swapped)

	// Test 10: Contains
	fmt.Println("\n9. Testing Contains:")
	cities := []string{"London", "Paris", "Tokyo"}
	fmt.Printf("Contains 'Paris'? %v\n", Contains(cities, "Paris"))
	fmt.Printf("Contains 'Berlin'? %v\n", Contains(cities, "Berlin"))
}

// Helper to handle error returns in simple prints
func try[T any](val T, err error) T {
	if err != nil {
		fmt.Println("Error:", err)
		return val
	}
	return val
}