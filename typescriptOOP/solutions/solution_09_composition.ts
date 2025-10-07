/**
 * SOLUTION 9: Composition & Dependency Injection
 * ==============================================
 *
 * Concepts covered:
 * - Interfaces defining component capabilities
 * - Dependency injection via constructor parameters
 * - Delegation from a high-level orchestrator class
 */

interface Processor {
  boot(): void;
  execute(task: string): void;
}

interface Memory {
  load(address: number): string;
  store(address: number, value: string): void;
}

interface PersistentStorage {
  read(path: string): string;
  write(path: string, data: string): void;
}

interface Logger {
  info(message: string): void;
  error(message: string): void;
}

class ConsoleLogger implements Logger {
  info(message: string): void {
    console.log(`ℹ️  ${new Date().toISOString()} :: ${message}`);
  }

  error(message: string): void {
    console.error(`❌ ${new Date().toISOString()} :: ${message}`);
  }
}

class FastProcessor implements Processor {
  constructor(private readonly logger: Logger) {}

  boot(): void {
    this.logger.info("FastProcessor boot sequence complete.");
  }

  execute(task: string): void {
    this.logger.info(`Executing task '${task}' with turbo boost.`);
  }
}

class StandardMemory implements Memory {
  private readonly cells = new Map<number, string>();

  constructor(private readonly logger: Logger) {}

  load(address: number): string {
    const value = this.cells.get(address) ?? "<empty>";
    this.logger.info(`Memory load @${address} => ${value}`);
    return value;
  }

  store(address: number, value: string): void {
    this.logger.info(`Memory store @${address} <= ${value}`);
    this.cells.set(address, value);
  }
}

class SolidStateDrive implements PersistentStorage {
  private readonly files = new Map<string, string>();

  constructor(private readonly logger: Logger) {}

  read(path: string): string {
    const data = this.files.get(path) ?? "{}";
    this.logger.info(`SSD read ${path}`);
    return data;
  }

  write(path: string, data: string): void {
    this.logger.info(`SSD write ${path}`);
    this.files.set(path, data);
  }
}

class CloudStorage implements PersistentStorage {
  constructor(private readonly logger: Logger, private readonly latencyMs = 120) {}

  read(path: string): string {
    this.logger.info(`Cloud read ${path} (latency ${this.latencyMs}ms)`);
    return `{"path":"${path}","synced":true}`;
  }

  write(path: string, data: string): void {
    this.logger.info(`Cloud write ${path} (latency ${this.latencyMs}ms)`);
    this.logger.info(`Payload: ${data}`);
  }
}

class ComputerSystem {
  constructor(
    private cpu: Processor,
    private memory: Memory,
    private storage: PersistentStorage,
    private readonly logger: Logger
  ) {}

  powerOn(): void {
    this.logger.info("Powering on system...");
    this.cpu.boot();
    this.logger.info("System ready.");
  }

  runTask(task: string): void {
    this.logger.info(`Dispatching task '${task}'.`);
    this.cpu.execute(task);
  }

  loadConfig(path: string): void {
    this.logger.info(`Loading configuration from ${path}...`);
    const data = this.storage.read(path);
    this.memory.store(0, data);
    this.logger.info("Configuration cached in memory.");
  }

  swapProcessor(newCpu: Processor): void {
    this.logger.info("Swapping processor component.");
    this.cpu = newCpu;
  }

  swapStorage(newStorage: PersistentStorage): void {
    this.logger.info("Swapping storage component.");
    this.storage = newStorage;
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const logger = new ConsoleLogger();
const cpu = new FastProcessor(logger);
const memory = new StandardMemory(logger);
const storage = new SolidStateDrive(logger);

const pc = new ComputerSystem(cpu, memory, storage, logger);
pc.powerOn();
pc.loadConfig("/etc/app.json");
pc.runTask("render-report");

logger.info("\nUpgrading storage to cloud...");
pc.swapStorage(new CloudStorage(logger));
pc.loadConfig("/configs/remote.json");
pc.runTask("data-sync");

export {};
