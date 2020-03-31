package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 *  Starts a new Receipt.
 */
public class NewReceiptCommand extends Command {

    public static final String COMMAND_WORD = "newreceipt";

    public static final String MESSAGE_SUCCESS = "Old receipt is cleared and a new Receipt is started.";

    @Override
    public CommandResult execute (SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        splitterModel.newReceipt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
