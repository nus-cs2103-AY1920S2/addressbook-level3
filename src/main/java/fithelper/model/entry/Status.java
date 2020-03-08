package fithelper.model.entry;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Entry's status in FitHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Status can only be TRUE or FALSE.";

    public final boolean value;

    /**
     * Constructs an {@code Status}.
     *
     * @param status A valid status of entry.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status.equals("TRUE");
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.equals("TRUE") || test.equals("FALSE");
    }

    @Override
    public String toString() {
        return value ? "Done" : "Not done";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && value == ((Status) other).value); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
