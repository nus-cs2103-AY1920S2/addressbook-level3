package seedu.address.logic.commands;

import seedu.address.model.Model;

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
    private boolean validity = false;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, false, false);
    }

    public void setValidity(boolean validity) {
        this.validity = validity;
    }

    public boolean isValid() {
        return validity;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof HelpCommand) {
            if (((HelpCommand) other).isValid() && this.isValid()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
