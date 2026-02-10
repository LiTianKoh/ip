import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Bob {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String line;

        String logo =
                " ______   _______   ______ \n"
                        + "|  __  \\ |  ___  | |  __  \\ \n"
                        + "| |__)  )| |   | | | |__)  )\n"
                        + "|  __  ( | |   | | |  __  ( \n"
                        + "| |__)  )| |___| | | |__)  )\n"
                        + "|______/ |_______| |______/ \n";
        System.out.println("Hello from\n" + logo);
        System.out.println("    ___________________________");
        System.out.println("    Hello! I'm BOB");
        System.out.println("    What can I do for you?");
        System.out.println("    ___________________________");

        Pattern pattern = Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

        do {
            line = in.nextLine();
            System.out.println("    ___________________________");

            if (!line.equalsIgnoreCase("Bye")) {
                try {
                    String[] parsed = BobExceptions.parseAndValidate(line);
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
                        handleGenericTask(tasks, line);
                        break;
                    }

                } catch (IllegalArgumentException e) {
                    BobExceptions.handleException(e);
                    System.out.println("    ___________________________");
                }
            }
        } while (!line.toLowerCase().contains("bye"));

        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ___________________________");
        in.close();
    }

    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("    No tasks in your list.");
        } else {
            System.out.println("    Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("    " + (i + 1) + "." + tasks.get(i));
            }
        }
        System.out.println("    ___________________________");
    }

    private static void handleTodo(ArrayList<Task> tasks, String description) {
        tasks.add(new Todo(description));
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ___________________________");
    }

    private static void handleDeadline(ArrayList<Task> tasks, String description, String by) {
        tasks.add(new Deadline(description, by));
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ___________________________");
    }

    private static void handleEvent(ArrayList<Task> tasks, String description, String start, String end) {
        tasks.add(new Event(description, start, end));
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + tasks.get(tasks.size() - 1));
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("    ___________________________");
    }

    private static void handleMark(ArrayList<Task> tasks, int taskNum, boolean markAsDone) {
        BobExceptions.validateTaskNumber(taskNum, tasks.size());
        Task task = tasks.get(taskNum - 1);
        task.isDone = markAsDone;

        if (markAsDone) {
            System.out.println("    Nice! I've marked this task as done: ");
        } else {
            System.out.println("    Ok, I've marked this task as not done yet: ");
        }
        System.out.println("    " + taskNum + "." + task.toString());
        System.out.println("    ___________________________");
    }

    private static void handleGenericTask(ArrayList<Task> tasks, String line) {
        Pattern pattern = Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(line).find()) {
            System.out.println("    Nonono, you are ;)");
        } else {
            tasks.add(new Task(line));
            System.out.println("    added: " + line);
        }
        System.out.println("    ___________________________");
    }
}