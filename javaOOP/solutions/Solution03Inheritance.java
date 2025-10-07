/**
 * SOLUTION 3: Inheritance and Method Overriding
 * =============================================
 * Demonstrates how subclasses extend behavior from a base class and override
 * methods to provide specialized functionality while still leveraging the
 * shared structure provided by the parent class.
 */
package solutions;

class Vehicle {
    private final String brand;
    private final int year;
    private final double price;

    public Vehicle(String brand, int year, double price) {
        this.brand = brand;
        this.year = year;
        this.price = price;
    }

    public void displayInfo() {
        System.out.printf("Vehicle - Brand: %s, Year: %d, Price: $%.2f%n", brand, year, price);
    }

    public void start() {
        System.out.println("Vehicle is starting...");
    }

    public void stop() {
        System.out.println("Vehicle is stopping...");
    }

    protected String getBrand() {
        return brand;
    }

    protected int getYear() {
        return year;
    }

    protected double getPrice() {
        return price;
    }
}

class Car extends Vehicle {
    private final int numberOfDoors;

    public Car(String brand, int year, double price, int numberOfDoors) {
        super(brand, year, price);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void start() {
        System.out.println("Car engine is starting...");
    }

    @Override
    public void displayInfo() {
        System.out.printf("Car - Brand: %s, Year: %d, Price: $%.2f, Doors: %d%n",
                getBrand(), getYear(), getPrice(), numberOfDoors);
    }
}

class Motorcycle extends Vehicle {
    private final boolean hasStorage;

    public Motorcycle(String brand, int year, double price, boolean hasStorage) {
        super(brand, year, price);
        this.hasStorage = hasStorage;
    }

    @Override
    public void start() {
        System.out.println("Motorcycle is revving...");
    }

    @Override
    public void displayInfo() {
        System.out.printf("Motorcycle - Brand: %s, Year: %d, Price: $%.2f, Storage: %b%n",
                getBrand(), getYear(), getPrice(), hasStorage);
    }
}

public class Solution03Inheritance {
    public static void main(String[] args) {
        System.out.println("=== VEHICLE INHERITANCE DEMO ===");

        Car car = new Car("Toyota", 2022, 25000, 4);
        Motorcycle bike = new Motorcycle("Harley", 2023, 15000, true);

        Vehicle[] vehicles = {car, bike};

        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
            vehicle.start();
            vehicle.stop();
            System.out.println();
        }

        System.out.println("Polymorphism check (Vehicle reference -> Car instance):");
        Vehicle polymorphicVehicle = new Car("Tesla", 2024, 42000, 5);
        polymorphicVehicle.displayInfo();
        polymorphicVehicle.start();
        polymorphicVehicle.stop();
    }
}
