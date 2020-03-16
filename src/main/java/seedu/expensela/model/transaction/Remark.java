package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Transaction's remark which is optional.
 * Guarantees: immutable.
 */
public class Remark {

    public final String value;

    public static final String VALIDATION_REGEX;

    public static final String MESSAGE_CONSTRAINTS =
            "Remarks should only contain alphanumeric characters, spaces, \":+-()'\", may or may not be blank";


    static {
        VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}:+-()' ]*";
    }

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        assert value != null;

        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

