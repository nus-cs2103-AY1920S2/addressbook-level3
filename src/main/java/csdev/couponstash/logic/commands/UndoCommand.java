package csdev.couponstash.logic.commands;

import static java.util.Objects.requireNonNull;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.Model;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Previous command undone";
    public static final String MESSAGE_NO_STATE_TO_UNDO_TO = "No commands to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoCouponStash()) {
            throw new CommandException(MESSAGE_NO_STATE_TO_UNDO_TO);
        }

        model.undoCouponStash();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
