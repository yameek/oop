"""
SOLUTION 9: Composition and Aggregation
========================================

CONCEPTS EXPLAINED:
-------------------
1. Composition: "Has-a" relationship where part cannot exist without whole
2. Aggregation: "Has-a" relationship where part can exist independently
3. Composition vs Inheritance: Use composition when object HAS components
4. Delegation: Object forwards work to its components
5. Favor composition over inheritance (design principle)
6. Parts are created and destroyed with the whole (composition)

"""

class CPU:
    """Represents a computer's CPU"""
    
    def __init__(self, brand, model, cores, speed_ghz):
        """
        Initialize a CPU
        
        Parameters:
        -----------
        brand : str
            CPU manufacturer
        model : str
            CPU model name
        cores : int
            Number of cores
        speed_ghz : float
            Clock speed in GHz
        """
        self.brand = brand
        self.model = model
        self.cores = cores
        self.speed_ghz = speed_ghz
    
    def process(self):
        """Simulate CPU processing"""
        print(f"⚙️  CPU ({self.brand} {self.model}) processing at {self.speed_ghz}GHz...")
    
    def get_specs(self):
        """Return CPU specifications"""
        return f"{self.brand} {self.model} ({self.cores} cores @ {self.speed_ghz}GHz)"


class RAM:
    """Represents computer memory"""
    
    def __init__(self, brand, capacity_gb, speed_mhz):
        """
        Initialize RAM
        
        Parameters:
        -----------
        brand : str
            RAM manufacturer
        capacity_gb : int
            Memory capacity in GB
        speed_mhz : int
            Speed in MHz
        """
        self.brand = brand
        self.capacity_gb = capacity_gb
        self.speed_mhz = speed_mhz
    
    def load_data(self):
        """Simulate loading data into RAM"""
        print(f"💾 RAM ({self.capacity_gb}GB) loading data...")
    
    def get_specs(self):
        """Return RAM specifications"""
        return f"{self.brand} {self.capacity_gb}GB @ {self.speed_mhz}MHz"


class Storage:
    """Represents storage device"""
    
    def __init__(self, storage_type, capacity_gb, read_speed, write_speed):
        """
        Initialize Storage
        
        Parameters:
        -----------
        storage_type : str
            Type of storage (SSD/HDD)
        capacity_gb : int
            Storage capacity in GB
        read_speed : int
            Read speed in MB/s
        write_speed : int
            Write speed in MB/s
        """
        self.storage_type = storage_type
        self.capacity_gb = capacity_gb
        self.read_speed = read_speed
        self.write_speed = write_speed
    
    def save_data(self):
        """Simulate saving data"""
        print(f"💿 {self.storage_type} ({self.capacity_gb}GB) saving data at {self.write_speed}MB/s...")
    
    def read_data(self):
        """Simulate reading data"""
        print(f"📀 {self.storage_type} reading data at {self.read_speed}MB/s...")
    
    def get_specs(self):
        """Return storage specifications"""
        return f"{self.storage_type} {self.capacity_gb}GB (R:{self.read_speed}MB/s, W:{self.write_speed}MB/s)"


class GPU:
    """Represents graphics processing unit"""
    
    def __init__(self, brand, model, vram_gb):
        """
        Initialize GPU
        
        Parameters:
        -----------
        brand : str
            GPU manufacturer
        model : str
            GPU model name
        vram_gb : int
            Video RAM in GB
        """
        self.brand = brand
        self.model = model
        self.vram_gb = vram_gb
    
    def render(self):
        """Simulate GPU rendering"""
        print(f"🎮 GPU ({self.brand} {self.model}) rendering with {self.vram_gb}GB VRAM...")
    
    def get_specs(self):
        """Return GPU specifications"""
        return f"{self.brand} {self.model} ({self.vram_gb}GB VRAM)"


