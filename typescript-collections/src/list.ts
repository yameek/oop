export function runListExamples(): void {
  console.log("--- List (Array) examples ---");

  const numbers: number[] = [10, 20, 30];
  numbers.push(40);
  numbers.unshift(5);

  console.log("List contents:", numbers.join(", "));
  console.log("Size:", numbers.length);
  console.log("Get index 2:", numbers[2]);

  const removed = numbers.splice(1, 1); // remove element at index 1
  console.log("Removed:", removed[0]);
  console.log("After removal:", numbers.join(", "));

  const sorted = [...numbers].sort((a, b) => a - b);
  console.log("Sorted copy:", sorted.join(", "));

  const doubled = numbers.map((n) => n * 2);
  console.log("Mapped (x2):", doubled.join(", "));

  const filtered = numbers.filter((n) => n >= 20);
  console.log("Filtered (>=20):", filtered.join(", "));

  console.log("Best practice: avoid mutating shared arrays unless intended.");
  console.log("");
}
