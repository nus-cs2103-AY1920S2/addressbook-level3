package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for customer */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for product */
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_COSTPRICE = new Prefix("cp/");
    public static final Prefix PREFIX_PRICE = new Prefix("pr/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_SALES = new Prefix("s/");
    public static final Prefix PREFIX_THRESHOLD = new Prefix("t/");

    /* Prefix definitions for transactions */
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");
    public static final Prefix PREFIX_PRODUCT = new Prefix("p/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_MONEY = new Prefix("m/");
    public static final Prefix PREFIX_TRANS_DESCRIPTION = new Prefix("d/");

    /* Prefix definitions for statistics */
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/");
    public static final Prefix PREFIX_END_DATE = new Prefix("ed/");
}
