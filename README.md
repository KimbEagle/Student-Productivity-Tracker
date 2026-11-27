# Student Productivity Tracker

A program that allows users to enter information about assignments and due dates, and organizes them and gives information about productivity.

## The Problem

For students, it becomes difficult to keep track of assignments and time management when tasks for each class pile up. Although traditional to-do lists and calendars help organize assignments, they don't provide valuable insight into workload, completion rates, or productivity and habits. Often times students will end up missing easy assignments because, without a clear picture of assignment completion rates and study habits, it can be difficult to recognize patterns, manage time effectively, and stay on top of coursework.

## How It Works

- Users navigate a main menu with options to add assignments, view assignments, mark assignments complete, record study hours, or exit.
- When adding an assignment, users enter its name, course, due date, and estimated study time.
- When viewing assignments, users see all entered assignments, deadlines, estimated hours, actual hours, and completion statuses.
- Users can mark unfinished assignments as completed.
- Users can record actual study hours across multiple study sessions for each assignment.
- After each action, users return to the main menu to select another option.
- Choosing Save and Exit saves all assignments to a local CSV file and then closes the program.
- Previously existing or entered assignments are now loaded from a local CSV file on program start.
- Users can delete unwanted or canceled assignments after confirming their selection.

![System diagram](docs/System-Diagram.png)

## Tech Stack

- **Language:** Java 26
- **Libraries:** ArrayList, LocalDate, Scanner, Java IO
- **Storage:** In-memory ArrayList and local CSV file
- **Development tools:** IntelliJ IDEA, Git, GitHub
- **External dependencies:** None

## Setup


### Prerequisites

- Install Git.
- Install the Java Development Kit (JDK 26).
- A terminal or Java IDE such as IntelliJ IDEA.


### Download the Project
```bash
git clone https://github.com/KimbEagle/Student-Productivity-Tracker.git
cd Student-Productivity-Tracker
```


### Compile and Run

From the project's directory, compile the Java files:

```bash
javac -d out src/*.java
```

Run the program:

```bash
java -cp out Main
```

The program will display an interactive menu in the terminal.



## Usage

The program displays an interactive main menu where users
can select an option by entering its corresponding number.

### Currently Implemented
- Add assignments with a name, course, due date, and estimated study time.
- Validate assignment information and handle invalid input.
- View all assignments, deadlines, and completion statuses.
- Exit the program.
- Mark assignments as completed.
- Record actual study hours across multiple study sessions.
- Save assignment information to a local CSV file.
- Automatically load previously saved assignments when the program starts.
- Delete assignments with confirmation.

### In Development
- Load previously saved assignments from a CSV file.
- Calculate and display assignment completion rates.

Assignment data is stored locally in assignments.csv.
The program automatically loads saved assignments on startup and saves
updated information when the user selected Save and Exit.


## Roadmap

### MVP Features
- [x] Set up GitHub repository and Java project
- [x] Create Assignment class with assignment attributes
- [x] Implement assignment storage using ArrayList
- [x] Implement assignment creation with input validation
- [x] Display assignments and their due dates
- [x] Allow users to mark assignments as completed
- [x] Allow users to record actual time spent on assignments
- [x] Save assignment data to CSV file
- [x] Load assignments from CSV file on program start

### Additional Improvements
- [x] Delete unwanted or canceled assignments

### Schedule Features
- [ ] Calculate and display basic productivity statistics
- [ ] Filter assignments by course or completion status
- [ ] Display weekly or class-specific productivity trends
- [ ] Display productivity using graphs and charts
- [ ] Develop a graphical user interface


## Author

Kim Eagle, https://github.com/KimbEagle.
