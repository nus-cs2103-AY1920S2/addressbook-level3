package seedu.zerotoone.logic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.zerotoone.logic.statistics.AverageWorkoutTimePerDay;
import seedu.zerotoone.logic.statistics.DataPoint;
import seedu.zerotoone.logic.statistics.StatisticsData;
import seedu.zerotoone.logic.statistics.TotalWorkoutTimeDataPoint;
import seedu.zerotoone.logic.statistics.WorkoutCountDataPoint;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * The type Statistics.
 */
public class Statistics {
    /**
     * Generate statistics data.
     *
     * @param workouts       the workouts
     * @param startDateRange the start date range
     * @param endDateRange   the end date range
     * @return the statistics data
     */
    public static StatisticsData generate(List<CompletedWorkout> workouts, Optional<LocalDateTime> startDateRange,
                                          Optional<LocalDateTime> endDateRange) {

        LocalDateTime startDateTime = startDateRange.orElseGet(() -> Statistics.getEarliestWorkoutStartTime(workouts));
        LocalDateTime endDateTime = endDateRange.orElseGet(() -> Statistics.getLatestEndDate(workouts));

        workouts.removeIf(workout -> workout.getStartTime().isBefore(startDateTime));
        workouts.removeIf(workout -> workout.getEndTime().isAfter(endDateTime));

        Integer workoutCount = workouts.size();

        if (workoutCount.equals(0)) {
            return new StatisticsData();
        }
        List<DataPoint> dataPoints = new ArrayList<>();

        WorkoutCountDataPoint workoutCountDataPoint = new WorkoutCountDataPoint();
        dataPoints.add(workoutCountDataPoint);
        TotalWorkoutTimeDataPoint totalWorkoutDuration = new TotalWorkoutTimeDataPoint();
        dataPoints.add(totalWorkoutDuration);
        AverageWorkoutTimePerDay averageWorkoutTimePerDay = new AverageWorkoutTimePerDay(startDateTime, endDateTime);
        dataPoints.add(averageWorkoutTimePerDay);

        dataPoints.forEach(point -> point.calculate(workouts));

        return new StatisticsData(workouts, startDateTime, endDateTime, dataPoints);
    }

    /**
     * Calculate average time spent on workout per session rounded to the nearest second.
     *
     * @param totalWorkoutDuration
     * @param numberOfDays
     * @return timeSpent
     */
    private static Duration calculateAverageTimePerSession(Duration totalWorkoutDuration, long numberOfDays) {
        if (numberOfDays == 0) {
            return Duration.ZERO;
        }

        // Round to nearest second
        return totalWorkoutDuration.dividedBy(numberOfDays);
    }

    /**
     * Gets earliest workout start time.
     *
     * @param workouts the workouts
     * @return the earliest workout start time
     */
    public static LocalDateTime getEarliestWorkoutStartTime(List<CompletedWorkout> workouts) {
        LocalDateTime earliest = LocalDateTime.MAX;

        for (CompletedWorkout workout : workouts) {
            if (workout.getStartTime().isBefore(earliest)) {
                earliest = workout.getStartTime();
            }
        }

        return earliest;
    }

    /**
     * Gets latest end date.
     *
     * @param workouts the workouts
     * @return the latest end date
     */
    public static LocalDateTime getLatestEndDate(List<CompletedWorkout> workouts) {
        LocalDateTime latest = LocalDateTime.MIN;

        for (CompletedWorkout workout : workouts) {
            if (workout.getEndTime().isAfter(latest)) {
                latest = workout.getEndTime();
            }
        }

        return latest;
    }
}
