package seedu.nova.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid. "
            + "Please only use edit, remark or delete command after using list, "
            + "list c\\teammate, list c\\classmate or find command.";
    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "You entered to many arguments! \n%1$s";
    public static final String MESSAGE_INVALID_ARGUMENTS = "Invalid arguments! \n%1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_EMPTY_ARGUMENT = "Look at the help panel on the left to get started!";

    public static final String MESSAGE_WRONG_TIME = "End time should be later than start time.";

}
