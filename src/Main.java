
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {

     void main() {

        Scanner scanner = new Scanner(System.in);
        AssignmentManager manager = new AssignmentManager();
        boolean running = true;

        while (running) {

            System.out.println("\n YOUR STUDENT PRODUCTIVITY TRACKER");
            System.out.println("1. Add Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Mark Assignment Complete");
            System.out.println("4. Record Study Hours");
            System.out.println("5. Save and Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    try {
                        System.out.print("Enter assignment name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter course name: ");
                        String course = scanner.nextLine();

                        System.out.print("Enter due date (YYYY-MM-DD): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

                        System.out.print("Enter estimated hours: ");
                        double estimatedHours = Double.parseDouble(scanner.nextLine());

                        if (name.isBlank() || course.isBlank()) {
                            System.out.println("Name and course cannot be empty.");
                        } else if (!Double.isFinite(estimatedHours) || estimatedHours <= 0) {
                            System.out.println("Estimated hours must be a positive number.");
                        } else {
                            Assignment assignment = new Assignment(
                                    name, course, dueDate, estimatedHours
                            );

                            manager.addAssignment(assignment);

                            System.out.println("Assignment added successfully!");
                            System.out.println("Total assignments: " + manager.getAssignmentCount());
                        }

                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date. Please use YYYY-MM-DD.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid hours. Please enter a number.");
                    }

                    break;


                case "2":
                    manager.viewAssignments();
                    break;


                case "3":
                    if (manager.getAssignmentCount() == 0) {
                        System.out.println("No assignments are available to complete.");
                        break;
                    }

                    manager.viewAssignments();

                    System.out.print("\nEnter assignment number: ");

                    try {
                        int assignmentNumber = Integer.parseInt(scanner.nextLine());
                        manager.completeAssignment(assignmentNumber);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid whole number.");
                    }

                    break;



                case "4":
                    if (manager.getAssignmentCount() == 0) {
                        System.out.println("No assignments are available to record hours for.");
                        break;
                    }

                    manager.viewAssignments();

                    try {
                        System.out.print("\nEnter assignment number: ");
                        int assignmentNumber = Integer.parseInt(scanner.nextLine());

                        if (assignmentNumber < 1 ||
                                assignmentNumber > manager.getAssignmentCount()) {
                            System.out.println("Invalid assignment number.");
                            break;
                        }

                        System.out.print("Enter hours spent studying: ");
                        double hours = Double.parseDouble(scanner.nextLine());

                        manager.recordStudyHours(assignmentNumber, hours);
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }

                    break;


                case "5":
                    if (manager.saveAssignments()) {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}

