package bob;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the Bob application.
 * This class manages input reading and output display with consistent formatting.
 */
public class Ui {
    private Scanner scanner;
    public static final String SEPARATOR = "    ___________________________";
    public static final String INDENT = "    ";

    /**
     * Creates a new UI handler and initializes the input scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        Logo.printBob();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void showError(String message) {
        System.out.println(INDENT + message);
        showLine();
    }

    public void showMessage(String message) {
        System.out.println(INDENT + message);
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + task.toString());
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showTaskRemoved(Task task, int size) {
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + task.toString());
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

    public void showTaskMarked(Task task, int taskNum, boolean isDone) {
        if (isDone) {
            System.out.println(INDENT + "Nice! I've marked this task as done: ");
        } else {
            System.out.println(INDENT + "Ok, I've marked this task as not done yet: ");
        }
        System.out.println(INDENT + taskNum + "." + task.toString());
        showLine();
    }

    public void showTaskList(java.util.ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(INDENT + "No tasks in your list.");
        } else {
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    public void showMatchingTasks(java.util.ArrayList<Task> tasks, String keyword) {
        if (tasks.isEmpty()) {
            System.out.println(INDENT + "No matching tasks found for: " + keyword);
        } else {
            System.out.println(INDENT + "Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }

    public void showGoodbye() {
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLoadingError() {
        System.out.println(INDENT + "Warning: Could not load tasks from file. Starting with empty list.");
    }

    public void showComplimentResponse() {
        System.out.println(INDENT + "Nonono, you are ;)");
        showLine();
    }
    /**
     * Displays the help menu with all available commands.
     */
    public void showHelp() {
        System.out.println(INDENT + "Available commands:");
        System.out.println(INDENT + "- todo DESCRIPTION");
        System.out.println(INDENT + "- deadline DESCRIPTION /by DATE");
        System.out.println(INDENT + "- event DESCRIPTION /from START /to END");
        System.out.println(INDENT + "- list");
        System.out.println(INDENT + "- mark TASK_NUMBER");
        System.out.println(INDENT + "- unmark TASK_NUMBER");
        System.out.println(INDENT + "- delete TASK_NUMBER");
        System.out.println(INDENT + "- find KEYWORD");
        System.out.println(INDENT + "- bye");
        showLine();
    }
}
