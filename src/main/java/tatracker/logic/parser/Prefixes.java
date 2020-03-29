package tatracker.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class Prefixes {

    /* Session definitions */
    public static final Prefix START_TIME = new Prefix("s/");
    public static final Prefix END_TIME = new Prefix("e/");
    public static final Prefix DATE = new Prefix("d/");
    public static final Prefix RECUR = new Prefix("-r");
    public static final Prefix SESSION_TYPE = new Prefix("t/");

    /* Module definitions */
    public static final Prefix MODULE = new Prefix("m/");
    public static final Prefix NOTES = new Prefix("n/");

    /* Group definitions */
    public static final Prefix GROUP = new Prefix("g/");
    public static final Prefix TYPE = new Prefix("t/");
    public static final Prefix NEWTYPE = new Prefix("nt/");
    public static final Prefix NEWGROUP = new Prefix("ng/");

    /* Student definitions */
    public static final Prefix MATRIC = new Prefix("id/");
    public static final Prefix NAME = new Prefix("n/");
    public static final Prefix PHONE = new Prefix("p/");
    public static final Prefix EMAIL = new Prefix("e/");
    public static final Prefix RATING = new Prefix("r/");
    public static final Prefix TAG = new Prefix("t/");
}
