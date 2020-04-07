package seedu.recipe.model.achievement;

import static java.util.Objects.requireNonNull;

/**
 * Streak helps user track how often they're eating healthy
 */
public class Streak {

    private final int CurrStreak;
    private final int HighStreak = 0;

    public Streak(int streak) {
        requireNonNull(streak);
        this.CurrStreak = streak;
    }

    @Override
    public String toString() {
        return Integer.toString(this.CurrStreak);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Streak // instanceof handles nulls
                && CurrStreak == (((Streak) other).CurrStreak)) // state check
                && HighStreak == (((Streak) other).HighStreak);
    }

    @Override
    public int hashCode() {
        return Integer.toString(CurrStreak).hashCode();
    }

    public int getCurrStreak() {
        return CurrStreak;
    }

    public int getHighStreak() {
        return HighStreak;
    }
}
