package seedu.recipe.model.recipe.ingredient;

/**
 * Represents a Vegetable Ingredient in the recipe book.
 */
public class Vegetable extends Ingredient {

    public Vegetable(String name, Quantity quantity) {
        super(name, quantity);
    }

    /**
     * Overloaded Vegetable constructor for the purpose of filtering by vegetable name.
     */
    public Vegetable(String name) {
        super(name);
    }

    /**
     * Method to indicate type of ingredient.
     * Only exists for ingredients included in calculation for goals.
     * @return Main Ingredient type of vegetable
     */
    @Override
    public MainIngredientType getMainIngredientType() {
        return MainIngredientType.VEGETABLE;
    }
}
