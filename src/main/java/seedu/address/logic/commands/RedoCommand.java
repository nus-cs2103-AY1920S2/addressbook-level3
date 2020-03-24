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

    public static final String MESSAGE_CANNOT_REDO = "Cannot redo!";
    public static final String MESSAGE_CONSTRAINTS = "Only one non-zero positive integer "
            + "(representing the number of actions you wish to redo) is allowed as an argument. "
            + "Otherwise, leaving it blank will do (undoes the actions of only the previous undo)!";
    public static final String MESSAGE_SUCCESS = "Redid %1$d action(s) successfully!";


    private final int numberOfRedo;

    public RedoCommand(int numberOfRedo) {
        this.numberOfRedo = numberOfRedo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRedo(numberOfRedo)) {
            throw new CommandException(MESSAGE_CANNOT_REDO);
        }
        model.redoRecipeBook(numberOfRedo);
        model.updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfRedo));
    }
}
