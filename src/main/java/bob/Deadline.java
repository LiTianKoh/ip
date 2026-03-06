package bob;

/**
 * Represents a deadline task that must be completed by a specific date/time.
 * A Deadline task has a description and a "by" date/time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Creates a new deadline task with the given description and due date.
     *
     * @param description The description of the deadline task
     * @param by The due date/time of the deadline
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Updates the due date/time of the deadline.
     *
     * @param by The new due date/time
     */
    public void setBy(String by) {
        this.by = by;
    }

    /**
     * Returns the due date/time of the deadline.
     *
     * @return The due date/time as a string
     */
    public String getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task for display.
     * Format: "[D][status icon] description (by: due date)"
     *
     * @return Formatted deadline string
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
