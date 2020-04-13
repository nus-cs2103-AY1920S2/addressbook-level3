package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Deletes existing Entries in a Receipt.
 */
public class ClearReceiptCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "clearreceipt";

    public static final String MESSAGE_SUCCESS = "Cleared the receipt.";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        if (!splitterModel.isReceiptDone()) {
            splitterModel.deleteAllEntries();
        } else {
            splitterModel.clearReceipt();
            splitterModel.getReceipt().makeUndone();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
