package seedu.address.model.recipe.ingredient;

/**
 * Represents a Protein Ingredient in the recipe book.
 */

public class Protein extends Ingredient {

    public Protein (String name, double quantity) {
        super(name, quantity);
        super.unit = Unit.SOLID;
        super.ingredientType = IngredientType.PROTEIN;
    }
}
