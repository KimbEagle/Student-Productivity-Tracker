
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

public class AssignmentManager {

    private final ArrayList<Assignment> assignments = new ArrayList<>();
    private static final String FILE_NAME = "assignments.csv";

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public int getAssignmentCount() {
        return assignments.size();
    }

    public void viewAssignments() {
        if (assignments.isEmpty()) {
            IO.println("No assignments have been added yet.");
            return;
        }

        IO.println("\n YOUR ASSIGNMENTS ");

        // Display numbers start at one so users can
        // identify and select assignments later on
        for (int i = 0; i < assignments.size(); i++) {
            IO.println("\nAssignment #" + (i + 1));
            IO.println(assignments.get(i));
        }

        IO.println("\nTotal assignments: " + assignments.size());
    }

    public void completeAssignment(int assignmentNumber) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }

        // Numbers for users start at 1, but ArrayList starts at 0 so we subtract one to get the correct index.
        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (assignment.isCompleted()) {
            IO.println("This assignment is already completed.");
            return;
        }

        assignment.markComplete();
        IO.println("Assignment marked as completed successfully!");
    }

    public void recordStudyHours(int assignmentNumber, double hours) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }

        if (!Double.isFinite(hours) || hours <= 0) {
            IO.println("Study hours must be a positive number.");
            return;
        }

        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (!Double.isFinite(assignment.getActualHours() + hours)) {
            IO.println("Total study hours are too large.");
            return;
        }

        assignment.addActualHours(hours);
        IO.println("Study hours recorded successfully!");
        IO.println("Total actual hours: " + assignment.getActualHours());
    }

    public void deleteAssignment(int assignmentNumber) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }

        Assignment assignment = assignments.remove(assignmentNumber - 1);

        IO.println(assignment.getName() + " deleted successfully!");
    }

    public void viewProductivityStatistics() {
        // Preventing division by zero when calculating completion percentage.
        if (assignments.isEmpty()) {
            IO.println("No assignments available to calculate statistics.");
            return;
        }

        int totalAssignments = assignments.size();
        int completedAssignments = 0;
        double totalEstimatedHours = 0.0;
        double totalActualHours = 0.0;

        // Calculate statistics using current assignment data so results
        // reflect any changes made by the user.
        for (Assignment assignment : assignments) {
            if (assignment.isCompleted()) {
                completedAssignments++;
            }

            totalEstimatedHours += assignment.getEstimatedHours();
            totalActualHours += assignment.getActualHours();
        }

        int incompleteAssignments = totalAssignments - completedAssignments;

        // Casting to a double to avoid truncating the result through integer division
        double completionPercentage =
                (double) completedAssignments / totalAssignments * 100;

        IO.println("\nPRODUCTIVITY STATISTICS");
        IO.println("Total assignments: " + totalAssignments);
        IO.println("Completed assignments: " + completedAssignments);
        IO.println("Incomplete assignments: " + incompleteAssignments);
        IO.println("Completion percentage: "
                + String.format("%.1f", completionPercentage) + "%");
        IO.println("Total estimated hours: "
                + String.format("%.2f", totalEstimatedHours));
        IO.println("Total actual hours: "
                + String.format("%.2f", totalActualHours));
    }


    public boolean saveAssignments() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            writer.write("Name,Course,DueDate,EstimatedHours,ActualHours,Completed");
            writer.newLine();

            for (Assignment assignment : assignments) {
                writer.write(
                        escapeCSV(assignment.getName()) + "," +
                                escapeCSV(assignment.getCourse()) + "," +
                                assignment.getDueDate() + "," +
                                assignment.getEstimatedHours() + "," +
                                assignment.getActualHours() + "," +
                                assignment.isCompleted()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            IO.println("Error saving assignments: " + e.getMessage());
            return false;
        }

        IO.println("Assignments saved successfully!");
        return true;
    }

    public boolean loadAssignments() {
        ArrayList<Assignment> loadedAssignments = new ArrayList<>();

        if (java.nio.file.Files.notExists(java.nio.file.Path.of(FILE_NAME))) {
            return true;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String header = reader.readLine();

            if (!"Name,Course,DueDate,EstimatedHours,ActualHours,Completed"
                    .equals(header)) {
                throw new IllegalArgumentException("Invalid CSV header.");
            }

            String line;

            while ((line = reader.readLine()) != null) {
                ArrayList<String> values = parseCSVLine(line);

                if (values.size() != 6) {
                    throw new IllegalArgumentException("Invalid assignment data.");
                }

                String name = values.get(0);
                String course = values.get(1);
                LocalDate dueDate = LocalDate.parse(values.get(2));
                double estimatedHours = Double.parseDouble(values.get(3));
                double actualHours = Double.parseDouble(values.get(4));
                String completed = values.get(5);

                if (name.isBlank() || course.isBlank()
                        || !Double.isFinite(estimatedHours)
                        || estimatedHours <= 0
                        || !Double.isFinite(actualHours)
                        || actualHours < 0
                        || (!completed.equals("true")
                        && !completed.equals("false"))) {
                    throw new IllegalArgumentException("Invalid assignment values.");
                }

                Assignment assignment = new Assignment(
                        name, course, dueDate, estimatedHours);

                assignment.addActualHours(actualHours);

                if (completed.equals("true")) {
                    assignment.markComplete();
                }

                loadedAssignments.add(assignment);
            }

        } catch (IOException | IllegalArgumentException
                 | java.time.format.DateTimeParseException e) {
            IO.println("Error loading assignments: " + e.getMessage());
            return false;
        }

        // Replace existing data only after the entire file loads successfully.
        assignments.clear();
        assignments.addAll(loadedAssignments);

        IO.println("Assignments loaded successfully!");
        IO.println("Total assignments: " + assignments.size());
        return true;
    }



    private String escapeCSV(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private ArrayList<String> parseCSVLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == '"') {
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    currentValue.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (character == ',' && !insideQuotes) {
                values.add(currentValue.toString());
                currentValue.setLength(0);
            } else {
                currentValue.append(character);
            }
        }

        if (insideQuotes) {
            throw new IllegalArgumentException("Unclosed CSV quotation marks.");
        }

        values.add(currentValue.toString());
        return values;
    }



}

