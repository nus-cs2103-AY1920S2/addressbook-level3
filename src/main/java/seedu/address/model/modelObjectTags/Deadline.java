package seedu.address.model.modelObjectTags;

import seedu.address.model.modelObjectTags.personTagValidations.DateValidator;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Assignment's deadline in the address book. Guarantees: immutable; is valid as declared in
 */


public class Deadline {

    public final String value;

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should be in the format YYYY-MM-DD, and it should not be blank";

    public static boolean isValidDeadline(String test) {
        return DateValidator.validateDate(test);
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline a valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        value = deadline;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
