/**
 * The Ui class handles user interface interactions by displaying messages to the user.
 * It provides a welcome message upon instantiation and has methods to display instructions and a farewell message.
 */
public class Ui {

    private static final String asciiArt =
            "     ___     ________   __\n"
                    + "    / \\ \\   / /  _ \\ \\ / /\n"
                    + "   / _ \\ \\ / /| |_) \\ V / \n"
                    + "  / ___ \\ V / |  _ < | |  \n"
                    + " /_/   \\_\\_/  |_| \\_\\|_|  ";

    private static final String welcome = asciiArt
            + "\n\nHEY HEY YOU YOU! I COULD BE YOUR...task manager bot.";

    private static final String instructions =
            "---------Follow the syntax below:---------------\n"
                    + "- Todos: todo <taskDescription>\n"
                    + "- Deadlines: deadline <taskDescription> /by <DD-MM-YYYY HHmm>\n"
                    + "- Events: event <taskDescription> /from <DD-MM-YYYY HHmm> /to <DD-MM-YYYY HHmm>\n"
                    + "- Plus point if you can spell my name right.\n"
                    + "- End the program by saying 'thank you and bye'\n"
                    + "--------------------------------------------------";
    private static final String farewell = "Byeeeeee! Hope to see you again!";

    Ui() {
        System.out.println(welcome);
    }

    void printInstructions() {
        System.out.println(instructions);
    }

    void printFarewell() {
        System.out.println(farewell);
    }
}
