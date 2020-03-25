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
}
