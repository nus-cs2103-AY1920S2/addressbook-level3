package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    public static final Prefix PREFIX_NOTES_PATH_TYPE = new Prefix("pt/");
    public static final Prefix PREFIX_NOTES_PATH = new Prefix("loc/");
    public static final Prefix PREFIX_NOTES_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_NOTES_FILE_NAME = new Prefix("name/");

    /* Module Prefixes*/
    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("m/");

    /* Task Prefixes*/
    public static final Prefix PREFIX_TASK_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_DEADLINE_DATE = new Prefix("by/");
    public static final Prefix PREFIX_TASK_CAT = new Prefix("cat/");
    public static final Prefix PREFIX_TASK_OPERATION = new Prefix("op/");
    public static final Prefix PREFIX_TASK_INDEX = new Prefix("index/");
    public static final Prefix PREFIX_MODULETASK_TIMING = new Prefix("date/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("pri/");
    public static final Prefix PREFIX_SORTING = new Prefix("by/");

    /* Diary Prefixes*/
    public static final Prefix PREFIX_ENTRY_CONTENT = new Prefix("ec/");
    public static final Prefix PREFIX_ENTRY_ID = new Prefix("id/");
    public static final Prefix PREFIX_WEATHER = new Prefix("w/");
    public static final Prefix PREFIX_MOOD = new Prefix("m/");
    public static final Prefix PREFIX_DIARY_DATE = new Prefix("date/");
}
