package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

/**
 * Undo previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Previous command undone: %s";
    public static final String MESSAGE_NO_STATE_TO_UNDO_TO = "No commands to undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo previous operation. "
            + "Only operations that change the coupons in the coupon stash can be undone.\n\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model, String commandText) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoCouponStash()) {
            throw new CommandException(MESSAGE_NO_STATE_TO_UNDO_TO);
        }

        String command = model.undoCouponStash();
        return new CommandResult(String.format(MESSAGE_SUCCESS, command));
    }
}
