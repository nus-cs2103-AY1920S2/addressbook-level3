package seedu.eylah.ui;

import java.util.Scanner;

public class UiManager implements Ui {
    private static final String WELCOME_MESSAGE = "Welcome to EYLAH";
    private static final String LOGO = "          _____                    _____            \n"
            + "         |       \\     /  |       |     |  |     | \n"
            + "         |        \\   /   |       |     |  |     | \n"
            + "         |_____    \\ /    |       |_____|  |_____| \n"
            + "         |          |     |       |     |  |     |  \n"
            + "         |_____     |     |_____  |     |  |     |  ";
    private static final String SEPARATOR = "_____________________________________________________________";
    private static final String USAGE = "Enter the INDEX to choose the mode.\n";
    private static final String NAV_LIST = "Content Page: \n"
            + "----------------\n"
            + "1. Diet Tracker\n"
            + "2. Expense Splitter\n"
            + "To exit the application: exit\n"
            + "----------------";
    private static final String READ_COMMAND = "Enter command: ";
    private static final String EXIT_MESSAGE = "Bye! See you next time :)";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private Scanner scanner;

    public UiManager() {
        scanner = new Scanner(System.in);
    }

    /**
     * Converts the given strings to the desired format.
     *
     * @param messages the given string to format
     */
    private void showToUser(String... messages) {
        for (String m : messages) {
            System.out.println(m.replace("\n", LS));
        }
    }

    @Override
    public void showWelcome() {
        showToUser(WELCOME_MESSAGE, SEPARATOR,LOGO, SEPARATOR, LS, USAGE, NAV_LIST);
    }

    @Override
    public String readCommand() {
        showToUser(READ_COMMAND);
        return scanner.nextLine();
    }

    @Override
    public void showError(String error) {
        showToUser(error);
    }

    @Override
    public void showResult(String result) {
        showToUser(result);
    }

    @Override
    public void showExit() {
        showToUser(EXIT_MESSAGE);
    }
}
