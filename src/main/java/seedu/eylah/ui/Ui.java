package seedu.eylah.ui;

import java.util.Scanner;

/**
 * Main CLI Ui component for the application.
 */
public class Ui {

    private static final String WELCOME_MESSAGE = "Welcome to EYLAH, this is a debug version of interface :)";
    private static final String USAGE = "To use the application, enter `diet` or "
            + "`splitter` to enter the different mode.";
    private static final String EXIT_MESSAGE = "Bye! See you next time :)";
    private static final String LS = System.lineSeparator();

    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    private void showToUser(String... message) {
        for (String m : message) {
            System.out.println(m.replace("\n", LS));
        }
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showWelcome() {
        showToUser(WELCOME_MESSAGE, USAGE);
    }

    public void showError(String error) {
        showToUser(error);
    }

    public void showResult(String result) {
        showToUser(result);
    }

    public void showExit() {
        showToUser(EXIT_MESSAGE);
    }

}
