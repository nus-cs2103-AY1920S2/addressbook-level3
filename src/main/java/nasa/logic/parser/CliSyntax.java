package nasa.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for NASA */
    public static final Prefix PREFIX_MODULE = new Prefix("m/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TO_DATE = new Prefix("td/");
    public static final Prefix PREFIX_FROM_DATE = new Prefix("fd/");
    public static final Prefix PREFIX_ACTIVITY_NAME = new Prefix("a/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_MODULE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NOTE = new Prefix("n/");
    public static final Prefix PREFIX_REPEAT = new Prefix("r/");
}
