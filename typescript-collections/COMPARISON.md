# Java Collection Framework vs TypeScript (Interview Prep)

This guide compares Java Collection Framework (JCF) with TypeScript/JavaScript collections, explains how they work under the hood, and highlights performance tradeoffs.

> Key idea: TypeScript uses JavaScript runtime data structures. JCF is a language-level framework with multiple concrete implementations optimized for different tradeoffs.

## 1) Quick Mapping

| Java Interface | Common Java Implementations | TypeScript/JS Equivalent | Notes |
|---|---|---|---|
| List | ArrayList, LinkedList, Vector | Array, LinkedList (custom) | JS Array is dynamic and optimized; no built-in LinkedList. |
| Set | HashSet, LinkedHashSet, TreeSet | Set | JS Set preserves insertion order, no sorted TreeSet built-in. |
| Map | HashMap, LinkedHashMap, TreeMap | Map | JS Map preserves insertion order; no sorted TreeMap built-in. |
| Queue | ArrayDeque, LinkedList, PriorityQueue | custom Queue, Array, custom PriorityQueue | No built-in queue/priority queue. |
| Deque | ArrayDeque, LinkedList | custom Deque | Implement with array or linked list. |
| Stack | Stack (legacy), ArrayDeque | Array (push/pop) | JS Array is a good stack. |

## 2) Java vs TypeScript Data Structure Internals

### ArrayList (Java) vs Array (JS)
- **Java ArrayList**
  - Backed by a resizable array.
  - On growth, a new array is allocated and elements are copied (usually 1.5x growth).
  - `get(i)` is O(1); `add` amortized O(1); insert/remove in middle O(n).

- **JS Array**
  - Implemented by the JS engine with multiple internal representations (dense vs sparse).
  - Engines (e.g., V8) optimize dense numeric arrays (packed elements) and switch to “dictionary mode” for sparse arrays.
  - `push`/`pop` are amortized O(1); `shift`/`unshift` are O(n) due to reindexing.

### HashMap/HashSet (Java) vs Map/Set (JS)
- **Java HashMap/HashSet**
  - Uses hashing into buckets; collisions handled via linked list or tree bins (Java 8+)
  - Time average O(1) for `get/put`, worst-case O(n), improved to O(log n) in tree bins.
  - Hashing depends on `hashCode()` and `equals()`.

- **JS Map/Set**
  - Uses hash tables (implementation detail), with insertion order preserved.
  - Keys are compared by identity for objects; primitives use SameValueZero semantics.
  - Average O(1) for `get/set/has`, no sorted ordering.

### TreeMap/TreeSet (Java)
- Backed by Red-Black Tree; always sorted by key or comparator.
- Operations are O(log n).
- **TypeScript**: no built-in balanced tree. Use a library (e.g., `sorted-btree`) when you need ordering.

### LinkedList (Java) vs Custom LinkedList (TS)
- **Java LinkedList**: doubly linked list, O(1) for head/tail add/remove, O(n) for indexed access.
- **TypeScript**: must implement manually. Often slower than arrays due to GC pressure and cache misses.

### PriorityQueue (Java) vs Custom Heap (TS)
- **Java PriorityQueue**: binary heap, O(log n) insert/remove, O(1) peek.
- **TypeScript**: implement a binary heap or use a library (`@datastructures-js/priority-queue`).

## 3) Performance Cheat Sheet (Big-O)

| Operation | Java ArrayList | Java LinkedList | JS Array | JS Map/Set | Java HashMap/HashSet |
|---|---|---|---|---|---|
| Access by index | O(1) | O(n) | O(1) | n/a | n/a |
| Add to end | O(1) amortized | O(1) | O(1) amortized | n/a | O(1) avg |
| Add to front | O(n) | O(1) | O(n) | n/a | O(1) avg |
| Remove from front | O(n) | O(1) | O(n) | n/a | O(1) avg |
| Search by value | O(n) | O(n) | O(n) | O(n) | O(n) |
| Add/Remove by key | n/a | n/a | n/a | O(1) avg | O(1) avg |

> Interview tip: JS `shift()`/`unshift()` are O(n) because elements must be reindexed.

## 4) Ordering Guarantees

| Structure | Java | TypeScript/JS |
|---|---|---|
| HashMap/HashSet | No order guarantee | Map/Set preserve insertion order |
| LinkedHashMap/LinkedHashSet | Insertion order | Map/Set preserve insertion order |
| TreeMap/TreeSet | Sorted order | No built-in sorted map/set |

## 5) Equality & Hashing

- **Java**: `HashMap` uses `hashCode()` and `equals()`; keys must be stable and consistent.
- **JS**: Map/Set compare primitives by value (SameValueZero); objects by reference identity. Two different objects with same content are different keys.

## 6) Iteration & Fail-Fast Behavior

- **Java**: Many collections are fail-fast on concurrent modification (throws `ConcurrentModificationException`).
- **JS**: Iterators are not fail-fast; modifications during iteration are allowed but can lead to surprising results.

## 7) Concurrency

- **Java**: Collections have synchronized variants and concurrent collections (`ConcurrentHashMap`, `CopyOnWriteArrayList`).
- **JS/TS**: Single-threaded event loop; concurrency is handled via async tasks. For true parallelism (Workers), you usually use message passing, not shared mutable collections.

## 8) Best Practices (Interview-Ready)

### When to choose Array vs LinkedList
- **Array**: best for random access and iteration; cache-friendly.
- **LinkedList**: use only if you need frequent insert/remove at head/tail and minimal random access.

### When to use Map/Set
- Use `Map` for key-value lookups and `Set` for uniqueness checks.
- Avoid using plain objects as maps for non-string keys.

### Immutability
- Prefer creating new arrays/maps when passing between layers to avoid unintended mutation.
- Use `const` for references; mutate only when necessary.

### Performance considerations in JS
- Keep arrays dense (avoid huge sparse indexes).
- Use `push`/`pop` for stack-like behavior; avoid `shift`/`unshift` in performance-critical code.

## 9) Interview Q&A Highlights

**Q: Why is `shift()` O(n) in JS arrays?**
A: Because all indices must be shifted left by 1.

**Q: Why are Map/Set better than plain objects for keys?**
A: They support non-string keys, have predictable iteration order, and avoid prototype collisions.

**Q: Why prefer Array over LinkedList in JS?**
A: Arrays are highly optimized in engines; linked lists allocate many small objects and hurt cache locality.

**Q: How do you implement TreeMap in TS?**
A: Use a balanced tree library or implement a Red-Black Tree yourself.

## 10) Practical Recommendations

- Use Array for most list-like needs.
- Use Map/Set for key-based operations.
- Build custom Queue/Deque if you need FIFO/LIFO operations with O(1) head/tail operations (or maintain a head index over a backing array).
- For ordered maps/sets, use a third-party library.

---

If you want, I can add priority queue and tree map implementations in TypeScript for deeper interview practice.
