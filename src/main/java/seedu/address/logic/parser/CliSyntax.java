package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("name/");
    public static final Prefix PREFIX_MATRIC_NUMBER = new Prefix("matric/");
    public static final Prefix PREFIX_EMAIL = new Prefix("email/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");

    public static final Prefix PREFIX_PLACE = new Prefix("place/");
    public static final Prefix PREFIX_STUDENT = new Prefix("student/");
    public static final Prefix PREFIX_INDEX = new Prefix("INDEX (must be a positive integer)");


    /* Consult definitions */
    public static final Prefix PREFIX_CONSULT_BEGIN_DATE_TIME = new Prefix("beginDateTime/");
    public static final Prefix PREFIX_CONSULT_END_DATE_TIME = new Prefix("endDateTime/");

    /* Tutorial definitions */
    public static final Prefix PREFIX_TUTORIAL_NAME = new Prefix("tutorialName/");
    public static final Prefix PREFIX_TUTORIAL_WEEKDAY = new Prefix("day/");
    public static final Prefix PREFIX_TUTORIAL_BEGIN_TIME = new Prefix("beginTime/");
    public static final Prefix PREFIX_TUTORIAL_END_TIME = new Prefix("endTime/");
    public static final Prefix PREFIX_TUTORIAL_INDEX = new Prefix("tutorialIndex/");
    public static final Prefix PREFIX_TUTORIAL_WEEK = new Prefix("week/");
    public static final Prefix PREFIX_CSV_FILEPATH = new Prefix("csvFilePath/");

    /* Module definitions */
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("modCode/");
    public static final Prefix PREFIX_MODULE_NAME = new Prefix("modName/");
    public static final Prefix PREFIX_MODULE_NOTE = new Prefix("modNote/");
    public static final Prefix PREFIX_MODULE_LINK = new Prefix("modLink/");
    public static final Prefix PREFIX_MODULE_LINK_NAME = new Prefix("modLinkName/");

    /* Reminder definitions */
    public static final Prefix PREFIX_REMINDER_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_REMINDER_DATE = new Prefix("date/");
    public static final Prefix PREFIX_REMINDER_TIME = new Prefix("time/");
    public static final Prefix PREFIX_REMINDER_DAY = new Prefix("day/");
    public static final Prefix PREFIX_REMINDER_HOUR = new Prefix("hour/");
    public static final Prefix PREFIX_REMINDER_MINUTE = new Prefix("minute/");
}
