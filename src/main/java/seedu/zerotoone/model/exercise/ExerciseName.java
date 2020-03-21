package seedu.zerotoone.model.exercise;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.AppUtil.checkArgument;

/**
 * Represents a Exercise's Name in the exercise list.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class ExerciseName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the exercise name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ExerciseName(String name) {
        requireNonNull(name);
        checkArgument(isValidExerciseName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidExerciseName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExerciseName // instanceof handles nulls
                && fullName.equals(((ExerciseName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
