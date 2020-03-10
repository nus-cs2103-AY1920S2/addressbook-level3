package seedu.address.model.ingredient;

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
