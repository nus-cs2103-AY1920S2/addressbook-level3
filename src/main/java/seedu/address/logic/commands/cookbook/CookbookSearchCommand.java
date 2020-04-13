package seedu.address.logic.commands.cookbook;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Searches for a recipe in the cookbook based on inventory/keyword/tag.
 */
public abstract class CookbookSearchCommand extends CookbookCommand {

    public static final String COMMAND_WORD = "search";
    public static final String SEARCH_INVENTORY_COMMAND = "inventory";
    public static final String SEARCH_TAG_COMMAND = "tag";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": searches the cookbook by keyword(s), tag(s) or your inventory.\n\n"
            + "Parameters for searching the cookbook by keyword(s): "
            + RECIPE_KEYWORD + " " + PREFIX_SEARCH_KEYWORD + "KEYWORD\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " "
            + RECIPE_KEYWORD + " " + PREFIX_SEARCH_KEYWORD + "carbonara\n\n"
            + "Parameters for searching the cookbook by tag(s): " + SEARCH_TAG_COMMAND + " " + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " "
            + SEARCH_TAG_COMMAND + " " + PREFIX_TAG + "breakfast\n\n"
            + "Parameters for searching the cookbook by the ingredients in your inventory: "
            + SEARCH_INVENTORY_COMMAND + "\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + SEARCH_INVENTORY_COMMAND;
}
