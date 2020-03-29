package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TID = new Prefix("tid/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_COD = new Prefix("cod/");
    public static final Prefix PREFIX_DELIVERY_TIMESTAMP = new Prefix("dts/");
    public static final Prefix PREFIX_RETURN_TIMESTAMP = new Prefix("rts/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");
    public static final Prefix PREFIX_WAREHOUSE = new Prefix("w/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");

    /* Flag definitions */
    public static final Flag FLAG_FORCE_CLEAR = new Flag("-f");
    public static final Flag FLAG_ORDER_BOOK = new Flag("-o");
    public static final Flag FLAG_RETURN_BOOK = new Flag("-r");
}
