package seedu.address.model.recipe.ingredient;

/**
 * Represents the standardized units that will be used in the recipe book.
 */

public enum Unit {
    LIQUID("ml"),
    SOLID("g"),
    COUNTABLE("");

    private final String label;

    Unit(final String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
