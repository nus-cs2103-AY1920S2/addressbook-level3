package seedu.address.logic.commands.cart;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;

/**
 * Adds all the ingredients from the indexed recipe to cart
 */
public class CartAddRecipeIngredientCommand extends CartAddCommand {

    public static final String COMMAND_WORD = "recipe";
    public static final String MESSAGE_SUCCESS = "Ingredients from recipe %1$s added.";
    public static final String MESSAGE_USAGE = "\n"
            + COMMAND_CATEGORY + " "
            + CartAddCommand.COMMAND_WORD + " "
            + COMMAND_WORD
            + ": adds all the ingredients from a recipe to your cart.\n"
            + "Parameters: \n"
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_CATEGORY + " " + COMMAND_WORD + " "
            + PREFIX_INGREDIENT_NAME + "Eggs "
            + PREFIX_INGREDIENT_QUANTITY + "10\n";

    private final Index recipeIndex;

    /**
     * Creates a CartAddIngredientCommand to add the specified {@code Ingredient} to the cart
     */
    public CartAddRecipeIngredientCommand(Index recipeIndex) {
        this.recipeIndex = recipeIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (recipeIndex.getZeroBased() >= model.getCookbook().getRecipeList().size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    MESSAGE_USAGE));
        }

        Recipe targetedRecipe = model.getCookbook().getRecipeList().get(recipeIndex.getZeroBased());
        targetedRecipe.getIngredients().stream().forEach(model::addCartIngredient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetedRecipe.getName()));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CartAddRecipeIngredientCommand // instanceof handles nulls
                && (recipeIndex.equals(((CartAddRecipeIngredientCommand) other).recipeIndex)));
    }
}
