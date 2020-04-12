package seedu.address.model.modelObjectTags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's id number in the address book. Guarantees: immutable; is valid as declared
 * in {@link #isValidId(String)}
 */
public class ID {

    public static final String MESSAGE_CONSTRAINTS =
            "ID numbers should only contain numbers, and it should be at least 1 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code ID}.
     *
     * @param id A valid Id number.
     */
    public ID(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    public ID() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid id number.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX) || test.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ID // instanceof handles nulls
                && value.equals(((ID) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
