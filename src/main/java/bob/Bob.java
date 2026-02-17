package bob;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.IOException;

public class Bob {

    // Constants for command prefixes
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String MARK_COMMAND = "mark ";
    private static final String UNMARK_COMMAND = "unmark ";
    private static final String DELETE_COMMAND = "delete ";

    // Constants for command lengths
    private static final int TODO_LENGTH = TODO_COMMAND.length();       // 5
    private static final int DEADLINE_LENGTH = DEADLINE_COMMAND.length(); // 9
    private static final int EVENT_LENGTH = EVENT_COMMAND.length();     // 6
    private static final int DELETE_LENGTH = DELETE_COMMAND.length();   // 7

    // Constants for date/time delimiters
    private static final String DEADLINE_DELIMITER = " /by ";
    private static final String EVENT_FROM_DELIMITER = " /from ";
    private static final String EVENT_TO_DELIMITER = " /to ";

    // Constants for UI
    private static final String SEPARATOR = "    ___________________________";
    private static final String INDENT = "    ";

    // Pattern for compliments
    private static final Pattern COMPLIMENT_PATTERN =
            Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String userInput;

        // Initialize storage and load tasks
        try {
            Storage.initializeDataFile();
            tasks = Storage.loadTasks();
            System.out.println(INDENT + "Loaded " + tasks.size() + " tasks from save file.");
        } catch (IOException e) {
            System.out.println(INDENT + "Warning: Could not load tasks from file. Starting with empty list.");
        }

        Logo.printBob();

        do {
            userInput = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (!userInput.equalsIgnoreCase("Bye")) {
                try {
                    String[] parsed = BobExceptions.parseAndValidate(userInput);
                    String command = parsed[0];
                    boolean listChanged = false;

                    switch (command) {
                    case "list":
                        handleList(tasks);
                        break;
                    case "todo":
                        handleTodo(tasks, parsed[1]);
                        listChanged = true;
                        break;
                    case "deadline":
                        handleDeadline(tasks, parsed[1], parsed[2]);
                        listChanged = true;
                        break;
                    case "event":
                        handleEvent(tasks, parsed[1], parsed[2], parsed[3]);
                        listChanged = true;
                        break;
                    case "mark":
                        handleMark(tasks, Integer.parseInt(parsed[1]), true);
                        listChanged = true;
                        break;
                    case "unmark":
                        handleMark(tasks, Integer.parseInt(parsed[1]), false);
                        listChanged = true;
                        break;
                    case "delete":
                        handleDelete(tasks, Integer.parseInt(parsed[1]));
                        listChanged = true;
                        break;
                    case "task":
                        handleGenericTask(tasks, userInput);
                        listChanged = true;
                        break;
                    }

                    // Save tasks if the list was modified
                    if (listChanged) {
                        try {
                            Storage.saveTasks(tasks);
                        } catch (IOException e) {
                            System.out.println(INDENT + "Warning: Could not save tasks to file.");
                        }
                    }

                } catch (IllegalArgumentException e) {
                    BobExceptions.handleException(e);
                    System.out.println(SEPARATOR);
                }
            }
        } while (!userInput.toLowerCase().contains("bye"));

        // Final save before exit
        try {
            Storage.saveTasks(tasks);
            System.out.println(INDENT + "Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println(INDENT + "Warning: Could not save tasks before exit.");
        }

        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        System.out.println(SEPARATOR);
        scanner.close();
    }

    /**
     * Handles the list command - displays all tasks
     */
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

    /**
     * Handles the todo command - adds a new Todo task
     */
    private static void handleTodo(ArrayList<Task> tasks, String description) {
        tasks.add(new Todo(description));
        printTaskAddedConfirmation(tasks);
        System.out.println(SEPARATOR);
    }

    /**
     * Handles the deadline command - adds a new Deadline task
     */
    private static void handleDeadline(ArrayList<Task> tasks, String description, String by) {
        tasks.add(new Deadline(description, by));
        printTaskAddedConfirmation(tasks);
        System.out.println(SEPARATOR);
    }

    /**
     * Handles the event command - adds a new Event task
     */
    private static void handleEvent(ArrayList<Task> tasks, String description, String start, String end) {
        tasks.add(new Event(description, start, end));
        printTaskAddedConfirmation(tasks);
        System.out.println(SEPARATOR);
    }

    /**
     * Handles both mark and unmark commands
     * @param markAsDone true for mark, false for unmark
     */
    private static void handleMark(ArrayList<Task> tasks, int taskNum, boolean markAsDone) {
        BobExceptions.validateTaskNumber(taskNum, tasks.size());
        Task task = tasks.get(taskNum - 1);
        task.setDone(markAsDone);

        if (markAsDone) {
            System.out.println(INDENT + "Nice! I've marked this task as done: ");
        } else {
            System.out.println(INDENT + "Ok, I've marked this task as not done yet: ");
        }
        System.out.println(INDENT + taskNum + "." + task.toString());
        System.out.println(SEPARATOR);
    }

    /**
     * Handles the delete command - removes a task
     */
    private static void handleDelete(ArrayList<Task> tasks, int taskNum) {
        BobExceptions.validateTaskNumber(taskNum, tasks.size());

        Task removedTask = tasks.get(taskNum - 1);
        tasks.remove(taskNum - 1);

        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + removedTask.toString());
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(SEPARATOR);
    }

    /**
     * Handles generic tasks (non-commands) and compliments
     */
    private static void handleGenericTask(ArrayList<Task> tasks, String userInput) {
        if (COMPLIMENT_PATTERN.matcher(userInput).find()) {
            System.out.println(INDENT + "Nonono, you are ;)");
        } else {
            tasks.add(new Task(userInput));
            System.out.println(INDENT + "added: " + userInput);
        }
        System.out.println(SEPARATOR);
    }

    /**
     * Prints confirmation message when a task is added
     */
    private static void printTaskAddedConfirmation(ArrayList<Task> tasks) {
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + tasks.get(tasks.size() - 1));
        System.out.println(INDENT + "Now you have " + tasks.size() + " tasks in the list.");
    }
}