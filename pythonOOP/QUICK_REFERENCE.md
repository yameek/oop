# Python OOP Quick Reference Guide

## Table of Contents
1. [Basic Class Syntax](#basic-class-syntax)
2. [Instance vs Class Variables](#instance-vs-class-variables)
3. [Methods Types](#method-types)
4. [Inheritance](#inheritance)
5. [Encapsulation](#encapsulation)
6. [Magic Methods](#magic-methods)
7. [Common Patterns](#common-patterns)

---

## Basic Class Syntax

```python
class MyClass:
    """Class docstring"""
    
    def __init__(self, param1, param2):
        """Constructor - called when creating an instance"""
        self.param1 = param1  # Instance variable
        self.param2 = param2
    
    def my_method(self):
        """Instance method - works with instance data"""
        return self.param1 + self.param2

# Creating an instance
obj = MyClass(10, 20)
result = obj.my_method()  # Returns 30
```

---

## Instance vs Class Variables

```python
class Example:
    class_var = "I'm shared by all instances"  # Class variable
    
    def __init__(self, value):
        self.instance_var = value  # Instance variable (unique per instance)

# Class variable - shared
Example.class_var  # Access via class
obj1 = Example("A")
obj2 = Example("B")
obj1.class_var == obj2.class_var  # True (same for all)

# Instance variable - unique
obj1.instance_var  # "A"
obj2.instance_var  # "B"
```

---

## Method Types

### Instance Methods
```python
class MyClass:
    def instance_method(self):
        """Has access to instance (self)"""
        return self.some_attribute
```

### Class Methods
```python
class MyClass:
    count = 0
    
    @classmethod
    def class_method(cls):
        """Has access to class (cls), not instance"""
        return cls.count
```

### Static Methods
```python
class MyClass:
    @staticmethod
    def static_method(x, y):
        """No access to class or instance - like a regular function"""
        return x + y
```

---

## Inheritance

### Single Inheritance
```python
class Parent:
    def __init__(self, name):
        self.name = name
    
    def greet(self):
        return f"Hello, I'm {self.name}"

class Child(Parent):
    def __init__(self, name, age):
        super().__init__(name)  # Call parent's __init__
        self.age = age
    
    def greet(self):
        """Override parent's method"""
        return f"Hi, I'm {self.name}, age {self.age}"
```

### Multiple Inheritance
```python
class A:
    def method(self):
        print("A")

class B:
    def method(self):
        print("B")

class C(A, B):  # Inherits from both A and B
    pass

# Method Resolution Order (MRO)
C.__mro__  # Shows: C -> A -> B -> object
```

---

## Encapsulation

### Public, Protected, Private
```python
class MyClass:
    def __init__(self):
        self.public = "Everyone can access"
        self._protected = "Convention: internal use"
        self.__private = "Name mangled to _MyClass__private"
    
    def get_private(self):
        return self.__private

obj = MyClass()
obj.public        # ✓ Works
obj._protected    # ✓ Works (but shouldn't be used outside class)
obj.__private     # ✗ AttributeError
obj.get_private() # ✓ Works (proper way to access)
```

### Properties (Getters/Setters)
```python
class Person:
    def __init__(self, name):
        self._name = name
    
    @property
    def name(self):
        """Getter - access like: person.name"""
        return self._name
    
    @name.setter
    def name(self, value):
        """Setter - use like: person.name = "New Name" """
        if not value:
            raise ValueError("Name cannot be empty")
        self._name = value

person = Person("Alice")
print(person.name)      # Uses getter
person.name = "Bob"     # Uses setter
```

---

## Magic Methods

### Common Magic Methods
```python
class MyClass:
    def __init__(self, value):
        """Constructor"""
        self.value = value
    
    def __str__(self):
        """Called by str() and print()"""
        return f"MyClass({self.value})"
    
    def __repr__(self):
        """Called by repr() - for developers"""
        return f"MyClass(value={self.value!r})"
    
    def __len__(self):
        """Called by len()"""
        return len(self.value)
    
    def __getitem__(self, key):
        """Called by obj[key]"""
        return self.value[key]
    
    def __eq__(self, other):
        """Called by =="""
        return self.value == other.value
    
    def __lt__(self, other):
        """Called by <"""
        return self.value < other.value
    
    def __add__(self, other):
        """Called by +"""
        return MyClass(self.value + other.value)
    
    def __contains__(self, item):
        """Called by 'in' operator"""
        return item in self.value
```

### Using Magic Methods
```python
obj = MyClass("Hello")
print(obj)           # __str__
len(obj)             # __len__
obj[0]               # __getitem__
obj1 == obj2         # __eq__
obj1 + obj2          # __add__
"H" in obj          # __contains__
```

---

## Common Patterns

### Singleton Pattern
```python
class Singleton:
    _instance = None
    
    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

# Always returns same instance
s1 = Singleton()
s2 = Singleton()
s1 is s2  # True
```

### Factory Pattern
```python
class Animal:
    def speak(self):
        pass

class Dog(Animal):
    def speak(self):
        return "Woof!"

class Cat(Animal):
    def speak(self):
        return "Meow!"

class AnimalFactory:
    @staticmethod
    def create_animal(animal_type):
        if animal_type == "dog":
            return Dog()
        elif animal_type == "cat":
            return Cat()
        else:
            raise ValueError("Unknown animal type")

# Use the factory
animal = AnimalFactory.create_animal("dog")
animal.speak()  # "Woof!"
```

### Composition
```python
class Engine:
    def start(self):
        print("Engine started")

class Car:
    def __init__(self):
        self.engine = Engine()  # Car HAS-AN Engine
    
    def start(self):
        self.engine.start()  # Delegate to engine

car = Car()
car.start()  # "Engine started"
```

---

## Abstract Base Classes

```python
from abc import ABC, abstractmethod

class Shape(ABC):
    @abstractmethod
    def area(self):
        """Must be implemented by subclasses"""
        pass
    
    @abstractmethod
    def perimeter(self):
        """Must be implemented by subclasses"""
        pass

class Rectangle(Shape):
    def __init__(self, width, height):
        self.width = width
        self.height = height
    
    def area(self):
        return self.width * self.height
    
    def perimeter(self):
        return 2 * (self.width + self.height)

# shape = Shape()  # ✗ Error: Can't instantiate abstract class
rect = Rectangle(5, 3)  # ✓ Works
```

---

## Type Checking

```python
class Animal:
    pass

class Dog(Animal):
    pass

dog = Dog()

# Check type
type(dog)                    # <class 'Dog'>
type(dog) == Dog             # True

# Check instance (includes inheritance)
isinstance(dog, Dog)         # True
isinstance(dog, Animal)      # True (Dog inherits from Animal)
isinstance(dog, object)      # True (everything inherits from object)

# Check subclass
issubclass(Dog, Animal)      # True
issubclass(Dog, Dog)         # True
```

---

## Quick Tips

1. **Use `self`** for instance methods (refers to the instance)
2. **Use `cls`** for class methods (refers to the class)
3. **Private attributes**: Use `__` prefix (e.g., `__private`)
4. **Protected attributes**: Use `_` prefix (e.g., `_protected`)
5. **Always call `super().__init__()`** in child class constructors
6. **Use `@property`** for getters, `@property_name.setter` for setters
7. **Use ABC** when you want to enforce method implementation
8. **Composition over Inheritance**: Prefer HAS-A over IS-A when possible
9. **DRY Principle**: Don't Repeat Yourself
10. **Single Responsibility**: Each class should do one thing well

---

## Common Errors and Solutions

### AttributeError: 'X' object has no attribute 'Y'
- **Cause**: Trying to access non-existent attribute
- **Solution**: Check spelling, ensure attribute is initialized in `__init__`

### TypeError: Can't instantiate abstract class
- **Cause**: Trying to create instance of ABC without implementing abstract methods
- **Solution**: Implement all `@abstractmethod` methods in subclass

### NameError: name 'self' is not defined
- **Cause**: Forgot `self` parameter in method definition
- **Solution**: Add `self` as first parameter: `def method(self):`

### TypeError: __init__() missing required positional argument
- **Cause**: Not passing required arguments when creating instance
- **Solution**: Pass all required parameters: `obj = MyClass(required_param)`

---

**Keep this guide handy while working on the tasks!**
