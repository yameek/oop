export class Deque<T> {
  private items: T[] = [];

  addFirst(item: T): void {
    this.items.unshift(item);
  }

  addLast(item: T): void {
    this.items.push(item);
  }

  removeFirst(): T | undefined {
    return this.items.shift();
  }

  removeLast(): T | undefined {
    return this.items.pop();
  }

  peekFirst(): T | undefined {
    return this.items[0];
  }

  peekLast(): T | undefined {
    return this.items[this.items.length - 1];
  }

  get size(): number {
    return this.items.length;
  }
}

export function runDequeExamples(): void {
  console.log("--- Deque examples ---");

  const deque = new Deque<number>();
  deque.addLast(1);
  deque.addLast(2);
  deque.addFirst(0);
  deque.addLast(3);

  console.log("Peek first:", deque.peekFirst());
  console.log("Peek last:", deque.peekLast());
  console.log("Remove first:", deque.removeFirst());
  console.log("Remove last:", deque.removeLast());
  console.log("Size:", deque.size);
  console.log("");
}
