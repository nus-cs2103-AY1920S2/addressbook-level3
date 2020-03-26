package cookbuddy.model.recipe.attribute;

import static cookbuddy.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents the Difficulty of the recipe in the recipe book.
 * Guarantees: immutable; difficulty is valid as declared in {@link #isValidDifficulty(int)}
 */
public class Difficulty {

    public static final String MESSAGE_CONSTRAINTS = "Difficulty should be an integer >= 0 and <= 5";

    public final int difficulty;

    /**
     * Constructs a {@code Difficulty}.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(int difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_CONSTRAINTS);
        this.difficulty = difficulty;
    }


    /**
     * Returns true if a given string is a valid difficulty.
     */
    public static boolean isValidDifficulty(int test) {
        return (test >= 0 && test <= 5);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && difficulty == (((Difficulty) other).difficulty)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(difficulty).hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.valueOf(difficulty);
    }
}
