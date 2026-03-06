package bob;

/**
 * Represents an event that occurs during a specific time period.
 * An Event task has a description, a start time, and an end time.
 */
public class Event extends Task {
    private String start;
    private String end;

    /**
     * Creates a new event task with the given description and time period.
     *
     * @param description The description of the event
     * @param start The start time of the event
     * @param end The end time of the event
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Updates the start time of the event.
     *
     * @param start The new start time
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Updates the end time of the event.
     *
     * @param end The new end time
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Returns the start time of the event.
     *
     * @return The start time as a string
     */
    public String getStart() {
        return start;
    }

    /**
     * Returns the end time of the event.
     *
     * @return The end time as a string
     */
    public String getEnd() {
        return end;
    }

    /**
     * Returns a string representation of the event task for display.
     * Format: "[E][status icon] description (from: start to: end)"
     *
     * @return Formatted event string
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
