
import java.time.LocalDate;
/**
 * A class that represents an individual assignment in the Student Productivity Tracker.
 * Stores assignment name, course, due date, estimated study hours, actual study hours,
 * and completion status.
 *
 * Purdue University -- CS19300 -- Fall 2026 -- Final Project -- Student Productivity Tracker
 *
 * @author Kim Eagle
 * @version 1.0
 */
public class Assignment {

    // Assignment details that remain unchanged after they're created
    private final String name;
    private final String course;
    private final LocalDate dueDate;
    private final double estimatedHours;
    // Aspects of assignments that will change when the user makes progress
    private double actualHours;
    private boolean completed;

    /**
     * Creates a new incomplete assignment with no recorded study hours.
     *
     * @param name the name of the assignment
     * @param course the name of the course associated with the assignment
     * @param dueDate the date the assignment is due
     * @param estimatedHours the amount of time the user thinks the assignment will take to complete
     */
    public Assignment(String name, String course,
                      LocalDate dueDate, double estimatedHours) {
        this.name = name;
        this.course = course;
        this.dueDate = dueDate;
        this.estimatedHours = estimatedHours;
        this.actualHours = 0.0;
        this.completed = false;
    }

    /**
     * @return the name of the assignment
     */
    public String getName() {
        return name;
    }

    /**
     * @return the course associated with the assignment
     */
    public String getCourse() {
        return course;
    }

    /**
     * @return the assignment's due date
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * @return the estimated study hours
     */
    public double getEstimatedHours() {
        return estimatedHours;
    }

    /**
     * @return the total recorded study hours
     */
    public double getActualHours() { return actualHours; }

    /**
     * @return true if the assignment is completed, otherwise false
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Updated the assignment's completion status to completed.
     */
    public void markComplete() {
        completed = true;
    }
    /**
     * Accumulates actual study hours so the user can record
     * multiple study sessions for the same assignment.
     *
     * @param hours the additional study hours to record
     */
    public void addActualHours(double hours) { actualHours += hours; }

    /**
     * Formats the assignment's information to display in the terminal.
     *
     * @return a formatted string containing assignment details and progress
     */
    @Override
    public String toString() {
        String status;

        if (completed) {
            status = "Completed";
        } else {
            status = "Incomplete";
        }

        return "Assignment: " + name
                + "\nCourse: " + course
                + "\nDue Date: " + dueDate
                + "\nEstimated Hours: " + estimatedHours
                + "\nActual Hours: " + actualHours
                + "\nStatus: " + status;
    }

}
