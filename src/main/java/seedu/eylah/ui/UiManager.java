package seedu.eylah.ui;

import java.util.Scanner;

import seedu.eylah.commons.logic.command.CommandResult;
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
            + "Enter Diet Tracker: diet or 1\n"
            + "Enter Expense Splitter: split or 2\n"
            + "Getting Help: help\n"
            + "Exit EYLAH: exit\n"
            + "----------------";
    private static final String READ_COMMAND = "Enter command: ";
    private static final String EXIT_MESSAGE = "Bye! See you next time :)";
    private static final String MODE_MESSAGE = "Currently at %s";
    private static final String MAIN_HELP_SEPARATOR = "--------------------------------\n";
    public static final String MAIN_HELP = "Welcome to EYLAH! The following commands are available:\n"
            + MAIN_HELP_SEPARATOR
            + "1. diet or 1 - Use this to enter Diet Tracker mode.\n"
            + "   USAGE: diet/1\n"
            + "   EXAMPLE: diet\n"
            + "   EXAMPLE: 1\n"
            + MAIN_HELP_SEPARATOR
            + "2. split or 2 - Use this to enter Expense Splitter mode.\n"
            + "   USAGE: split/2\n"
            + "   EXAMPLE: split\n"
            + "   EXAMPLE: 2\n"
            + MAIN_HELP_SEPARATOR
            + "3. exit - Use this to exit the EYLAH.\n"
            + "   USAGE: exit\n"
            + "   EXAMPLE: exit\n"
            + "We hope you enjoy your usage of EYLAH!";

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
    public void showResult(CommandResult commandResult) {
        showToUser(commandResult.getFeedbackToUser());
    }

    @Override
    public void showExit() {
        showToUser(EXIT_MESSAGE);
    }

    @Override
    public void showMainHelp() {
        showToUser(SEPARATOR, MAIN_HELP);
    }
}
