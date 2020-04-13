package seedu.address.logic.commands.cookbook;

import seedu.address.logic.commands.Command;

/**
 * Represents a cookbook command with hidden internal logic and the ability to be executed.
 */
public abstract class CookbookCommand extends Command {

    public static final String COMMAND_CATEGORY = "cookbook";
    public static final String RECIPE_KEYWORD = "recipe";

}
