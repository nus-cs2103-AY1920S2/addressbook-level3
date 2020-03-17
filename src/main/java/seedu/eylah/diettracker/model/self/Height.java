package seedu.eylah.diettracker.model.self;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents the user's height in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS =
            "Height should only contain numbers, and it should not be blank. It is to be input in centimeters.";

    /*
     * The first character of the height must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]*";

    private final String heightString;
    private final Float heightFloat;

    /**
     * Constructs a {@code Height}.
     *
     * @param heightString A valid height.
     */
    public Height(String heightString) {
        requireNonNull(heightString);
        checkArgument(isValidHeight(heightString), MESSAGE_CONSTRAINTS);
        this.heightString = heightString;
        this.heightFloat = Float.parseFloat(this.heightString);
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public float getHeightFloat() {
        return this.heightFloat;
    }

    @Override
    public String toString() {
        return "" + heightString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && heightString.equals(((Height) other).heightString)); // state check
    }

    @Override
    public int hashCode() {
        return heightString.hashCode();
    }

}
