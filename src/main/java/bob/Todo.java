package bob;

/**
 * Represents a todo task without any date/time constraints.
 * A Todo task is the simplest type of task, only having a description.
 */
public class Todo extends Task {
    /**
     * Creates a new todo task with the given description.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task for display.
     * Format: "[T][status icon] description"
     *
     * @return Formatted todo string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
