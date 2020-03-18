package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.expensesplitter.model.Model;

/**
 * Lists all users with their amount.
 */
public class ListAmountCommand extends Command {

    public static final String COMMAND_WORD = "listamount";

    public static final String MESSAGE_SUCCESS = "Listed all person with their amount.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.listAmount();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
