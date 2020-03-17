package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_EXERCISE_NAME = new Prefix("e/");
    public static final Prefix PREFIX_NEW_EXERCISE_NAME = new Prefix("f/");
    public static final Prefix PREFIX_WORKOUT_NAME = new Prefix("w/");
    public static final Prefix PREFIX_NUM_OF_REPS = new Prefix("r/");
    public static final Prefix PREFIX_NUM_OF_SETS = new Prefix("s/");
    public static final Prefix PREFIX_INTERVAL = new Prefix("i/");
    public static final Prefix PREFIX_FREQUENCY = new Prefix("f/");
    public static final Prefix PREFIX_DATETIME = new Prefix("d/");
}
