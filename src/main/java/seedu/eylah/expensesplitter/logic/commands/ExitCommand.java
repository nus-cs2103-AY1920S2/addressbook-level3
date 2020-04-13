package seedu.eylah.expensesplitter.logic.commands;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Eylah as requested ...";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, true, false);
    }
}
