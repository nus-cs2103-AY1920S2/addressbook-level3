package seedu.jelphabot.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command. Type 'help' for our user guide.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASKS_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format! "
                                                                 + "Date should be one of these possible formats:\n"
                                                                 + "[d-MMM-uuuu], E.g. 1-Jan-2020\n"
                                                                 + "[d/MMM/uuuu], E.g. 1/Jan/2020\n"
                                                                 + "[MMM-d-uuuu], E.g. Jan-1-2020\n"
                                                                 + "[MMM/d/uuuu], E.g. Jan/1/2020";
    public static final String MESSAGE_INVALID_YEARMONTH_FORMAT = "Invalid YearMonth format! YearMonth should be one "
                                                                      + "of these possible formats:\n"
                                                                      + "[MMM-uuuu], E.g. Jan-2020\n"
                                                                      + "[MMM/uuuu], E.g. Jan/2020\n"
                                                                      + "[uuuu/MMM], E.g. 2020/Jan\n"
                                                                      + "[uuuu-MMM], E.g. 2020-Jan\n"
                                                                      + "[uuuu-MM], E.g. 2020-01\n"
                                                                      + "[uuuu/MM], E.g. 2020/01\n"
                                                                      + "[uu-MM], E.g. 20-01\n"
                                                                      + "[uu/MM], E.g. 20/01";
    public static final String MESSAGE_COMPLIMENT = "Good work! (:";
    public static final String MESSAGE_ENCOURAGEMENT = "You can do better than this!";
    public static final String MESSAGE_CRITICISM = "Do better! You have many unfinished tasks that are past their"
                                                       + " due date!";
    public static final String MESSAGE_NO_TIMERS = "There are no tasks being timed at the moment.";
}
