package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_SEARCH_KEYWORD = new Prefix("k/");

    public static final Prefix PREFIX_RECIPE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_RECIPE_DESCRIPTION = new Prefix("d/");

    public static final Prefix PREFIX_INGREDIENT_NAME = new Prefix("i/");
    public static final Prefix PREFIX_INGREDIENT_QUANTITY = new Prefix("q/");

    public static final Prefix PREFIX_STEP_INDEX = new Prefix("x/");
    public static final Prefix PREFIX_STEP_DESCRIPTION = new Prefix("s/");

    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
