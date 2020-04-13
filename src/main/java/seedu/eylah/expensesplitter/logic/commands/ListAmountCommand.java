package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Lists all Person(s) and the Amount they owe you.
 */
public class ListAmountCommand extends Command<SplitterModel> {

    public static final String COMMAND_WORD = "listamount";
    public static final String MESSAGE_SUCCESS = "Listed all person with their amount.";
    public static final String MESSAGE_EMPTY = "Currently no one owes you any money.\n"
            + "\n"
            + "Please use the additem command to add an item.\n"
            + "Example:\n"
            + "additem -i ITEMNAME -p PRICE -n NAME [-n NAME]..."
            + "\n";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        if (!splitterModel.getPersonAmountBook().getPersonList().isEmpty()) {
            String output = splitterModel.listAmount();
            return new CommandResult(output.concat(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(MESSAGE_EMPTY);
        }
    }

}
