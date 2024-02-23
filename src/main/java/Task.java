public class Task {
    public String description;
    public boolean isDone;
    public String status;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.status = "0";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + description;
    }

    public String toFileString() {
        return (status = isDone ? "1" : "0");
    }
}