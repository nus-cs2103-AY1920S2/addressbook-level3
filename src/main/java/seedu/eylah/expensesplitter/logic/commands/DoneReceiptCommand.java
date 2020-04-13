package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Set the Receipt as DONE thus no further modification to the Receipt can be made.
 * For instance, after the Receipt is set as DONE, additem and deleteitem commands will not work until
 * a new receipt is created.
 */
public class DoneReceiptCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "donereceipt";

    public static final String MESSAGE_SUCCESS = "Receipt has been marked as completed.";

    public static final String MESSAGE_RECEIPT_DONE = "Receipt has already been marked as completed.";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        if (splitterModel.isReceiptDone()) {
            return new CommandResult(MESSAGE_RECEIPT_DONE);
        } else {
            splitterModel.getReceipt().makeDone();
            splitterModel.listReceipt();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

}
