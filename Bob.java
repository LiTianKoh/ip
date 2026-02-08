import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Bob {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>(); //Initialise dynamic array
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

        //Word recognition
        Pattern pattern = Pattern.compile("handsome|beautiful", Pattern.CASE_INSENSITIVE);

        do {
            line = in.nextLine();
            System.out.println("    ___________________________");

            //Check if it's not Bye
            if (!line.equalsIgnoreCase("Bye")) {
                //Lists out the tasks when list is inputted
                if (line.equalsIgnoreCase("list")) {
                    if (tasks.isEmpty()) {
                        System.out.println("    No tasks in your list.");
                    } else {
                        System.out.println("    Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("    " + (i + 1) + "." + tasks.get(i));
                        }
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                // Check for todo command (exact match or starts with "todo ")
                if (line.equalsIgnoreCase("todo") || line.toLowerCase().startsWith("todo ")) {
                    if (line.equalsIgnoreCase("todo")) {
                        System.out.println("    Error: Todo description cannot be empty.");
                        System.out.println("    ___________________________");
                        continue;
                    }

                    String description = line.substring(5).trim();
                    if (description.isEmpty()) {
                        System.out.println("    Error: Todo description cannot be empty.");
                    } else {
                        tasks.add(new Todo(description)); //Add the new task into the task array
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + tasks.get(tasks.size() - 1));
                        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //handle deadline commands (exact match or starts with "deadline ")
                if (line.equalsIgnoreCase("deadline") || line.toLowerCase().startsWith("deadline ")) {
                    if (line.equalsIgnoreCase("deadline")) {
                        System.out.println("    Error: Deadline must have description and /by time.");
                        System.out.println("    ___________________________");
                        continue;
                    }

                    String rest = line.substring(9).trim(); //9 because 'deadline ' is 9 char long
                    String[] parts = rest.split(" /by ");
                    if (parts.length < 2) {
                        System.out.println("    Error: Deadline must have description and /by time.");
                    } else {
                        String description = parts[0].trim();
                        String by = parts[1].trim();
                        tasks.add(new Deadline(description, by)); //Add the new deadline task into the task array
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + tasks.get(tasks.size() - 1));
                        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //handle event commands (exact match or starts with "event ")
                if (line.equalsIgnoreCase("event") || line.toLowerCase().startsWith("event ")) {
                    if (line.equalsIgnoreCase("event")) {
                        System.out.println("    Error: Event must have description, /from and /to times.");
                        System.out.println("    ___________________________");
                        continue;
                    }

                    String rest = line.substring(6).trim(); //6 because 'event ' is 6 char long
                    String[] parts = rest.split(" /from | /to ");
                    if (parts.length < 3) {
                        System.out.println("    Error: Event must have description, /from and /to times.");
                    } else {
                        String description = parts[0].trim();
                        String start = parts[1].trim();
                        String end = parts[2].trim();
                        tasks.add(new Event(description, start, end)); //Add the new event task into the task array
                        System.out.println("    Got it. I've added this task:");
                        System.out.println("    " + tasks.get(tasks.size() - 1));
                        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Handle mark command (exact match or starts with "mark ")
                if (line.equalsIgnoreCase("mark") || line.toLowerCase().startsWith("mark ")) {
                    if (line.equalsIgnoreCase("mark")) {
                        System.out.println("    Error: Please specify a task number.");
                        System.out.println("    ___________________________");
                        continue;
                    }

                    try  {
                        String[] parts = line.split(" ");
                        int taskNum = Integer.parseInt(parts[1]); //Convert the string digit to int digit

                        //Check if the task number is within the listed range
                        if (taskNum >= 1 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);  // -1 because of the indexing
                            task.isDone = true; //Add [X]
                            System.out.println("    Nice! I've marked this task as done: ");
                            System.out.println("    " + taskNum + "." + task.toString());
                        } else {
                            System.out.println("    Error: Task number " + taskNum + " does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    Error: Please specify a valid task number.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("    Error: Please specify a task number.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Handle unmark command (exact match or starts with "unmark ")
                if (line.equalsIgnoreCase("unmark") || line.toLowerCase().startsWith("unmark ")) {
                    if (line.equalsIgnoreCase("unmark")) {
                        System.out.println("    Error: Please specify a task number.");
                        System.out.println("    ___________________________");
                        continue;
                    }

                    try  {
                        String[] parts = line.split(" ");
                        int taskNum = Integer.parseInt(parts[1]); //Convert the string digit to int digits

                        //Check if the task number is within the listed range
                        if (taskNum >= 1 && taskNum <= tasks.size()) {
                            Task task = tasks.get(taskNum - 1);  // -1 because of the indexing
                            task.isDone = false; //Add [ ]
                            System.out.println("    Ok, I've marked this task as not done yet: ");
                            System.out.println("    " + taskNum + "." + task.toString());
                        } else {
                            System.out.println("    Error: Task number " + taskNum + " does not exist.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("    Error: Please specify a valid task number.");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("    Error: Please specify a task number.");
                    }
                    System.out.println("    ___________________________");
                    continue;
                }

                //Added personality: Check if the line input consist of handsome or beautiful
                if (pattern.matcher(line).find()) {
                    System.out.println("    Nonono, you are ;)");
                    System.out.println("    ___________________________");
                    continue;
                }

                //Otherwise, add a task
                tasks.add(new Task(line));
                System.out.println("    added: " + line);
                System.out.println("    ___________________________");
            }
        } while (!line.toLowerCase().contains("bye")); //Lines containing bye
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ___________________________");

        in.close();
    }
}