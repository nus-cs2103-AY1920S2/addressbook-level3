package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Deletes existing Entries in a Receipt.
 */
public class ClearReceiptCommand extends Command {

    public static final String COMMAND_WORD = "clearreceipt";

    public static final String MESSAGE_SUCCESS = "Cleared the receipt.";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        splitterModel.clearReceipt();
        splitterModel.getReceipt().makeUndone();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
