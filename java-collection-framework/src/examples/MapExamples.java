import java.util.HashMap;
import java.util.Map;

public class MapExamples {
    public static void main(String[] args) {
        // Creating a HashMap
        Map<String, Integer> map = new HashMap<>();

        // Adding key-value pairs
        map.put("Apple", 1);
        map.put("Banana", 2);
        map.put("Orange", 3);

        // Displaying the map
        System.out.println("Initial map: " + map);

        // Accessing a value
        int appleCount = map.get("Apple");
        System.out.println("Count of Apples: " + appleCount);

        // Checking if a key exists
        if (map.containsKey("Banana")) {
            System.out.println("Banana is in the map.");
        }

        // Removing a key-value pair
        map.remove("Orange");
        System.out.println("Map after removing Orange: " + map);

        // Iterating through the map
        System.out.println("Iterating through map:");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}