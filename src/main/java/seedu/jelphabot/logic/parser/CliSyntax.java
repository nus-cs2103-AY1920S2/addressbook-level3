package seedu.jelphabot.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("m/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMIND_DAY = new Prefix("days/");
    public static final Prefix PREFIX_REMIND_HOUR = new Prefix("hours/");

}
