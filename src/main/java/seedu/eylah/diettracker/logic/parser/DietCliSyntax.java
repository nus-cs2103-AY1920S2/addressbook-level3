package seedu.eylah.diettracker.logic.parser;

import seedu.eylah.commons.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class DietCliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("-n");
    public static final Prefix PREFIX_CALORIES = new Prefix("-c");
    public static final Prefix PREFIX_INDEX = new Prefix("-i");
    public static final Prefix PREFIX_TAG = new Prefix("-t");
    public static final Prefix PREFIX_HEIGHT = new Prefix("-h");
    public static final Prefix PREFIX_WEIGHT = new Prefix("-w");
    public static final Prefix PREFIX_ALL = new Prefix("-a");
    public static final Prefix PREFIX_DAYS = new Prefix("-d");
    public static final Prefix PREFIX_LOSS = new Prefix("-l");
    public static final Prefix PREFIX_GAIN = new Prefix("-g");
    public static final Prefix PREFIX_MAINTAIN = new Prefix("-m");
}
