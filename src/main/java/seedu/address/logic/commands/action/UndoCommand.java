package seedu.address.logic.commands.action;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.util.action.DoableAction;


/**
 * Represents an Undo command.
 */
public class UndoCommand extends Command {
    public static final String MESSAGE_USAGE = COMMAND_GROUP_UNDO
            + ": Undo a previous action which changed the system's data. "
            + "Such actions come from your add, edit, or delete commands.\n"
            + "Format: " + COMMAND_GROUP_UNDO;
    public static final String MESSAGE_SUCCESS = "Undone action: %s";
    public static final String MESSAGE_CANNOT_UNDO = "There are no valid actions to undo.";

    private final String addAction = "Add\n";
    private final String editAction = "Edit\nFROM:   %s\nTO:   %s";
    private final String deleteAction = "Delete\n";

    /**
     * Empty constructor for nothing.
     */
    public UndoCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_CANNOT_UNDO);
        }
        DoableAction<?> undone = model.undo();
        String msg = makeMessage(undone);

        return new CommandResult(String.format(MESSAGE_SUCCESS, msg), CommandType.UNDO);
    }

    /**
     * Makes appropriate return message for users.
     */
    private String makeMessage(DoableAction<?> action) {
        switch (action.getType()) {
        case ADD:
            return addAction + action.getTarget();
        case EDIT:
            return String.format(editAction, action.getTarget(), action.getReplacement());
        case DELETE:
            return deleteAction + action.getTarget();
        default:
            return "";
        }
    }
}
