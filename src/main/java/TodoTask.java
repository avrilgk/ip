public class TodoTask extends Task {
    public TodoTask(String description) {
        super(description);
    }
    public TodoTask(String description, boolean isDone) {
        super(description);
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + super.toFileString();
    }
}

