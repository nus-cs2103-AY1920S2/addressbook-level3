package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_HELP = new Prefix("h/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("-t/");
    public static final Prefix PREFIX_REMARK = new Prefix("i/");
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
    public static final Prefix PREFIX_ORGANIZATION = new Prefix("g/"); // g is for group (o was taken)
    public static final Prefix PREFIX_LINE_NUMBER = new Prefix("l/");
    public static final Prefix PREFIX_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_ESTHOURS = new Prefix("e/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_DEADLINE_LIST = new Prefix("-d");
    public static final Prefix PREFIX_ESTHOURS_LIST = new Prefix("-e");
    public static final Prefix PREFIX_NUM_DAYS = new Prefix("n/"); // Number of days to visualise
    public static final Prefix PREFIX_RESTAURANT = new Prefix("n/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_OPERATING_HOURS = new Prefix("o/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_CUISINE = new Prefix("c/");
    public static final Prefix PREFIX_VISITED = new Prefix("v/");
    public static final Prefix PREFIX_RECOMMENDED = new Prefix("r/");
    public static final Prefix PREFIX_GOOD = new Prefix("g/");
    public static final Prefix PREFIX_BAD = new Prefix("b/");
    public static final Prefix PREFIX_LINE_NUMBER_RECOMMENDED = new Prefix("rl/");
    public static final Prefix PREFIX_LINE_NUMBER_GOOD = new Prefix("gl/");
    public static final Prefix PREFIX_LINE_NUMBER_BAD = new Prefix("bl/");
    public static final Prefix PREFIX_EVENTTITLE = new Prefix("et/"); // event title
    public static final Prefix PREFIX_EVENTDATE = new Prefix("edt/"); // event date time
    public static final Prefix PREFIX_PLACE = new Prefix("ep/"); // event place
    public static final Prefix PREFIX_DURATION = new Prefix("ed/"); // event duration
}
