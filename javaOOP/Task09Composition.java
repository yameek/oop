/**
 * TASK 9: Composition and Aggregation
 * ====================================
 * Difficulty: Very Advanced ⭐⭐⭐⭐⭐
 * 
 * Learn about: Composition, Aggregation, Has-a relationships
 * 
 * PROBLEM:
 * --------
 * Create a computer system using composition and aggregation.
 * 
 * Requirements:
 * 
 * 1. Create class 'CPU':
 *    - Variables: brand (String), cores (int), speedGHz (double)
 *    - Constructor and displayInfo()
 * 
 * 2. Create class 'RAM':
 *    - Variables: sizeGB (int), type (String), speedMHz (int)
 *    - Constructor and displayInfo()
 * 
 * 3. Create class 'Storage':
 *    - Variables: capacityGB (int), type (String - "SSD" or "HDD")
 *    - Constructor and displayInfo()
 * 
 * 4. Create class 'Monitor' (for aggregation):
 *    - Variables: brand (String), sizeInches (int), resolution (String)
 *    - Constructor and displayInfo()
 * 
 * 5. Create class 'Computer':
 *    - COMPOSITION: Has CPU, RAM, Storage (created in constructor)
 *    - AGGREGATION: Has Monitor (passed to constructor, can exist independently)
 *    - Variables: model (String), cpu, ram, storage, monitor
 *    - Constructor creates CPU, RAM, Storage but accepts Monitor
 *    - Method: displayFullSpecs() - displays all component info
 *    - Method: upgradeRAM(RAM newRam) - replaces RAM
 *    - Method: changeMonitor(Monitor newMonitor) - changes monitor
 * 
 * 6. Demonstrate the difference:
 *    - When Computer is destroyed, CPU/RAM/Storage are destroyed (composition)
 *    - But Monitor can be used with different computers (aggregation)
 * 
 * TEST YOUR CODE:
 * ---------------
 * - Create a computer with all components
 * - Display full specs
 * - Upgrade RAM
 * - Share a monitor between two computers
 * - Demonstrate composition vs aggregation
 * 
 * CONCEPTS TO UNDERSTAND:
 * -----------------------
 * - Composition (strong has-a, lifecycle dependent)
 * - Aggregation (weak has-a, independent lifecycle)
 * - When to use composition vs inheritance
 * - Object relationships
 * - Encapsulation at system level
 * 
 * EXPECTED OUTPUT EXAMPLE:
 * ------------------------
 * Computer: Gaming PC
 * CPU: Intel i9, 16 cores, 3.6 GHz
 * RAM: 32 GB DDR4, 3200 MHz
 * Storage: 1000 GB SSD
 * Monitor: Dell 27" 4K
 * 
 * After RAM upgrade:
 * RAM: 64 GB DDR5, 4800 MHz
 * 
 * Sharing monitor with second computer...
 * Computer 1: Gaming PC with Dell monitor
 * Computer 2: Office PC with Dell monitor (same monitor!)
 */

// Write your solution below:

