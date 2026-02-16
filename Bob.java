import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Bob {

    // Constants for command prefixes
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";

    // Constants for command lengths
    private static final int TODO_LENGTH = TODO_COMMAND.length();       // 5
    private static final int DEADLINE_LENGTH = DEADLINE_COMMAND.length(); // 9
    private static final int EVENT_LENGTH = EVENT_COMMAND.length();     // 6

    // Constants for date/time delimiters
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    // Constants for UI
    private static final String SEPARATOR = "    ___________________________";
    private static final String INDENT = "    ";

    // Pattern for compliments (make it class-level)
    private static final Pattern COMPLIMENT_PATTERN =
            Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String userInput;

        Logo.printBob();     // To print the logo and the greeting

        do {
            userInput = in.nextLine();
            System.out.println(SEPARATOR);

            if (!userInput.equalsIgnoreCase("Bye")) {
                try {
                    String[] parsed = BobExceptions.parseAndValidate(userInput);
                    String command = parsed[0];

                    switch (command) {
                    case "list":
                        handleList(tasks);
                        break;
                    case "todo":
                        handleTodo(tasks, parsed[1]);
                        break;
                    case "deadline":
                        handleDeadline(tasks, parsed[1], parsed[2]);
                        break;
                    case "event":
                        handleEvent(tasks, parsed[1], parsed[2], parsed[3]);
                        break;
                    case "mark":
                        handleMark(tasks, Integer.parseInt(parsed[1]), true);
                        break;
                    case "unmark":
                        handleMark(tasks, Integer.parseInt(parsed[1]), false);
                        break;
                    case "task":
                        handleGenericTask(tasks, userInput);
                        break;
                    }

                } catch (IllegalArgumentException e) {
                    BobExceptions.handleException(e);
                    System.out.println(SEPARATOR);
                }
            }
        } while (!userInput.toLowerCase().contains("bye"));

        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
        in.close();
    }

    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(INDENT + "No tasks in your list.");
        } else {
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println(SEPARATOR);
    }

    private static void handleTodo(ArrayList<Task> tasks, String description) {
        tasks.add(new Todo(description));
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + tasks.get(tasks.size() - 1));
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    private static void handleDeadline(ArrayList<Task> tasks, String description, String by) {
        tasks.add(new Deadline(description, by));
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + tasks.get(tasks.size() - 1));
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    private static void handleEvent(ArrayList<Task> tasks, String description, String start, String end) {
        tasks.add(new Event(description, start, end));
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + tasks.get(tasks.size() - 1));
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    private static void handleMark(ArrayList<Task> tasks, int taskNum, boolean markAsDone) {
        BobExceptions.validateTaskNumber(taskNum, tasks.size());
        Task task = tasks.get(taskNum - 1);
        task.isDone = markAsDone;

        if (markAsDone) {
            System.out.println(INDENT + "Nice! I've marked this task as done: ");
        } else {
            System.out.println(INDENT + "Ok, I've marked this task as not done yet: ");
        }
        System.out.println(INDENT + taskNum + "." + task.toString());
        System.out.println(SEPARATOR);
    }

    private static void handleGenericTask(ArrayList<Task> tasks, String userInput) {
        // Use the class-level pattern instead of creating a new one
        if (COMPLIMENT_PATTERN.matcher(userInput).find()) {
            System.out.println(INDENT + "Nonono, you are ;)");
        } else {
            tasks.add(new Task(userInput));
            System.out.println(INDENT + "added: " + userInput);
        }
        System.out.println(SEPARATOR);
    }
}