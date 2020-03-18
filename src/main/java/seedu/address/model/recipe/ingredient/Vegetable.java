package seedu.address.model.recipe.ingredient;

/**
 * Represents a Vegetable Ingredient in the recipe book.
 */
public class Vegetable extends Ingredient {

    public Vegetable(String name, double quantity) {
        super(name, quantity);
        super.unit = Unit.SOLID;
        super.ingredientType = IngredientType.VEGETABLE;
    }
}
