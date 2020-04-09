package csdev.couponstash.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_ARCHIVE = new Prefix("a/"); // for ListCommand only
    public static final Prefix PREFIX_CONDITION = new Prefix("c/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_EXPIRY_DATE = new Prefix("e/");
    public static final Prefix PREFIX_LIMIT = new Prefix("l/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PROMO_CODE = new Prefix("p/");
    public static final Prefix PREFIX_REMIND = new Prefix("r/");
    public static final Prefix PREFIX_SAVINGS = new Prefix("s/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_USAGE = new Prefix("u/");
    public static final Prefix PREFIX_MONEY_SYMBOL = new Prefix("ms/");
    public static final Prefix PREFIX_MONTH_YEAR = new Prefix("my/");
    public static final Prefix PREFIX_START_DATE = new Prefix("sd/");


}
