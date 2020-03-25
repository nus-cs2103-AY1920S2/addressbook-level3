package seedu.recipe.model.recipe.ingredient;

/**
 * Represents a Fruit Ingredient in the recipe book.
 */
public class Fruit extends Ingredient {

    public Fruit(String name, Quantity quantity) {
        super(name, quantity);
    }

    /**
     * Overloaded Fruit constructor for the purpose of filtering by fruit name.
     */
    public Fruit(String name) {
        super(name);
    }

}
