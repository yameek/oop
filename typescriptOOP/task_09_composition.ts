/**
 * TASK 9: Composition & Dependency Injection
 * ==========================================
 * Difficulty: Advanced
 *
 * Learn about: HAS-A relationships, delegation, dependency injection, testing seams
 *
 * SCENARIO
 * --------
 * Model a `ComputerSystem` that is composed of multiple components (CPU, RAM,
 * Storage, etc.). Each component should be responsible for part of the overall
 * behavior, and the system should delegate work to them.
 *
 * REQUIREMENTS
 * ------------
 * 1. Define interfaces (or abstract classes) for core components, e.g.:
 *    - `Processor` with `boot()` and `execute(task: string)`.
 *    - `Memory` with `load(address: number): string` and `store(address: number, value: string): void`.
 *    - `Storage` with `read(path: string): string` and `write(path: string, data: string): void`.
 *
 * 2. Create concrete implementations for each interface (e.g.,
 *    `FastProcessor`, `StandardMemory`, `SolidStateDrive`).
 *
 * 3. Create a `ComputerSystem` class that accepts these dependencies via its
 *    constructor (dependency injection) and exposes methods:
 *    - `powerOn(): void` → boots the processor and logs status.
 *    - `runTask(task: string): void` → delegates to the processor.
 *    - `loadConfig(path: string): void` → loads configuration from storage and
 *      stores it in memory.
 *
 * 4. Demonstrate by wiring together the components, calling the system methods,
 *    and logging meaningful output for each delegated call.
 *
 * BONUS (optional)
 * ---------------
 * - Add an interface `Logger` and inject it for better observability.
 * - Create mock implementations for testing (e.g., `FakeStorage`).
 * - Support swapping components at runtime by exposing setters.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_09_composition.ts`
 */

// Write your solution below this line 👇

