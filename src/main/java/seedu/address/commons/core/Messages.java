package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_UNKNOWN_MODULE_COMMAND = "Unknown mod command! \n%1$s";
    public static final String MESSAGE_UNKNOWN_FACILITATOR_COMMAND = "Unknown facil command! \n%1$s";
    public static final String MESSAGE_UNKNOWN_LESSON_COMMAND = "Unknown class command! \n%1$s";
    public static final String MESSAGE_UNKNOWN_TASK_COMMAND = "Unknown task command! \n%1$s";
    public static final String MESSAGE_UNKNOWN_CALENDAR_COMMAND = "Unknown cal command! \n%1$s";
    public static final String MESSAGE_UNKNOWN_CMD_COMMAND = "Unknown cmd command! \n%1$s";

    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_INDEX =
            "The module index provided is invalid! "
            + "Index must be a positive integer smaller than or equal to the size of the displayed module list.";
    public static final String MESSAGE_INVALID_FACILITATOR_DISPLAYED_INDEX =
            "The facilitator index provided is invalid! "
            + "Index must be a positive integer smaller than or equal to the size of the displayed facilitator list.";
    public static final String MESSAGE_PARTIAL_FACILITATOR_NAME_MATCHING_FOUND =
            "Facilitator %s cannot be found, or isn't unique. But here are the ones that are close to your query:\n";
    public static final String MESSAGE_ASK_TO_CONFIRM_FACILITATOR =
            "Please input the exact facilitator you want to delete. Facilitator name is case-sensitive.";
    public static final String MESSAGE_FACILITATORS_LISTED_OVERVIEW = "%1$d facilitators listed!";
    public static final String MESSAGE_FACILITATOR_NOT_FOUND = "Facilitator %1$s does not exist in Mod Manager.";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d matching tasks are listed!";
    public static final String MESSAGE_TASKS_UNDONE_OVERVIEW = "%1$d undone tasks are listed!";
    public static final String MESSAGE_TASKS_FILTERED_OVERVIEW = "%1$d tasks matched your query! "
            + "Type: task list to return to the main task list.";
    public static final String MESSAGE_INVALID_CMD_GROUP = "%1$s group does not exist.";
    public static final String MESSAGE_AT_WITHOUT_ON_ERROR = "/at parameter must not exist without /on parameter.";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX =
            "The class index provided is invalid! "
            + "Index must be a positive integer smaller than or equal to the size of the displayed class list "
            + "and should not be blank.";
    public static final String MESSAGE_LESSON_INVALID_TIME_RANGE = "The %s class clashes with another class";

    public static final String MESSAGE_TOO_MANY_ARGUMENTS = "There should not be more than %1$s %2$s prefix provided.";
    public static final String MESSAGE_NOT_EDITED = "No difference detected. At least one different field is required.";
}
