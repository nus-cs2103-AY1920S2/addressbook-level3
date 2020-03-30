package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_FUNCTION = "Undo the last entered command";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n";

    public static final String MESSAGE_SUCCESS = "Command Undone!";
    public static final String MESSAGE_LAST_CHANGE = "Already At Last Change!";

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public UndoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.undoStackSize() == 1) {
            throw new CommandException(MESSAGE_LAST_CHANGE);
        }

        String commandType = model.undo();

        if (commandType.equals("ADDRESS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else if (commandType.equals("ASSIGNMENTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    true, false, false, false, false);
        } else if (commandType.equals("EVENTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, true, false, false, false);
        } else if (commandType.equals("RESTAURANTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, false, false, true, false);
        } else {
            throw new CommandException("BUG ENCOUNTERED, NOT SUPPOSED TO REACH HERE");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