class Computer:
    """
    Computer class using composition
    
    NOTE: This is COMPOSITION - the computer HAS components
    Not inheritance (computer IS-A component) ❌
    Components are part of the computer and managed by it
    """
    
    def __init__(self, brand, model, cpu, ram, storage, gpu=None):
        """
        Initialize a Computer with its components
        
        Parameters:
        -----------
        brand : str
            Computer brand
        model : str
            Computer model
        cpu : CPU
            CPU component (required)
        ram : RAM
            RAM component (required)
        storage : Storage
            Storage component (required)
        gpu : GPU, optional
            GPU component (optional)
        
        NOTE: We compose the computer from components
        The computer owns and manages these components
        """
        self.brand = brand
        self.model = model
        
        # Composition - computer HAS these components
        self.cpu = cpu
        self.ram = ram
        self.storage = storage  # Primary storage
        self.additional_storage = []  # List for additional storage devices
        self.gpu = gpu
    
    def boot(self):
        """
        Boot the computer
        
        NOTE: This method DELEGATES work to components
        """
        print(f"\n{'=' * 60}")
        print(f"🔌 Booting {self.brand} {self.model}...")
        print(f"{'=' * 60}")
        
        # Delegate to components
        self.cpu.process()
        self.ram.load_data()
        self.storage.read_data()
        
        if self.gpu:
            print("🖥️  Initializing graphics...")
            self.gpu.render()
        
        print("✓ System ready!")
        print(f"{'=' * 60}")
    
    def run_program(self, program_name):
        """
        Simulate running a program
        
        Parameters:
        -----------
        program_name : str
            Name of program to run
        """
        print(f"\n▶️  Running {program_name}...")
        self.cpu.process()
        self.ram.load_data()
        
        if self.gpu:
            self.gpu.render()
        
        print(f"✓ {program_name} is running")
    
    def upgrade_ram(self, new_ram):
        """
        Upgrade the RAM
        
        Parameters:
        -----------
        new_ram : RAM
            New RAM module
        """
        old_ram = self.ram
        self.ram = new_ram
        print(f"🔧 RAM upgraded from {old_ram.get_specs()} to {new_ram.get_specs()}")
    
    def add_storage(self, additional_storage):
        """
        Add additional storage device
        
        Parameters:
        -----------
        additional_storage : Storage
            Additional storage to add
        """
        self.additional_storage.append(additional_storage)
        print(f"🔧 Added storage: {additional_storage.get_specs()}")
    
    def get_specs(self):
        """Display complete computer specifications"""
        print(f"\n{'=' * 60}")
        print(f"COMPUTER SPECIFICATIONS")
        print(f"{'=' * 60}")
        print(f"Brand: {self.brand}")
        print(f"Model: {self.model}")
        print(f"\nComponents:")
        print(f"  CPU: {self.cpu.get_specs()}")
        print(f"  RAM: {self.ram.get_specs()}")
        print(f"  Storage: {self.storage.get_specs()}")
        
        if self.additional_storage:
            print(f"\nAdditional Storage:")
            for i, storage in enumerate(self.additional_storage, 1):
                print(f"  {i}. {storage.get_specs()}")
        
        if self.gpu:
            print(f"  GPU: {self.gpu.get_specs()}")
        else:
            print(f"  GPU: Integrated Graphics")
        
        print(f"{'=' * 60}")


class GamingComputer(Computer):
    """
    Gaming computer with enhanced features
    
    NOTE: This combines INHERITANCE (is-a Computer) 
    and COMPOSITION (has components)
    """
    
    def __init__(self, brand, model, cpu, ram, storage, gpu, rgb_lighting=True):
        """
        Initialize a Gaming Computer
        
        NOTE: Gaming computer MUST have a GPU (not optional)
        """
        # Call parent constructor
        super().__init__(brand, model, cpu, ram, storage, gpu)
        
        # Gaming-specific attribute
        self.rgb_lighting = rgb_lighting
        
        # Validate GPU is present
        if not self.gpu:
            raise ValueError("Gaming computer must have a GPU!")
    
    def play_game(self, game_name):
        """
        Play a game
        
        Parameters:
        -----------
        game_name : str
            Name of the game
        """
        print(f"\n{'=' * 60}")
        print(f"🎮 Launching {game_name}...")
        print(f"{'=' * 60}")
        
        # Use all components
        self.cpu.process()
        self.ram.load_data()
        self.gpu.render()
        self.storage.read_data()
        
        if self.rgb_lighting:
            print("🌈 RGB lighting effects activated!")
        
        print(f"✓ Enjoy playing {game_name}!")
        print(f"{'=' * 60}")
    
    def get_specs(self):
        """Override to include gaming-specific information"""
        super().get_specs()
        print(f"\nGaming Features:")
        print(f"  RGB Lighting: {'Yes' if self.rgb_lighting else 'No'}")
        print(f"  Gaming Optimized: Yes")
        print(f"{'=' * 60}")


