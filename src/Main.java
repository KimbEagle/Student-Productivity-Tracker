
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {

     void main() {

         Scanner scanner = new Scanner(System.in);
         AssignmentManager manager = new AssignmentManager();

         if (!manager.loadAssignments()) {
             IO.println("Program stopped to protect existing saved data.");
             scanner.close();
             return;
         }

         boolean running = true;

        while (running) {

            IO.println("\n YOUR STUDENT PRODUCTIVITY TRACKER");
            IO.println("1. Add Assignment");
            IO.println("2. View Assignments");
            IO.println("3. Mark Assignment Complete");
            IO.println("4. Record Study Hours");
            IO.println("5. Delete Assignment");
            IO.println("6. View Productivity Statistics");
            IO.println("7. Save and Exit");

            IO.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    try {
                        IO.print("Enter assignment name: ");
                        String name = scanner.nextLine();

                        IO.print("Enter course name: ");
                        String course = scanner.nextLine();

                        IO.print("Enter due date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

                        IO.print("Enter estimated hours: ");
                        double estimatedHours = Double.parseDouble(scanner.nextLine());

                        if (name.isBlank() || course.isBlank()) {
                            IO.println("Name and course cannot be empty.");
                        } else if (!Double.isFinite(estimatedHours) || estimatedHours <= 0) {
                            IO.println("Estimated hours must be a positive number.");
                        } else {
                            Assignment assignment = new Assignment(
                                    name, course, dueDate, estimatedHours
                            );

                            manager.addAssignment(assignment);

                            IO.println("Assignment added successfully!");
                            IO.println("Total assignments: " + manager.getAssignmentCount());
                        }

                    } catch (DateTimeParseException e) {
                        IO.println("Invalid date. Please use YYYY-MM-DD.");
                    } catch (NumberFormatException e) {
                        IO.println("Invalid hours. Please enter a number.");
                    }

                    break;


                case "2":
                    manager.viewAssignments();
                    break;


                case "3":
                    if (manager.getAssignmentCount() == 0) {
                        IO.println("No assignments are available to complete.");
                        break;
                    }

                    manager.viewAssignments();

                    IO.print("\nEnter assignment number: ");

                    try {
                        int assignmentNumber = Integer.parseInt(scanner.nextLine());
                        manager.completeAssignment(assignmentNumber);
                    } catch (NumberFormatException e) {
                        IO.println("Please enter a valid whole number.");
                    }

                    break;



                case "4":
                    if (manager.getAssignmentCount() == 0) {
                        IO.println("No assignments are available to record hours for.");
                        break;
                    }

                    manager.viewAssignments();

                    try {
                        IO.print("\nEnter assignment number: ");
                        int assignmentNumber = Integer.parseInt(scanner.nextLine());

                        if (assignmentNumber < 1 ||
                                assignmentNumber > manager.getAssignmentCount()) {
                            IO.println("Invalid assignment number.");
                            break;
                        }

                        IO.print("Enter hours spent studying: ");
                        double hours = Double.parseDouble(scanner.nextLine());

                        manager.recordStudyHours(assignmentNumber, hours);
                    } catch (NumberFormatException e) {
                        IO.println("Please enter a valid number.");
                    }

                    break;

                case "5":
                    if (manager.getAssignmentCount() == 0) {
                        IO.println("No assignments to delete.");
                        break;
                    }

                    manager.viewAssignments();

                    try {
                        IO.print("Enter assignment number to delete: ");
                        int assignmentNumber = Integer.parseInt(scanner.nextLine());

                        if (assignmentNumber < 1
                                || assignmentNumber > manager.getAssignmentCount()) {
                            IO.println("Invalid assignment number.");
                            break;
                        }

                        IO.print("Are you sure you want to delete this assignment? (y/n): ");
                        String confirmation = scanner.nextLine().trim();

                        if (confirmation.equalsIgnoreCase("y")) {
                            manager.deleteAssignment(assignmentNumber);
                        } else {
                            IO.println("Deletion canceled.");
                        }

                    } catch (NumberFormatException e) {
                        IO.println("Please enter a valid assignment number.");
                    }

                    break;

                case "6":
                    manager.viewProductivityStatistics();
                    break;

                case "7":
                    if (manager.saveAssignments()) {
                        IO.println("Goodbye!");
                        running = false;
                    }
                    break;

                default:
                    IO.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}

