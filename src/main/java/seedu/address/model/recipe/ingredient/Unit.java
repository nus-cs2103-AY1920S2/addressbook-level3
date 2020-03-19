package seedu.address.model.recipe.ingredient;

/**
 * Represents the standardized units that will be used in the recipe book.
 */

public enum Unit {
    LIQUID("ml"),
    SOLID("g"),
    COUNTABLE("");

    private final String unit;

    Unit(final String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}
