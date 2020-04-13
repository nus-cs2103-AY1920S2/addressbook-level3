package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.ui.tab.Tab;

/**
 *  Restores any action that have been previously undone using an UndoCommand.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_CANNOT_REDO = "Unable to redo!";
    public static final String MESSAGE_CONSTRAINTS = "Only one non-zero unsigned integer "
            + "(representing the number of actions you wish to redo) or the 'all' keyword is allowed as an argument. "
            + "Otherwise, leaving it blank will do (undoes the actions of only the previous undo)!";
    public static final String MESSAGE_SUCCESS = "Redid %1$d action(s) successfully!";
    public static final String MESSAGE_ALL_SUCCESS = "Redid all actions successfully!";

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
        Tab toSwitch = model.redoBook(numberOfRedo, model);
        if (numberOfRedo > 0) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfRedo),
                    false, false, toSwitch, false);
        } else {
            return new CommandResult(MESSAGE_ALL_SUCCESS, false, false, toSwitch, false);
        }
    }
}
