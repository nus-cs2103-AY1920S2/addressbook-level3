package seedu.eylah.expensesplitter.logic.commands;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * This Command is used to go back to the Main Application of EYLAH.
 */
public class BackCommand extends Command {

    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_SUCCESS = "Returned to Main Menu.";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }


}
