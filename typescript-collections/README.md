# TypeScript Collection Framework (Java-style)

This directory provides runnable TypeScript examples that map Java Collection Framework features to idiomatic TS.

## What’s included
- Lists: dynamic arrays (Array) and a simple LinkedList
- Sets: uniqueness and set operations
- Maps: key-value data structures
- Queues: FIFO queues
- Deques: double-ended queues
- Best practices: immutability, defensive copying, iteration, and performance notes

## Run
1) Install dependencies:
- npm install

2) Run examples:
- npm run start

## Files
- src/index.ts: runner
- src/list.ts: Array/List operations
- src/linked_list.ts: simple linked list implementation
- src/set.ts: Set operations
- src/map.ts: Map operations
- src/queue.ts: FIFO queue
- src/deque.ts: Deque

## Notes
TypeScript does not have a built-in Java Collection Framework. Use:
- Array for List
- Set for Set
- Map for Map
- Custom classes for Queue/Deque/LinkedList

Prefer immutability and return new collections where practical. Avoid exposing internal arrays directly.
