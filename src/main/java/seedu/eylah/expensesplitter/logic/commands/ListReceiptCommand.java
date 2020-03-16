package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;
//import seedu.eylah.expensesplitter.logic.commands.CommandResult;

/**
 *  Lists the current receipt to the user.
 */
public class ListReceiptCommand extends Command {

    public static final String COMMAND_WORD = "listreceipt";
    public static final String MESSAGE_USAGE = "listreceipt is entered as `listreceipt`";
    public static final String MESSAGE_SUCCESS = "Listed current receipt containing all Items and Person(s) involved"
            + "in splitting it.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.listReceipt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
