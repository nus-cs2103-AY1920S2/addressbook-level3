package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's instructions in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstructions(String)}
 */
public class Instructions {

    public static final String MESSAGE_CONSTRAINTS = "Instructions can take any values, and it should not be blank";

    /*
     * The first character of the instructions must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Instructions}.
     *
     * @param instructions A valid instruction string.
     */
    public Instructions(String instructions) {
        requireNonNull(instructions);
        checkArgument(isValidInstructions(instructions), MESSAGE_CONSTRAINTS);
        value = instructions;
    }

    /**
     * Returns true if a given string is a valid instruction.
     */
    public static boolean isValidInstructions(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instructions // instanceof handles nulls
                    && value.equals(((Instructions) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
