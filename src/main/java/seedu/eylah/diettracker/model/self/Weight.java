package seedu.eylah.diettracker.model.self;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's weight in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {

    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only contain numbers, and it should not be blank. It is to be input in kilograms.";

    /*
     * The first character of the weight must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String weight;
    public final Float wt;

    /**
     * Constructs a {@code Weight}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        checkArgument(isValidWeight(weight), MESSAGE_CONSTRAINTS);
        this.weight = weight;
        this.wt = Float.parseFloat(this.weight);
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return weight;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && weight.equals(((Weight) other).weight)); // state check
    }

    @Override
    public int hashCode() {
        return weight.hashCode();
    }

}
