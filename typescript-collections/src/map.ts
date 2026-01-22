export function runMapExamples(): void {
  console.log("--- Map examples ---");

  const inventory = new Map<string, number>();
  inventory.set("apples", 10);
  inventory.set("bananas", 7);
  inventory.set("oranges", 3);

  console.log("Has apples:", inventory.has("apples"));
  console.log("Get bananas:", inventory.get("bananas"));

  inventory.set("bananas", 9); // update
  inventory.delete("oranges");

  console.log("Entries:");
  for (const [item, qty] of inventory) {
    console.log(`  ${item} -> ${qty}`);
  }

  const keys = [...inventory.keys()];
  const values = [...inventory.values()];

  console.log("Keys:", keys.join(", "));
  console.log("Values:", values.join(", "));
  console.log("");
}
