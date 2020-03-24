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

    public static final String MESSAGE_CANNOT_UNDO = "Cannot undo!";
    public static final String MESSAGE_CONSTRAINTS = "Only one non-zero positive integer "
            + "(representing the number of actions you wish to undo) is allowed as an argument. "
            + "Otherwise, leaving it blank will do (undoes only the previous action)!";
    public static final String MESSAGE_SUCCESS = "Undid %1$d action(s) successfully!";

    private final int numberOfUndo;

    public UndoCommand(int numberOfUndo) {
        this.numberOfUndo = numberOfUndo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canUndo(numberOfUndo)) {
            throw new CommandException(MESSAGE_CANNOT_UNDO);
        }
        model.undoRecipeBook(numberOfUndo);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfUndo));
    }
}
