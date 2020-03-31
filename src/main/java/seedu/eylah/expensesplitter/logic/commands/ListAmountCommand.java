package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.expensesplitter.model.SplitterModel;

/**
 * Lists all Person(s) and the Amount they owe you.
 */
public class ListAmountCommand extends Command {

    public static final String COMMAND_WORD = "listamount";

    public static final String MESSAGE_SUCCESS = "Listed all person with their amount.";

    @Override
    public CommandResult execute(SplitterModel splitterModel) {
        requireNonNull(splitterModel);
        splitterModel.listAmount();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
