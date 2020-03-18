
package seedu.address.model.recipe.ingredient;

/**
 * Represents a Grain Ingredient in the recipe book.
 */
public class Grain extends Ingredient {

    public Grain(String name, double quantity) {
        super(name, quantity);
        super.unit = Unit.SOLID;
        super.ingredientType = IngredientType.GRAIN;
    }
}
