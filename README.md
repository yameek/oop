# OOP Exercises — Go, Java and Python

This repository contains sets of Object-Oriented Programming (OOP) learning exercises implemented in three languages: Go, Java and Python. Each language folder contains a set of small tasks (with filenames prefixed by `task_XX_...`) and a corresponding `solutions/` folder with sample solutions.

This top-level README explains the repository layout, quick run commands, conventions, and how to contribute.

## Repository layout

- `golangOOP/` — Go exercises
  - `task_01_structs_methods.go` ... `task_10_design_patterns.go` — tasks
  - `solutions/` — one solution file per task
  - `START_HERE.go` — recommended entrypoint for a quick run or demo
  - `create_solutions.sh` — helper script used for generating or aggregating solution files (inspect before running)

- `javaOOP/` — Java exercises
  - `Task01BasicClasses.java` ... `Task10DesignPatterns.java` — tasks
  - `solutions/` — solution files (same idea as other languages)
  - `START_HERE.java` — recommended entrypoint

- `pythonOOP/` — Python exercises
  - `task_01_basic_classes.py` ... `task_10_design_patterns.py` — tasks
  - `solutions/` — solution files
  - `START_HERE.py` — recommended entrypoint

## Purpose

The goal is to provide small, focused exercises to learn core OOP concepts in multiple languages. Each task is intentionally small so you can implement, run, and iterate quickly.

Use the `solutions/` folder for reference after you've attempted a task.

## Quick start — requirements

- Go: a recent Go toolchain (go 1.18+ recommended)
- Java: JDK 11+ (javac/java on PATH)
- Python: Python 3.8+

You only need the language runtimes/compilers to run the examples.

## How to run

Run the example entrypoints or individual task files from the repository root.

Go (run the starter or a specific task):

```bash
go run golangOOP/START_HERE.go
# or run a specific task
go run golangOOP/task_01_structs_methods.go
```

If you want to build a binary:

```bash
go build -o bin/golang_oop_demo ./golangOOP
./bin/golang_oop_demo
```

Java (compile & run starter or all tasks):

```bash
javac javaOOP/START_HERE.java
java -cp javaOOP START_HERE

# or compile everything and run a task class with a main
javac javaOOP/*.java
java -cp javaOOP Task01BasicClasses
```

Python (run starter or a specific task):

```bash
python3 pythonOOP/START_HERE.py
# or a specific task
python3 pythonOOP/task_01_basic_classes.py
```

Notes:
- Some Java examples may expect package declarations. If a file contains a `package` line, compile/run from the repository root or follow the package directory structure.
- Inspect `START_HERE.*` files in each folder for a simple demo harness.

## Conventions

- Task files are named with a numeric prefix so they sort naturally (e.g., `task_01_...`, `Task01...`).
- `solutions/` mirrors the tasks and provides reference implementations.
- Keep each task single-responsibility: small, focused on one concept.

## Contributing

1. Create a new task file following the naming convention.
2. Add a small README or comment block at the top describing the learning objective.
3. Add your reference solution to the language's `solutions/` folder (optional if you want a reviewer to provide one).
4. Open an issue or a PR with a brief description of the task and any special run instructions.

## Safety and inspection

- Inspect `create_solutions.sh` (in `golangOOP/`) before running it — it is a shell script that may modify files or aggregate content.

## Troubleshooting

- If a run fails with a compile/runtime error: first check that you are using the correct runtime version for the language. Then open the task file and read top comments for assumptions.
- For Java, ensure `CLASSPATH` or `-cp` points to the `javaOOP` directory when running compiled classes.

## Next steps / Ideas

- Add small unit test harnesses for each language (Go tests, JUnit for Java, pytest for Python).
- Add CI to run smoke tests on each `START_HERE.*` file.

---

If you'd like, I can also:

- Add a lightweight CI workflow to run Go/Java/Python starters on push.
- Add small unit tests and a script to run all starters.

Tell me which follow-up you'd like and I'll implement it.
