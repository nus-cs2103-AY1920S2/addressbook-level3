package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_INGREDIENTS = new Prefix("ing/");
    public static final Prefix PREFIX_INSTRUCTIONS = new Prefix("ins/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    // TODO: Delete these ASAP. Only here so they don't break tests.
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
}
