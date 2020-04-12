package seedu.zerotoone.logic.statistics;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.util.List;

import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.ui.util.DateViewUtil;

/**
 * The type Total workout time data point.
 */
public class TotalWorkoutTimeDataPoint extends DataPoint {

    /**
     * Instantiates a new Total workout time data point.
     */
    public TotalWorkoutTimeDataPoint() {
        super("Total time spent in workout", "0 workouts");
    }

    @Override
    public void calculate(List<CompletedWorkout> completedWorkouts) {
        requireNonNull(completedWorkouts);

        Duration totalTime = completedWorkouts.stream().map(
            workout -> Duration.between(workout.getStartTime(), workout.getEndTime()))
            .reduce(Duration.ZERO, Duration::plus);

        result = format(totalTime);
    }

    /**
     * Format string.
     *
     * @param totalTime the total time
     * @return the string
     */
    public String format(Duration totalTime) {
        return DateViewUtil.getPrettyDuration(totalTime);
    }
}
