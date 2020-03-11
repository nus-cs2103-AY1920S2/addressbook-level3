package seedu.address.model.ingredient;

/**
 * Represents a Vegetable Ingredient in the recipe book.
 */
public class Vegetable extends Ingredient {

    public Vegetable(double quantity, String name) {
        super(quantity, name);
        super.unit = Unit.SOLID;
    }
}
