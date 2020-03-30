package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;

/**
 *  Starts a new Receipt.
 */
public class NewReceiptCommand extends Command {

    public static final String COMMAND_WORD = "newreceipt";

    public static final String MESSAGE_SUCCESS = "Old receipt is cleared and a new Receipt is started.";

    @Override
    public CommandResult execute (Model model) {
        requireNonNull(model);
        model.newReceipt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
