package seedu.address.model.step;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a cooking step in a recipe
 * Guarantees: immutable; is valid as declared in {@link #isValidStep(String)}
 */
public class Step {

    public static final String MESSAGE_CONSTRAINTS =
            "Step should only contain sentences, and it should not be blank";

    /*
     * The first character of the step must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{Graph} ]*";

    public final String stepDescription;

    /**
     * Constructs a {@code Step}.
     *
     * @param step A valid step.
     */
    public Step(String step) {
        requireNonNull(step);
        checkArgument(isValidStep(step), MESSAGE_CONSTRAINTS);
        stepDescription = step;
    }

    /**
     * Returns true if a given string is a valid step.
     */
    public static boolean isValidStep(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getStepDescription() {
        return stepDescription;
    }

    @Override
    public String toString() {
        return stepDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Step // instanceof handles nulls
                && stepDescription.equals(((Step) other).stepDescription)); // state check
    }

    @Override
    public int hashCode() {
        return stepDescription.hashCode();
    }
}
