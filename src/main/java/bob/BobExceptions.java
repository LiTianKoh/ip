package bob;

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
    public static final String UNKNOWN_COMMAND =
            "Unknown command: '%s', Type 'help' to see available commands.";
    public static final String DEADLINE_MULTIPLE_BY =
            "Error: Deadline should have only one '/by' delimiter.";
    public static final String EVENT_MULTIPLE_FROM =
            "Error: Event should have only one '/from' delimiter.";
    public static final String EVENT_MULTIPLE_TO =
            "Error: Event should have only one '/to' delimiter.";
}
