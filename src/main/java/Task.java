/**
 * Represents a generic task with a description and a completion status.
 * This class serves as a base for more specific types of tasks.
 */

public class Task {
    private final String description;
    private boolean done;
    public String status;

    /**
     * Constructs a new Task with the specified description
     *
     * @param description Description of the task
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
        this.status = "0";
    }

    public void markAsDone() {
        this.done = true;
    }

    public void markAsNotDone() {
        this.done = false;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a string representation of the task, including its completion status
     *
     * @return A formatted string representing the task and its status.
     */
    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + description;
    }

    public String toFileString() {
        return (status = done ? "true" : "false");
    }
}