# ============================================
# TESTING THE CODE
# ============================================

if __name__ == "__main__":
    print("=" * 60)
    print("TESTING COMPOSITION AND AGGREGATION")
    print("=" * 60)
    
    # Create individual components
    print("\n1. Creating computer components:")
    cpu1 = CPU("Intel", "i7-12700K", 12, 3.6)
    ram1 = RAM("Corsair", 16, 3200)
    storage1 = Storage("SSD", 512, 3500, 3000)
    gpu1 = GPU("NVIDIA", "RTX 3070", 8)
    
    print("✓ Created CPU, RAM, Storage, and GPU")
    
    # Create a regular computer using composition
    print("\n2. Creating a regular computer (composition):")
    pc1 = Computer("Dell", "XPS 8950", cpu1, ram1, storage1)
    pc1.get_specs()
    
    # Boot the computer
    print("\n3. Booting the computer:")
    pc1.boot()
    
    # Run a program
    print("\n4. Running programs:")
    pc1.run_program("Microsoft Word")
    pc1.run_program("Chrome Browser")
    
    # Upgrade RAM
    print("\n5. Upgrading RAM:")
    ram2 = RAM("Corsair", 32, 3600)
    pc1.upgrade_ram(ram2)
    
    # Add additional storage
    print("\n6. Adding additional storage:")
    hdd1 = Storage("HDD", 2000, 150, 120)
    ssd2 = Storage("SSD", 1000, 3500, 3000)
    pc1.add_storage(hdd1)
    pc1.add_storage(ssd2)
    
    # Display updated specs
    print("\n7. Updated specifications:")
    pc1.get_specs()
    
    # Create gaming computer
    print("\n8. Creating a gaming computer:")
    gaming_cpu = CPU("AMD", "Ryzen 9 5900X", 12, 3.7)
    gaming_ram = RAM("G.Skill", 32, 3600)
    gaming_storage = Storage("NVMe SSD", 1000, 7000, 5000)
    gaming_gpu = GPU("NVIDIA", "RTX 4080", 16)
    
    gaming_pc = GamingComputer(
        "Custom Build",
        "Ultimate Gaming Rig",
        gaming_cpu,
        gaming_ram,
        gaming_storage,
        gaming_gpu,
        rgb_lighting=True
    )
    
    gaming_pc.get_specs()
    
    # Boot gaming PC
    print("\n9. Booting gaming PC:")
    gaming_pc.boot()
    
    # Play games
    print("\n10. Playing games:")
    gaming_pc.play_game("Cyberpunk 2077")
    gaming_pc.play_game("Call of Duty")
    
    # Test that gaming PC is also a Computer
    print("\n11. Testing inheritance:")
    print(f"gaming_pc is GamingComputer: {isinstance(gaming_pc, GamingComputer)}")
    print(f"gaming_pc is Computer: {isinstance(gaming_pc, Computer)}")
    
    # Regular computer methods work on gaming PC
    gaming_pc.run_program("Video Editor")
    
    # Try to create gaming PC without GPU (should fail)
    print("\n12. Testing GPU requirement for gaming PC:")
    try:
        invalid_gaming_pc = GamingComputer(
            "Invalid",
            "No GPU",
            cpu1,
            ram1,
            storage1,
            None  # No GPU!
        )
    except ValueError as e:
        print(f"✓ Caught error: {e}")
    
    # Demonstrate composition benefits
    print("\n13. Demonstrating composition benefits:")
    print("\nComponents can be reused:")
    
    # Create another computer with some same components
    cpu2 = CPU("Intel", "i5-12600K", 10, 3.7)
    pc2 = Computer("HP", "Pavilion", cpu2, ram1, storage1, gpu1)
    print("✓ Created second computer sharing RAM, Storage, and GPU")
    
    # Components are independent
    print("\nComponents are objects with their own methods:")
    cpu1.process()
    ram1.load_data()
    storage1.save_data()
    gpu1.render()
    
    print("\n" + "=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. Composition: Object HAS components (not IS-A)")
    print("2. Components are passed to constructor or created internally")
    print("3. Delegation: Object forwards work to components")
    print("4. Favor composition over inheritance for flexibility")
    print("5. Components can be swapped or upgraded")
    print("6. Can combine composition AND inheritance")
    print("7. Composition makes code more modular and reusable")
    print("8. Components can be shared between objects")
