package seedu.address.model.ingredient;

/**
 * Represents an Ingredient in the recipe book.
 */

public abstract class Ingredient {
    protected String name;
    protected double quantity;
    protected Unit unit;

    public Ingredient(double quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return quantity + unit.toString() + " " + name;
    }
}
