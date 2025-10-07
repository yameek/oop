package main

import (
"fmt"
"time"
)

// Vehicle is the base struct
type Vehicle struct {
Brand string
Model string
Year  int
}

// GetAge returns the age of the vehicle
func (v Vehicle) GetAge() int {
return time.Now().Year() - v.Year
}

// Car embeds Vehicle and adds car-specific fields
type Car struct {
Vehicle  // Embedded - fields and methods are promoted
NumDoors int
FuelType string
}

// IsElectric returns true if the car is electric
func (c Car) IsElectric() bool {
return c.FuelType == "Electric"
}

// Motorcycle embeds Vehicle and adds motorcycle-specific fields
type Motorcycle struct {
Vehicle
HasSidecar bool
EngineCC   int
}

// IsHighPerformance returns true if engine is > 600cc
func (m Motorcycle) IsHighPerformance() bool {
return m.EngineCC > 600
}

// VehicleInfo interface
type VehicleInfo interface {
GetDetails() string
}

// GetDetails for Car
func (c Car) GetDetails() string {
return fmt.Sprintf("%s %s %d (%d doors, %s)", 
c.Brand, c.Model, c.Year, c.NumDoors, c.FuelType)
}

// GetDetails for Motorcycle  
func (m Motorcycle) GetDetails() string {
sidecar := "no sidecar"
if m.HasSidecar {
sidecar = "with sidecar"
}
return fmt.Sprintf("%s %s %d (%dcc, %s)", 
m.Brand, m.Model, m.Year, m.EngineCC, sidecar)
}

func main() {
fmt.Println("TESTING STRUCT EMBEDDING")
fmt.Println("=".repeat(60))

car := Car{
Vehicle:  Vehicle{Brand: "Tesla", Model: "Model 3", Year: 2022},
NumDoors: 4,
FuelType: "Electric",
}

bike := Motorcycle{
Vehicle:    Vehicle{Brand: "Harley", Model: "Street 750", Year: 2021},
HasSidecar: false,
EngineCC:   750,
}

// Access embedded fields directly
fmt.Printf("\nCar brand: %s\n", car.Brand)  // Promoted from Vehicle
fmt.Printf("Car age: %d years\n", car.GetAge())  // Promoted method
fmt.Printf("Is electric: %v\n", car.IsElectric())

fmt.Printf("\nBike details: %s\n", bike.GetDetails())
fmt.Printf("High performance: %v\n", bike.IsHighPerformance())

// Use interface
vehicles := []VehicleInfo{car, bike}
fmt.Println("\nAll vehicles:")
for _, v := range vehicles {
fmt.Println("  -", v.GetDetails())
}

fmt.Println("\nKEY TAKEAWAYS:")
fmt.Println("1. Embedding promotes fields and methods")
fmt.Println("2. Not inheritance - it's composition")
fmt.Println("3. Access embedded fields directly")
fmt.Println("4. Embedding supports has-a relationships")
}
