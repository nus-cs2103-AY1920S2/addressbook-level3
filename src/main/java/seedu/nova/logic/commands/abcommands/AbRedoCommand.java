package seedu.nova.logic.commands.abcommands;

import static java.util.Objects.requireNonNull;

import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * Clears the nova book.
 */
public class AbRedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Address book has been redo-ed!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the address book\n"
            + "Example: redo";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.canRedoAddressBook() == false) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
