package seedu.address.model.goal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Goal in the recipe book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGoalName(String)}
 */
public class Goal {

    public static final String MESSAGE_CONSTRAINTS = "Goals names should contain only alphabetical letters or spaces";
    public static final String VALIDATION_REGEX = "^[ A-Za-z]+$+";

    public final String goalName;

    /**
     * Constructs a {@code Goal}.
     *
     * @param goalName A valid goal name.
     */
    public Goal(String goalName) {
        requireNonNull(goalName);
        checkArgument(isValidGoalName(goalName), MESSAGE_CONSTRAINTS);
        this.goalName = goalName;
    }

    /**
     * Returns true if a given string is a valid goal name.
     */
    public static boolean isValidGoalName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Goal // instanceof handles nulls
                && goalName.toLowerCase().equals(((Goal) other).goalName.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return goalName.toLowerCase().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + goalName + ']';
    }

}
