"""
SOLUTION 3: Inheritance
=======================

CONCEPTS EXPLAINED:
-------------------
1. Inheritance: Creating a new class based on an existing class
2. Base/Parent class: The class being inherited from
3. Derived/Child class: The class that inherits
4. super(): Calls methods from the parent class
5. Method Overriding: Replacing parent's method in child class
6. Code reusability: DRY (Don't Repeat Yourself) principle

"""

from datetime import datetime

class Vehicle:
    """Base class for all vehicles"""
    
    def __init__(self, brand, model, year, price):
        """
        Initialize a Vehicle object
        
        Parameters:
        -----------
        brand : str
            Vehicle brand/manufacturer
        model : str
            Vehicle model name
        year : int
            Year of manufacture
        price : float
            Vehicle price
        """
        self.brand = brand
        self.model = model
        self.year = year
        self.price = price
    
    def display_info(self):
        """Display basic vehicle information"""
        print(f"\n{'=' * 50}")
        print(f"Brand: {self.brand}")
        print(f"Model: {self.model}")
        print(f"Year: {self.year}")
        print(f"Price: ${self.price:,.2f}")
        print(f"Age: {self.calculate_age()} years")
        print(f"{'=' * 50}")
    
    def calculate_age(self):
        """
        Calculate vehicle age
        
        Returns:
        --------
        int : Age of the vehicle in years
        """
        current_year = datetime.now().year
        return current_year - self.year


class Car(Vehicle):
    """Car class inheriting from Vehicle"""
    
    def __init__(self, brand, model, year, price, num_doors, fuel_type):
        """
        Initialize a Car object
        
        NOTE: We use super().__init__() to call the parent's constructor
        This avoids code duplication
        """
        # Call parent class constructor
        super().__init__(brand, model, year, price)
        
        # Add car-specific attributes
        self.num_doors = num_doors
        self.fuel_type = fuel_type
    
    def display_info(self):
        """
        Override parent's display_info to show car-specific details
        
        NOTE: This is method overriding - we replace the parent's method
        but we can still call it using super()
        """
        # Call parent's display_info first
        super().display_info()
        
        # Add car-specific information
        print(f"Vehicle Type: Car")
        print(f"Number of Doors: {self.num_doors}")
        print(f"Fuel Type: {self.fuel_type}")
        print(f"Fuel Efficient: {'Yes' if self.is_fuel_efficient() else 'No'}")
        print(f"{'=' * 50}")
    
    def is_fuel_efficient(self):
        """
        Check if car is fuel efficient
        
        Returns:
        --------
        bool : True if electric, False otherwise
        """
        return self.fuel_type.lower() == "electric"


class Motorcycle(Vehicle):
    """Motorcycle class inheriting from Vehicle"""
    
    def __init__(self, brand, model, year, price, has_sidecar, engine_cc):
        """Initialize a Motorcycle object"""
        super().__init__(brand, model, year, price)
        self.has_sidecar = has_sidecar
        self.engine_cc = engine_cc
    
    def display_info(self):
        """Override to show motorcycle-specific details"""
        super().display_info()
        print(f"Vehicle Type: Motorcycle")
        print(f"Has Sidecar: {'Yes' if self.has_sidecar else 'No'}")
        print(f"Engine Capacity: {self.engine_cc}cc")
        print(f"High Performance: {'Yes' if self.is_high_performance() else 'No'}")
        print(f"{'=' * 50}")
    
    def is_high_performance(self):
        """
        Check if motorcycle is high performance
        
        Returns:
        --------
        bool : True if engine > 600cc, False otherwise
        """
        return self.engine_cc > 600


class Truck(Vehicle):
    """Truck class inheriting from Vehicle"""
    
    def __init__(self, brand, model, year, price, cargo_capacity, num_axles):
        """Initialize a Truck object"""
        super().__init__(brand, model, year, price)
        self.cargo_capacity = cargo_capacity
        self.num_axles = num_axles
    
    def display_info(self):
        """Override to show truck-specific details"""
        super().display_info()
        print(f"Vehicle Type: Truck")
        print(f"Cargo Capacity: {self.cargo_capacity} tons")
        print(f"Number of Axles: {self.num_axles}")
        print(f"Heavy Duty: {'Yes' if self.is_heavy_duty() else 'No'}")
        print(f"{'=' * 50}")
    
    def is_heavy_duty(self):
        """
        Check if truck is heavy duty
        
        Returns:
        --------
        bool : True if cargo capacity > 10 tons, False otherwise
        """
        return self.cargo_capacity > 10


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING INHERITANCE WITH VEHICLE HIERARCHY")
    print("=" * 60)
    
    # Create a Car object
    print("\n1. Creating a Car:")
    car1 = Car("Tesla", "Model 3", 2022, 45000, 4, "Electric")
    car1.display_info()
    
    print(f"\nCar-specific method: is_fuel_efficient() = {car1.is_fuel_efficient()}")
    print(f"Inherited method: calculate_age() = {car1.calculate_age()} years")
    
    # Create another Car with different fuel type
    print("\n2. Creating a Gasoline Car:")
    car2 = Car("Honda", "Civic", 2020, 25000, 4, "Petrol")
    car2.display_info()
    
    # Create a Motorcycle object
    print("\n3. Creating a Motorcycle:")
    bike1 = Motorcycle("Harley-Davidson", "Street 750", 2021, 8000, False, 750)
    bike1.display_info()
    
    # Create a high-performance motorcycle
    print("\n4. Creating a High-Performance Motorcycle:")
    bike2 = Motorcycle("Kawasaki", "Ninja ZX-10R", 2023, 16000, False, 998)
    bike2.display_info()
    
    # Create a Truck object
    print("\n5. Creating a Light Truck:")
    truck1 = Truck("Ford", "F-150", 2019, 35000, 3.5, 2)
    truck1.display_info()
    
    # Create a heavy-duty truck
    print("\n6. Creating a Heavy-Duty Truck:")
    truck2 = Truck("Volvo", "FH16", 2021, 120000, 25, 3)
    truck2.display_info()
    
    # Demonstrate polymorphism - all are vehicles
    print("\n7. Demonstrating Polymorphism (all are Vehicles):")
    vehicles = [car1, bike1, truck1, car2, bike2, truck2]
    
    print("\nAll vehicle ages:")
    for vehicle in vehicles:
        print(f"{vehicle.brand} {vehicle.model}: {vehicle.calculate_age()} years old")
    
    print("\nTotal value of all vehicles:")
    total_value = sum(vehicle.price for vehicle in vehicles)
    print(f"${total_value:,.2f}")
    
    # Test isinstance and type checking
    print("\n8. Type Checking:")
    print(f"car1 is a Car: {isinstance(car1, Car)}")
    print(f"car1 is a Vehicle: {isinstance(car1, Vehicle)}")
    print(f"bike1 is a Car: {isinstance(bike1, Car)}")
    print(f"bike1 is a Vehicle: {isinstance(bike1, Vehicle)}")
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Child classes inherit all attributes and methods from parent")
    print("2. Use super() to call parent class methods")
    print("3. Override methods to provide specialized behavior")
    print("4. Inheritance promotes code reuse (DRY principle)")
    print("5. Child class objects are also instances of parent class")
    print("6. Use inheritance for 'is-a' relationships (Car IS-A Vehicle)")
