
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

}

