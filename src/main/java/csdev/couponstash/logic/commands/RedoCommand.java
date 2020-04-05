package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

/**
 * Redo previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Undone command redone: %s";
    public static final String MESSAGE_NO_STATE_TO_REDO_TO = "Nothing to redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo the previously undone command.\n\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoCouponStash()) {
            throw new CommandException(MESSAGE_NO_STATE_TO_REDO_TO);
        }

        String command = model.redoCouponStash();
        return new CommandResult(String.format(MESSAGE_SUCCESS, command));
    }
}

