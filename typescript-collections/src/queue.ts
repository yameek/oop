export class Queue<T> {
  private items: T[] = [];

  enqueue(item: T): void {
    this.items.push(item);
  }

  dequeue(): T | undefined {
    return this.items.shift();
  }

  peek(): T | undefined {
    return this.items[0];
  }

  get size(): number {
    return this.items.length;
  }
}

export function runQueueExamples(): void {
  console.log("--- Queue (FIFO) examples ---");

  const queue = new Queue<string>();
  queue.enqueue("task-1");
  queue.enqueue("task-2");
  queue.enqueue("task-3");

  console.log("Peek:", queue.peek());
  console.log("Dequeue:", queue.dequeue());
  console.log("Dequeue:", queue.dequeue());
  console.log("Size:", queue.size);
  console.log("");
}
