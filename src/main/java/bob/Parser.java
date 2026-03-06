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
     * @throws IllegalArgumentException if the input is invalid or cannot be parsed
     */
    public Command parseCommand(String userInput) throws IllegalArgumentException {
        String trimmedInput = userInput.trim();
        
        if (trimmedInput.isEmpty()) {
            throw new IllegalArgumentException("Error: Empty command.");
        }

        String lowerCaseInput = trimmedInput.toLowerCase();

        if (lowerCaseInput.equals("list")) {
            return new ListCommand();
        } else if (lowerCaseInput.equals("bye")) {
            return new ExitCommand();
        } else if (lowerCaseInput.equals("help")) {
          return new HelpCommand();
        } else if (lowerCaseInput.startsWith("todo")) {
            return parseTodo(trimmedInput);
        } else if (lowerCaseInput.startsWith("deadline")) {
            return parseDeadline(trimmedInput);
        } else if (lowerCaseInput.startsWith("event")) {
            return parseEvent(trimmedInput);
        } else if (lowerCaseInput.startsWith("mark")) {
            return parseMark(trimmedInput);
        } else if (lowerCaseInput.startsWith("unmark")) {
            return parseUnmark(trimmedInput);
        } else if (lowerCaseInput.startsWith("delete")) {
            return parseDelete(trimmedInput);
        } else if (lowerCaseInput.startsWith("find")) {
            return parseFind(trimmedInput);
        } else {
            return new UnknownCommand(trimmedInput);
        }
    }

    private Command parseTodo(String input) {
        if (input.equalsIgnoreCase("todo")) {
            throw new IllegalArgumentException(BobExceptions.TODO_EMPTY_DESCRIPTION);
        }
        
        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.TODO_EMPTY_DESCRIPTION);
        }
        
        return new AddCommand(new Todo(description));
    }

    private Command parseDeadline(String input) {
        if (input.equalsIgnoreCase("deadline")) {
            throw new IllegalArgumentException(BobExceptions.DEADLINE_INVALID_FORMAT);
        }
        
        String rest = input.substring(9).trim();
        String[] parts = rest.split(" /by ");
        
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.DEADLINE_INVALID_FORMAT);
        }
        
        // For Level-8, you would parse the date here
        // LocalDate by = LocalDate.parse(parts[1].trim());
        // return new AddCommand(new Deadline(parts[0].trim(), by));
        
        return new AddCommand(new Deadline(parts[0].trim(), parts[1].trim()));
    }

    private Command parseEvent(String input) {
        if (input.equalsIgnoreCase("event")) {
            throw new IllegalArgumentException(BobExceptions.EVENT_INVALID_FORMAT);
        }
        
        String rest = input.substring(6).trim();
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length < 3 || parts[0].trim().isEmpty() || 
            parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.EVENT_INVALID_FORMAT);
        }
        
        return new AddCommand(new Event(parts[0].trim(), parts[1].trim(), 
parts[2].trim()));
    }

    private Command parseMark(String input) {
        if (input.equalsIgnoreCase("mark")) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        String rest = input.substring(5).trim(); // "mark ".length() = 5
        if (rest.isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        try {
            int taskNum = Integer.parseInt(rest);
            return new MarkCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    private Command parseUnmark(String input) {
        if (input.equalsIgnoreCase("unmark")) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        String rest = input.substring(7).trim(); // "unmark ".length() = 7
        if (rest.isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        try {
            int taskNum = Integer.parseInt(rest);
            return new UnmarkCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    private Command parseDelete(String input) {
        if (input.equalsIgnoreCase("delete")) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        String rest = input.substring(7).trim(); // "delete ".length() = 7
        if (rest.isEmpty()) {
            throw new IllegalArgumentException(BobExceptions.MISSING_TASK_NUMBER);
        }
        
        try {
            int taskNum = Integer.parseInt(rest);
            return new DeleteCommand(taskNum);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(BobExceptions.INVALID_TASK_NUMBER);
        }
    }

    private Command parseFind(String input) {
        if (input.equalsIgnoreCase("find")) {
            throw new IllegalArgumentException("Error: Please specify a keyword to find.");
        }
        
        String keyword = input.substring(5).trim(); // "find ".length() = 5
        if (keyword.isEmpty()) {
            throw new IllegalArgumentException("Error: Please specify a keyword to find.");
        }
        
        return new FindCommand(keyword);
    }
}
