package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;

/**
 * Lists all users with their amount.
 */
public class ClearReceiptCommand extends Command {

    public static final String COMMAND_WORD = "clearreceipt";

    public static final String MESSAGE_SUCCESS = "Cleared the receipt.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearReceipt();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
