package seedu.address.logic.commands.cookbook;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECIPE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STEP_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * Adds a recipe into the cookbook.
 */
public class CookbookAddCommand extends CookbookCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " "
            + COMMAND_WORD + " " + "recipe"
            + ": adds a new recipe to the cookbook.\n\n"
            + "Parameters: "
            + PREFIX_RECIPE_NAME + "NAME "
            + PREFIX_RECIPE_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_INGREDIENT_NAME + "INGREDIENT_NAME] "
            + "[" + PREFIX_INGREDIENT_QUANTITY + "INGREDIENT_QUANTITY] "
            + "[" + PREFIX_STEP_DESCRIPTION + "STEP_DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]\n\n"
            + "Examples:\n"
            + "- " + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD + " "
            + PREFIX_RECIPE_NAME + "Bacon Carbonara "
            + PREFIX_RECIPE_DESCRIPTION + "Best cream pasta made in Italy.\n"
            + "- " + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD + " "
            + PREFIX_RECIPE_NAME + "Chicken Ham Sandwich "
            + PREFIX_RECIPE_DESCRIPTION + "Juicy ham sandwiched between crispy toasted bread. "
            + PREFIX_INGREDIENT_NAME + "Chicken Ham " + PREFIX_INGREDIENT_QUANTITY + "1 "
            + PREFIX_INGREDIENT_NAME + "White Bread " + PREFIX_INGREDIENT_QUANTITY + "2 slices "
            + PREFIX_STEP_DESCRIPTION + "Toast the bread "
            + PREFIX_STEP_DESCRIPTION + "Grill the ham "
            + PREFIX_TAG + "Sandwich " + PREFIX_TAG + "Easy";

    public static final String MESSAGE_SUCCESS = "New recipe (index %2$d) added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECIPE = "This recipe (%1$s) already exists in the cookbook.";

    private final Recipe toAdd;

    /**
     * Creates a CookbookAddCommand to add a new {@code recipe} to the cookbook
     */
    public CookbookAddCommand(Recipe recipe) {
        requireNonNull(recipe);

        toAdd = recipe;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCookbookRecipe(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_RECIPE,
                    toAdd.getName().fullRecipeName));
        }

        model.addCookbookRecipe(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName().fullRecipeName,
                model.getCookbook().getRecipeList().indexOf(toAdd) + 1));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookbookAddCommand // instanceof handles nulls
                && toAdd.equals(((CookbookAddCommand) other).toAdd));
    }
}
