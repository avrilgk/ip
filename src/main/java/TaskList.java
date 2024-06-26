import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskList {

    private static ArrayList<Task> tasks;

    public TaskList() {
    }

    /**
     * Initializes or re-initializes the task list. This method can be used to reset the task list.
     */
    public void load() {
        TaskList.tasks = new ArrayList<>();
    }

    public void load(ArrayList<Task> tasks) {
        if (tasks == null) {
            TaskList.tasks = new ArrayList<>();
        } else {
            TaskList.tasks = tasks;
        }
    }

    public static ArrayList<Task> getTaskList() {
        return tasks;
    }

    /**
     *
     * @param userCommand The command input by the user, expected to contain a description of the task following the command keyword.
     */

    static void addTodoTask(String userCommand) {

        String taskDescription = userCommand.substring(4).trim();
        if (taskDescription.isEmpty()) {
            System.out.println("use english pls. type properly.");
        } else {
            tasks.add(new TodoTask(taskDescription));

            System.out.println("____________________________________________________________");
            System.out.println(" Got it. AVRY added this task:");
            System.out.println("   [T][ ]  " + taskDescription);
            if (tasks.size() == 1) {
                System.out.println(" Now you have 1 task in the list.");
            } else {
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            }
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * Adds a new Deadline task based on user input.
     *
     * @param userCommand The command input by the user, expected to contain a description of the task and a deadline, separated by '/by'.
     */
    static void addDeadline(String userCommand) throws TaskManagerException {
        String[] descParts = userCommand.split("deadline");
        String[] deadlineParts = descParts[1].split("/by", 2);

        if (descParts[1].trim().isEmpty()) {
            throw new TaskManagerException("Invalid deadline format. Please use '/by' to specify the deadline.");
        }

        if (deadlineParts.length < 2) {
            throw new TaskManagerException("Invalid deadline format. Please use '/by' to specify the deadline.");
        }

        String description = deadlineParts[0];
        String deadlineString = deadlineParts[1].trim();

        LocalDateTime deadline;
        try {
            deadline = LocalDateTime.parse(deadlineString, DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        } catch (DateTimeParseException e) {
            System.out.println("Invalid deadline date format. Please use the format 'DD-MM-YYYY HHmm'.");
            return;
        }

        tasks.add(new DeadlineTask(description, deadline));
        System.out.println("____________________________________________________________");
        System.out.println(" Got it. AVRY added this task:");
        System.out.println("   [D][ ] " + description + " (By: " + deadline.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")) + ")");

        if (tasks.size() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * Adds a new Event task based on user input.
     *
     * @param userCommand The command input by the user, expected to contain a description of the task and a deadline, separated by '/from'.
     */
    static void addEvent(String userCommand) throws TaskManagerException {

        String[] descParts = userCommand.split("event", 2);
        String[] eventParts = descParts[1].split("/from", 2);

        if (descParts[1].trim().isEmpty()) {
            throw new TaskManagerException("Invalid event format. Please use 'event <description> /from <start date> /to <end date>' to specify the event.");
        }

        if (eventParts.length < 2 || eventParts[1].trim().isEmpty()) {
            throw new TaskManagerException("Invalid event format. Please use '/from' to specify the start date and time.");
        }

        String description = eventParts[0].trim();
        String[] timeParts = eventParts[1].split("/to", 2);

        if (timeParts.length != 2) {
            throw new TaskManagerException("Invalid event format. Please use '/to' to specify the end date and time.");
        }

        // Parse the start and end times of command
        String startTimeString = timeParts[0].trim();
        String endTimeString = timeParts[1].trim();
        LocalDateTime startTime, endTime;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
            startTime = LocalDateTime.parse(startTimeString, formatter);
            endTime = LocalDateTime.parse(endTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new TaskManagerException("Invalid date-time format. Please use the format 'DD-MM-YYYY HHmm'.");
        }

        // Format the start and end times for output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);

        // Adding the new event task to the task list
        tasks.add(new EventTask(description, startTime, endTime));

        System.out.println("____________________________________________________________");
        System.out.println(" Got it. AVRY added this task:");
        System.out.println("   [E][ ] " + description + " (From: " + formattedStartTime + " To: " + formattedEndTime + ")");
        if (tasks.size() == 1) {
            System.out.println(" Now you have 1 task in the list.");
        } else {
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        }
        System.out.println("____________________________________________________________");
    }

    /**
     * 'displayTasks' method displays all the lists -- marked and unmarked
     * when the command 'list' is inputted by the user
     */
    static void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("____________________________________________________________");
            System.out.println(" Tasks list is empty.");
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            System.out.println("____________________________________________________________");
        }
    }

    /**
     * `markTask` method marks a task as done.
     * it extracts the task index, and updates the task's status
     */
    static void markTask(String userCommand) {
        String[] characters = userCommand.split("\\s+");

        if (characters.length != 2 || !characters[1].matches("\\d+")) {
            System.out.println("Invalid command format. Please use 'mark <task index>'.");
            return;
        }

        int taskIndex = Integer.parseInt(characters[1]) - 1;

        if (isValidTaskIndex(taskIndex)) {
            tasks.get(taskIndex).markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println("   " + tasks.get(taskIndex));
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("Invalid task index. Please enter a valid number.");
        }
    }

    /**
     * `unmarkTask` method un-marks a task that was originally marked as done.
     * it extracts the task index, and updates the task's status, then prints a confirmation message.
     */
    static void unmarkTask(String userCommand) {
        String[] characters = userCommand.split("\\s+");

        if (characters.length == 2 && characters[1].matches("\\d+")) {
            int taskIndex = Integer.parseInt(characters[1]) - 1;

            if (isValidTaskIndex(taskIndex)) {
                tasks.get(taskIndex).markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks.get(taskIndex));
                System.out.println("____________________________________________________________");
            } else {
                System.out.println("Invalid task index.");
            }
        } else {
            System.out.println("Invalid format. Please check your spelling or punctuation.");
        }
    }

    static void deleteTask(String userCommand) {
        String[] characters = userCommand.split("\\s+");

        if (characters.length != 2 || !characters[1].matches("\\d+")) {
            System.out.println("type properly");
            return;
        }

        int taskIndex = Integer.parseInt(characters[1]) - 1;
        if (isValidTaskIndex(taskIndex)) {
            Task deletedTask = tasks.remove(taskIndex);
            System.out.println("____________________________________________________________");
            System.out.println(" orh ok. Make sure hor, I deleted this:");
            System.out.println("   " + deletedTask);
            if (tasks.size() == 1) {
                System.out.println(" Now you have 1 task in the list.");
            } else {
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            }
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("Invalid task index. Please enter a valid number.");
        }
    }

    /**
     * Searches for and displays tasks that contain the keyword in the user input.
     * If one or more matches are found, it lists those tasks along with their indices.
     * If no matches are found, it displays an error message.
     *
     * @param keyword The keyword to search for within the tasks
     */
    public static void findTask(String keyword) {
        StringBuilder results = new StringBuilder();
        boolean isFound = false;

        for (int i = 0; i < tasks.size(); ++i) {
            Task task = tasks.get(i);
            if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
                if (!isFound) {
                    results.append("Here are the matching tasks in your list:\n");
                    isFound = true;
                }
                results.append(String.format("%d. %s%n", (i + 1), task));
            }
        }

        if (!isFound) {
            System.out.println("No matching tasks found.");
        } else {
            System.out.println(results);
        }
    }

    private static boolean isValidTaskIndex(int taskIndex) {
        return taskIndex >= 0 && taskIndex < tasks.size();
    }

}
