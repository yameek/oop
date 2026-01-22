type Node<T> = {
  value: T;
  next: Node<T> | null;
};

export class LinkedList<T> implements Iterable<T> {
  private head: Node<T> | null = null;
  private tail: Node<T> | null = null;
  private sizeValue = 0;

  get size(): number {
    return this.sizeValue;
  }

  addLast(value: T): void {
    const node: Node<T> = { value, next: null };
    if (!this.head) {
      this.head = node;
      this.tail = node;
    } else {
      this.tail!.next = node;
      this.tail = node;
    }
    this.sizeValue += 1;
  }

  addFirst(value: T): void {
    const node: Node<T> = { value, next: this.head };
    this.head = node;
    if (!this.tail) {
      this.tail = node;
    }
    this.sizeValue += 1;
  }

  removeFirst(): T | undefined {
    if (!this.head) return undefined;
    const value = this.head.value;
    this.head = this.head.next;
    if (!this.head) {
      this.tail = null;
    }
    this.sizeValue -= 1;
    return value;
  }

  *[Symbol.iterator](): Iterator<T> {
    let current = this.head;
    while (current) {
      yield current.value;
      current = current.next;
    }
  }
}

export function runLinkedListExamples(): void {
  console.log("--- LinkedList examples ---");

  const list = new LinkedList<string>();
  list.addLast("alpha");
  list.addLast("beta");
  list.addFirst("zero");

  console.log("Size:", list.size);
  console.log("Items:", [...list].join(", "));

  const first = list.removeFirst();
  console.log("Removed first:", first);
  console.log("After removal:", [...list].join(", "));
  console.log("");
}
