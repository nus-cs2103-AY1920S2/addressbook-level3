package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.ui.tab.Tab;

/**
 * Reverses the action of an earlier action.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_CANNOT_UNDO = "Unable to undo!";
    public static final String MESSAGE_CONSTRAINTS = "Only one non-zero unsigned integer "
            + "(representing the number of actions you wish to undo) or the 'all' keyword is allowed as an argument. "
            + "Otherwise, leaving it blank will do (undoes only the previous action)!";
    public static final String MESSAGE_SUCCESS = "Undid %1$d action(s) successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Undid all actions successfully!";

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
        Tab toSwitch = model.undoBook(numberOfUndo, model);
        if (numberOfUndo > 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfUndo),
                    false, false, toSwitch, false);
        } else {
            return new CommandResult(MESSAGE_ALL_SUCCESS, false, false, toSwitch, false);
        }
    }
}
