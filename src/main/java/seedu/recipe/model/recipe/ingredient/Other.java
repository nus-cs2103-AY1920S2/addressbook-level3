package seedu.recipe.model.recipe.ingredient;

/**
 * Represents Other Ingredients in the recipe book. These ingredients include spices, liquids and condiments.
 */
public class Other extends Ingredient {

    public Other(String name, Quantity quantity) {
        super(name, quantity);
    }

    /**
     * Overloaded Other constructor for the purpose of filtering by this ingredient's name.
     */
    public Other(String name) {
        super(name);
    }

}
