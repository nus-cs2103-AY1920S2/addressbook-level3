package seedu.address.logic.commands.commandAssign;

import seedu.address.logic.commands.UndoableCommand;

public abstract class AssignCommandBase extends UndoableCommand {
    public static final String COMMAND_WORD = "assign";
    public static final String MESSAGE_ASSIGNMENT_SUCCESS = "Assignment success";
}
