export function runSetExamples(): void {
  console.log("--- Set examples ---");

  const values = new Set<number>([1, 2, 2, 3, 4]);
  values.add(5);
  values.delete(2);

  console.log("Set has 3:", values.has(3));
  console.log("Size:", values.size);
  console.log("Values:", [...values].join(", "));

  const a = new Set([1, 2, 3]);
  const b = new Set([3, 4, 5]);

  const union = new Set([...a, ...b]);
  const intersection = new Set([...a].filter((x) => b.has(x)));
  const difference = new Set([...a].filter((x) => !b.has(x)));

  console.log("Union:", [...union].join(", "));
  console.log("Intersection:", [...intersection].join(", "));
  console.log("Difference (a - b):", [...difference].join(", "));
  console.log("");
}
