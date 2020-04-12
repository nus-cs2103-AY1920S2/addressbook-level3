package seedu.address.logic.commands.commandUnassign;

import seedu.address.logic.commands.UndoableCommand;

public abstract class UnassignCommandBase extends UndoableCommand {
    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_UNASSIGNMENT_SUCCESS = "Unassignment success";
}
