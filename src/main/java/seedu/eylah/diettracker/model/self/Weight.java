package seedu.eylah.diettracker.model.self;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's weight in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeight(String)}
 */
public class Weight {

    public static final String MESSAGE_CONSTRAINTS =
            "Weight should only be a floating point number, and it should not be blank. It is to be input in "
                    + "kilograms.";

    /*
     * The first character of the weight must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[-+]?\\d*\\.?\\d*$*";

    private String weightString;
    private Float weightFloat;

    /**
     * Constructs a {@code Height}.
     *
     * @param weight A valid weight in float.
     */
    public Weight(float weight) {
        requireNonNull(weight);
        String heightString = Float.valueOf(weight).toString();
        checkArgument(isValidWeight(heightString), MESSAGE_CONSTRAINTS);
        this.weightString = heightString;
        this.weightFloat = weight;
    }


    /**
     * Constructs a {@code Weight}.
     *
     * @param weightString A valid weight in string.
     */
    public Weight(String weightString) {
        requireNonNull(weightString);
        checkArgument(isValidWeight(weightString), MESSAGE_CONSTRAINTS);
        this.weightString = weightString;
        this.weightFloat = Float.parseFloat(this.weightString);
    }

    public Weight() {
    }

    public Weight(String weightString, float weightFloat) {
        this.weightString = weightString;
        this.weightFloat = weightFloat;
    }

    /**
     * Returns true if a given string is a valid weight.
     */
    public static boolean isValidWeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public float getWeightFloat() {
        return this.weightFloat;
    }

    public boolean isZero() {
        return weightFloat.equals(Float.valueOf(0));
    }

    public boolean isNotZero() {
        return !weightFloat.equals(Float.valueOf(0));
    }

    @Override
    public String toString() {
        return weightString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Weight // instanceof handles nulls
                && weightString.equals(((Weight) other).weightString)); // state check
    }

    @Override
    public int hashCode() {
        return weightString.hashCode();
    }

}
