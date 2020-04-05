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
    public static final Prefix PREFIX_NOTES_OPERATION = new Prefix("ops/");
    public static final Prefix PREFIX_NOTES_PATH = new Prefix("loc/");

    public static final Prefix PREFIX_GRADE = new Prefix("g/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("m/");

    public static final Prefix PREFIX_TASK_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_DEADLINE_DATE = new Prefix("by/");
    public static final Prefix PREFIX_TASK_CAT = new Prefix("cat/");
    public static final Prefix PREFIX_TASK_OPERATION = new Prefix("op/");
    public static final Prefix PREFIX_TASK_INDEX = new Prefix("index/");
    public static final Prefix PREFIX_MODULETASK_TIMING = new Prefix("date/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("pri/");

    /* Diary Prefixes*/
    public static final Prefix PREFIX_ENTRY_CONTENT = new Prefix("ec/");
    public static final Prefix PREFIX_ENTRY_ID = new Prefix("id/");


}
