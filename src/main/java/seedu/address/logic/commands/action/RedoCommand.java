package seedu.address.logic.commands.action;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.util.action.DoableAction;

/**
 * Represents a Redo command.
 */
public class RedoCommand extends Command {
    public static final String MESSAGE_USAGE = COMMAND_GROUP_REDO
            + ": Redo a previously undone action which changed the system's data. "
            + "Such actions come from your add, edit, or delete commands.\n"
            + "Format: " + COMMAND_GROUP_REDO;
    public static final String MESSAGE_SUCCESS = "Redone action: %s";
    public static final String MESSAGE_CANNOT_REDO = "There are no valid actions to redo.";

    private final String addAction = "Add\n";
    private final String editAction = "Edit\nFROM:   %s\nTO:   %s";
    private final String deleteAction = "Delete\n";

    /**
     * Empty constructor for nothing.
     */
    public RedoCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_CANNOT_REDO);
        }
        DoableAction<?> redone = model.redo();
        String msg = makeMessage(redone);

        return new CommandResult(String.format(MESSAGE_SUCCESS, msg), CommandType.REDO);
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
