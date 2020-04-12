package seedu.address.logic.commands.commandDelete;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Abstract class for the deletion of objects. All delete commands will extend off this one.
 */

public abstract class DeleteCommand extends UndoableCommand {
    // TODO: Consider deleting by ID instead of Index
    public static final String COMMAND_WORD = "delete";
}
