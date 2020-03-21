package seedu.address.model.recipe.ingredient;

/**
 * Represents a Quantity in an Ingredient.
 */
public class Quantity {

    public static final String MESSAGE_CONSTRAINTS = "The available units of measurement are g, ml, tbsp, tsp and cup.";
    private double magnitude;
    private Unit unit;

    public Quantity(double magnitude, Unit unit) {
        this.magnitude = magnitude;
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override
    public String toString() {
        return "" + magnitude + unit;
    }
}
