package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 *  Restores any action that have been previously undone using an UndoCommand.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_CANNOT_REDO = "Nothing to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_CANNOT_REDO);
        }
        model.redoRecipeBook();
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
