package tatracker.logic.commands;

/**
 * Container for user visible messages after executing commands.
 */
public class CommandMessages {

    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists.";

    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code.";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the given module.";

    public static final String MESSAGE_INVALID_STUDENT = "There is no student with the given matric number.";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the given module and group.";

    public static final String MESSAGE_INVALID_SESSION_DISPLAYED_INDEX = "There is no session at the given list index.";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists.";
    public static final String MESSAGE_INVALID_SESSION_TIMES = "You cannot have a session start after it ends!";

    public static final String MESSAGE_NOT_EDITED = "You must provide at least one edited field.";
}
