package seedu.address.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class AccListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_FAILURE = "Command is in wrong format.";

    public static final String MESSAGE_SUCCESS = "Listed all accounts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredExpenditureList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
