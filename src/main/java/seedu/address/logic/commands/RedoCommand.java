package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_FUNCTION = "Redo the last undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n";

    public static final String MESSAGE_SUCCESS = "Command Redone!";
    public static final String MESSAGE_LAST_CHANGE = "Nothing to Redo!";

    /**
     * Creates an RedoCommand to add the specified {@code Person}
     */
    public RedoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.redoStackSize() == 0) {
            throw new CommandException(MESSAGE_LAST_CHANGE);
        }

        String commandType = model.redo();

        if (commandType.equals("ADDRESS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else if (commandType.equals("BIRTHDAY")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, false, true, false, false);
        } else if (commandType.equals("ASSIGNMENTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    true, false, false, false, false);
        } else if (commandType.equals("EVENTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, true, false, false, false);
        } else if (commandType.equals("RESTAURANTS")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, false, false, true, false);
        } else if (commandType.equals("USERPREF")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                    false, false, false, false, false);
        } else if (commandType.equals("GETDETAIL")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, true,
                    false, false, false, false, false);
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
