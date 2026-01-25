package main

import "fmt"

// Solution for Task 7: CUSTOM TYPES AND METHODS
// =============================================

// Custom types for temperature
type Celsius float64
type Fahrenheit float64
type Kelvin float64

// Celsius methods
func (c Celsius) ToCelsius() Celsius {
	return c
}

func (c Celsius) ToFahrenheit() Fahrenheit {
	return Fahrenheit(c*9/5 + 32)
}

func (c Celsius) ToKelvin() Kelvin {
	return Kelvin(c + 273.15)
}

func (c Celsius) String() string {
	return fmt.Sprintf("%.2f°C", c)
}

// Fahrenheit methods
func (f Fahrenheit) ToCelsius() Celsius {
	return Celsius((f - 32) * 5 / 9)
}

func (f Fahrenheit) ToFahrenheit() Fahrenheit {
	return f
}

func (f Fahrenheit) ToKelvin() Kelvin {
	return f.ToCelsius().ToKelvin()
}

func (f Fahrenheit) String() string {
	return fmt.Sprintf("%.2f°F", f)
}

// Kelvin methods
func (k Kelvin) ToCelsius() Celsius {
	return Celsius(k - 273.15)
}

func (k Kelvin) ToFahrenheit() Fahrenheit {
	return k.ToCelsius().ToFahrenheit()
}

func (k Kelvin) ToKelvin() Kelvin {
	return k
}

func (k Kelvin) String() string {
	return fmt.Sprintf("%.2fK", k)
}

// Temperature interface
type Temperature interface {
	ToCelsius() Celsius
	ToFahrenheit() Fahrenheit
	ToKelvin() Kelvin
}

// Temperatures slice type
type Temperatures []float64

func (t Temperatures) Average() float64 {
	if len(t) == 0 {
		return 0
	}
	var sum float64
	for _, v := range t {
		sum += v
	}
	return sum / float64(len(t))
}

func (t Temperatures) Max() float64 {
	if len(t) == 0 {
		return 0
	}
	max := t[0]
	for _, v := range t[1:] {
		if v > max {
			max = v
		}
	}
	return max
}

func (t Temperatures) Min() float64 {
	if len(t) == 0 {
		return 0
	}
	min := t[0]
	for _, v := range t[1:] {
		if v < min {
			min = v
		}
	}
	return min
}

func (t *Temperatures) Add(temp float64) {
	*t = append(*t, temp)
}

func (t Temperatures) Len() int {
	return len(t)
}

// CompareTemperatures compares two temperatures
func CompareTemperatures(t1, t2 Temperature) {
	c1 := t1.ToCelsius()
	c2 := t2.ToCelsius()

	if c1 > c2 {
		fmt.Printf("  %v is hotter than %v\n", t1, t2)
	} else if c1 < c2 {
		fmt.Printf("  %v is cooler than %v\n", t1, t2)
	} else {
		fmt.Printf("  %v and %v are the same temperature\n", t1, t2)
	}
}

func main() {
	fmt.Println("SOLUTION 7: CUSTOM TYPES AND METHODS")
	fmt.Println("====================================")

	// Test temperature conversions
	fmt.Println("\n1. Temperature Conversions:")
	c := Celsius(100)
	fmt.Printf("  %v = %v = %v\n", c, c.ToFahrenheit(), c.ToKelvin())

	f := Fahrenheit(32)
	fmt.Printf("  %v = %v = %v\n", f, f.ToCelsius(), f.ToKelvin())

	k := Kelvin(0)
	fmt.Printf("  %v = %v = %v\n", k, k.ToCelsius(), k.ToFahrenheit())

	// Test String() method (fmt.Stringer interface)
	fmt.Println("\n2. String() Method (fmt.Stringer):")
	fmt.Printf("  Boiling point: %v\n", Celsius(100))
	fmt.Printf("  Freezing point: %v\n", Fahrenheit(32))

	// Test Temperatures slice
	fmt.Println("\n3. Temperatures Slice Methods:")
	temps := Temperatures{20.5, 22.3, 18.7, 25.1, 19.8}
	fmt.Printf("  Temperatures: %v\n", temps)
	fmt.Printf("  Average: %.2f\n", temps.Average())
	fmt.Printf("  Max: %.2f\n", temps.Max())
	fmt.Printf("  Min: %.2f\n", temps.Min())
	fmt.Printf("  Length: %d\n", temps.Len())

	temps.Add(30.0)
	fmt.Printf("  After adding 30.0: %v\n", temps)

	// Test Temperature interface
	fmt.Println("\n4. Temperature Interface Comparison:")
	CompareTemperatures(Celsius(25), Fahrenheit(80))
	CompareTemperatures(Kelvin(300), Celsius(26.85))
}
