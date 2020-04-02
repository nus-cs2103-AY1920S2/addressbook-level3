package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a sort order on entries.
 * Guarantees: immutable; is valid as declared in {@link #isValidCategory(String)}
 */
public class SortBy {
    public static final String MESSAGE_CONSTRAINTS = "Order can only be cal/c or time/t.";
    private final String value;

    /**
     * Constructs an {@code SortBy}.
     *
     * @param order A valid sort order.
     */
    public SortBy(String order) {
        requireNonNull(order);
        checkArgument(isValidCategory(order), MESSAGE_CONSTRAINTS);
        value = ("cal".equals(order) || "c".equals(order)) ? "calorie" : "time";
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidCategory(String test) {
        return "cal".equals(test) || "c".equals(test) || "time".equals(test) || "t".equals(test);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortBy // instanceof handles nulls
                && value.equals(((SortBy) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
