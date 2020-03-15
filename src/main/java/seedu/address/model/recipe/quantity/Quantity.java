package seedu.address.model.recipe.quantity;

/**
 * A class to model quantities used in creating {@code Ingredient}s used in each recipe.
 */
public class Quantity {
    private float value;
    private Unit unit;

    Quantity(float value, String unitString) {
        this.value = value;
    }

    private Quantity(float value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public Quantity() {
    }
}
