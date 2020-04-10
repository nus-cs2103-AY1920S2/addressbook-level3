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

    /**
     * Method to indicate type of ingredient.
     * Only exists for ingredients included in calculation for goals.
     * @return Main Ingredient type of fruit
     */
    @Override
    public MainIngredientType getMainIngredientType() {
        return MainIngredientType.FRUIT;
    }

}
