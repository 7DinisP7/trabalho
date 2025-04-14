# HomeAway From Home

## Project Description
The goal of this project is to develop an application that supports international students in a campus-oriented town by providing location-based information about useful services.

### Features
- Add and manage services: Eating, Lodging, Leisure.
- Add and manage students: Bookish, Outgoing, Thrifty.
- Evaluate services and rank them.
- Allow students to move between services and locate relevant services.

## Development
This project is developed using Java and follows Object-Oriented Programming principles. The project does not use Java's standard collection framework but relies on custom implementations.

## Commands
The application supports the following commands:
- `help`: Displays the list of supported commands.
- `exit`: Terminates the program.
- `bounds`: Defines the system's bounding rectangle.
- `eating`, `lodging`, `leisure`: Adds new services to the system.
- `services`: Lists all services.
- `student`, `students`, `leave`: Manage students.
- `go`, `move`, `where`, `visited`: Tracks and changes student locations.
- `star`, `ranking`, `ranked`: Evaluates and ranks services.
- `find`: Locates the most relevant service for a student.

### How to Run
1. Compile the code using `javac`.
2. Run the program using `java Main`.

## Development Progress
The system is being developed incrementally, starting with the basic command interpreter.