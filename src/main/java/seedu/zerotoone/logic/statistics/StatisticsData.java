package seedu.zerotoone.logic.statistics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * The type Statistics data.
 */
public class StatisticsData {

    private List<DataPoint> dataPoints;
    private LocalDateTime startRange;
    private LocalDateTime endRange;
    private List<CompletedWorkout> workouts;

    /**
     * Instantiates a new Statistics data.
     */
    public StatisticsData() {
        dataPoints = new ArrayList<>();
    }

    /**
     * Instantiates a new Statistics data.
     *
     * @param workouts   the workouts
     * @param startRange the start range
     * @param endRange   the end range
     * @param dataPoints the data points
     */
    public StatisticsData(List<CompletedWorkout> workouts, LocalDateTime startRange, LocalDateTime endRange,
                          List<DataPoint> dataPoints) {
        this.workouts = workouts;
        this.startRange = startRange;
        this.endRange = endRange;
        this.dataPoints = dataPoints;
    }

    /**
     * Gets data points.
     *
     * @return the data points
     */
    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    /**
     * Gets workouts.
     *
     * @return the workouts
     */
    public List<CompletedWorkout> getWorkouts() {
        return workouts;
    }

    /**
     * Sets workouts.
     *
     * @param workouts the workouts
     */
    public void setWorkouts(List<CompletedWorkout> workouts) {
        this.workouts = workouts;
    }

    /**
     * Gets start range.
     *
     * @return the start range
     */
    public LocalDateTime getStartRange() {
        return startRange;
    }

    /**
     * Gets end range.
     *
     * @return the end range
     */
    public LocalDateTime getEndRange() {
        return endRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatisticsData that = (StatisticsData) o;
        return Objects.equals(startRange, that.startRange)
            && Objects.equals(endRange, that.endRange)
            && Objects.equals(workouts, that.workouts)
            && dataPoints.equals(((StatisticsData) o).dataPoints);
    }
}
