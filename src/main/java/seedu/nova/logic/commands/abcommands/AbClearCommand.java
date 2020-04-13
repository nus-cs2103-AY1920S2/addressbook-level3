package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.model.AddressBook;
import seedu.nova.model.Model;

/**
 * Clears the nova book.
 */
public class AbClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears the address book\n"
            + "Example: clear";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
