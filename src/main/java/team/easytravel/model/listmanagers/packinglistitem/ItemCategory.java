package team.easytravel.model.listmanagers.packinglistitem;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.util.AppUtil.checkArgument;

/**
 * The type Item category.
 */
public class ItemCategory {
    /**
     * The constant MESSAGE_CONSTRAINTS.
     */
    public static final String MESSAGE_CONSTRAINTS = "Category must be made up of alphanumeric words that is "
            + "less than 30 characters long, and it will always be in lowercase";

    /**
     * The constant VALIDATION_REGEX.
     */
    // todo update regex to match constraints
    public static final String VALIDATION_REGEX = "(?!\\s*$)[A-Za-z0-9\\s]{1,30}";

    public final String value;

    /**
     * Instantiates a new Item category.
     *
     * @param category the category
     */
    public ItemCategory(String category) {
        requireNonNull(category);
        checkArgument(isValidItemCategory(category), MESSAGE_CONSTRAINTS);
        value = category.toLowerCase();
    }

    /**
     * Is valid item category boolean.
     *
     * @param test the test
     * @return the boolean
     */
    public static boolean isValidItemCategory(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemCategory // instanceof handles nulls
                && value.equals(((ItemCategory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
