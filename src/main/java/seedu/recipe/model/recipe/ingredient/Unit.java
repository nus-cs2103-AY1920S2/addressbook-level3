package seedu.recipe.model.recipe.ingredient;

/**
 * Represents the standardized units that will be used in the recipe book.
 */
public enum Unit {
    MILLILITER("ml"),
    GRAM("g"),
    TEASPOON("tsp"),
    TABLESPOON("tbsp"),
    CUP("cup");

    private final String unit;

    Unit(final String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return unit;
    }
}

