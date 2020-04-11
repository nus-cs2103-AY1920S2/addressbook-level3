package seedu.eylah.expensesplitter.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item's name in ExpenseSplitter of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ItemName {

    public static final String MESSAGE_CONSTRAINTS =
            "ItemName should only contain alphanumeric characters and spaces, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String itemName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ItemName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.itemName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test whether a name is valid or not.
     */
    public static boolean isValidName(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return itemName;
    }


    /**
     * Checks if two Items have the same ItemName.
     *
     * @param other ItemName to be checked against.
     * @return True if two of the Items have the same ItemName, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemName // instanceof handles nulls
                && itemName.equals(((ItemName) other).itemName)); // state check
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

    public String getItemName() {
        return this.itemName;
    }

}
