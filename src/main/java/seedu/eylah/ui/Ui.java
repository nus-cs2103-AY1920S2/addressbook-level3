package seedu.eylah.ui;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.model.Mode;

/**
 * API of UI component.
 */
public interface Ui {

    /**
     * Shows the welcome message, logo, and the main command to the user.
     */
    void showWelcome();

    /**
     * Reads the userInput from the user.
     *
     * @return the given user input
     */
    String readCommand();

    /**
     * Shows the result of the command to user.
     */
    void showResult(CommandResult commandResult);

    /**
     * Shows the logo of EYLAH to user.
     */
    void showLogo();

    /**
     * Shows current mode of the EYLAH to user.
     */
    void showMode(Mode mode);

    /**
     * Shows the exit message to user.
     */
    void showExit();

    /**
     * Shows the error message to user.
     */
    void showError(String error);

    /**
     * Shows the main menu help message to user.
     */
    void showMainHelp();

}
