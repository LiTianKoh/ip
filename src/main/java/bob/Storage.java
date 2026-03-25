package bob;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all file storage operations for the Bob application.
 * This class manages saving tasks to and loading tasks from a file,
 * ensuring data persistence between sessions.
 */
public class Storage {
    private static final String DATA_DIR = "data";
    private static final String HEADER = "===================\nTaskings\n===================";
    private String filePath;

    /**
     * Creates a storage handler for the specified file path.
     *
     * @param filePath The path to the save file where tasks will be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Initializes the storage by creating the data directory and save file if they don't exist.
     * Creates the directory structure and writes the initial header to a new file.
     *
     * @throws IOException if an I/O error occurs during directory or file creation
     */
    public void initialize() throws IOException {
        Path dataDir = Paths.get(DATA_DIR);
        Path dataFile = Paths.get(filePath);

        // Create directory if it doesn't exist
        if (!Files.exists(dataDir)) {
            Files.createDirectory(dataDir);
        }

        // Create file if it doesn't exist
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            FileWriter writer = new FileWriter(filePath);
            writer.write(HEADER + System.lineSeparator());
            writer.close();
        }
    }

    /**
     * Loads tasks from the save file and reconstructs them as Task objects.
     * Reads the file line by line, skips header and empty lines, and parses
     * each valid task line into the corresponding Task subclass.
     *
     * @return An ArrayList of tasks loaded from the file. Returns an empty list
     *         if the file doesn't exist or contains no valid tasks.
     * @throws IOException if an I/O error occurs during reading
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty() || line.equals("===================") || line.equals("Taskings")) {
                continue;
            }

            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                // Skip corrupted lines
            }
        }
        scanner.close();
        return tasks;
    }

    /**
     * Saves all current tasks to the file in a human-readable format.
     * Writes the header followed by each task converted to a string representation.
     * Overwrites any existing content in the file.
     *
     * @param tasks The list of tasks to save to the file
     * @throws IOException if an I/O error occurs during writing
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);

        writer.write(HEADER + System.lineSeparator());

        for (Task task : tasks) {
            String line = taskToFileString(task);
            writer.write(line + System.lineSeparator());
        }

        writer.close();
    }

    /**
     * Converts a Task object into a string format suitable for file storage.
     * The format differs based on the task type:
     * - Todo: "T | [status] | description"
     * - Deadline: "D | [status] | description | due date"
     * - Event: "E | [status] | description | start to end"
     *
     * @param task The task to convert to a string representation
     * @return A formatted string representing the task for file storage
     */
    private String taskToFileString(Task task) {
        String status = task.isDone() ? "[X]" : "[ ]";

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
            return String.format("E | %s | %s | %s to %s",
                    status,
                    event.getDescription(),
                    event.getStart(),
                    event.getEnd());
        }

        return "";
    }

    /**
     * Parses a line from the save file and reconstructs the corresponding Task object.
     * This method handles the inverse operation of {@link #taskToFileString}.
     * It splits the line by the " | " delimiter and creates the appropriate task type
     * based on the first character (T, D, or E).
     *
     * The expected formats are:
     * - Todo: "T | [status] | description"
     * - Deadline: "D | [status] | description | due date"
     * - Event: "E | [status] | description | start to end"
     *
     * @param line A single line from the save file representing one task
     * @return The reconstructed Task object, or null if the line format is invalid
     * @throws IllegalArgumentException if the line format is corrupted or unknown
     */
    private Task parseTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("[X]");

        switch (type) {
        case "T":
            if (parts.length >= 3) {
                Todo todo = new Todo(parts[2]);
                todo.setDone(isDone);
                return todo;
            }
            break;

        case "D":
            if (parts.length >= 4) {
                Deadline deadline = new Deadline(parts[2], parts[3]);
                deadline.setDone(isDone);
                return deadline;
            }
            break;

        case "E":
            if (parts.length >= 4) {
                String period = parts[3];
                String[] timeParts = period.split(" to ");
                if (timeParts.length >= 2) {
                    Event event = new Event(parts[2], timeParts[0], timeParts[1]);
                    event.setDone(isDone);
                    return event;
                }
            }
            break;
        }

        return null;
    }
}
