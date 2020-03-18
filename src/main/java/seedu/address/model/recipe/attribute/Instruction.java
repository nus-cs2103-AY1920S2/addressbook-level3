package seedu.address.model.recipe.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's instruction in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstructions(String)}
 */
public class Instruction {

    public static final String MESSAGE_CONSTRAINTS = "Instruction can take any values, and it should not be blank";

    /*
     * The first character of the instructions must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Instruction}.
     *
     * @param instruction A valid instruction string.
     */
    public Instruction(String instruction) {
        requireNonNull(instruction);
        checkArgument(isValidInstructions(instruction), MESSAGE_CONSTRAINTS);
        value = instruction;
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
                || (other instanceof Instruction // instanceof handles nulls
                    && value.equals(((Instruction) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
