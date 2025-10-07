"""
TASK 3: Inheritance
===================
Difficulty: Intermediate

Learn about: Inheritance, super(), Method Overriding

PROBLEM:
--------
Create a vehicle management system using inheritance.

Requirements:
1. Create a base class 'Vehicle' with:
   - Attributes: brand, model, year, price
   - Method: display_info() - shows all vehicle details
   - Method: calculate_age() - returns how old the vehicle is (2024 - year)

2. Create a class 'Car' that inherits from Vehicle with:
   - Additional attribute: num_doors (number of doors)
   - Additional attribute: fuel_type (petrol/diesel/electric)
   - Override display_info() to include car-specific details
   - Method: is_fuel_efficient() - returns True if fuel_type is "electric"

3. Create a class 'Motorcycle' that inherits from Vehicle with:
   - Additional attribute: has_sidecar (boolean)
   - Additional attribute: engine_cc (engine capacity in cc)
   - Override display_info() to include motorcycle-specific details
   - Method: is_high_performance() - returns True if engine_cc > 600

4. Create a class 'Truck' that inherits from Vehicle with:
   - Additional attribute: cargo_capacity (in tons)
   - Additional attribute: num_axles
   - Override display_info() to include truck-specific details
   - Method: is_heavy_duty() - returns True if cargo_capacity > 10 tons

TEST YOUR CODE:
---------------
Create at least one object of each type (Car, Motorcycle, Truck)
Test all methods including inherited and overridden ones
"""

# Write your solution below:

