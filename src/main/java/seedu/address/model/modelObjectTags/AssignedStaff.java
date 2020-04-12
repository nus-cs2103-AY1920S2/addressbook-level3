package seedu.address.model.modelObjectTags;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's assignedStaff in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidAssignedStaff(String)}
 */
public class AssignedStaff {

    public static final String MESSAGE_CONSTRAINTS =
            "AssignedStaffs should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullAssignedStaff;

    /**
     * Constructs a {@code AssignedStaff}.
     *
     * @param assignedStaff A valid assignedStaff.
     */
    public AssignedStaff(String assignedStaff) {
        requireNonNull(assignedStaff);
        checkArgument(isValidAssignedStaff(assignedStaff), MESSAGE_CONSTRAINTS);
        fullAssignedStaff = assignedStaff;
    }

    /**
     * Returns true if a given string is a valid assignedStaff.
     */
    public static boolean isValidAssignedStaff(String test) {
        return test.matches(VALIDATION_REGEX) || test.equals("");
    }


    @Override
    public String toString() {
        return fullAssignedStaff;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignedStaff // instanceof handles nulls
                && fullAssignedStaff.equals(((AssignedStaff) other).fullAssignedStaff)); // state check
    }

    @Override
    public int hashCode() {
        return fullAssignedStaff.hashCode();
    }

}
