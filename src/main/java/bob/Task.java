package bob;

/**
 * Represents a generic task in the Bob task manager.
 * A task has a description and a completion status.
 * This is the base class for all specific task types (Todo, Deadline, Event).
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done true to mark as done, false to mark as not done
     */
    public void setDone(boolean done) {
        this.isDone = done;
    }

    /**
     * Returns the status icon representing the task's completion state.
     *
     * @return "[X]" if done, "[ ]" if not done
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]");
    }

    /**
     * Returns a string representation of the task for display.
     * Format: "[status icon] description"
     *
     * @return Formatted task string
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
