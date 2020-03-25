package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's step in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStep(String)}
 */
public class Step {

    public static final String MESSAGE_CONSTRAINTS = "Step should not be empty or blank";
    public static final String VALIDATION_REGEX = "(.|\\s)*\\S(.|\\s)*";

    public final String value;

    /**
     * Constructs an {@code Step}.
     *
     * @param step A valid step address.
     */
    public Step(String step) {
        requireNonNull(step);
        checkArgument(isValidStep(step), MESSAGE_CONSTRAINTS);
        this.value = step;
    }

    /**
     * Returns if a given string is a valid step.
     */
    public static boolean isValidStep(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Step // instanceof handles nulls
                && value.equals(((Step) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
