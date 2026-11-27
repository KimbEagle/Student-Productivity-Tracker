
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
/**
 * A class that contains methods to add, view, complete, record study hours for, delete,
 * save, and load assignments as well as to view the user's productivity statistics which is the schedule feature
 *
 *
 * Purdue University -- CS19300 -- Fall 2026 -- Final Project -- Student Productivity Tracker
 *
 * @author Kim Eagle
 * @version 1.0
 */
public class AssignmentManager {

    // Using an ArrayList so the collection can increase and decrease in size
    // as assignments are added and deleted by the user.
    private final ArrayList<Assignment> assignments = new ArrayList<>();
    private static final String FILE_NAME = "assignments.csv";

    /**
     * Adds an existing Assignment to the ArrayList of assignments.
     *
     * @param assignment the assignment to add to the ArrayList
     */
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
    /**
     * Gets the number of assignments currently stored in the ArrayList.
     *
     * @return the total number of assignments
     */
    public int getAssignmentCount() {
        return assignments.size();
    }
    /**
     * Shows all assignments that the user has entered over time.
     * Will indicate if no assignments exist yet.
     */
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
    /**
     * Marks the indicated assignment as completed.
     *
     * @param assignmentNumber the assignment the user wants to mark as completed
     */
    public void completeAssignment(int assignmentNumber) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }

        // Converting user's assignment number to a zero-based ArrayList index.
        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (assignment.isCompleted()) {
            IO.println("This assignment is already completed.");
            return;
        }

        assignment.markComplete();
        IO.println("Assignment marked as completed successfully!");
    }
    /**
     * Adds actual study hours to time spent studying on a particular assignment.
     *
     * @param assignmentNumber the assignment the user wants to add hours to
     * @param hours the number of hours the user wants to add
     */
    public void recordStudyHours(int assignmentNumber, double hours) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }

        if (!Double.isFinite(hours) || hours <= 0) {
            IO.println("Study hours must be a positive number.");
            return;
        }
        // Converting user's assignment number to a zero-based ArrayList index.
        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (!Double.isFinite(assignment.getActualHours() + hours)) {
            IO.println("Total study hours are too large.");
            return;
        }
        // Accumulating study hours across multiple study sessions
        // instead of replacing previously recorded hours.
        assignment.addActualHours(hours);
        IO.println("Study hours recorded successfully!");
        IO.println("Total actual hours: " + assignment.getActualHours());
    }
    /**
     * Removes the selected assignment from the ArrayList.
     * The removal will then be reflected in the CSV file when assignments are saved during exiting.
     *
     * @param assignmentNumber the assignment number selected by the user
     */
    public void deleteAssignment(int assignmentNumber) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            IO.println("Invalid assignment number.");
            return;
        }
        // Converting user's assignment number to a zero-based ArrayList index.
        Assignment assignment = assignments.remove(assignmentNumber - 1);

        IO.println(assignment.getName() + " deleted successfully!");
    }
    /**
     * Displays assignment completion statistics and total estimated versus actual
     * hours spent studying.
     *
     */
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
    /**
     * Saves the current assignments to a local CSV file, assignments.csv.
     *
     * @return true if saving succeeds, otherwise false if an error occurs
     */
    public boolean saveAssignments() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_NAME))) {

            // Creating the columns that users can view in their CSV file
            writer.write("Name,Course,DueDate,EstimatedHours,ActualHours,Completed");
            writer.newLine();

            // Writing all assignment information into the CSV file, separating by commas
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
    /**
     * Loads previously saved assignments from the CSV file.
     * If a file is missing, it is treated as a new session with no previously saved assignments.
     *
     * @return true if the loading succeeds or no save file exists, otherwise false if loading fails
     */
    public boolean loadAssignments() {
        // Store loaded assignments separately to avoid replacing existing data
        // in case an error occurs while reading the CSV file
        ArrayList<Assignment> loadedAssignments = new ArrayList<>();

        // If the file does not already exist, return true so later on a new file can be created
        if (java.nio.file.Files.notExists(java.nio.file.Path.of(FILE_NAME))) {
            return true;
        }

        // Ensuring that the existing CSV file contains the correct header columns
        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String header = reader.readLine();

            if (!"Name,Course,DueDate,EstimatedHours,ActualHours,Completed"
                    .equals(header)) {
                throw new IllegalArgumentException("Invalid CSV header.");
            }

            String line;
            // Ensuring that existing assignment data is properly formatted in the CSV file
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

                // If properly formatted, checking if individual assignment values are valid
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
    /**
     * Formats a string to store in CSV by escaping the existing quotation marks and
     * also enclosing the string in quotation marks. Prevents commas within assignment names or courses
     * from separating fields.
     *
     * @param value the string to format
     * @return the escaped string to store in CSV
     */
    private String escapeCSV(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    /**
     * Separates a CSV line into individual field values while preserving
     * commas and quotation marks within quoted text.
     *
     * @param line the line read from the CSV file
     * @return an ArrayList containing the individual field values
     * @throws IllegalArgumentException if quotation marks are left unclosed
     */
    private ArrayList<String> parseCSVLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        // Tracking whether characters are inside of a quoted field
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == '"') {
                // Two consecutive quotation marks inside a quoted field
                // will only represent one literal quotation mark.
                if (insideQuotes && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    currentValue.append('"');
                    // Skip the second quotation mark since it is already considered
                    i++;
                } else {
                    // Tracking the beginning or the end of a quoted field.
                    insideQuotes = !insideQuotes;
                }
                // Only treating commas outside of quoted text as field separators
            } else if (character == ',' && !insideQuotes) {
                values.add(currentValue.toString());
                currentValue.setLength(0);
            } else {
                currentValue.append(character);
            }
        }
        // Rejecting incomplete quoted fields to avoid loading data that is invalid
        if (insideQuotes) {
            throw new IllegalArgumentException("Unclosed CSV quotation marks.");
        }
        // Store the final field since it is not followed by a comma
        values.add(currentValue.toString());
        return values;
    }



}

