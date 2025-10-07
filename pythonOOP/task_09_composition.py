"""
TASK 9: Composition and Aggregation
====================================
Difficulty: Advanced

Learn about: Composition, Aggregation, "Has-a" relationships

PROBLEM:
--------
Create a computer system using composition.

Requirements:
1. Create a class 'CPU' with:
   - Attributes: brand, model, cores, speed_ghz
   - Method: process() - prints processing message

2. Create a class 'RAM' with:
   - Attributes: brand, capacity_gb, speed_mhz
   - Method: load_data() - prints loading message

3. Create a class 'Storage' with:
   - Attributes: storage_type (SSD/HDD), capacity_gb, read_speed, write_speed
   - Method: save_data() - prints saving message
   - Method: read_data() - prints reading message

4. Create a class 'GPU' with:
   - Attributes: brand, model, vram_gb
   - Method: render() - prints rendering message

5. Create a class 'Computer' that uses composition:
   - Attributes: brand, model
   - Components: cpu (CPU object), ram (RAM object), storage (Storage object), gpu (GPU object - optional)
   - Method: boot() - simulates computer booting (calls relevant component methods)
   - Method: run_program(program_name) - simulates running a program
   - Method: upgrade_ram(new_ram) - replaces RAM
   - Method: add_storage(additional_storage) - adds storage to a list
   - Method: get_specs() - displays all component specifications

6. Create a class 'GamingComputer' inheriting from Computer:
   - Must have a GPU (not optional)
   - Additional attribute: rgb_lighting (boolean)
   - Method: play_game(game_name) - uses GPU for rendering
   - Override get_specs() to include gaming-specific info

TEST YOUR CODE:
---------------
- Create individual components
- Create a regular Computer with those components
- Create a GamingComputer
- Test all methods
- Upgrade components
- Show that composition works (computer has components)
"""

# Write your solution below:

