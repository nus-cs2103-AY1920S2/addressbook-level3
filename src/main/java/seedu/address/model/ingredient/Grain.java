package seedu.address.model.ingredient;

/**
 * Represents a Grain Ingredient in the recipe book.
 */
public class Grain extends Ingredient {

    public Grain(double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
