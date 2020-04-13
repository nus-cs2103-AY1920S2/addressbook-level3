// @@author potatocombat
package tatracker.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_WELCOME = "Welcome to TA-Tracker!\n\n";
    public static final String MESSAGE_HELP = "Enter help to view the list of commands";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid command format!\n\n";

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command\n\n";

    public static final String MESSAGE_NOT_EDITED = "You must provide at least one edited field";

    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with the given module code";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists";

    public static final String MESSAGE_INVALID_GROUP_CODE = "There is no group with the given group code";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the given module";

    public static final String MESSAGE_INVALID_STUDENT = "There is no student with the given matric number";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the given module and group";

    public static final String MESSAGE_INVALID_SESSION_DISPLAYED_INDEX = "There is no session at the given list index";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists";
    public static final String MESSAGE_INVALID_SESSION_TIMES = "You cannot have a session start after it ends!";

    public static String getUnknownCommandWithHelpMessage() {
        return MESSAGE_UNKNOWN_COMMAND + MESSAGE_HELP;
    }

    public static String getInvalidCommandWithHelpMessage() {
        return MESSAGE_INVALID_COMMAND + MESSAGE_HELP;
    }

    public static String getInvalidCommandMessage(String commandUsage) {
        return String.format(MESSAGE_INVALID_COMMAND_FORMAT, commandUsage);
    }
}
