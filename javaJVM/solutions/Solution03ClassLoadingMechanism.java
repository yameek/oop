import java.lang.reflect.Method;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

/**
 * SOLUTION 3: Class Loading Mechanism & Custom ClassLoader
 * ========================================================
 * Demonstrates ClassLoader hierarchy, parent delegation, Class.forName vs loadClass initialization,
 * and custom class loading from raw byte arrays.
 */
public class Solution03ClassLoadingMechanism {

    public static void main(String[] args) throws Exception {
        System.out.println("=== 1. Inspecting ClassLoader Hierarchy ===");
        inspectClassLoaders();

        System.out.println("\n=== 2. Class.forName() vs ClassLoader.loadClass() ===");
        testClassInitialization();

        System.out.println("\n=== 3. Custom ClassLoader Implementation ===");
        testCustomClassLoader();
    }

    public static class SampleClass {
        static {
            System.out.println("  --> [Static Initializer] SampleClass has been INITIALIZED!");
        }

        public String greet() {
            return "Hello from dynamically loaded class!";
        }
    }

    private static void inspectClassLoaders() {
        System.out.println("String.class ClassLoader         : " + String.class.getClassLoader() + " (Bootstrap ClassLoader)");

        ClassLoader appLoader = Solution03ClassLoadingMechanism.class.getClassLoader();
        System.out.println("Application ClassLoader          : " + appLoader);

        ClassLoader parentLoader = appLoader.getParent();
        System.out.println("Parent (Platform) ClassLoader    : " + parentLoader);

        if (parentLoader != null) {
            System.out.println("Grandparent (Bootstrap) ClassLoader: " + parentLoader.getParent() + " (null in Java)");
        }
    }

    private static void testClassInitialization() throws Exception {
        String className = "Solution03ClassLoadingMechanism$SampleClass";
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();

        System.out.println("Step 1: Calling ClassLoader.loadClass('" + className + "')...");
        Class<?> loadedClass = systemLoader.loadClass(className);
        System.out.println("  Class loaded successfully! Class object: " + loadedClass.getName());

        System.out.println("\nStep 2: Calling Class.forName('" + className + "')...");
        Class<?> initializedClass = Class.forName(className);
        System.out.println("  Class.forName complete!");
    }

    private static void testCustomClassLoader() throws Exception {
        // Read bytecode of SampleClass dynamically
        String sampleClassName = Solution03ClassLoadingMechanism.class.getName() + "$SampleClass";
        String resourcePath = sampleClassName.replace('.', '/') + ".class";
        
        byte[] classBytes;
        try (InputStream is = ClassLoader.getSystemResourceAsStream(resourcePath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (is == null) {
                System.out.println("  Resource not found: " + resourcePath);
                return;
            }
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            classBytes = baos.toByteArray();
        }

        CustomMemoryClassLoader customLoader = new CustomMemoryClassLoader();
        Class<?> dynamicClass = customLoader.defineCustomClass(sampleClassName, classBytes);
        
        System.out.println("Custom ClassLoader created class : " + dynamicClass.getName());
        System.out.println("Class loaded by custom loader    : " + dynamicClass.getClassLoader());

        Object instance = dynamicClass.getDeclaredConstructor().newInstance();
        Method greetMethod = dynamicClass.getMethod("greet");
        Object result = greetMethod.invoke(instance);
        System.out.println("Invoked greet() method output    : " + result);
    }

    public static class CustomMemoryClassLoader extends ClassLoader {
        public Class<?> defineCustomClass(String name, byte[] bytecode) {
            return defineClass(name, bytecode, 0, bytecode.length);
        }
    }
}
