package fithelper.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntaxUtil {

    /* Prefix definitions */
    public static final Prefix PREFIX_TYPE = new Prefix("x/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_CALORIE = new Prefix("c/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_KEYWORD = new Prefix("k/");
    public static final Prefix PREFIX_SORT_BY = new Prefix("by/");
    public static final Prefix PREFIX_SORT_ORDER = new Prefix("o/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_MODE = new Prefix("m/");
    public static final Prefix PREFIX_DIARY_CONTENT = new Prefix("dc/");
    public static final Prefix PREFIX_DURATION = new Prefix("dr/");
    public static final Prefix PREFIX_SHOW = new Prefix("sh/");

    public static final Prefix PREFIX_ATTRIBUTE = new Prefix("attr/");
    public static final Prefix PREFIX_VALUE = new Prefix("v/");

    /* Flag definitions */
    public static final Flag FLAG_FORCE = new Flag("-f");
}
