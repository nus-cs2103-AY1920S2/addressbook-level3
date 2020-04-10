package seedu.eylah.ui;

import java.util.Scanner;

import seedu.eylah.commons.model.Mode;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {
    private static final String WELCOME_MESSAGE = "Welcome to EYLAH";
    private static final String LOGO = "          _____                    _____            \n"
            + "         |       \\     /  |       |     |  |     | \n"
            + "         |        \\   /   |       |     |  |     | \n"
            + "         |_____    \\ /    |       |_____|  |_____| \n"
            + "         |          |     |       |     |  |     |  \n"
            + "         |_____     |     |_____  |     |  |     |  ";
    private static final String SEPARATOR = "_____________________________________________________________";
    private static final String USAGE = "Enter command to choose the mode.\n";
    private static final String NAV_LIST = "Content Page: \n"
            + "----------------\n"
            + "Enter Diet Tracker: 1/diet\n"
            + "Enter Expense Splitter: 2/split\n"
            + "Exit EYLAH: exit\n"
            + "----------------";
    private static final String READ_COMMAND = "Enter command: ";
    private static final String EXIT_MESSAGE = "Bye! See you next time :)";
    private static final String MODE_MESSAGE = "Currently at %s";

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
        showToUser(SEPARATOR, USAGE, NAV_LIST);
    }

    @Override
    public void showLogo() {
        showToUser(SEPARATOR, LOGO, SEPARATOR, WELCOME_MESSAGE);
    }

    @Override
    public void showMode(Mode mode) {
        showToUser(SEPARATOR, String.format(MODE_MESSAGE, mode.toString()));
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
