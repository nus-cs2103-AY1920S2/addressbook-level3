package seedu.address.logic.commands.commandList;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FINANCES;

/**
 * Lists all persons in the address book to the user.
 */
public class ListFinanceCommand extends ListCommand {

    public static final String COMMAND_WORD = "list-finance";

    public static final String MESSAGE_SUCCESS = "Listed all finances";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFinanceList(PREDICATE_SHOW_ALL_FINANCES);
        model.getMainWindow().callSwitchToFinance();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
