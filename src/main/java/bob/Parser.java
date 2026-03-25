package bob;

/**
 * Parses user input into executable commands.
 * The Parser interprets raw input strings and creates the appropriate Command objects.
 */
public class Parser {

    /**
     * Parses the user's input and returns the corresponding Command object.
     *
     * @param userInput The raw input string from the user
     * @return A Command object representing the user's intended action
     * @throws BobException if the input is invalid or cannot be parsed
     */
    public Command parseCommand(String userInput) throws BobException {
        String trimmedInput = userInput.trim();

        if (trimmedInput.isEmpty()) {
            throw new BobException("Error: Empty command.");
        }

        // Split into first word and the rest - strict tokenization!
        String[] tokens = trimmedInput.split("\\s+", 2);
        String commandWord = tokens[0].toLowerCase();
        String arguments = tokens.length > 1 ? tokens[1] : "";

        switch (commandWord) {
        case "list":
            return new ListCommand();
        case "bye":
            return new ExitCommand();
        case "help":
            return new HelpCommand();
        case "todo":
            return parseTodo(arguments);
        case "deadline":
            return parseDeadline(arguments);
        case "event":
            return parseEvent(arguments);
        case "mark":
            return parseMark(arguments);
        case "unmark":
            return parseUnmark(arguments);
        case "delete":
            return parseDelete(arguments);
        case "find":
            return parseFind(arguments);
        default:
            return new UnknownCommand(trimmedInput);
        }
    }

    /**
     * Parses a todo command.
     * Format: todo DESCRIPTION
     *
     * @param args The arguments after the todo command
     * @return An AddCommand with a new Todo task
     * @throws BobException if the description is empty
     */
    private Command parseTodo(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.TODO_EMPTY_DESCRIPTION);
        }

        String description = args.trim();
        if (description.isEmpty()) {
            throw new BobException(BobExceptions.TODO_EMPTY_DESCRIPTION);
        }

        return new AddCommand(new Todo(description));
    }

    /**
     * Parses a deadline command.
     * Format: deadline DESCRIPTION /by DATE
     *
     * @param args The arguments after the deadline command
     * @return An AddCommand with a new Deadline task
     * @throws BobException if format is invalid or description/date missing
     */
    private Command parseDeadline(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.DEADLINE_INVALID_FORMAT);
        }

        // Split by " /by " to separate description and date
        String[] parts = args.split(" /by ");

        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new BobException(BobExceptions.DEADLINE_INVALID_FORMAT);
        }

        String description = parts[0].trim();
        String byDate = parts[1].trim();

        // For Level-8, you would parse the date here:
        // LocalDateTime by = parseDateTime(byDate);
        // return new AddCommand(new Deadline(description, by));

        return new AddCommand(new Deadline(description, byDate));
    }

    /**
     * Parses an event command.
     * Format: event DESCRIPTION /from START /to END
     *
     * @param args The arguments after the event command
     * @return An AddCommand with a new Event task
     * @throws BobException if format is invalid or description/start/end missing
     */
    private Command parseEvent(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.EVENT_INVALID_FORMAT);
        }

        // Split by " /from " and " /to " to separate description, start, and end
        // First, split into description and the rest
        String[] fromParts = args.split(" /from ");
        if (fromParts.length < 2) {
            throw new BobException(BobExceptions.EVENT_INVALID_FORMAT);
        }

        String description = fromParts[0].trim();
        if (description.isEmpty()) {
            throw new BobException(BobExceptions.EVENT_INVALID_FORMAT);
        }

        // Now split the remaining part by " /to "
        String[] toParts = fromParts[1].split(" /to ");
        if (toParts.length < 2) {
            throw new BobException(BobExceptions.EVENT_INVALID_FORMAT);
        }

        String start = toParts[0].trim();
        String end = toParts[1].trim();

        if (start.isEmpty() || end.isEmpty()) {
            throw new BobException(BobExceptions.EVENT_INVALID_FORMAT);
        }

        return new AddCommand(new Event(description, start, end));
    }

    /**
     * Parses a mark command.
     * Format: mark TASK_NUMBER
     *
     * @param args The arguments after the mark command
     * @return A MarkCommand
     * @throws BobException if task number is missing or invalid
     */
    private Command parseMark(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        String taskNumStr = args.trim();
        if (taskNumStr.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        try {
            int taskNum = Integer.parseInt(taskNumStr);
            return new MarkCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new BobException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses an unmark command.
     * Format: unmark TASK_NUMBER
     *
     * @param args The arguments after the unmark command
     * @return An UnmarkCommand
     * @throws BobException if task number is missing or invalid
     */
    private Command parseUnmark(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        String taskNumStr = args.trim();
        if (taskNumStr.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        try {
            int taskNum = Integer.parseInt(taskNumStr);
            return new UnmarkCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new BobException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses a delete command.
     * Format: delete TASK_NUMBER
     *
     * @param args The arguments after the delete command
     * @return A DeleteCommand
     * @throws BobException if task number is missing or invalid
     */
    private Command parseDelete(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        String taskNumStr = args.trim();
        if (taskNumStr.isEmpty()) {
            throw new BobException(BobExceptions.MISSING_TASK_NUMBER);
        }

        try {
            int taskNum = Integer.parseInt(taskNumStr);
            return new DeleteCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new BobException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    /**
     * Parses a find command.
     * Format: find KEYWORD
     *
     * @param args The arguments after the find command
     * @return A FindCommand
     * @throws BobException if keyword is missing
     */
    private Command parseFind(String args) throws BobException {
        if (args.isEmpty()) {
            throw new BobException("Error: Please specify a keyword to find.");
        }

        String keyword = args.trim();
        if (keyword.isEmpty()) {
            throw new BobException("Error: Please specify a keyword to find.");
        }

        return new FindCommand(keyword);
    }
}