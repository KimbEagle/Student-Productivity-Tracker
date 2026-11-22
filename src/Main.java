
import java.util.Scanner;

public class Main {

    public void main() {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("\n YOUR STUDENT PRODUCTIVITY TRACKER");
            System.out.println("1. Add Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Mark Assignment Complete");
            System.out.println("4. View Productivity");
            System.out.println("5. Save and Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Add Assignment selected");
                    break;

                case "2":
                    System.out.println("View Assignments selected");
                    break;

                case "3":
                    System.out.println("Mark Assignment Complete selected");
                    break;

                case "4":
                    System.out.println("View Productivity selected");
                    break;

                case "5":
                    System.out.println("See you next time!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}

