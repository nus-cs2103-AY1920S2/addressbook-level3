package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeInventoryIngredientsSimilarityComparator;

/**
 * Removes all the ingredients of the selected recipe from the inventory
 */

public class InventoryCookCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "cook";
    public static final String RECIPE_KEYWORD = "recipe";
    public static final String MESSAGE_SUCCESS = "All ingredients from recipe %1$s removed from inventory";
    public static final String MESSAGE_MISSING_INGREDIENT =
            "Your inventory does not contain all the ingredients of recipe %1$s.";
    public static final String MESSAGE_INSUFFICIENT_QUANTITY =
            "Your inventory contains insufficient quantities of the ingredients in recipe %1$s.";
    public static final String MESSAGE_NO_INGREDIENT_IN_RECIPE =
            "Recipe %1$s does not require any ingredients to cook.";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD
            + ": removes all ingredients of a specified recipe from your inventory.\n"
            + "\nParameters: "
            + "INDEX (must be a valid positive integer)\n"
            + "Example: "
            + COMMAND_CATEGORY + " " + COMMAND_WORD + " " + RECIPE_KEYWORD + " 1";

    private final Index targetIndex;

    /**
     * Creates a InventoryCookRecipeCommand to remove all the {@code Ingredient} of a recipe from the inventory
     */
    public InventoryCookCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Returns true if all the ingredients in recipe exist in the inventory
     */
    private boolean hasInventoryIngredients(ReadOnlyInventory inventory, Recipe recipe) {
        requireNonNull(inventory);
        requireNonNull(recipe);
        ObservableList<Ingredient> inventoryList = inventory.getIngredientList();
        ObservableList<Ingredient> recipeIngredients = recipe.getIngredients();

        return recipeIngredients.stream().map(recipeIngredient -> inventoryList
                .stream().map(inventoryIngredient -> inventoryIngredient.isCompatibleWith(recipeIngredient))
                .reduce(false, (x, y) -> x || y, (x , y) -> x || y))
                .allMatch(isCompatible -> isCompatible.equals(true));
    }

    /**
     * Returns true if all ingredients's quantity in recipe is lesser than or equal to the ingredient's quantity
     * in inventory
     * The ingredients in the recipe must exist in the inventory.
     */
    private boolean hasSufficientInventoryIngredients(ReadOnlyInventory inventory, Recipe recipe) {
        requireNonNull(inventory);
        requireNonNull(recipe);
        return RecipeInventoryIngredientsSimilarityComparator.calculateSimilarity(recipe, inventory) == 1;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Recipe> recipeList = model.getFilteredCookbookRecipeList();

        if (targetIndex.getZeroBased() >= recipeList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX,
                    InventoryCookCommand.MESSAGE_USAGE));
        }

        Recipe selectedRecipe = recipeList.get(targetIndex.getZeroBased());

        if (selectedRecipe.getIngredients().size() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_INGREDIENT_IN_RECIPE,
                    selectedRecipe.getName().toString()));
        }

        if (!hasInventoryIngredients(model.getInventory(), selectedRecipe)) {
            throw new CommandException(String.format(MESSAGE_MISSING_INGREDIENT,
                    selectedRecipe.getName().toString()));
        } else if (!hasSufficientInventoryIngredients(model.getInventory(), selectedRecipe)) {
            throw new CommandException(String.format(MESSAGE_INSUFFICIENT_QUANTITY,
                    selectedRecipe.getName().toString()));
        }

        selectedRecipe.getIngredients().stream().forEach(model::removeInventoryIngredient);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryCookCommand // instanceof handles nulls
                && targetIndex.equals(((InventoryCookCommand) other).targetIndex));
    }
}
