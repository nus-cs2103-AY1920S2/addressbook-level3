package team.easytravel.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {
    // Success messages
    public static final String MESSAGE_SORT_SUCCESS = "%1$s are sorted successfully! \uD83D\uDE03";
    public static final String MESSAGE_ITEMS_LISTED_OVERVIEW = "%1$d %2$s listed! \uD83D\uDE03";

    // Error messages
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command \uD83D\uDE1E";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \uD83D\uDE1E\n%1$s";
    public static final String MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT = "The %1$s index provided is invalid. "
            + "\uD83D\uDE1E";
    public static final String MESSAGE_EMPTY_LIST_FORMAT = "The %1$s list is empty. \uD83D\uDE1E";
}
