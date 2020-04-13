package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("/code");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("/desc");

    public static final Prefix PREFIX_TYPE = new Prefix("/type");
    public static final Prefix PREFIX_VENUE = new Prefix("/venue");
    public static final Prefix PREFIX_NEXT = new Prefix("/next");

    public static final Prefix PREFIX_ON = new Prefix("/on");
    public static final Prefix PREFIX_AT = new Prefix("/at");
    public static final Prefix PREFIX_DATE = new Prefix("/date");
    public static final Prefix PREFIX_MONTH = new Prefix("/month");
    public static final Prefix PREFIX_YEAR = new Prefix("/year");
    public static final Prefix PREFIX_TASK_ID = new Prefix("/id");

    public static final Prefix PREFIX_NAME = new Prefix("/name");
    public static final Prefix PREFIX_PHONE = new Prefix("/phone");
    public static final Prefix PREFIX_EMAIL = new Prefix("/email");
    public static final Prefix PREFIX_OFFICE = new Prefix("/office");

    public static final Prefix PREFIX_WEEK = new Prefix("/week");
}
