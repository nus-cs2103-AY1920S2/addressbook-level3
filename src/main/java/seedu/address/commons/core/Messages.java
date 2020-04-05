package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX = "The assignment index is invalid.";
    public static final String MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX = "The restaurant index is invalid.";
    public static final String MESSAGE_NO_INFO = "This person has no existing information!";
    public static final String MESSAGE_INVALID_LINE_NUMBER = "Line number does not exist in person's information.";
    public static final String MESSAGE_NO_LINE_NUMBER = "Line number of remarks is not provided.";
    public static final String MESSAGE_TOO_MANY_FILTERS = "Too many filters! Either deadline or estimated completion "
            + "time can be used to sort each time.";
    public static final String MESSAGE_INVALID_PREFIX = "Name and phone cannot be deleted!";
    public static final String MESSAGE_NO_PREFIX = "At least one recommended, good or bad food should be specified.";
    public static final String WELCOME_MESSAGE = "Hi! I'm Naggy Joel, the best social life manager you can ever have. "
        + "If you are new, you can type 'help' in the command box below to see the list of commands that I understand.";
}
