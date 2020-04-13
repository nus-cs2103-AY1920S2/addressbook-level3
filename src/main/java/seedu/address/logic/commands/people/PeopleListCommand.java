package seedu.address.logic.commands.people;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class PeopleListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_EXECUTION = "Listing people...";
    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private static final Logger logger = LogsCenter.getLogger(PeopleListCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info(MESSAGE_LIST_EXECUTION);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
