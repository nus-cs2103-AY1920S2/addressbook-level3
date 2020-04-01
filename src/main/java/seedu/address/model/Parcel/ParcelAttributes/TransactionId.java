package seedu.address.model.Parcel.ParcelAttributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's Transaction ID in the Order List.
 * Guarantees: immutable; is valid as declared in {@link #isValidTid(String)}
 */
public class TransactionId {

    public static final String MESSAGE_CONSTRAINTS =
            "Transaction ID should not be empty.";
    /**
     * Check if first character of given input is whitespace.
     * Otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String tid;

    /**
     * Constructs an {@code TransactionId}.
     *
     * @param tid A valid transaction ID.
     */
    public TransactionId(String tid) {
        requireNonNull(tid);
        checkArgument(isValidTid(tid), MESSAGE_CONSTRAINTS);
        this.tid = tid;
    }

    /**
     * Returns true if a given string is a valid transaction ID.
     */
    public static boolean isValidTid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return tid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof TransactionId // instanceof handles nulls
                && tid.equals(((TransactionId) obj).tid)); // state check
    }

    @Override
    public String toString() {
        return tid;
    }
}
