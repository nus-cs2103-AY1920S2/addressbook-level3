package seedu.address.model.recipe.ingredient;

public class Quantity {

    private double magnitude;
    private Unit unit;
    public static final String MESSAGE_CONSTRAINTS = "The available units of measurement are g, ml, tbsp, tsp and cup.";

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