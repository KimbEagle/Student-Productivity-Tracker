
import java.time.LocalDate;

public class Assignment {

    private final String name;
    private final String course;
    private final LocalDate dueDate;
    private final double estimatedHours;
    private double actualHours;
    private boolean completed;

    public Assignment(String name, String course,
                      LocalDate dueDate, double estimatedHours) {
        this.name = name;
        this.course = course;
        this.dueDate = dueDate;
        this.estimatedHours = estimatedHours;
        this.actualHours = 0.0;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public double getActualHours() { return actualHours; }

    public boolean isCompleted() {
        return completed;
    }

    public void markComplete() {
        completed = true;
    }

    public void addActualHours(double hours) { actualHours += hours; }

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
