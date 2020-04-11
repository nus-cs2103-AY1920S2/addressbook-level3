package seedu.recipe.model.goal;

import java.util.Objects;

/**
 * A container to track number of cooked meals for each goal.
 */
public class GoalCount {

    private final Goal goal;
    private int count;

    public GoalCount(Goal goal, int count) {
        this.goal = goal;
        this.count = count;
    }

    public Goal getGoal() {
        return this.goal;
    }

    public int getCount() {
        return this.count;
    }

    public void incrementCount() {
        this.count++;
    }

    @Override
    public int hashCode() {
        return Objects.hash(goal, count);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalCount // instanceof handles nulls
                && goal.goalName.equals(((GoalCount) other).goal.goalName)
                && count == (((GoalCount) other).count)); // state check
    }

    @Override
    public String toString() {
        return "Goal: " + goal + ", Count: " + count;
    }
}
