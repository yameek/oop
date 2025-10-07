/*
TASK 3: Struct Embedding (Composition)
=======================================
Difficulty: Intermediate

Learn about: Struct Embedding, Composition, Method Promotion

PROBLEM:
--------
Create a vehicle system using struct embedding.

Requirements:
1. Create a base struct 'Vehicle' with:
   - Brand string
   - Model string
   - Year int

2. Add a method 'GetAge() int' to Vehicle that returns current year - vehicle year

3. Create a struct 'Car' that embeds Vehicle and adds:
   - NumDoors int
   - FuelType string

4. Create a method 'IsElectric() bool' for Car that returns true if FuelType is "Electric"

5. Create a struct 'Motorcycle' that embeds Vehicle and adds:
   - HasSidecar bool
   - EngineCC int

6. Create a method 'IsHighPerformance() bool' for Motorcycle 
   that returns true if EngineCC > 600

7. Create an interface 'VehicleInfo' with:
   - GetDetails() string

8. Implement GetDetails() for both Car and Motorcycle to return formatted info

TEST YOUR CODE:
---------------
- Create cars and motorcycles
- Access embedded Vehicle fields directly
- Test embedded methods (GetAge)
- Test type-specific methods
- Use the VehicleInfo interface

NOTES:
------
- Embedding is Go's way of achieving composition
- Embedded struct's fields and methods are "promoted"
- Not inheritance - it's composition
- You can access embedded fields directly: car.Brand
*/

package main

import (
	"fmt"
	"time"
)

// Write your solution below:

func main() {
	// Test your code here
	
}
