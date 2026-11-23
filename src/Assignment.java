
import java.time.LocalDate;

public class Assignment {

    private String name;
    private String course;
    private LocalDate dueDate;
    private double estimatedHours;
    private boolean completed;

    public Assignment(String name, String course,
                      LocalDate dueDate, double estimatedHours) {
        this.name = name;
        this.course = course;
        this.dueDate = dueDate;
        this.estimatedHours = estimatedHours;
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

    public boolean isCompleted() {
        return completed;
    }

    public void markComplete() {
        completed = true;
    }

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
                + "\nStatus: " + status;
    }

}
