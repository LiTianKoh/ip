package bob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Save {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "bob.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + DATA_FILE;
    private static final String HEADER = "===================\nTaskings\n===================";

    /**
     * Creates data directory and file if they don't exist
     */
    public static void initializeDataFile() throws IOException {
        Path dataDir = Paths.get(DATA_DIR);
        Path dataFile = Paths.get(FILE_PATH);

        // Create directory if it doesn't exist
        if (!Files.exists(dataDir)) {
            Files.createDirectory(dataDir);
        }

        // Create file if it doesn't exist
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            // Write header to new file
            FileWriter writer = new FileWriter(FILE_PATH);
            writer.write(HEADER + System.lineSeparator());
            writer.close();
        }
    }

    /**
     * Loads tasks from the save file
     * @return ArrayList of tasks loaded from file
     * @throws IOException if file cannot be read
     */
    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        boolean headerPassed = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Skip empty lines and header
            if (line.isEmpty() || line.equals("===================") || line.equals("Taskings")) {
                continue;
            }

            // Once we've passed the header, parse tasks
            if (!headerPassed) {
                headerPassed = true;
                continue;
            }

            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                // Skip corrupted lines
                System.out.println("Warning: Skipping corrupted line: " + line);
            }
        }
        scanner.close();
        return tasks;
    }

    /**
     * Saves tasks to the file with the specified format
     * @param tasks The list of tasks to save
     * @throws IOException if file cannot be written
     */
    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH);

        // Write header
        writer.write(HEADER + System.lineSeparator());

        // Write each task
        for (Task task : tasks) {
            writer.write(taskToFileString(task) + System.lineSeparator());
        }

        writer.close();
    }

    /**
     * Converts a Task to the specified string format
     * Format: T | [✓] | description
     *         D | [ ] | description | date
     *         E | [✓] | description | period
     */
    private static String taskToFileString(Task task) {
        String status = task.isDone() ? "[✓]" : "[ ]";

        if (task instanceof Todo) {
            return String.format("T | %s | %s",
                    status,
                    task.getDescription());

        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return String.format("D | %s | %s | %s",
                    status,
                    deadline.getDescription(),
                    deadline.getBy());

        } else if (task instanceof Event) {
            Event event = (Event) task;
            return String.format("E | %s | %s | %s",
                    status,
                    event.getDescription(),
                    event.getStart() + " to " + event.getEnd());
        }

        return "";
    }

    /**
     * Parses a line from the save file into a Task object
     */
    private static Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("[✓]");

        switch (type) {
        case "T":
            Todo todo = new Todo(parts[2]);
            todo.setDone(isDone);
            return todo;

        case "D":
            Deadline deadline = new Deadline(parts[2], parts[3]);
            deadline.setDone(isDone);
            return deadline;

        case "E":
            // Handle period format "start to end"
            String period = parts[3];
            String[] timeParts = period.split(" to ");
            Event event = new Event(parts[2], timeParts[0], timeParts[1]);
            event.setDone(isDone);
            return event;

        default:
            return null;
        }
    }
}