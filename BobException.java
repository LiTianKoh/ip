public class BobExceptions {

    // Error messages
    public static final String TODO_EMPTY_DESCRIPTION =
            "OOPS!! The description of a todo cannot be empty.";
    public static final String DEADLINE_INVALID_FORMAT =
            "OOPS!! Deadline must have a description and /by time.";
    public static final String EVENT_INVALID_FORMAT =
            "OOPS!! Event must have a description, /from and /to times.";
    public static final String TASK_NUMBER_NOT_EXIST =
            "Error: Task number %d does not exist.";
    public static final String INVALID_TASK_NUMBER =
            "Error: Please specify a valid task number.";
    public static final String MISSING_TASK_NUMBER =
            "Error: Please specify a task number.";

    /**
     * Parses and validates a line of input
     * Returns an array where first element is command type, rest are arguments
     */
    public static String[] parseAndValidate(String line) throws IllegalArgumentException {
        String trimmedLine = line.trim().toLowerCase();

        if (trimmedLine.isEmpty()) {
            throw new IllegalArgumentException("Error: Empty command.");
        }

        // Check command type and delegate to appropriate parser
        if (trimmedLine.equals("todo") || trimmedLine.startsWith("todo ")) {
            return parseTodo(line);
        } else if (trimmedLine.equals("deadline") || trimmedLine.startsWith("deadline ")) {
            return parseDeadline(line);
        } else if (trimmedLine.equals("event") || trimmedLine.startsWith("event ")) {
            return parseEvent(line);
        } else if (trimmedLine.equals("mark") || trimmedLine.startsWith("mark ")) {
            return parseMarkUnmark(line, "mark");
        } else if (trimmedLine.equals("unmark") || trimmedLine.startsWith("unmark ")) {
            return parseMarkUnmark(line, "unmark");
        } else if (trimmedLine.equals("list")) {
            return new String[]{"list"};
        }

        // Generic task
        return new String[]{"task", line};
    }

    private static String[] parseTodo(String line) {
        if (line.equalsIgnoreCase("todo")) {
            throw new IllegalArgumentException(TODO_EMPTY_DESCRIPTION);
        }

        String description = line.substring(5).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException(TODO_EMPTY_DESCRIPTION);
        }

        return new String[]{"todo", description};
    }

    private static String[] parseDeadline(String line) {
        if (line.equalsIgnoreCase("deadline")) {
            throw new IllegalArgumentException(DEADLINE_INVALID_FORMAT);
        }

        String rest = line.substring(9).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException(DEADLINE_INVALID_FORMAT);
        }

        return new String[]{"deadline", parts[0].trim(), parts[1].trim()};
    }

    private static String[] parseEvent(String line) {
        if (line.equalsIgnoreCase("event")) {
            throw new IllegalArgumentException(EVENT_INVALID_FORMAT);
        }

        String rest = line.substring(6).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length < 3 || parts[0].trim().isEmpty() ||
                parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new IllegalArgumentException(EVENT_INVALID_FORMAT);
        }

        return new String[]{"event", parts[0].trim(), parts[1].trim(), parts[2].trim()};
    }

    private static String[] parseMarkUnmark(String line, String command) {
        if (line.equalsIgnoreCase(command)) {
            throw new IllegalArgumentException(MISSING_TASK_NUMBER);
        }

        String rest = line.substring(command.length()).trim();
        if (rest.isEmpty()) {
            throw new IllegalArgumentException(MISSING_TASK_NUMBER);
        }

        try {
            int taskNum = Integer.parseInt(rest.trim());
            return new String[]{command, String.valueOf(taskNum)};
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_TASK_NUMBER);
        }
    }

    /**
     * Validates task number range
     */
    public static void validateTaskNumber(int taskNum, int taskCount)
            throws IllegalArgumentException {
        if (taskNum < 1 || taskNum > taskCount) {
            throw new IllegalArgumentException(String.format(TASK_NUMBER_NOT_EXIST, taskNum));
        }
    }

    /**
     * Handles exceptions and prints appropriate error messages
     */
    public static void handleException(Exception e) {
        System.out.println("    " + e.getMessage());
    }
}