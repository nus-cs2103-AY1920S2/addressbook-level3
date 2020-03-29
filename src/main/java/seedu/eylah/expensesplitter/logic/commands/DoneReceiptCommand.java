package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;

/**
 * Lists all users with their amount.
 */
public class DoneReceiptCommand extends Command {

    public static final String COMMAND_WORD = "donereceipt";

    public static final String MESSAGE_SUCCESS = "Receipt has been marked as completed.";

    public static final String MESSAGE_RECEIPT_DONE = "Receipt has already been marked as completed.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (model.isReceiptDone()) {
            return new CommandResult(MESSAGE_RECEIPT_DONE);
        } else {
            model.getReceipt().toggleDoneStatus();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

}
