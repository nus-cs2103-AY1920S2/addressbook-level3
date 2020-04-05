package seedu.address.model.parcel.itemtype;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of item in the order book.
 * Guarantees: immutable; item is valid as declared in {@link #isValidItemType(String)}}
 */

public class TypeOfItem {

    public static final String MESSAGE_CONSTRAINTS =
            "Type Of Item should be alphanumeric";

    /**
     * The first character of the type of item must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String itemType;

    /**
     * Constructs a {@code Name}.
     *
     * @param itemType A valid monetary value.
     */
    public TypeOfItem(String itemType) {
        requireNonNull(itemType);
        checkArgument(isValidItemType(itemType), MESSAGE_CONSTRAINTS);
        this.itemType = itemType;
    }

    /**
     * Returns true if a given string is a valid Item.
     */
    public static boolean isValidItemType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return itemType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof TypeOfItem // instanceof handles nulls
                && itemType.equals(((TypeOfItem) obj).itemType)); // state check
    }

    @Override
    public String toString() {
        return itemType;
    }
}
