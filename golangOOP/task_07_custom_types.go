/*
TASK 7: Custom Types and Methods
=================================
Difficulty: Intermediate to Advanced

Learn about: Type Definitions, Methods on Custom Types, Type Aliasing

PROBLEM:
--------
Create a temperature conversion system with custom types.

Requirements:
1. Create custom type 'Celsius' based on float64

2. Create custom type 'Fahrenheit' based on float64

3. Create custom type 'Kelvin' based on float64

4. Add methods to Celsius:
   - ToFahrenheit() Fahrenheit
   - ToKelvin() Kelvin
   - String() string  // Implement fmt.Stringer interface

5. Add methods to Fahrenheit:
   - ToCelsius() Celsius
   - ToKelvin() Kelvin
   - String() string

6. Add methods to Kelvin:
   - ToCelsius() Celsius
   - ToFahrenheit() Fahrenheit
   - String() string

7. Create a custom type 'Temperatures' as []float64

8. Add methods to Temperatures:
   - Average() float64
   - Max() float64
   - Min() float64
   - Add(temp float64)
   - Len() int  // Implement a method similar to built-in len

9. Create interface 'Temperature' with:
   - ToCelsius() Celsius
   - ToFahrenheit() Fahrenheit
   - ToKelvin() Kelvin

10. Create function 'CompareTemperatures(t1, t2 Temperature)' that:
    - Converts both to same unit
    - Compares them
    - Prints which is hotter

TEST YOUR CODE:
---------------
- Create temperatures in different units
- Convert between units
- Test String() methods with fmt.Println
- Use Temperatures slice methods
- Test the Temperature interface

NOTES:
------
- Custom types can have methods
- Type definition: type Name UnderlyingType
- Implementing String() makes it work with fmt package
- Methods can be added to any named type
*/

package main

import (
	"fmt"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
