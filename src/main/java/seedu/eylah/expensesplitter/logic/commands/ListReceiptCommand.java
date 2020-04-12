package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;


/**
 *  Lists the current receipt to the user.
 */
public class ListReceiptCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "listreceipt";
    public static final String MESSAGE_USAGE = "listreceipt is entered as `listreceipt`";
    public static final String MESSAGE_SUCCESS = "Listed current receipt containing all Items and Person(s) involved "
            + "in splitting it.";
    public static final String MESSAGE_EMPTY = "Receipt is currently empty.\n"
            + "\n"
            + "Please use the additem command to add an item.\n"
            + "Example:\n"
            + "additem -i ITEMNAME -p PRICE -n NAME [-n NAME]..."
            + "\n";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        if (!splitterModel.getReceipt().getReceipt().isEmpty()) {
            String output = splitterModel.listReceipt();
            return new CommandResult(output.concat(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(MESSAGE_EMPTY);
        }
    }
}
