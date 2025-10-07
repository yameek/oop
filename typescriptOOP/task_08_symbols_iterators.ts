/**
 * TASK 8: Symbols, Iterators & Custom Behavior
 * ============================================
 * Difficulty: Advanced
 *
 * Learn about: iterables, generator methods, symbol customization, custom toString
 *
 * SCENARIO
 * --------
 * Build a `Playlist` class that models a collection of songs. It should be
 * iterable, expose a friendly `toString()`, and provide metadata through
 * `Symbol.toStringTag`.
 *
 * REQUIREMENTS
 * ------------
 * 1. Implement a `Playlist` class with:
 *    - A private array of songs (each song can be a simple object or string).
 *    - A constructor that accepts an optional iterable of songs.
 *    - Methods `add(song: string): void` and `remove(song: string): void`.
 *
 * 2. Implement `[Symbol.iterator]()` so that the playlist can be used in
 *    `for...of`, spread syntax, etc. Use a generator function to yield songs in
 *    order.
 *
 * 3. Override `toString()` to return something like "Playlist (3 songs): ...".
 *    Also provide a getter for `[Symbol.toStringTag]` that returns "Playlist".
 *
 * 4. Add a method `shuffle(): Playlist` that returns a *new* playlist instance
 *    with the same songs in randomized order, leaving the original unchanged.
 *
 * 5. Demonstrate usage by:
 *    - Creating a playlist with multiple songs
 *    - Iterating through it with `for...of`
 *    - Logging the result of `String(playlist)` and `Object.prototype.toString.call(playlist)`
 *    - Calling `shuffle()` and showing the new order
 *
 * BONUS (optional)
 * ---------------
 * - Implement `[Symbol.asyncIterator]` to simulate asynchronous streaming.
 * - Add a method `groupByArtist()` that returns a `Map<string, string[]>`.
 * - Use a private `#id` field and include it in the `toString()` output.
 *
 * TESTING YOUR WORK
 * -----------------
 * Run the file with: `npm run task -- task_08_symbols_iterators.ts`
 */

// Write your solution below this line 👇

