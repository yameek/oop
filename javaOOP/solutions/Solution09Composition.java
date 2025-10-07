/**
 * SOLUTION 9: Composition and Aggregation
 * ======================================
 * Models a computer assembled from tightly coupled internal components (CPU,
 * RAM, Storage) and a loosely coupled external component (Monitor).
 *
 * Implementation notes:
 * - Internal components (`CPU`, `RAM`, `Storage`) are created for each `Computer` instance and never
 *   shared, illustrating composition where lifecycles are bound together.
 * - `Monitor` is passed in from the outside and can be reused across computers, highlighting
 *   aggregation with an independent lifecycle.
 * - Helper methods (`upgradeRAM`, `changeMonitor`, `getMonitor`) make the demo concise and show how
 *   composition enables internal upgrades while aggregation allows resource sharing.
 * - The `main` method walks through initial specs, an upgrade, monitor sharing, and a subsequent
 *   monitor swap to clearly contrast the two relationship types in practice.
 */
package solutions;

class CPU {
    private final String brand;
    private final int cores;
    private final double speedGHz;

    public CPU(String brand, int cores, double speedGHz) {
        this.brand = brand;
        this.cores = cores;
        this.speedGHz = speedGHz;
    }

    public void displayInfo() {
        System.out.printf("CPU: %s, %d cores, %.1f GHz%n", brand, cores, speedGHz);
    }
}

class RAM {
    private final int sizeGB;
    private final String type;
    private final int speedMHz;

    public RAM(int sizeGB, String type, int speedMHz) {
        this.sizeGB = sizeGB;
        this.type = type;
        this.speedMHz = speedMHz;
    }

    public void displayInfo() {
        System.out.printf("RAM: %d GB %s, %d MHz%n", sizeGB, type, speedMHz);
    }
}

class Storage {
    private final int capacityGB;
    private final String type;

    public Storage(int capacityGB, String type) {
        this.capacityGB = capacityGB;
        this.type = type;
    }

    public void displayInfo() {
        System.out.printf("Storage: %d GB %s%n", capacityGB, type);
    }
}

class Monitor {
    private final String brand;
    private final int sizeInches;
    private final String resolution;

    public Monitor(String brand, int sizeInches, String resolution) {
        this.brand = brand;
        this.sizeInches = sizeInches;
        this.resolution = resolution;
    }

    public void displayInfo() {
        System.out.printf("Monitor: %s %d\" %s%n", brand, sizeInches, resolution);
    }

    public String getBrand() {
        return brand;
    }
}

class Computer {
    private final String model;
    private CPU cpu;
    private RAM ram;
    private Storage storage;
    private Monitor monitor; // Aggregated

    public Computer(String model, CPU cpu, RAM ram, Storage storage, Monitor monitor) {
        this.model = model;
        // Composition: these components belong exclusively to this computer
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        // Aggregation: monitor is shared/optional
        this.monitor = monitor;
    }

    public String getModel() {
        return model;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void displayFullSpecs() {
        System.out.println("Computer: " + model);
        cpu.displayInfo();
        ram.displayInfo();
        storage.displayInfo();
        if (monitor != null) {
            monitor.displayInfo();
        } else {
            System.out.println("Monitor: Not attached");
        }
    }

    public void upgradeRAM(RAM newRam) {
        System.out.printf("Upgrading RAM on %s...%n", model);
        this.ram = newRam;
    }

    public void changeMonitor(Monitor newMonitor) {
        System.out.printf("Changing monitor on %s to %s%n", model, newMonitor.getBrand());
        this.monitor = newMonitor;
    }
}

public class Solution09Composition {
    public static void main(String[] args) {
        Monitor dellMonitor = new Monitor("Dell", 27, "4K");

        Computer gamingPC = new Computer(
                "Gaming PC",
                new CPU("Intel i9", 16, 3.6),
                new RAM(32, "DDR4", 3200),
                new Storage(1000, "SSD"),
                dellMonitor
        );

        System.out.println("=== Initial Specs ===");
        gamingPC.displayFullSpecs();

        System.out.println("\n=== Upgrading RAM ===");
        gamingPC.upgradeRAM(new RAM(64, "DDR5", 5200));
        gamingPC.displayFullSpecs();

        System.out.println("\n=== Aggregation Demo (Sharing Monitor) ===");
        Computer officePC = new Computer(
                "Office PC",
                new CPU("AMD Ryzen 5", 6, 3.0),
                new RAM(16, "DDR4", 2666),
                new Storage(512, "SSD"),
                dellMonitor // same monitor referenced
        );
        System.out.println("Gaming PC monitor brand: " + gamingPC.getMonitor().getBrand());
        System.out.println("Office PC monitor brand: " + officePC.getMonitor().getBrand());

        System.out.println("\n=== Composition Demo ===");
        // Internal components are exclusive to each computer.
        System.out.println("Each computer owns its CPU/RAM/Storage. They cannot be shared or reused once the computer is disposed.");

        System.out.println("\nForcing monitor change on office PC...");
        officePC.changeMonitor(new Monitor("LG", 24, "1080p"));
        System.out.println("Office PC new monitor brand: " + officePC.getMonitor().getBrand());
        System.out.println("Gaming PC monitor brand remains: " + gamingPC.getMonitor().getBrand());
    }
}
