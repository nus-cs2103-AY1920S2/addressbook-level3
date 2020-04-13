package seedu.address.model.recipe;

import static seedu.address.model.recipe.RecipeInventoryIngredientsSimilarityComparator.calculateSimilarity;

import java.util.function.Predicate;

import seedu.address.model.ReadOnlyInventory;

/**
 * Tests that a {@code Recipe}'s {@code Ingredient} matches any of the ingredients in the inventory.
 */
public class RecipeContainsInventoryIngredientsPredicate implements Predicate<Recipe> {
    private final ReadOnlyInventory inventory;

    public RecipeContainsInventoryIngredientsPredicate(ReadOnlyInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean test(Recipe recipe) {
        return calculateSimilarity(recipe, inventory) > 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RecipeContainsInventoryIngredientsPredicate // instanceof handles nulls
            && inventory.equals(((RecipeContainsInventoryIngredientsPredicate) other).inventory)); // state check
    }
}
