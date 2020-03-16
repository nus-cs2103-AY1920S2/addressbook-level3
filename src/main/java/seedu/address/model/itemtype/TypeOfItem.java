package seedu.address.model.itemtype;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.order.TransactionID;

/**
 * Represents the type of item in the order book.
 * Guarantees: immutable; item is valid as declared in {@link #isValidItemName(String)}}
 */

public class TypeOfItem {

    public static final String MESSAGE_CONSTRAINTS =
            "Type should not be Blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String itemType;

    /**
     * Constructs a {@code Name}.
     *
     * @param itemType A valid monetary value.
     */
    public TypeOfItem(String itemType) {
        requireNonNull(itemType);
        checkArgument(isValidItemName(itemType), MESSAGE_CONSTRAINTS);
        this.itemType = itemType;
    }

    /**
     * Returns true if a given string is a valid transaction ID.
     */
    public static boolean isValidItemName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return itemType.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof TransactionID // instanceof handles nulls
                && itemType.equals(((TypeOfItem) obj).itemType)); // state check
    }

    @Override
    public String toString() {
        return itemType;
    }
}
