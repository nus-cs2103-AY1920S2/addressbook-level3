package seedu.zerotoone.logic.statistics;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.ui.util.DateViewUtil;

/**
 * The type Average workout time per day.
 * This class helps to calculate the average time the user spends in his workout a day
 */
public class AverageWorkoutTimePerDay extends DataPoint {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Instantiates a new Average workout time per day.
     *
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     */
    public AverageWorkoutTimePerDay(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super("Average workout time per day", "0");
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public void calculate(List<CompletedWorkout> completedWorkouts) {
        requireNonNull(completedWorkouts);

        Duration totalWorkoutTime = completedWorkouts.stream().map(
            workout -> Duration.between(workout.getStartTime(), workout.getEndTime()))
            .reduce(Duration.ZERO, Duration::plus);

        long numberOfDays = Duration.between(startDateTime, endDateTime).toDays() + 1;

        Duration averageTimePerSession = calculateAverageTimePerSession(totalWorkoutTime, numberOfDays);

        result = format(averageTimePerSession);
    }

    /**
     * @param totalWorkoutDuration
     * @param numberOfDays
     * @return
     */
    private Duration calculateAverageTimePerSession(Duration totalWorkoutDuration, long numberOfDays) {
        return totalWorkoutDuration.dividedBy(numberOfDays);
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
