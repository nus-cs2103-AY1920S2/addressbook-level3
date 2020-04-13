package seedu.nova.model.category;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.util.AppUtil.checkArgument;

/**
 * Represents a Category in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS = "Category should be either classmate or teammate only";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String categoryName;

    /**
     * Constructs a {@code Category}.
     *
     * @param categoryName A valid tag name.
     */
    public Category(String categoryName) {
        requireNonNull(categoryName);
        checkArgument(isValidTagName(categoryName), MESSAGE_CONSTRAINTS);
        this.categoryName = categoryName.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        if (test.matches(VALIDATION_REGEX)
                && (test.toLowerCase().equals("classmate") || test.toLowerCase().equals("teammate"))) {
            return true;
        } else {
            return false;
        }
        //return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && categoryName.equals(((Category) other).categoryName)); // state check
    }

    @Override
    public int hashCode() {
        return categoryName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return categoryName;
    }

}
