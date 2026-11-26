
import java.util.ArrayList;

public class AssignmentManager {

    private ArrayList<Assignment> assignments = new ArrayList<>();

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public int getAssignmentCount() {
        return assignments.size();
    }
}

