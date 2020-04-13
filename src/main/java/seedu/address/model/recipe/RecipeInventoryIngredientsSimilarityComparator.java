package seedu.address.model.recipe;

import java.util.Comparator;

import seedu.address.model.ReadOnlyInventory;

/**
 * A comparator for {@code Recipe}, which compares recipes by their similarity to an inventory's ingredients.
 */
public class RecipeInventoryIngredientsSimilarityComparator implements Comparator<Recipe> {
    private static final int NO_INGREDIENTS = 0;
    private static final double ZERO_SIMILARITY = 0;
    private static final double DEFAULT_SIMILARITY = 0.5;

    public final ReadOnlyInventory inventory;

    public RecipeInventoryIngredientsSimilarityComparator(ReadOnlyInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Compares recipes by their similarity to an inventory's ingredients.
     */
    @Override
    public int compare(Recipe o1, Recipe o2) {
        return Double.compare(calculateSimilarity(o2, inventory), calculateSimilarity(o1, inventory));
    }

    /**
     * Returns the proportion of recipe ingredients that the inventory contains.
     * @param recipe the specified recipe
     * @param inventory the specified inventory
     * @return a double value between 0 and 1 (inclusive)
     */
    public static double calculateSimilarity(Recipe recipe, ReadOnlyInventory inventory) {
        if (recipe.getIngredients().size() == NO_INGREDIENTS) {
            return ZERO_SIMILARITY;
        }

        return recipe.getIngredients().stream()
                .map(recipeIngredient -> inventory.getIngredientList().stream()
                    .filter(inventoryIngredient -> inventoryIngredient.isCompatibleWith(recipeIngredient))
                    .findFirst()
                    .map(inventoryIngredient -> inventoryIngredient.asProportionOf(recipeIngredient))
                    .orElseGet(() -> (inventory.getIngredientList().stream().filter(inventoryIngredient
                        -> inventoryIngredient.isSameIngredient(recipeIngredient)).count() > NO_INGREDIENTS)
                            ? DEFAULT_SIMILARITY : ZERO_SIMILARITY))
                .reduce(ZERO_SIMILARITY, (x, y) -> x + y, (x, y) -> x + y) / recipe.getIngredients().size();
    }
}
