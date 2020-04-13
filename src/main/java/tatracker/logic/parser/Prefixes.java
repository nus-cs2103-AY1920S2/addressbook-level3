//@@author potatocombat

package tatracker.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class Prefixes {

    public static final String DELIMITER = "/";

    /* Placeholders */
    public static final Prefix INDEX = new Prefix("", "INDEX");
    public static final Prefix TAB_NAME = new Prefix("", "TAB_NAME");
    public static final Prefix RATE = new Prefix("", "RATE");

    /* Session definitions */
    public static final Prefix START_TIME = new Prefix("s/", "START_TIME");
    public static final Prefix END_TIME = new Prefix("e/", "END_TIME");
    public static final Prefix DATE = new Prefix("d/", "DATE");
    public static final Prefix RECUR = new Prefix("w/", "RECUR");
    public static final Prefix SESSION_TYPE = new Prefix("t/", "SESSION_TYPE");
    public static final Prefix NOTES = new Prefix("n/", "NOTES");

    /* Module definitions */
    public static final Prefix MODULE = new Prefix("m/", "MODULE");
    public static final Prefix MODULE_ID = new Prefix("", "MODULE_ID");
    public static final Prefix MODULE_NAME = new Prefix("n/", "MODULE_NAME");
    public static final Prefix MODULE_NEW_NAME = new Prefix("n/", "NEW_NAME");


    /* Group definitions */
    public static final Prefix GROUP = new Prefix("g/", "GROUP");
    public static final Prefix NEWGROUP = new Prefix("ng/", "NEW_GROUP");
    public static final Prefix TYPE = new Prefix("t/", "GROUP_TYPE");
    public static final Prefix NEWTYPE = new Prefix("nt/", "NEW_TYPE");

    /* Student definitions */
    public static final Prefix MATRIC = new Prefix("id/", "MATRIC");
    public static final Prefix NAME = new Prefix("n/", "NAME");
    public static final Prefix PHONE = new Prefix("p/", "PHONE");
    public static final Prefix EMAIL = new Prefix("e/", "EMAIL");
    public static final Prefix RATING = new Prefix("r/", "RATING");
    public static final Prefix TAG = new Prefix("t/", "TAG");

    /* Action definitions */
    public static final Prefix SORT_TYPE = new Prefix("t/", "SORT_TYPE");
}
