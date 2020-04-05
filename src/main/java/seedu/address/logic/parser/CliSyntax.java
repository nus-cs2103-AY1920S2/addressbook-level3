package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_YEAR = new Prefix("y/");
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_TASK = new Prefix("t/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_NEW_TASK = new Prefix("nt/");

    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_COURSE_NAME = new Prefix("c/");
    public static final Prefix PREFIX_FOCUS_AREA = new Prefix("f/");

}
