package seedu.address.model.recipe.attribute;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's instruction in the recipe book. Guarantees: immutable;
 * is valid as declared in {@link #isValidInstructions(String)}
 */
public class Instruction {

    public static final String MESSAGE_CONSTRAINTS = "Each instruction should be "
            + "a non-blank string.";

    private final String instructionString;

    /**
     * Constructs an {@code Instruction}.
     *
     * @param instructionString a valid, non-null {@link String} that represents a
     *                          single line of instructions in a recipe.
     */
    public Instruction(String instructionString) {
        requireNonNull(instructionString);
        checkArgument(isValidInstruction(instructionString), MESSAGE_CONSTRAINTS);
        this.instructionString = instructionString;
    }

    /**
     * Returns true if {@code instructionString} is a valid instruction, as specified by
     * {@link String#isBlank()}.
     */
    public static boolean isValidInstruction(String instructionString) {
        return !instructionString.isBlank();
    }

    @Override
    public String toString() {
        return this.instructionString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && instructionString.equals(((Instruction) other).instructionString)); // state check
    }

    @Override
    public int hashCode() {
        return this.instructionString.hashCode();
    }

}
