package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

/**
 * Streak helps user track how often they're eating healthy
 */
public class Streak {

    private final int currStreak;
    private final int highStreak = 0;

    public Streak(int streak) {
        requireNonNull(streak);
        this.currStreak = streak;
    }

    @Override
    public String toString() {
        return Integer.toString(this.currStreak);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Streak // instanceof handles nulls
                && currStreak == (((Streak) other).currStreak)) // state check
                && highStreak == (((Streak) other).highStreak);
    }

    @Override
    public int hashCode() {
        return Integer.toString(currStreak).hashCode();
    }

    public int getCurrStreak() {
        return currStreak;
    }

    public int getHighStreak() {
        return highStreak;
    }
}
