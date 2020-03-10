package seedu.address.model.ingredient;

/**
 * Represents a Protein Ingredient in the recipe book.
 */

public class Protein extends Ingredient {

    public Protein (double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
