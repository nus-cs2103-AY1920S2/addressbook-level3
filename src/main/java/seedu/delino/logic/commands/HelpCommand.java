package seedu.delino.logic.commands;

import java.util.logging.Logger;

import seedu.delino.model.Model;

//@@author cherweijie
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;
    public static final String SHOW_ADDITIONAL_PARAMETERS_MESSAGE = "Please do not enter any additional non-whitespace "
            + "characters after"
            + " the command word 'help'.";

    public static final String SHOWING_HELP_MESSAGE = "Please refer to the pop-up window for more information."
            + "\n" + "Alternatively, you may refer to Delino's User Guide at https://bit.ly/38Y296W";
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());
    private boolean validity = false;
    @Override
    public CommandResult execute(Model model) {
        logger.fine("Execute help command based on valid user input.");
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, false, false);
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    /**
     * Used to check if a helpcommand object is valid.
     * @return True if it is valid.
     */
    public boolean isValid() {
        logger.fine("Check if the help command is valid.");
        return validity;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof HelpCommand) {
            if (((HelpCommand) other).isValid() && this.isValid()) {
                return true;
            }
        }
        return false;
    }
}
