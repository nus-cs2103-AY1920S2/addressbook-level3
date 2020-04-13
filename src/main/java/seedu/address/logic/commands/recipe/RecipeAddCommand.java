package seedu.address.logic.commands.recipe;

import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Adds a step/ingredient/tag to a recipe inside the cookbook
 */
public abstract class RecipeAddCommand extends RecipeCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " INDEX " + COMMAND_WORD
            + ": adds an ingredient, a step, or a tag to a recipe with the given INDEX.\n"
            + "\nParameters for adding an ingredient: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + COMMAND_WORD + " "
            + INGREDIENT_KEYWORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "12\n"
            + "\nParameters for adding a step: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_STEP_INDEX + "STEP_INDEX "
            + PREFIX_STEP_DESCRIPTION + "STEP_DESCRIPTION\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + COMMAND_WORD + " "
            + STEP_KEYWORD + " "
            + PREFIX_STEP_INDEX + "1 "
            + PREFIX_STEP_DESCRIPTION + "Add potatoes and water to a large pot, and bring to a boil.\n"
            + "\nParameters for adding a tag: "
            + "INDEX (must be a valid positive integer) "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_CATEGORY + " 1 "
            + COMMAND_WORD + " "
            + TAG_KEYWORD + " "
            + PREFIX_TAG + "Pasta";
}
