package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECIPES;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverses the action of an earlier action.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_CANNOT_UNDO = "Nothing to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_CANNOT_UNDO);
        }
        model.undoRecipeBook();
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
