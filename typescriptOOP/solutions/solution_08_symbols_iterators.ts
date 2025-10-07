/**
 * SOLUTION 8: Symbols, Iterators & Custom Behavior
 * ================================================
 *
 * Concepts covered:
 * - Implementing the iterable protocol via generators
 * - Customizing `toString()` and `Symbol.toStringTag`
 * - Returning cloned/shuffled data without mutating the original
 */

class Playlist implements Iterable<string> {
  #songs: string[];
  #id: string = Math.random().toString(36).slice(2, 8).toUpperCase();

  constructor(songs: Iterable<string> = []) {
    this.#songs = Array.from(songs);
  }

  add(song: string): void {
    this.#songs.push(song);
  }

  remove(song: string): void {
    const index = this.#songs.indexOf(song);
    if (index >= 0) {
      this.#songs.splice(index, 1);
    }
  }

  *[Symbol.iterator](): Iterator<string> {
    for (const song of this.#songs) {
      yield song;
    }
  }

  get [Symbol.toStringTag](): string {
    return "Playlist";
  }

  toString(): string {
    const list = this.#songs.join(", ");
    return `Playlist#${this.#id} (${this.#songs.length} songs): ${list}`;
  }

  shuffle(): Playlist {
    const clone = [...this.#songs];
    for (let i = clone.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      const temp = clone[i]!;
      clone[i] = clone[j]!;
      clone[j] = temp;
    }
    return new Playlist(clone);
  }

  snapshot(): ReadonlyArray<string> {
    return [...this.#songs];
  }
}

// --------------------------------------------
// Demonstration
// --------------------------------------------

const playlist = new Playlist(["Lost Stars", "Gravity", "Yellow"]);
playlist.add("Clocks");
playlist.add("Fix You");
playlist.remove("Gravity");

console.log(String(playlist));
console.log(Object.prototype.toString.call(playlist));

console.log("\nIterating:");
for (const song of playlist) {
  console.log(` - ${song}`);
}

const shuffled = playlist.shuffle();
console.log("\nShuffled copy:");
for (const song of shuffled) {
  console.log(` • ${song}`);
}

console.log("\nOriginal remains:", playlist.snapshot());

export {};
