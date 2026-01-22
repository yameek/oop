public class ListExamples {
    
    public static void main(String[] args) {
        ListExamples examples = new ListExamples();
        examples.runExamples();
    }

    private void runExamples() {
        createListExample();
        modifyListExample();
        iterateListExample();
    }

    private void createListExample() {
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        System.out.println("Created List: " + list);
    }

    private void modifyListExample() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        list.set(1, "Blueberry");
        list.remove("Cherry");
        System.out.println("Modified List: " + list);
    }

    private void iterateListExample() {
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Cherry"));
        System.out.println("Iterating over List:");
        for (String fruit : list) {
            System.out.println(fruit);
        }
    }
}