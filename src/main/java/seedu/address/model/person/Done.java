package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 */
public class Done {

    public static final String MESSAGE_CONSTRAINTS =
            "Done should be a simple Boolean ";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String DONE = "Y";
    public static final String NOT_DONE = "N";

    public static final String VALIDATION_REGEX = "Y|N";

    public final Boolean isDone;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Done(String isDone) {
        requireNonNull(isDone);
        checkArgument(isValidDone(isDone), MESSAGE_CONSTRAINTS);
        this.isDone = isDone.equals(DONE);
    }

    public Done() { // TODO maybe don't need, but i don't get why we should use a PersonBuilder to inject default values
        this.isDone = false;
    }

    /** Returns true if a given string is a valid name. */
    public static boolean isValidDone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return isDone ? DONE : NOT_DONE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Done // instanceof handles nulls
                        && isDone == (((Done) other).isDone)); // state check
    }

    @Override
    public int hashCode() {
        return isDone.hashCode();
    }
}
