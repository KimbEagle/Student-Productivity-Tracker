
import java.util.ArrayList;

public class AssignmentManager {

    private final ArrayList<Assignment> assignments = new ArrayList<>();

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public int getAssignmentCount() {
        return assignments.size();
    }

    public void viewAssignments() {
        if (assignments.isEmpty()) {
            System.out.println("No assignments have been added yet.");
            return;
        }

        System.out.println("\n YOUR ASSIGNMENTS ");

        // Display numbers start at one so users can
        // identify and select assignments later on
        for (int i = 0; i < assignments.size(); i++) {
            System.out.println("\nAssignment #" + (i + 1));
            System.out.println(assignments.get(i));
        }

        System.out.println("\nTotal assignments: " + assignments.size());
    }

    public void completeAssignment(int assignmentNumber) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            System.out.println("Invalid assignment number.");
            return;
        }

        // Numbers for users start at 1, but ArrayList starts at 0 so we subtract one to get the correct index.
        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (assignment.isCompleted()) {
            System.out.println("This assignment is already completed.");
            return;
        }

        assignment.markComplete();
        System.out.println("Assignment marked as completed successfully!");
    }

    public void recordStudyHours(int assignmentNumber, double hours) {
        if (assignmentNumber < 1 || assignmentNumber > assignments.size()) {
            System.out.println("Invalid assignment number.");
            return;
        }

        if (!Double.isFinite(hours) || hours <= 0) {
            System.out.println("Study hours must be a positive number.");
            return;
        }

        Assignment assignment = assignments.get(assignmentNumber - 1);

        if (!Double.isFinite(assignment.getActualHours() + hours)) {
            System.out.println("Total study hours are too large.");
            return;
        }

        assignment.addActualHours(hours);
        System.out.println("Study hours recorded successfully!");
        System.out.println("Total actual hours: " + assignment.getActualHours());
    }


}

