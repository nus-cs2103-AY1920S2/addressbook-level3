package seedu.zerotoone.logic.statistics;

import java.util.List;

import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * The type Data point.
 */
public abstract class DataPoint {
    /**
     * The Label name.
     */
    protected String labelName;
    /**
     * The Result.
     */
    protected String result;

    /**
     * Instantiates a new Data point.
     *
     * @param labelName     the label name
     * @param defaultResult the default result
     */
    public DataPoint(String labelName, String defaultResult) {
        this.labelName = labelName;
        this.result = defaultResult;
    }

    /**
     * Gets label name.
     *
     * @return the label name
     */
    public String getLabelName() {
        return labelName;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * Calculate.
     *
     * @param completedWorkouts the completed workouts
     */
    public abstract void calculate(List<CompletedWorkout> completedWorkouts);
}
