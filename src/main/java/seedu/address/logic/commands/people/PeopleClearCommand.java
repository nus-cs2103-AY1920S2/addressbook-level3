package seedu.address.logic.commands.people;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class PeopleClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_CLEAR_EXECUTION = "Clearing contacts...";
    public static final String MESSAGE_SUCCESS = "Contact list has been cleared!";

    private static final Logger logger = LogsCenter.getLogger(PeopleClearCommand.class);

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info(MESSAGE_CLEAR_EXECUTION);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
