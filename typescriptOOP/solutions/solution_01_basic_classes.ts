/**
 * SOLUTION 1: Basic Classes and Objects
 * =====================================
 *
 * Concepts covered:
 * - Class declarations & constructors
 * - Parameter properties for concise field initialization
 * - Instance methods and state mutations
 * - Simple logging for verification
 */

class Book {
  private borrowCount = 0;

  constructor(
    public title: string,
    public author: string,
    public pages: number,
    public isAvailable: boolean = true
  ) {}

  describe(): string {
    const status = this.isAvailable ? "Available" : "Borrowed";
    return `${this.title} by ${this.author} — ${this.pages} pages (${status})`;
  }

  borrow(): void {
    if (!this.isAvailable) {
      console.log(`✗ '${this.title}' is already checked out.`);
      return;
    }

    this.isAvailable = false;
    this.borrowCount += 1;
    console.log(`✓ Enjoy reading '${this.title}'! (borrowed ${this.borrowCount}x)`);
  }

  return(): void {
    if (this.isAvailable) {
      console.log(`⚠ '${this.title}' was not borrowed, but marked available.`);
      return;
    }

    this.isAvailable = true;
    console.log(`✓ Thanks for returning '${this.title}'.`);
  }

  static printInventory(books: Book[]): void {
    console.log("\n--- Library Inventory ---");
    books.forEach((book, index) => {
      console.log(`${index + 1}. ${book.describe()}`);
    });
    console.log("--------------------------\n");
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const cleanCode = new Book("Clean Code", "Robert C. Martin", 464);
const pragmatic = new Book("The Pragmatic Programmer", "Andy Hunt & Dave Thomas", 352);
const designPatterns = new Book("Design Patterns", "Erich Gamma et al.", 395);

Book.printInventory([cleanCode, pragmatic, designPatterns]);

cleanCode.borrow();
cleanCode.borrow();
pragmatic.borrow();

Book.printInventory([cleanCode, pragmatic, designPatterns]);

cleanCode.return();
cleanCode.return();

Book.printInventory([cleanCode, pragmatic, designPatterns]);

export {};