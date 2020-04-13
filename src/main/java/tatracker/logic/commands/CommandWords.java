// @@author potatocombat

package tatracker.logic.commands;

/**
 * Contains a list of common command words in TA-Tracker.
 * These have been separated as they will be used by the CommandDictionary in future versions.
 */
public final class CommandWords {

    /* List of command words for each model in TA-Tracker. */
    public static final String MODULE = "module";
    public static final String GROUP = "group";
    public static final String STUDENT = "student";
    public static final String SESSION = "session";
    public static final String CLAIM = "claims";

    /* List of command words for commands that are common between TA-Tracker models. */
    public static final String ADD_MODEL = "add";
    public static final String DELETE_MODEL = "delete";
    public static final String EDIT_MODEL = "edit";
    public static final String FILTER_MODEL = "filter";

    /* List of command words for the different sort types in TA-Tracker. */
    public static final String SORT = "sort";
    public static final String SORT_ALL = "all";
    public static final String SORT_GROUP = "group";
    public static final String SORT_MODULE = "module";

    /* List of command words for special actions in TA-Tracker. */
    public static final String CLEAR = "clear";
    public static final String GOTO = "goto";
    public static final String REPORT = "report";
    public static final String SET_RATE = "setrate";
    public static final String HELP = "help";
    public static final String EXIT = "exit";

    /* Others */
    public static final String LIST = "list";
    public static final String DONE_SESSION = "done";
}
